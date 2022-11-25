package com.eq02.metalurgica.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.eq02.metalurgica.IntegrationTest;
import com.eq02.metalurgica.domain.RawMaterial;
import com.eq02.metalurgica.repository.RawMaterialRepository;
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
 * Integration tests for the {@link RawMaterialResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RawMaterialResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_STOCK = 1;
    private static final Integer UPDATED_STOCK = 2;

    private static final String ENTITY_API_URL = "/api/raw-materials";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRawMaterialMockMvc;

    private RawMaterial rawMaterial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RawMaterial createEntity(EntityManager em) {
        RawMaterial rawMaterial = new RawMaterial().name(DEFAULT_NAME).stock(DEFAULT_STOCK);
        return rawMaterial;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RawMaterial createUpdatedEntity(EntityManager em) {
        RawMaterial rawMaterial = new RawMaterial().name(UPDATED_NAME).stock(UPDATED_STOCK);
        return rawMaterial;
    }

    @BeforeEach
    public void initTest() {
        rawMaterial = createEntity(em);
    }

    @Test
    @Transactional
    void createRawMaterial() throws Exception {
        int databaseSizeBeforeCreate = rawMaterialRepository.findAll().size();
        // Create the RawMaterial
        restRawMaterialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rawMaterial)))
            .andExpect(status().isCreated());

        // Validate the RawMaterial in the database
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();
        assertThat(rawMaterialList).hasSize(databaseSizeBeforeCreate + 1);
        RawMaterial testRawMaterial = rawMaterialList.get(rawMaterialList.size() - 1);
        assertThat(testRawMaterial.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRawMaterial.getStock()).isEqualTo(DEFAULT_STOCK);
    }

    @Test
    @Transactional
    void createRawMaterialWithExistingId() throws Exception {
        // Create the RawMaterial with an existing ID
        rawMaterial.setId(1L);

        int databaseSizeBeforeCreate = rawMaterialRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRawMaterialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rawMaterial)))
            .andExpect(status().isBadRequest());

        // Validate the RawMaterial in the database
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();
        assertThat(rawMaterialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRawMaterials() throws Exception {
        // Initialize the database
        rawMaterialRepository.saveAndFlush(rawMaterial);

        // Get all the rawMaterialList
        restRawMaterialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rawMaterial.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK)));
    }

    @Test
    @Transactional
    void getRawMaterial() throws Exception {
        // Initialize the database
        rawMaterialRepository.saveAndFlush(rawMaterial);

        // Get the rawMaterial
        restRawMaterialMockMvc
            .perform(get(ENTITY_API_URL_ID, rawMaterial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rawMaterial.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK));
    }

    @Test
    @Transactional
    void getNonExistingRawMaterial() throws Exception {
        // Get the rawMaterial
        restRawMaterialMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRawMaterial() throws Exception {
        // Initialize the database
        rawMaterialRepository.saveAndFlush(rawMaterial);

        int databaseSizeBeforeUpdate = rawMaterialRepository.findAll().size();

        // Update the rawMaterial
        RawMaterial updatedRawMaterial = rawMaterialRepository.findById(rawMaterial.getId()).get();
        // Disconnect from session so that the updates on updatedRawMaterial are not directly saved in db
        em.detach(updatedRawMaterial);
        updatedRawMaterial.name(UPDATED_NAME).stock(UPDATED_STOCK);

        restRawMaterialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRawMaterial.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRawMaterial))
            )
            .andExpect(status().isOk());

        // Validate the RawMaterial in the database
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();
        assertThat(rawMaterialList).hasSize(databaseSizeBeforeUpdate);
        RawMaterial testRawMaterial = rawMaterialList.get(rawMaterialList.size() - 1);
        assertThat(testRawMaterial.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRawMaterial.getStock()).isEqualTo(UPDATED_STOCK);
    }

    @Test
    @Transactional
    void putNonExistingRawMaterial() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialRepository.findAll().size();
        rawMaterial.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRawMaterialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rawMaterial.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterial))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawMaterial in the database
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();
        assertThat(rawMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRawMaterial() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialRepository.findAll().size();
        rawMaterial.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawMaterialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterial))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawMaterial in the database
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();
        assertThat(rawMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRawMaterial() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialRepository.findAll().size();
        rawMaterial.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawMaterialMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rawMaterial)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RawMaterial in the database
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();
        assertThat(rawMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRawMaterialWithPatch() throws Exception {
        // Initialize the database
        rawMaterialRepository.saveAndFlush(rawMaterial);

        int databaseSizeBeforeUpdate = rawMaterialRepository.findAll().size();

        // Update the rawMaterial using partial update
        RawMaterial partialUpdatedRawMaterial = new RawMaterial();
        partialUpdatedRawMaterial.setId(rawMaterial.getId());

        restRawMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRawMaterial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRawMaterial))
            )
            .andExpect(status().isOk());

        // Validate the RawMaterial in the database
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();
        assertThat(rawMaterialList).hasSize(databaseSizeBeforeUpdate);
        RawMaterial testRawMaterial = rawMaterialList.get(rawMaterialList.size() - 1);
        assertThat(testRawMaterial.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRawMaterial.getStock()).isEqualTo(DEFAULT_STOCK);
    }

    @Test
    @Transactional
    void fullUpdateRawMaterialWithPatch() throws Exception {
        // Initialize the database
        rawMaterialRepository.saveAndFlush(rawMaterial);

        int databaseSizeBeforeUpdate = rawMaterialRepository.findAll().size();

        // Update the rawMaterial using partial update
        RawMaterial partialUpdatedRawMaterial = new RawMaterial();
        partialUpdatedRawMaterial.setId(rawMaterial.getId());

        partialUpdatedRawMaterial.name(UPDATED_NAME).stock(UPDATED_STOCK);

        restRawMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRawMaterial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRawMaterial))
            )
            .andExpect(status().isOk());

        // Validate the RawMaterial in the database
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();
        assertThat(rawMaterialList).hasSize(databaseSizeBeforeUpdate);
        RawMaterial testRawMaterial = rawMaterialList.get(rawMaterialList.size() - 1);
        assertThat(testRawMaterial.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRawMaterial.getStock()).isEqualTo(UPDATED_STOCK);
    }

    @Test
    @Transactional
    void patchNonExistingRawMaterial() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialRepository.findAll().size();
        rawMaterial.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRawMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rawMaterial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterial))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawMaterial in the database
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();
        assertThat(rawMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRawMaterial() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialRepository.findAll().size();
        rawMaterial.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterial))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawMaterial in the database
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();
        assertThat(rawMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRawMaterial() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialRepository.findAll().size();
        rawMaterial.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(rawMaterial))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RawMaterial in the database
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();
        assertThat(rawMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRawMaterial() throws Exception {
        // Initialize the database
        rawMaterialRepository.saveAndFlush(rawMaterial);

        int databaseSizeBeforeDelete = rawMaterialRepository.findAll().size();

        // Delete the rawMaterial
        restRawMaterialMockMvc
            .perform(delete(ENTITY_API_URL_ID, rawMaterial.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();
        assertThat(rawMaterialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
