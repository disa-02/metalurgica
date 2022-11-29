package com.eq02.metalurgica.service;

import com.eq02.metalurgica.domain.Sale;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Sale}.
 */
public interface SaleService {
    /**
     * Save a sale.
     *
     * @param sale the entity to save.
     * @return the persisted entity.
     */
    Sale save(Sale sale);

    /**
     * Updates a sale.
     *
     * @param sale the entity to update.
     * @return the persisted entity.
     */
    Sale update(Sale sale);

    /**
     * Partially updates a sale.
     *
     * @param sale the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Sale> partialUpdate(Sale sale);

    /**
     * Get all the sales.
     *
     * @return the list of entities.
     */
    List<Sale> findAll();

    /**
     * Get the "id" sale.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Sale> findOne(String id);

    /**
     * Delete the "id" sale.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
