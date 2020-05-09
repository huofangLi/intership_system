package com.intership.server.service;

import com.intership.server.domain.InternshipReport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link InternshipReport}.
 */
public interface InternshipReportService {

    /**
     * Save a internshipReport.
     *
     * @param internshipReport the entity to save.
     * @return the persisted entity.
     */
    InternshipReport save(InternshipReport internshipReport);

    /**
     * Get all the internshipReports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InternshipReport> findAll(Pageable pageable);


    /**
     * Get the "id" internshipReport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InternshipReport> findOne(Long id);

    /**
     * Delete the "id" internshipReport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
