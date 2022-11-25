package com.eq02.metalurgica.service.impl;

import com.eq02.metalurgica.domain.Subscribe;
import com.eq02.metalurgica.repository.SubscribeRepository;
import com.eq02.metalurgica.service.SubscribeService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Subscribe}.
 */
@Service
@Transactional
public class SubscribeServiceImpl implements SubscribeService {

    private final Logger log = LoggerFactory.getLogger(SubscribeServiceImpl.class);

    private final SubscribeRepository subscribeRepository;

    public SubscribeServiceImpl(SubscribeRepository subscribeRepository) {
        this.subscribeRepository = subscribeRepository;
    }

    @Override
    public Subscribe save(Subscribe subscribe) {
        log.debug("Request to save Subscribe : {}", subscribe);
        return subscribeRepository.save(subscribe);
    }

    @Override
    public Subscribe update(Subscribe subscribe) {
        log.debug("Request to update Subscribe : {}", subscribe);
        return subscribeRepository.save(subscribe);
    }

    @Override
    public Optional<Subscribe> partialUpdate(Subscribe subscribe) {
        log.debug("Request to partially update Subscribe : {}", subscribe);

        return subscribeRepository
            .findById(subscribe.getId())
            .map(existingSubscribe -> {
                return existingSubscribe;
            })
            .map(subscribeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Subscribe> findAll() {
        log.debug("Request to get all Subscribes");
        return subscribeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Subscribe> findOne(Long id) {
        log.debug("Request to get Subscribe : {}", id);
        return subscribeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Subscribe : {}", id);
        subscribeRepository.deleteById(id);
    }
}
