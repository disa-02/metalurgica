package com.eq02.metalurgica.service;

import com.eq02.metalurgica.domain.MadeOf;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MadeOf}.
 */
public interface MadeOfService {
    /**
     * Save a madeOf.
     *
     * @param madeOf the entity to save.
     * @return the persisted entity.
     */
    MadeOf save(MadeOf madeOf);

    /**
     * Updates a madeOf.
     *
     * @param madeOf the entity to update.
     * @return the persisted entity.
     */
    MadeOf update(MadeOf madeOf);

    /**
     * Partially updates a madeOf.
     *
     * @param madeOf the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MadeOf> partialUpdate(MadeOf madeOf);

    /**
     * Get all the madeoves.
     *
     * @return the list of entities.
     */
    List<MadeOf> findAll();

    /**
     * Get the "id" madeOf.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MadeOf> findOne(Long id);

    /**
     * Delete the "id" madeOf.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
