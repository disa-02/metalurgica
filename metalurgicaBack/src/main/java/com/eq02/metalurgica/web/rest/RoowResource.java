package com.eq02.metalurgica.web.rest;

import com.eq02.metalurgica.domain.Roow;
import com.eq02.metalurgica.repository.RoowRepository;
import com.eq02.metalurgica.service.RoowService;
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
 * REST controller for managing {@link com.eq02.metalurgica.domain.Roow}.
 */
@RestController
@RequestMapping("/api")
public class RoowResource {

    private final Logger log = LoggerFactory.getLogger(RoowResource.class);

    private static final String ENTITY_NAME = "roow";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoowService roowService;

    private final RoowRepository roowRepository;

    public RoowResource(RoowService roowService, RoowRepository roowRepository) {
        this.roowService = roowService;
        this.roowRepository = roowRepository;
    }

    /**
     * {@code POST  /roows} : Create a new roow.
     *
     * @param roow the roow to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roow, or with status {@code 400 (Bad Request)} if the roow has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/roows")
    public ResponseEntity<Roow> createRoow(@RequestBody Roow roow) throws URISyntaxException {
        log.debug("REST request to save Roow : {}", roow);
        if (roow.getId() != null) {
            throw new BadRequestAlertException("A new roow cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Roow result = roowService.save(roow);
        return ResponseEntity
            .created(new URI("/api/roows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /roows/:id} : Updates an existing roow.
     *
     * @param id the id of the roow to save.
     * @param roow the roow to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roow,
     * or with status {@code 400 (Bad Request)} if the roow is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roow couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/roows/{id}")
    public ResponseEntity<Roow> updateRoow(@PathVariable(value = "id", required = false) final Long id, @RequestBody Roow roow)
        throws URISyntaxException {
        log.debug("REST request to update Roow : {}, {}", id, roow);
        if (roow.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roow.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roowRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Roow result = roowService.update(roow);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roow.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /roows/:id} : Partial updates given fields of an existing roow, field will ignore if it is null
     *
     * @param id the id of the roow to save.
     * @param roow the roow to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roow,
     * or with status {@code 400 (Bad Request)} if the roow is not valid,
     * or with status {@code 404 (Not Found)} if the roow is not found,
     * or with status {@code 500 (Internal Server Error)} if the roow couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/roows/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Roow> partialUpdateRoow(@PathVariable(value = "id", required = false) final Long id, @RequestBody Roow roow)
        throws URISyntaxException {
        log.debug("REST request to partial update Roow partially : {}, {}", id, roow);
        if (roow.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roow.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roowRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Roow> result = roowService.partialUpdate(roow);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roow.getId().toString())
        );
    }

    /**
     * {@code GET  /roows} : get all the roows.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roows in body.
     */
    @GetMapping("/roows")
    public List<Roow> getAllRoows() {
        log.debug("REST request to get all Roows");
        return roowService.findAll();
    }

    /**
     * {@code GET  /roows/:id} : get the "id" roow.
     *
     * @param id the id of the roow to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roow, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/roows/{id}")
    public ResponseEntity<Roow> getRoow(@PathVariable Long id) {
        log.debug("REST request to get Roow : {}", id);
        Optional<Roow> roow = roowService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roow);
    }

    /**
     * {@code DELETE  /roows/:id} : delete the "id" roow.
     *
     * @param id the id of the roow to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/roows/{id}")
    public ResponseEntity<Void> deleteRoow(@PathVariable Long id) {
        log.debug("REST request to delete Roow : {}", id);
        roowService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
