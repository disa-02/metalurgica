package com.eq02.metalurgica.service;

import com.eq02.metalurgica.domain.Subscribe;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Subscribe}.
 */
public interface SubscribeService {
    /**
     * Save a subscribe.
     *
     * @param subscribe the entity to save.
     * @return the persisted entity.
     */
    Subscribe save(Subscribe subscribe);

    /**
     * Updates a subscribe.
     *
     * @param subscribe the entity to update.
     * @return the persisted entity.
     */
    Subscribe update(Subscribe subscribe);

    /**
     * Partially updates a subscribe.
     *
     * @param subscribe the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Subscribe> partialUpdate(Subscribe subscribe);

    /**
     * Get all the subscribes.
     *
     * @return the list of entities.
     */
    List<Subscribe> findAll();

    /**
     * Get the "id" subscribe.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Subscribe> findOne(Long id);

    /**
     * Delete the "id" subscribe.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
