package com.eq02.metalurgica.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.eq02.metalurgica.IntegrationTest;
import com.eq02.metalurgica.domain.Subscribe;
import com.eq02.metalurgica.repository.SubscribeRepository;
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
 * Integration tests for the {@link SubscribeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubscribeResourceIT {

    private static final String ENTITY_API_URL = "/api/subscribes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SubscribeRepository subscribeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubscribeMockMvc;

    private Subscribe subscribe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subscribe createEntity(EntityManager em) {
        Subscribe subscribe = new Subscribe();
        return subscribe;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subscribe createUpdatedEntity(EntityManager em) {
        Subscribe subscribe = new Subscribe();
        return subscribe;
    }

    @BeforeEach
    public void initTest() {
        subscribe = createEntity(em);
    }

    @Test
    @Transactional
    void createSubscribe() throws Exception {
        int databaseSizeBeforeCreate = subscribeRepository.findAll().size();
        // Create the Subscribe
        restSubscribeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subscribe)))
            .andExpect(status().isCreated());

        // Validate the Subscribe in the database
        List<Subscribe> subscribeList = subscribeRepository.findAll();
        assertThat(subscribeList).hasSize(databaseSizeBeforeCreate + 1);
        Subscribe testSubscribe = subscribeList.get(subscribeList.size() - 1);
    }

    @Test
    @Transactional
    void createSubscribeWithExistingId() throws Exception {
        // Create the Subscribe with an existing ID
        subscribe.setId(1L);

        int databaseSizeBeforeCreate = subscribeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubscribeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subscribe)))
            .andExpect(status().isBadRequest());

        // Validate the Subscribe in the database
        List<Subscribe> subscribeList = subscribeRepository.findAll();
        assertThat(subscribeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubscribes() throws Exception {
        // Initialize the database
        subscribeRepository.saveAndFlush(subscribe);

        // Get all the subscribeList
        restSubscribeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subscribe.getId().intValue())));
    }

    @Test
    @Transactional
    void getSubscribe() throws Exception {
        // Initialize the database
        subscribeRepository.saveAndFlush(subscribe);

        // Get the subscribe
        restSubscribeMockMvc
            .perform(get(ENTITY_API_URL_ID, subscribe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subscribe.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingSubscribe() throws Exception {
        // Get the subscribe
        restSubscribeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSubscribe() throws Exception {
        // Initialize the database
        subscribeRepository.saveAndFlush(subscribe);

        int databaseSizeBeforeUpdate = subscribeRepository.findAll().size();

        // Update the subscribe
        Subscribe updatedSubscribe = subscribeRepository.findById(subscribe.getId()).get();
        // Disconnect from session so that the updates on updatedSubscribe are not directly saved in db
        em.detach(updatedSubscribe);

        restSubscribeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSubscribe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSubscribe))
            )
            .andExpect(status().isOk());

        // Validate the Subscribe in the database
        List<Subscribe> subscribeList = subscribeRepository.findAll();
        assertThat(subscribeList).hasSize(databaseSizeBeforeUpdate);
        Subscribe testSubscribe = subscribeList.get(subscribeList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingSubscribe() throws Exception {
        int databaseSizeBeforeUpdate = subscribeRepository.findAll().size();
        subscribe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubscribeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subscribe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subscribe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subscribe in the database
        List<Subscribe> subscribeList = subscribeRepository.findAll();
        assertThat(subscribeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubscribe() throws Exception {
        int databaseSizeBeforeUpdate = subscribeRepository.findAll().size();
        subscribe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubscribeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subscribe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subscribe in the database
        List<Subscribe> subscribeList = subscribeRepository.findAll();
        assertThat(subscribeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubscribe() throws Exception {
        int databaseSizeBeforeUpdate = subscribeRepository.findAll().size();
        subscribe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubscribeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subscribe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subscribe in the database
        List<Subscribe> subscribeList = subscribeRepository.findAll();
        assertThat(subscribeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubscribeWithPatch() throws Exception {
        // Initialize the database
        subscribeRepository.saveAndFlush(subscribe);

        int databaseSizeBeforeUpdate = subscribeRepository.findAll().size();

        // Update the subscribe using partial update
        Subscribe partialUpdatedSubscribe = new Subscribe();
        partialUpdatedSubscribe.setId(subscribe.getId());

        restSubscribeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubscribe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubscribe))
            )
            .andExpect(status().isOk());

        // Validate the Subscribe in the database
        List<Subscribe> subscribeList = subscribeRepository.findAll();
        assertThat(subscribeList).hasSize(databaseSizeBeforeUpdate);
        Subscribe testSubscribe = subscribeList.get(subscribeList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateSubscribeWithPatch() throws Exception {
        // Initialize the database
        subscribeRepository.saveAndFlush(subscribe);

        int databaseSizeBeforeUpdate = subscribeRepository.findAll().size();

        // Update the subscribe using partial update
        Subscribe partialUpdatedSubscribe = new Subscribe();
        partialUpdatedSubscribe.setId(subscribe.getId());

        restSubscribeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubscribe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubscribe))
            )
            .andExpect(status().isOk());

        // Validate the Subscribe in the database
        List<Subscribe> subscribeList = subscribeRepository.findAll();
        assertThat(subscribeList).hasSize(databaseSizeBeforeUpdate);
        Subscribe testSubscribe = subscribeList.get(subscribeList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingSubscribe() throws Exception {
        int databaseSizeBeforeUpdate = subscribeRepository.findAll().size();
        subscribe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubscribeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subscribe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subscribe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subscribe in the database
        List<Subscribe> subscribeList = subscribeRepository.findAll();
        assertThat(subscribeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubscribe() throws Exception {
        int databaseSizeBeforeUpdate = subscribeRepository.findAll().size();
        subscribe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubscribeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subscribe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subscribe in the database
        List<Subscribe> subscribeList = subscribeRepository.findAll();
        assertThat(subscribeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubscribe() throws Exception {
        int databaseSizeBeforeUpdate = subscribeRepository.findAll().size();
        subscribe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubscribeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(subscribe))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subscribe in the database
        List<Subscribe> subscribeList = subscribeRepository.findAll();
        assertThat(subscribeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubscribe() throws Exception {
        // Initialize the database
        subscribeRepository.saveAndFlush(subscribe);

        int databaseSizeBeforeDelete = subscribeRepository.findAll().size();

        // Delete the subscribe
        restSubscribeMockMvc
            .perform(delete(ENTITY_API_URL_ID, subscribe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Subscribe> subscribeList = subscribeRepository.findAll();
        assertThat(subscribeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
