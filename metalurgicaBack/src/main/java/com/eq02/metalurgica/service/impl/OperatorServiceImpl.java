package com.eq02.metalurgica.service.impl;

import com.eq02.metalurgica.domain.Operator;
import com.eq02.metalurgica.repository.OperatorRepository;
import com.eq02.metalurgica.service.OperatorService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Operator}.
 */
@Service
@Transactional
public class OperatorServiceImpl implements OperatorService {

    private final Logger log = LoggerFactory.getLogger(OperatorServiceImpl.class);

    private final OperatorRepository operatorRepository;

    public OperatorServiceImpl(OperatorRepository operatorRepository) {
        this.operatorRepository = operatorRepository;
    }

    @Override
    public Operator save(Operator operator) {
        log.debug("Request to save Operator : {}", operator);
        return operatorRepository.save(operator);
    }

    @Override
    public Operator update(Operator operator) {
        log.debug("Request to update Operator : {}", operator);
        return operatorRepository.save(operator);
    }

    @Override
    public Optional<Operator> partialUpdate(Operator operator) {
        log.debug("Request to partially update Operator : {}", operator);

        return operatorRepository
            .findById(operator.getId())
            .map(existingOperator -> {
                return existingOperator;
            })
            .map(operatorRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Operator> findAll() {
        log.debug("Request to get all Operators");
        return operatorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Operator> findOne(Long id) {
        log.debug("Request to get Operator : {}", id);
        return operatorRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Operator : {}", id);
        operatorRepository.deleteById(id);
    }
}
