package com.eq02.metalurgica.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.eq02.metalurgica.IntegrationTest;
import com.eq02.metalurgica.domain.Sale;
import com.eq02.metalurgica.repository.SaleRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link SaleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SaleResourceIT {

    private static final Integer DEFAULT_SALE_CODE = 1;
    private static final Integer UPDATED_SALE_CODE = 2;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_TOTAL = 1D;
    private static final Double UPDATED_TOTAL = 2D;

    private static final String ENTITY_API_URL = "/api/sales";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSaleMockMvc;

    private Sale sale;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sale createEntity(EntityManager em) {
        Sale sale = new Sale().saleCode(DEFAULT_SALE_CODE).date(DEFAULT_DATE).total(DEFAULT_TOTAL);
        return sale;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sale createUpdatedEntity(EntityManager em) {
        Sale sale = new Sale().saleCode(UPDATED_SALE_CODE).date(UPDATED_DATE).total(UPDATED_TOTAL);
        return sale;
    }

    @BeforeEach
    public void initTest() {
        sale = createEntity(em);
    }

    @Test
    @Transactional
    void createSale() throws Exception {
        int databaseSizeBeforeCreate = saleRepository.findAll().size();
        // Create the Sale
        restSaleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sale)))
            .andExpect(status().isCreated());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeCreate + 1);
        Sale testSale = saleList.get(saleList.size() - 1);
        assertThat(testSale.getSaleCode()).isEqualTo(DEFAULT_SALE_CODE);
        assertThat(testSale.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSale.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    void createSaleWithExistingId() throws Exception {
        // Create the Sale with an existing ID
        sale.setId(1L);

        int databaseSizeBeforeCreate = saleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sale)))
            .andExpect(status().isBadRequest());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSales() throws Exception {
        // Initialize the database
        saleRepository.saveAndFlush(sale);

        // Get all the saleList
        restSaleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sale.getId().intValue())))
            .andExpect(jsonPath("$.[*].saleCode").value(hasItem(DEFAULT_SALE_CODE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())));
    }

    @Test
    @Transactional
    void getSale() throws Exception {
        // Initialize the database
        saleRepository.saveAndFlush(sale);

        // Get the sale
        restSaleMockMvc
            .perform(get(ENTITY_API_URL_ID, sale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sale.getId().intValue()))
            .andExpect(jsonPath("$.saleCode").value(DEFAULT_SALE_CODE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingSale() throws Exception {
        // Get the sale
        restSaleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSale() throws Exception {
        // Initialize the database
        saleRepository.saveAndFlush(sale);

        int databaseSizeBeforeUpdate = saleRepository.findAll().size();

        // Update the sale
        Sale updatedSale = saleRepository.findById(sale.getId()).get();
        // Disconnect from session so that the updates on updatedSale are not directly saved in db
        em.detach(updatedSale);
        updatedSale.saleCode(UPDATED_SALE_CODE).date(UPDATED_DATE).total(UPDATED_TOTAL);

        restSaleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSale.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSale))
            )
            .andExpect(status().isOk());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeUpdate);
        Sale testSale = saleList.get(saleList.size() - 1);
        assertThat(testSale.getSaleCode()).isEqualTo(UPDATED_SALE_CODE);
        assertThat(testSale.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSale.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void putNonExistingSale() throws Exception {
        int databaseSizeBeforeUpdate = saleRepository.findAll().size();
        sale.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sale.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sale))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSale() throws Exception {
        int databaseSizeBeforeUpdate = saleRepository.findAll().size();
        sale.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sale))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSale() throws Exception {
        int databaseSizeBeforeUpdate = saleRepository.findAll().size();
        sale.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sale)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSaleWithPatch() throws Exception {
        // Initialize the database
        saleRepository.saveAndFlush(sale);

        int databaseSizeBeforeUpdate = saleRepository.findAll().size();

        // Update the sale using partial update
        Sale partialUpdatedSale = new Sale();
        partialUpdatedSale.setId(sale.getId());

        partialUpdatedSale.date(UPDATED_DATE).total(UPDATED_TOTAL);

        restSaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSale.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSale))
            )
            .andExpect(status().isOk());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeUpdate);
        Sale testSale = saleList.get(saleList.size() - 1);
        assertThat(testSale.getSaleCode()).isEqualTo(DEFAULT_SALE_CODE);
        assertThat(testSale.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSale.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void fullUpdateSaleWithPatch() throws Exception {
        // Initialize the database
        saleRepository.saveAndFlush(sale);

        int databaseSizeBeforeUpdate = saleRepository.findAll().size();

        // Update the sale using partial update
        Sale partialUpdatedSale = new Sale();
        partialUpdatedSale.setId(sale.getId());

        partialUpdatedSale.saleCode(UPDATED_SALE_CODE).date(UPDATED_DATE).total(UPDATED_TOTAL);

        restSaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSale.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSale))
            )
            .andExpect(status().isOk());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeUpdate);
        Sale testSale = saleList.get(saleList.size() - 1);
        assertThat(testSale.getSaleCode()).isEqualTo(UPDATED_SALE_CODE);
        assertThat(testSale.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSale.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void patchNonExistingSale() throws Exception {
        int databaseSizeBeforeUpdate = saleRepository.findAll().size();
        sale.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sale.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sale))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSale() throws Exception {
        int databaseSizeBeforeUpdate = saleRepository.findAll().size();
        sale.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sale))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSale() throws Exception {
        int databaseSizeBeforeUpdate = saleRepository.findAll().size();
        sale.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sale)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSale() throws Exception {
        // Initialize the database
        saleRepository.saveAndFlush(sale);

        int databaseSizeBeforeDelete = saleRepository.findAll().size();

        // Delete the sale
        restSaleMockMvc
            .perform(delete(ENTITY_API_URL_ID, sale.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
