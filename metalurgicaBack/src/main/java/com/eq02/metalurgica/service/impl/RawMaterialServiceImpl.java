package com.eq02.metalurgica.service.impl;

import com.eq02.metalurgica.domain.RawMaterial;
import com.eq02.metalurgica.repository.RawMaterialRepository;
import com.eq02.metalurgica.service.RawMaterialService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RawMaterial}.
 */
@Service
@Transactional
public class RawMaterialServiceImpl implements RawMaterialService {

    private final Logger log = LoggerFactory.getLogger(RawMaterialServiceImpl.class);

    private final RawMaterialRepository rawMaterialRepository;

    public RawMaterialServiceImpl(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }

    @Override
    public RawMaterial save(RawMaterial rawMaterial) {
        log.debug("Request to save RawMaterial : {}", rawMaterial);
        return rawMaterialRepository.save(rawMaterial);
    }

    @Override
    public RawMaterial update(RawMaterial rawMaterial) {
        log.debug("Request to update RawMaterial : {}", rawMaterial);
        return rawMaterialRepository.save(rawMaterial);
    }

    @Override
    public Optional<RawMaterial> partialUpdate(RawMaterial rawMaterial) {
        log.debug("Request to partially update RawMaterial : {}", rawMaterial);

        return rawMaterialRepository
            .findById(rawMaterial.getId())
            .map(existingRawMaterial -> {
                if (rawMaterial.getName() != null) {
                    existingRawMaterial.setName(rawMaterial.getName());
                }
                if (rawMaterial.getStock() != null) {
                    existingRawMaterial.setStock(rawMaterial.getStock());
                }

                return existingRawMaterial;
            })
            .map(rawMaterialRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RawMaterial> findAll() {
        log.debug("Request to get all RawMaterials");
        return rawMaterialRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RawMaterial> findOne(Long id) {
        log.debug("Request to get RawMaterial : {}", id);
        return rawMaterialRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RawMaterial : {}", id);
        rawMaterialRepository.deleteById(id);
    }
}
