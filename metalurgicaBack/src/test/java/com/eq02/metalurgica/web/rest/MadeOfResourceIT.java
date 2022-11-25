package com.eq02.metalurgica.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.eq02.metalurgica.IntegrationTest;
import com.eq02.metalurgica.domain.MadeOf;
import com.eq02.metalurgica.repository.MadeOfRepository;
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
 * Integration tests for the {@link MadeOfResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MadeOfResourceIT {

    private static final Double DEFAULT_AMOUNT_MATERIAL = 1D;
    private static final Double UPDATED_AMOUNT_MATERIAL = 2D;

    private static final String ENTITY_API_URL = "/api/madeoves";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MadeOfRepository madeOfRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMadeOfMockMvc;

    private MadeOf madeOf;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MadeOf createEntity(EntityManager em) {
        MadeOf madeOf = new MadeOf().amountMaterial(DEFAULT_AMOUNT_MATERIAL);
        return madeOf;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MadeOf createUpdatedEntity(EntityManager em) {
        MadeOf madeOf = new MadeOf().amountMaterial(UPDATED_AMOUNT_MATERIAL);
        return madeOf;
    }

    @BeforeEach
    public void initTest() {
        madeOf = createEntity(em);
    }

    @Test
    @Transactional
    void createMadeOf() throws Exception {
        int databaseSizeBeforeCreate = madeOfRepository.findAll().size();
        // Create the MadeOf
        restMadeOfMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(madeOf)))
            .andExpect(status().isCreated());

        // Validate the MadeOf in the database
        List<MadeOf> madeOfList = madeOfRepository.findAll();
        assertThat(madeOfList).hasSize(databaseSizeBeforeCreate + 1);
        MadeOf testMadeOf = madeOfList.get(madeOfList.size() - 1);
        assertThat(testMadeOf.getAmountMaterial()).isEqualTo(DEFAULT_AMOUNT_MATERIAL);
    }

    @Test
    @Transactional
    void createMadeOfWithExistingId() throws Exception {
        // Create the MadeOf with an existing ID
        madeOf.setId(1L);

        int databaseSizeBeforeCreate = madeOfRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMadeOfMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(madeOf)))
            .andExpect(status().isBadRequest());

        // Validate the MadeOf in the database
        List<MadeOf> madeOfList = madeOfRepository.findAll();
        assertThat(madeOfList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMadeoves() throws Exception {
        // Initialize the database
        madeOfRepository.saveAndFlush(madeOf);

        // Get all the madeOfList
        restMadeOfMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(madeOf.getId().intValue())))
            .andExpect(jsonPath("$.[*].amountMaterial").value(hasItem(DEFAULT_AMOUNT_MATERIAL.doubleValue())));
    }

    @Test
    @Transactional
    void getMadeOf() throws Exception {
        // Initialize the database
        madeOfRepository.saveAndFlush(madeOf);

        // Get the madeOf
        restMadeOfMockMvc
            .perform(get(ENTITY_API_URL_ID, madeOf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(madeOf.getId().intValue()))
            .andExpect(jsonPath("$.amountMaterial").value(DEFAULT_AMOUNT_MATERIAL.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingMadeOf() throws Exception {
        // Get the madeOf
        restMadeOfMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMadeOf() throws Exception {
        // Initialize the database
        madeOfRepository.saveAndFlush(madeOf);

        int databaseSizeBeforeUpdate = madeOfRepository.findAll().size();

        // Update the madeOf
        MadeOf updatedMadeOf = madeOfRepository.findById(madeOf.getId()).get();
        // Disconnect from session so that the updates on updatedMadeOf are not directly saved in db
        em.detach(updatedMadeOf);
        updatedMadeOf.amountMaterial(UPDATED_AMOUNT_MATERIAL);

        restMadeOfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMadeOf.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMadeOf))
            )
            .andExpect(status().isOk());

        // Validate the MadeOf in the database
        List<MadeOf> madeOfList = madeOfRepository.findAll();
        assertThat(madeOfList).hasSize(databaseSizeBeforeUpdate);
        MadeOf testMadeOf = madeOfList.get(madeOfList.size() - 1);
        assertThat(testMadeOf.getAmountMaterial()).isEqualTo(UPDATED_AMOUNT_MATERIAL);
    }

    @Test
    @Transactional
    void putNonExistingMadeOf() throws Exception {
        int databaseSizeBeforeUpdate = madeOfRepository.findAll().size();
        madeOf.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMadeOfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, madeOf.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(madeOf))
            )
            .andExpect(status().isBadRequest());

        // Validate the MadeOf in the database
        List<MadeOf> madeOfList = madeOfRepository.findAll();
        assertThat(madeOfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMadeOf() throws Exception {
        int databaseSizeBeforeUpdate = madeOfRepository.findAll().size();
        madeOf.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMadeOfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(madeOf))
            )
            .andExpect(status().isBadRequest());

        // Validate the MadeOf in the database
        List<MadeOf> madeOfList = madeOfRepository.findAll();
        assertThat(madeOfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMadeOf() throws Exception {
        int databaseSizeBeforeUpdate = madeOfRepository.findAll().size();
        madeOf.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMadeOfMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(madeOf)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MadeOf in the database
        List<MadeOf> madeOfList = madeOfRepository.findAll();
        assertThat(madeOfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMadeOfWithPatch() throws Exception {
        // Initialize the database
        madeOfRepository.saveAndFlush(madeOf);

        int databaseSizeBeforeUpdate = madeOfRepository.findAll().size();

        // Update the madeOf using partial update
        MadeOf partialUpdatedMadeOf = new MadeOf();
        partialUpdatedMadeOf.setId(madeOf.getId());

        restMadeOfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMadeOf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMadeOf))
            )
            .andExpect(status().isOk());

        // Validate the MadeOf in the database
        List<MadeOf> madeOfList = madeOfRepository.findAll();
        assertThat(madeOfList).hasSize(databaseSizeBeforeUpdate);
        MadeOf testMadeOf = madeOfList.get(madeOfList.size() - 1);
        assertThat(testMadeOf.getAmountMaterial()).isEqualTo(DEFAULT_AMOUNT_MATERIAL);
    }

    @Test
    @Transactional
    void fullUpdateMadeOfWithPatch() throws Exception {
        // Initialize the database
        madeOfRepository.saveAndFlush(madeOf);

        int databaseSizeBeforeUpdate = madeOfRepository.findAll().size();

        // Update the madeOf using partial update
        MadeOf partialUpdatedMadeOf = new MadeOf();
        partialUpdatedMadeOf.setId(madeOf.getId());

        partialUpdatedMadeOf.amountMaterial(UPDATED_AMOUNT_MATERIAL);

        restMadeOfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMadeOf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMadeOf))
            )
            .andExpect(status().isOk());

        // Validate the MadeOf in the database
        List<MadeOf> madeOfList = madeOfRepository.findAll();
        assertThat(madeOfList).hasSize(databaseSizeBeforeUpdate);
        MadeOf testMadeOf = madeOfList.get(madeOfList.size() - 1);
        assertThat(testMadeOf.getAmountMaterial()).isEqualTo(UPDATED_AMOUNT_MATERIAL);
    }

    @Test
    @Transactional
    void patchNonExistingMadeOf() throws Exception {
        int databaseSizeBeforeUpdate = madeOfRepository.findAll().size();
        madeOf.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMadeOfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, madeOf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(madeOf))
            )
            .andExpect(status().isBadRequest());

        // Validate the MadeOf in the database
        List<MadeOf> madeOfList = madeOfRepository.findAll();
        assertThat(madeOfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMadeOf() throws Exception {
        int databaseSizeBeforeUpdate = madeOfRepository.findAll().size();
        madeOf.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMadeOfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(madeOf))
            )
            .andExpect(status().isBadRequest());

        // Validate the MadeOf in the database
        List<MadeOf> madeOfList = madeOfRepository.findAll();
        assertThat(madeOfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMadeOf() throws Exception {
        int databaseSizeBeforeUpdate = madeOfRepository.findAll().size();
        madeOf.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMadeOfMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(madeOf)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MadeOf in the database
        List<MadeOf> madeOfList = madeOfRepository.findAll();
        assertThat(madeOfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMadeOf() throws Exception {
        // Initialize the database
        madeOfRepository.saveAndFlush(madeOf);

        int databaseSizeBeforeDelete = madeOfRepository.findAll().size();

        // Delete the madeOf
        restMadeOfMockMvc
            .perform(delete(ENTITY_API_URL_ID, madeOf.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MadeOf> madeOfList = madeOfRepository.findAll();
        assertThat(madeOfList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
