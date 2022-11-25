package com.eq02.metalurgica.service;

import com.eq02.metalurgica.domain.RawMaterial;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link RawMaterial}.
 */
public interface RawMaterialService {
    /**
     * Save a rawMaterial.
     *
     * @param rawMaterial the entity to save.
     * @return the persisted entity.
     */
    RawMaterial save(RawMaterial rawMaterial);

    /**
     * Updates a rawMaterial.
     *
     * @param rawMaterial the entity to update.
     * @return the persisted entity.
     */
    RawMaterial update(RawMaterial rawMaterial);

    /**
     * Partially updates a rawMaterial.
     *
     * @param rawMaterial the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RawMaterial> partialUpdate(RawMaterial rawMaterial);

    /**
     * Get all the rawMaterials.
     *
     * @return the list of entities.
     */
    List<RawMaterial> findAll();

    /**
     * Get the "id" rawMaterial.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RawMaterial> findOne(Long id);

    /**
     * Delete the "id" rawMaterial.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
