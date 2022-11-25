package com.eq02.metalurgica.service;

import com.eq02.metalurgica.domain.SalesPerson;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link SalesPerson}.
 */
public interface SalesPersonService {
    /**
     * Save a salesPerson.
     *
     * @param salesPerson the entity to save.
     * @return the persisted entity.
     */
    SalesPerson save(SalesPerson salesPerson);

    /**
     * Updates a salesPerson.
     *
     * @param salesPerson the entity to update.
     * @return the persisted entity.
     */
    SalesPerson update(SalesPerson salesPerson);

    /**
     * Partially updates a salesPerson.
     *
     * @param salesPerson the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SalesPerson> partialUpdate(SalesPerson salesPerson);

    /**
     * Get all the salesPeople.
     *
     * @return the list of entities.
     */
    List<SalesPerson> findAll();

    /**
     * Get the "id" salesPerson.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SalesPerson> findOne(Long id);

    /**
     * Delete the "id" salesPerson.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
