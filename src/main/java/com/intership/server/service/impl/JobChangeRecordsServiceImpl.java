package com.intership.server.service.impl;

import com.intership.server.service.JobChangeRecordsService;
import com.intership.server.domain.JobChangeRecords;
import com.intership.server.repository.JobChangeRecordsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link JobChangeRecords}.
 */
@Service
@Transactional
public class JobChangeRecordsServiceImpl implements JobChangeRecordsService {

    private final Logger log = LoggerFactory.getLogger(JobChangeRecordsServiceImpl.class);

    private final JobChangeRecordsRepository jobChangeRecordsRepository;

    public JobChangeRecordsServiceImpl(JobChangeRecordsRepository jobChangeRecordsRepository) {
        this.jobChangeRecordsRepository = jobChangeRecordsRepository;
    }

    /**
     * Save a jobChangeRecords.
     *
     * @param jobChangeRecords the entity to save.
     * @return the persisted entity.
     */
    @Override
    public JobChangeRecords save(JobChangeRecords jobChangeRecords) {
        log.debug("Request to save JobChangeRecords : {}", jobChangeRecords);
        return jobChangeRecordsRepository.save(jobChangeRecords);
    }

    /**
     * Get all the jobChangeRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JobChangeRecords> findAll(Pageable pageable) {
        log.debug("Request to get all JobChangeRecords");
        return jobChangeRecordsRepository.findAll(pageable);
    }


    /**
     * Get one jobChangeRecords by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<JobChangeRecords> findOne(Long id) {
        log.debug("Request to get JobChangeRecords : {}", id);
        return jobChangeRecordsRepository.findById(id);
    }

    /**
     * Delete the jobChangeRecords by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete JobChangeRecords : {}", id);
        jobChangeRecordsRepository.deleteById(id);
    }
}
