package com.eq02.metalurgica.service.impl;

import com.eq02.metalurgica.domain.MadeOf;
import com.eq02.metalurgica.repository.MadeOfRepository;
import com.eq02.metalurgica.service.MadeOfService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MadeOf}.
 */
@Service
@Transactional
public class MadeOfServiceImpl implements MadeOfService {

    private final Logger log = LoggerFactory.getLogger(MadeOfServiceImpl.class);

    private final MadeOfRepository madeOfRepository;

    public MadeOfServiceImpl(MadeOfRepository madeOfRepository) {
        this.madeOfRepository = madeOfRepository;
    }

    @Override
    public MadeOf save(MadeOf madeOf) {
        log.debug("Request to save MadeOf : {}", madeOf);
        return madeOfRepository.save(madeOf);
    }

    @Override
    public MadeOf update(MadeOf madeOf) {
        log.debug("Request to update MadeOf : {}", madeOf);
        return madeOfRepository.save(madeOf);
    }

    @Override
    public Optional<MadeOf> partialUpdate(MadeOf madeOf) {
        log.debug("Request to partially update MadeOf : {}", madeOf);

        return madeOfRepository
            .findById(madeOf.getId())
            .map(existingMadeOf -> {
                if (madeOf.getAmountMaterial() != null) {
                    existingMadeOf.setAmountMaterial(madeOf.getAmountMaterial());
                }

                return existingMadeOf;
            })
            .map(madeOfRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MadeOf> findAll() {
        log.debug("Request to get all Madeoves");
        return madeOfRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MadeOf> findOne(Long id) {
        log.debug("Request to get MadeOf : {}", id);
        return madeOfRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MadeOf : {}", id);
        madeOfRepository.deleteById(id);
    }
}
