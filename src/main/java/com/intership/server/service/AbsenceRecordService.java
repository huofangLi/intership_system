package com.intership.server.service;

import com.intership.server.domain.AbsenceRecord;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link AbsenceRecord}.
 */
public interface AbsenceRecordService {

    /**
     * Save a absenceRecord.
     *
     * @param absenceRecord the entity to save.
     * @return the persisted entity.
     */
    AbsenceRecord save(AbsenceRecord absenceRecord);

    /**
     * Get all the absenceRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AbsenceRecord> findAll(Pageable pageable);


    /**
     * Get the "id" absenceRecord.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AbsenceRecord> findOne(Long id);

    /**
     * Delete the "id" absenceRecord.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
