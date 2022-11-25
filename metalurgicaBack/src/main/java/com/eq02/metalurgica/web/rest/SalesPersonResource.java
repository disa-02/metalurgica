package com.eq02.metalurgica.web.rest;

import com.eq02.metalurgica.domain.SalesPerson;
import com.eq02.metalurgica.repository.SalesPersonRepository;
import com.eq02.metalurgica.service.SalesPersonService;
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
 * REST controller for managing {@link com.eq02.metalurgica.domain.SalesPerson}.
 */
@RestController
@RequestMapping("/api")
public class SalesPersonResource {

    private final Logger log = LoggerFactory.getLogger(SalesPersonResource.class);

    private static final String ENTITY_NAME = "salesPerson";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalesPersonService salesPersonService;

    private final SalesPersonRepository salesPersonRepository;

    public SalesPersonResource(SalesPersonService salesPersonService, SalesPersonRepository salesPersonRepository) {
        this.salesPersonService = salesPersonService;
        this.salesPersonRepository = salesPersonRepository;
    }

    /**
     * {@code POST  /sales-people} : Create a new salesPerson.
     *
     * @param salesPerson the salesPerson to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salesPerson, or with status {@code 400 (Bad Request)} if the salesPerson has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sales-people")
    public ResponseEntity<SalesPerson> createSalesPerson(@RequestBody SalesPerson salesPerson) throws URISyntaxException {
        log.debug("REST request to save SalesPerson : {}", salesPerson);
        if (salesPerson.getId() != null) {
            throw new BadRequestAlertException("A new salesPerson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalesPerson result = salesPersonService.save(salesPerson);
        return ResponseEntity
            .created(new URI("/api/sales-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sales-people/:id} : Updates an existing salesPerson.
     *
     * @param id the id of the salesPerson to save.
     * @param salesPerson the salesPerson to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesPerson,
     * or with status {@code 400 (Bad Request)} if the salesPerson is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salesPerson couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sales-people/{id}")
    public ResponseEntity<SalesPerson> updateSalesPerson(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SalesPerson salesPerson
    ) throws URISyntaxException {
        log.debug("REST request to update SalesPerson : {}, {}", id, salesPerson);
        if (salesPerson.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salesPerson.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesPersonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SalesPerson result = salesPersonService.update(salesPerson);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, salesPerson.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sales-people/:id} : Partial updates given fields of an existing salesPerson, field will ignore if it is null
     *
     * @param id the id of the salesPerson to save.
     * @param salesPerson the salesPerson to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesPerson,
     * or with status {@code 400 (Bad Request)} if the salesPerson is not valid,
     * or with status {@code 404 (Not Found)} if the salesPerson is not found,
     * or with status {@code 500 (Internal Server Error)} if the salesPerson couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sales-people/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SalesPerson> partialUpdateSalesPerson(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SalesPerson salesPerson
    ) throws URISyntaxException {
        log.debug("REST request to partial update SalesPerson partially : {}, {}", id, salesPerson);
        if (salesPerson.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salesPerson.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesPersonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SalesPerson> result = salesPersonService.partialUpdate(salesPerson);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, salesPerson.getId().toString())
        );
    }

    /**
     * {@code GET  /sales-people} : get all the salesPeople.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salesPeople in body.
     */
    @GetMapping("/sales-people")
    public List<SalesPerson> getAllSalesPeople() {
        log.debug("REST request to get all SalesPeople");
        return salesPersonService.findAll();
    }

    /**
     * {@code GET  /sales-people/:id} : get the "id" salesPerson.
     *
     * @param id the id of the salesPerson to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salesPerson, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sales-people/{id}")
    public ResponseEntity<SalesPerson> getSalesPerson(@PathVariable Long id) {
        log.debug("REST request to get SalesPerson : {}", id);
        Optional<SalesPerson> salesPerson = salesPersonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salesPerson);
    }

    /**
     * {@code DELETE  /sales-people/:id} : delete the "id" salesPerson.
     *
     * @param id the id of the salesPerson to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sales-people/{id}")
    public ResponseEntity<Void> deleteSalesPerson(@PathVariable Long id) {
        log.debug("REST request to delete SalesPerson : {}", id);
        salesPersonService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
