package com.eq02.metalurgica.service.impl;

import com.eq02.metalurgica.domain.Roow;
import com.eq02.metalurgica.repository.RoowRepository;
import com.eq02.metalurgica.service.RoowService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Roow}.
 */
@Service
@Transactional
public class RoowServiceImpl implements RoowService {

    private final Logger log = LoggerFactory.getLogger(RoowServiceImpl.class);

    private final RoowRepository roowRepository;

    public RoowServiceImpl(RoowRepository roowRepository) {
        this.roowRepository = roowRepository;
    }

    @Override
    public Roow save(Roow roow) {
        log.debug("Request to save Roow : {}", roow);
        return roowRepository.save(roow);
    }

    @Override
    public Roow update(Roow roow) {
        log.debug("Request to update Roow : {}", roow);
        return roowRepository.save(roow);
    }

    @Override
    public Optional<Roow> partialUpdate(Roow roow) {
        log.debug("Request to partially update Roow : {}", roow);

        return roowRepository
            .findById(roow.getId())
            .map(existingRoow -> {
                if (roow.getAmountProduct() != null) {
                    existingRoow.setAmountProduct(roow.getAmountProduct());
                }
                if (roow.getSubTotal() != null) {
                    existingRoow.setSubTotal(roow.getSubTotal());
                }

                return existingRoow;
            })
            .map(roowRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Roow> findAll() {
        log.debug("Request to get all Roows");
        return roowRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Roow> findOne(Long id) {
        log.debug("Request to get Roow : {}", id);
        return roowRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Roow : {}", id);
        roowRepository.deleteById(id);
    }
}
