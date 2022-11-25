package com.eq02.metalurgica.service.impl;

import com.eq02.metalurgica.domain.Record;
import com.eq02.metalurgica.repository.RecordRepository;
import com.eq02.metalurgica.service.RecordService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Record}.
 */
@Service
@Transactional
public class RecordServiceImpl implements RecordService {

    private final Logger log = LoggerFactory.getLogger(RecordServiceImpl.class);

    private final RecordRepository recordRepository;

    public RecordServiceImpl(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Override
    public Record save(Record record) {
        log.debug("Request to save Record : {}", record);
        return recordRepository.save(record);
    }

    @Override
    public Record update(Record record) {
        log.debug("Request to update Record : {}", record);
        return recordRepository.save(record);
    }

    @Override
    public Optional<Record> partialUpdate(Record record) {
        log.debug("Request to partially update Record : {}", record);

        return recordRepository
            .findById(record.getId())
            .map(existingRecord -> {
                if (record.getDateRange() != null) {
                    existingRecord.setDateRange(record.getDateRange());
                }
                if (record.getAmount() != null) {
                    existingRecord.setAmount(record.getAmount());
                }

                return existingRecord;
            })
            .map(recordRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Record> findAll() {
        log.debug("Request to get all Records");
        return recordRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Record> findOne(Long id) {
        log.debug("Request to get Record : {}", id);
        return recordRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Record : {}", id);
        recordRepository.deleteById(id);
    }
}
