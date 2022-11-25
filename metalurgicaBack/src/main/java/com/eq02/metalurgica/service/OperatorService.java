package com.eq02.metalurgica.service;

import com.eq02.metalurgica.domain.Operator;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Operator}.
 */
public interface OperatorService {
    /**
     * Save a operator.
     *
     * @param operator the entity to save.
     * @return the persisted entity.
     */
    Operator save(Operator operator);

    /**
     * Updates a operator.
     *
     * @param operator the entity to update.
     * @return the persisted entity.
     */
    Operator update(Operator operator);

    /**
     * Partially updates a operator.
     *
     * @param operator the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Operator> partialUpdate(Operator operator);

    /**
     * Get all the operators.
     *
     * @return the list of entities.
     */
    List<Operator> findAll();

    /**
     * Get the "id" operator.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Operator> findOne(Long id);

    /**
     * Delete the "id" operator.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
