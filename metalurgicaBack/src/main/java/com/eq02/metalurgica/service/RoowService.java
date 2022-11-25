package com.eq02.metalurgica.service;

import com.eq02.metalurgica.domain.Roow;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Roow}.
 */
public interface RoowService {
    /**
     * Save a roow.
     *
     * @param roow the entity to save.
     * @return the persisted entity.
     */
    Roow save(Roow roow);

    /**
     * Updates a roow.
     *
     * @param roow the entity to update.
     * @return the persisted entity.
     */
    Roow update(Roow roow);

    /**
     * Partially updates a roow.
     *
     * @param roow the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Roow> partialUpdate(Roow roow);

    /**
     * Get all the roows.
     *
     * @return the list of entities.
     */
    List<Roow> findAll();

    /**
     * Get the "id" roow.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Roow> findOne(Long id);

    /**
     * Delete the "id" roow.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
