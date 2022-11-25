package com.eq02.metalurgica.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.eq02.metalurgica.IntegrationTest;
import com.eq02.metalurgica.domain.Roow;
import com.eq02.metalurgica.repository.RoowRepository;
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
 * Integration tests for the {@link RoowResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoowResourceIT {

    private static final Integer DEFAULT_AMOUNT_PRODUCT = 1;
    private static final Integer UPDATED_AMOUNT_PRODUCT = 2;

    private static final Double DEFAULT_SUB_TOTAL = 1D;
    private static final Double UPDATED_SUB_TOTAL = 2D;

    private static final String ENTITY_API_URL = "/api/roows";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RoowRepository roowRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoowMockMvc;

    private Roow roow;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Roow createEntity(EntityManager em) {
        Roow roow = new Roow().amountProduct(DEFAULT_AMOUNT_PRODUCT).subTotal(DEFAULT_SUB_TOTAL);
        return roow;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Roow createUpdatedEntity(EntityManager em) {
        Roow roow = new Roow().amountProduct(UPDATED_AMOUNT_PRODUCT).subTotal(UPDATED_SUB_TOTAL);
        return roow;
    }

    @BeforeEach
    public void initTest() {
        roow = createEntity(em);
    }

    @Test
    @Transactional
    void createRoow() throws Exception {
        int databaseSizeBeforeCreate = roowRepository.findAll().size();
        // Create the Roow
        restRoowMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roow)))
            .andExpect(status().isCreated());

        // Validate the Roow in the database
        List<Roow> roowList = roowRepository.findAll();
        assertThat(roowList).hasSize(databaseSizeBeforeCreate + 1);
        Roow testRoow = roowList.get(roowList.size() - 1);
        assertThat(testRoow.getAmountProduct()).isEqualTo(DEFAULT_AMOUNT_PRODUCT);
        assertThat(testRoow.getSubTotal()).isEqualTo(DEFAULT_SUB_TOTAL);
    }

    @Test
    @Transactional
    void createRoowWithExistingId() throws Exception {
        // Create the Roow with an existing ID
        roow.setId(1L);

        int databaseSizeBeforeCreate = roowRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoowMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roow)))
            .andExpect(status().isBadRequest());

        // Validate the Roow in the database
        List<Roow> roowList = roowRepository.findAll();
        assertThat(roowList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRoows() throws Exception {
        // Initialize the database
        roowRepository.saveAndFlush(roow);

        // Get all the roowList
        restRoowMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roow.getId().intValue())))
            .andExpect(jsonPath("$.[*].amountProduct").value(hasItem(DEFAULT_AMOUNT_PRODUCT)))
            .andExpect(jsonPath("$.[*].subTotal").value(hasItem(DEFAULT_SUB_TOTAL.doubleValue())));
    }

    @Test
    @Transactional
    void getRoow() throws Exception {
        // Initialize the database
        roowRepository.saveAndFlush(roow);

        // Get the roow
        restRoowMockMvc
            .perform(get(ENTITY_API_URL_ID, roow.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(roow.getId().intValue()))
            .andExpect(jsonPath("$.amountProduct").value(DEFAULT_AMOUNT_PRODUCT))
            .andExpect(jsonPath("$.subTotal").value(DEFAULT_SUB_TOTAL.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingRoow() throws Exception {
        // Get the roow
        restRoowMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRoow() throws Exception {
        // Initialize the database
        roowRepository.saveAndFlush(roow);

        int databaseSizeBeforeUpdate = roowRepository.findAll().size();

        // Update the roow
        Roow updatedRoow = roowRepository.findById(roow.getId()).get();
        // Disconnect from session so that the updates on updatedRoow are not directly saved in db
        em.detach(updatedRoow);
        updatedRoow.amountProduct(UPDATED_AMOUNT_PRODUCT).subTotal(UPDATED_SUB_TOTAL);

        restRoowMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRoow.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRoow))
            )
            .andExpect(status().isOk());

        // Validate the Roow in the database
        List<Roow> roowList = roowRepository.findAll();
        assertThat(roowList).hasSize(databaseSizeBeforeUpdate);
        Roow testRoow = roowList.get(roowList.size() - 1);
        assertThat(testRoow.getAmountProduct()).isEqualTo(UPDATED_AMOUNT_PRODUCT);
        assertThat(testRoow.getSubTotal()).isEqualTo(UPDATED_SUB_TOTAL);
    }

    @Test
    @Transactional
    void putNonExistingRoow() throws Exception {
        int databaseSizeBeforeUpdate = roowRepository.findAll().size();
        roow.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoowMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roow.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roow))
            )
            .andExpect(status().isBadRequest());

        // Validate the Roow in the database
        List<Roow> roowList = roowRepository.findAll();
        assertThat(roowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoow() throws Exception {
        int databaseSizeBeforeUpdate = roowRepository.findAll().size();
        roow.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoowMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roow))
            )
            .andExpect(status().isBadRequest());

        // Validate the Roow in the database
        List<Roow> roowList = roowRepository.findAll();
        assertThat(roowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoow() throws Exception {
        int databaseSizeBeforeUpdate = roowRepository.findAll().size();
        roow.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoowMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roow)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Roow in the database
        List<Roow> roowList = roowRepository.findAll();
        assertThat(roowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoowWithPatch() throws Exception {
        // Initialize the database
        roowRepository.saveAndFlush(roow);

        int databaseSizeBeforeUpdate = roowRepository.findAll().size();

        // Update the roow using partial update
        Roow partialUpdatedRoow = new Roow();
        partialUpdatedRoow.setId(roow.getId());

        partialUpdatedRoow.subTotal(UPDATED_SUB_TOTAL);

        restRoowMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoow.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoow))
            )
            .andExpect(status().isOk());

        // Validate the Roow in the database
        List<Roow> roowList = roowRepository.findAll();
        assertThat(roowList).hasSize(databaseSizeBeforeUpdate);
        Roow testRoow = roowList.get(roowList.size() - 1);
        assertThat(testRoow.getAmountProduct()).isEqualTo(DEFAULT_AMOUNT_PRODUCT);
        assertThat(testRoow.getSubTotal()).isEqualTo(UPDATED_SUB_TOTAL);
    }

    @Test
    @Transactional
    void fullUpdateRoowWithPatch() throws Exception {
        // Initialize the database
        roowRepository.saveAndFlush(roow);

        int databaseSizeBeforeUpdate = roowRepository.findAll().size();

        // Update the roow using partial update
        Roow partialUpdatedRoow = new Roow();
        partialUpdatedRoow.setId(roow.getId());

        partialUpdatedRoow.amountProduct(UPDATED_AMOUNT_PRODUCT).subTotal(UPDATED_SUB_TOTAL);

        restRoowMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoow.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoow))
            )
            .andExpect(status().isOk());

        // Validate the Roow in the database
        List<Roow> roowList = roowRepository.findAll();
        assertThat(roowList).hasSize(databaseSizeBeforeUpdate);
        Roow testRoow = roowList.get(roowList.size() - 1);
        assertThat(testRoow.getAmountProduct()).isEqualTo(UPDATED_AMOUNT_PRODUCT);
        assertThat(testRoow.getSubTotal()).isEqualTo(UPDATED_SUB_TOTAL);
    }

    @Test
    @Transactional
    void patchNonExistingRoow() throws Exception {
        int databaseSizeBeforeUpdate = roowRepository.findAll().size();
        roow.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoowMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, roow.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roow))
            )
            .andExpect(status().isBadRequest());

        // Validate the Roow in the database
        List<Roow> roowList = roowRepository.findAll();
        assertThat(roowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoow() throws Exception {
        int databaseSizeBeforeUpdate = roowRepository.findAll().size();
        roow.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoowMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roow))
            )
            .andExpect(status().isBadRequest());

        // Validate the Roow in the database
        List<Roow> roowList = roowRepository.findAll();
        assertThat(roowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoow() throws Exception {
        int databaseSizeBeforeUpdate = roowRepository.findAll().size();
        roow.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoowMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(roow)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Roow in the database
        List<Roow> roowList = roowRepository.findAll();
        assertThat(roowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoow() throws Exception {
        // Initialize the database
        roowRepository.saveAndFlush(roow);

        int databaseSizeBeforeDelete = roowRepository.findAll().size();

        // Delete the roow
        restRoowMockMvc
            .perform(delete(ENTITY_API_URL_ID, roow.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Roow> roowList = roowRepository.findAll();
        assertThat(roowList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
