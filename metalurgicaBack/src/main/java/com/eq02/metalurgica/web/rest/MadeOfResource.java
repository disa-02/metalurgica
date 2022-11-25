package com.eq02.metalurgica.web.rest;

import com.eq02.metalurgica.domain.MadeOf;
import com.eq02.metalurgica.repository.MadeOfRepository;
import com.eq02.metalurgica.service.MadeOfService;
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
 * REST controller for managing {@link com.eq02.metalurgica.domain.MadeOf}.
 */
@RestController
@RequestMapping("/api")
public class MadeOfResource {

    private final Logger log = LoggerFactory.getLogger(MadeOfResource.class);

    private static final String ENTITY_NAME = "madeOf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MadeOfService madeOfService;

    private final MadeOfRepository madeOfRepository;

    public MadeOfResource(MadeOfService madeOfService, MadeOfRepository madeOfRepository) {
        this.madeOfService = madeOfService;
        this.madeOfRepository = madeOfRepository;
    }

    /**
     * {@code POST  /madeoves} : Create a new madeOf.
     *
     * @param madeOf the madeOf to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new madeOf, or with status {@code 400 (Bad Request)} if the madeOf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/madeoves")
    public ResponseEntity<MadeOf> createMadeOf(@RequestBody MadeOf madeOf) throws URISyntaxException {
        log.debug("REST request to save MadeOf : {}", madeOf);
        if (madeOf.getId() != null) {
            throw new BadRequestAlertException("A new madeOf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MadeOf result = madeOfService.save(madeOf);
        return ResponseEntity
            .created(new URI("/api/madeoves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /madeoves/:id} : Updates an existing madeOf.
     *
     * @param id the id of the madeOf to save.
     * @param madeOf the madeOf to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated madeOf,
     * or with status {@code 400 (Bad Request)} if the madeOf is not valid,
     * or with status {@code 500 (Internal Server Error)} if the madeOf couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/madeoves/{id}")
    public ResponseEntity<MadeOf> updateMadeOf(@PathVariable(value = "id", required = false) final Long id, @RequestBody MadeOf madeOf)
        throws URISyntaxException {
        log.debug("REST request to update MadeOf : {}, {}", id, madeOf);
        if (madeOf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, madeOf.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!madeOfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MadeOf result = madeOfService.update(madeOf);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, madeOf.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /madeoves/:id} : Partial updates given fields of an existing madeOf, field will ignore if it is null
     *
     * @param id the id of the madeOf to save.
     * @param madeOf the madeOf to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated madeOf,
     * or with status {@code 400 (Bad Request)} if the madeOf is not valid,
     * or with status {@code 404 (Not Found)} if the madeOf is not found,
     * or with status {@code 500 (Internal Server Error)} if the madeOf couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/madeoves/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MadeOf> partialUpdateMadeOf(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MadeOf madeOf
    ) throws URISyntaxException {
        log.debug("REST request to partial update MadeOf partially : {}, {}", id, madeOf);
        if (madeOf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, madeOf.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!madeOfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MadeOf> result = madeOfService.partialUpdate(madeOf);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, madeOf.getId().toString())
        );
    }

    /**
     * {@code GET  /madeoves} : get all the madeoves.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of madeoves in body.
     */
    @GetMapping("/madeoves")
    public List<MadeOf> getAllMadeoves() {
        log.debug("REST request to get all Madeoves");
        return madeOfService.findAll();
    }

    /**
     * {@code GET  /madeoves/:id} : get the "id" madeOf.
     *
     * @param id the id of the madeOf to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the madeOf, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/madeoves/{id}")
    public ResponseEntity<MadeOf> getMadeOf(@PathVariable Long id) {
        log.debug("REST request to get MadeOf : {}", id);
        Optional<MadeOf> madeOf = madeOfService.findOne(id);
        return ResponseUtil.wrapOrNotFound(madeOf);
    }

    /**
     * {@code DELETE  /madeoves/:id} : delete the "id" madeOf.
     *
     * @param id the id of the madeOf to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/madeoves/{id}")
    public ResponseEntity<Void> deleteMadeOf(@PathVariable Long id) {
        log.debug("REST request to delete MadeOf : {}", id);
        madeOfService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
