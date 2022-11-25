package com.eq02.metalurgica.web.rest;

import com.eq02.metalurgica.domain.Subscribe;
import com.eq02.metalurgica.repository.SubscribeRepository;
import com.eq02.metalurgica.service.SubscribeService;
import com.eq02.metalurgica.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.eq02.metalurgica.domain.Subscribe}.
 */
@RestController
@RequestMapping("/api")
public class SubscribeResource {

    private final Logger log = LoggerFactory.getLogger(SubscribeResource.class);

    private static final String ENTITY_NAME = "subscribe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubscribeService subscribeService;

    private final SubscribeRepository subscribeRepository;

    public SubscribeResource(SubscribeService subscribeService, SubscribeRepository subscribeRepository) {
        this.subscribeService = subscribeService;
        this.subscribeRepository = subscribeRepository;
    }

    /**
     * {@code POST  /subscribes} : Create a new subscribe.
     *
     * @param subscribe the subscribe to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subscribe, or with status {@code 400 (Bad Request)} if the subscribe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subscribes")
    public ResponseEntity<Subscribe> createSubscribe(@RequestBody Subscribe subscribe) throws URISyntaxException {
        log.debug("REST request to save Subscribe : {}", subscribe);
        if (subscribe.getId() != null) {
            throw new BadRequestAlertException("A new subscribe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Subscribe result = subscribeService.save(subscribe);
        return ResponseEntity
            .created(new URI("/api/subscribes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subscribes/:id} : Updates an existing subscribe.
     *
     * @param id the id of the subscribe to save.
     * @param subscribe the subscribe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subscribe,
     * or with status {@code 400 (Bad Request)} if the subscribe is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subscribe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subscribes/{id}")
    public ResponseEntity<Subscribe> updateSubscribe(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Subscribe subscribe
    ) throws URISyntaxException {
        log.debug("REST request to update Subscribe : {}, {}", id, subscribe);
        if (subscribe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subscribe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subscribeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Subscribe result = subscribeService.update(subscribe);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subscribe.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /subscribes/:id} : Partial updates given fields of an existing subscribe, field will ignore if it is null
     *
     * @param id the id of the subscribe to save.
     * @param subscribe the subscribe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subscribe,
     * or with status {@code 400 (Bad Request)} if the subscribe is not valid,
     * or with status {@code 404 (Not Found)} if the subscribe is not found,
     * or with status {@code 500 (Internal Server Error)} if the subscribe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/subscribes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Subscribe> partialUpdateSubscribe(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Subscribe subscribe
    ) throws URISyntaxException {
        log.debug("REST request to partial update Subscribe partially : {}, {}", id, subscribe);
        if (subscribe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subscribe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subscribeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Subscribe> result = subscribeService.partialUpdate(subscribe);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subscribe.getId().toString())
        );
    }

    /**
     * {@code GET  /subscribes} : get all the subscribes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subscribes in body.
     */
    @GetMapping("/subscribes")
    public List<Subscribe> getAllSubscribes() {
        log.debug("REST request to get all Subscribes");
        return subscribeService.findAll();
    }

    /**
     * {@code GET  /subscribes/:id} : get the "id" subscribe.
     *
     * @param id the id of the subscribe to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subscribe, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subscribes/{id}")
    public ResponseEntity<Subscribe> getSubscribe(@PathVariable Long id) {
        log.debug("REST request to get Subscribe : {}", id);
        Optional<Subscribe> subscribe = subscribeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subscribe);
    }

    /**
     * {@code DELETE  /subscribes/:id} : delete the "id" subscribe.
     *
     * @param id the id of the subscribe to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subscribes/{id}")
    public ResponseEntity<Void> deleteSubscribe(@PathVariable Long id) {
        log.debug("REST request to delete Subscribe : {}", id);
        subscribeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
