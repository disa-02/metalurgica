package com.eq02.metalurgica.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.eq02.metalurgica.IntegrationTest;
import com.eq02.metalurgica.domain.SalesPerson;
import com.eq02.metalurgica.repository.SalesPersonRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SalesPersonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SalesPersonResourceIT {

    private static final String ENTITY_API_URL = "/api/sales-people";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SalesPersonRepository salesPersonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalesPersonMockMvc;

    private SalesPerson salesPerson;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesPerson createEntity(EntityManager em) {
        SalesPerson salesPerson = new SalesPerson();
        return salesPerson;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesPerson createUpdatedEntity(EntityManager em) {
        SalesPerson salesPerson = new SalesPerson();
        return salesPerson;
    }

    @BeforeEach
    public void initTest() {
        salesPerson = createEntity(em);
    }

    @Test
    @Transactional
    void createSalesPerson() throws Exception {
        int databaseSizeBeforeCreate = salesPersonRepository.findAll().size();
        // Create the SalesPerson
        restSalesPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salesPerson)))
            .andExpect(status().isCreated());

        // Validate the SalesPerson in the database
        List<SalesPerson> salesPersonList = salesPersonRepository.findAll();
        assertThat(salesPersonList).hasSize(databaseSizeBeforeCreate + 1);
        SalesPerson testSalesPerson = salesPersonList.get(salesPersonList.size() - 1);
    }

    @Test
    @Transactional
    void createSalesPersonWithExistingId() throws Exception {
        // Create the SalesPerson with an existing ID
        salesPerson.setId(1L);

        int databaseSizeBeforeCreate = salesPersonRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salesPerson)))
            .andExpect(status().isBadRequest());

        // Validate the SalesPerson in the database
        List<SalesPerson> salesPersonList = salesPersonRepository.findAll();
        assertThat(salesPersonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSalesPeople() throws Exception {
        // Initialize the database
        salesPersonRepository.saveAndFlush(salesPerson);

        // Get all the salesPersonList
        restSalesPersonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesPerson.getId().intValue())));
    }

    @Test
    @Transactional
    void getSalesPerson() throws Exception {
        // Initialize the database
        salesPersonRepository.saveAndFlush(salesPerson);

        // Get the salesPerson
        restSalesPersonMockMvc
            .perform(get(ENTITY_API_URL_ID, salesPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(salesPerson.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingSalesPerson() throws Exception {
        // Get the salesPerson
        restSalesPersonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSalesPerson() throws Exception {
        // Initialize the database
        salesPersonRepository.saveAndFlush(salesPerson);

        int databaseSizeBeforeUpdate = salesPersonRepository.findAll().size();

        // Update the salesPerson
        SalesPerson updatedSalesPerson = salesPersonRepository.findById(salesPerson.getId()).get();
        // Disconnect from session so that the updates on updatedSalesPerson are not directly saved in db
        em.detach(updatedSalesPerson);

        restSalesPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSalesPerson.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSalesPerson))
            )
            .andExpect(status().isOk());

        // Validate the SalesPerson in the database
        List<SalesPerson> salesPersonList = salesPersonRepository.findAll();
        assertThat(salesPersonList).hasSize(databaseSizeBeforeUpdate);
        SalesPerson testSalesPerson = salesPersonList.get(salesPersonList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingSalesPerson() throws Exception {
        int databaseSizeBeforeUpdate = salesPersonRepository.findAll().size();
        salesPerson.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salesPerson.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salesPerson))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesPerson in the database
        List<SalesPerson> salesPersonList = salesPersonRepository.findAll();
        assertThat(salesPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSalesPerson() throws Exception {
        int databaseSizeBeforeUpdate = salesPersonRepository.findAll().size();
        salesPerson.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salesPerson))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesPerson in the database
        List<SalesPerson> salesPersonList = salesPersonRepository.findAll();
        assertThat(salesPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSalesPerson() throws Exception {
        int databaseSizeBeforeUpdate = salesPersonRepository.findAll().size();
        salesPerson.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesPersonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salesPerson)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalesPerson in the database
        List<SalesPerson> salesPersonList = salesPersonRepository.findAll();
        assertThat(salesPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSalesPersonWithPatch() throws Exception {
        // Initialize the database
        salesPersonRepository.saveAndFlush(salesPerson);

        int databaseSizeBeforeUpdate = salesPersonRepository.findAll().size();

        // Update the salesPerson using partial update
        SalesPerson partialUpdatedSalesPerson = new SalesPerson();
        partialUpdatedSalesPerson.setId(salesPerson.getId());

        restSalesPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSalesPerson))
            )
            .andExpect(status().isOk());

        // Validate the SalesPerson in the database
        List<SalesPerson> salesPersonList = salesPersonRepository.findAll();
        assertThat(salesPersonList).hasSize(databaseSizeBeforeUpdate);
        SalesPerson testSalesPerson = salesPersonList.get(salesPersonList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateSalesPersonWithPatch() throws Exception {
        // Initialize the database
        salesPersonRepository.saveAndFlush(salesPerson);

        int databaseSizeBeforeUpdate = salesPersonRepository.findAll().size();

        // Update the salesPerson using partial update
        SalesPerson partialUpdatedSalesPerson = new SalesPerson();
        partialUpdatedSalesPerson.setId(salesPerson.getId());

        restSalesPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSalesPerson))
            )
            .andExpect(status().isOk());

        // Validate the SalesPerson in the database
        List<SalesPerson> salesPersonList = salesPersonRepository.findAll();
        assertThat(salesPersonList).hasSize(databaseSizeBeforeUpdate);
        SalesPerson testSalesPerson = salesPersonList.get(salesPersonList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingSalesPerson() throws Exception {
        int databaseSizeBeforeUpdate = salesPersonRepository.findAll().size();
        salesPerson.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, salesPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salesPerson))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesPerson in the database
        List<SalesPerson> salesPersonList = salesPersonRepository.findAll();
        assertThat(salesPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSalesPerson() throws Exception {
        int databaseSizeBeforeUpdate = salesPersonRepository.findAll().size();
        salesPerson.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salesPerson))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesPerson in the database
        List<SalesPerson> salesPersonList = salesPersonRepository.findAll();
        assertThat(salesPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSalesPerson() throws Exception {
        int databaseSizeBeforeUpdate = salesPersonRepository.findAll().size();
        salesPerson.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesPersonMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(salesPerson))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalesPerson in the database
        List<SalesPerson> salesPersonList = salesPersonRepository.findAll();
        assertThat(salesPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSalesPerson() throws Exception {
        // Initialize the database
        salesPersonRepository.saveAndFlush(salesPerson);

        int databaseSizeBeforeDelete = salesPersonRepository.findAll().size();

        // Delete the salesPerson
        restSalesPersonMockMvc
            .perform(delete(ENTITY_API_URL_ID, salesPerson.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SalesPerson> salesPersonList = salesPersonRepository.findAll();
        assertThat(salesPersonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
