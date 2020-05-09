package com.intership.server.service;

import com.intership.server.domain.JobChangeRecords;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link JobChangeRecords}.
 */
public interface JobChangeRecordsService {

    /**
     * Save a jobChangeRecords.
     *
     * @param jobChangeRecords the entity to save.
     * @return the persisted entity.
     */
    JobChangeRecords save(JobChangeRecords jobChangeRecords);

    /**
     * Get all the jobChangeRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JobChangeRecords> findAll(Pageable pageable);


    /**
     * Get the "id" jobChangeRecords.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JobChangeRecords> findOne(Long id);

    /**
     * Delete the "id" jobChangeRecords.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
