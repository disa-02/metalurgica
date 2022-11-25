package com.eq02.metalurgica.service.impl;

import com.eq02.metalurgica.domain.Sale;
import com.eq02.metalurgica.repository.SaleRepository;
import com.eq02.metalurgica.service.SaleService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Sale}.
 */
@Service
@Transactional
public class SaleServiceImpl implements SaleService {

    private final Logger log = LoggerFactory.getLogger(SaleServiceImpl.class);

    private final SaleRepository saleRepository;

    public SaleServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public Sale save(Sale sale) {
        log.debug("Request to save Sale : {}", sale);
        return saleRepository.save(sale);
    }

    @Override
    public Sale update(Sale sale) {
        log.debug("Request to update Sale : {}", sale);
        return saleRepository.save(sale);
    }

    @Override
    public Optional<Sale> partialUpdate(Sale sale) {
        log.debug("Request to partially update Sale : {}", sale);

        return saleRepository
            .findById(sale.getId())
            .map(existingSale -> {
                if (sale.getSaleCode() != null) {
                    existingSale.setSaleCode(sale.getSaleCode());
                }
                if (sale.getDate() != null) {
                    existingSale.setDate(sale.getDate());
                }
                if (sale.getTotal() != null) {
                    existingSale.setTotal(sale.getTotal());
                }

                return existingSale;
            })
            .map(saleRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sale> findAll() {
        log.debug("Request to get all Sales");
        return saleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Sale> findOne(Long id) {
        log.debug("Request to get Sale : {}", id);
        return saleRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sale : {}", id);
        saleRepository.deleteById(id);
    }
}
