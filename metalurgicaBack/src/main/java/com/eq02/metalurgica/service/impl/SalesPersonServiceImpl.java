package com.eq02.metalurgica.service.impl;

import com.eq02.metalurgica.domain.SalesPerson;
import com.eq02.metalurgica.repository.SalesPersonRepository;
import com.eq02.metalurgica.service.SalesPersonService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SalesPerson}.
 */
@Service
@Transactional
public class SalesPersonServiceImpl implements SalesPersonService {

    private final Logger log = LoggerFactory.getLogger(SalesPersonServiceImpl.class);

    private final SalesPersonRepository salesPersonRepository;

    public SalesPersonServiceImpl(SalesPersonRepository salesPersonRepository) {
        this.salesPersonRepository = salesPersonRepository;
    }

    @Override
    public SalesPerson save(SalesPerson salesPerson) {
        log.debug("Request to save SalesPerson : {}", salesPerson);
        return salesPersonRepository.save(salesPerson);
    }

    @Override
    public SalesPerson update(SalesPerson salesPerson) {
        log.debug("Request to update SalesPerson : {}", salesPerson);
        return salesPersonRepository.save(salesPerson);
    }

    @Override
    public Optional<SalesPerson> partialUpdate(SalesPerson salesPerson) {
        log.debug("Request to partially update SalesPerson : {}", salesPerson);

        return salesPersonRepository
            .findById(salesPerson.getId())
            .map(existingSalesPerson -> {
                return existingSalesPerson;
            })
            .map(salesPersonRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SalesPerson> findAll() {
        log.debug("Request to get all SalesPeople");
        return salesPersonRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SalesPerson> findOne(Long id) {
        log.debug("Request to get SalesPerson : {}", id);
        return salesPersonRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SalesPerson : {}", id);
        salesPersonRepository.deleteById(id);
    }
}
