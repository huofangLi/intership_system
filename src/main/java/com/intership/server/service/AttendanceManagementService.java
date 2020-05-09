package com.intership.server.service;

import com.intership.server.domain.AttendanceManagement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link AttendanceManagement}.
 */
public interface AttendanceManagementService {

    /**
     * Save a attendanceManagement.
     *
     * @param attendanceManagement the entity to save.
     * @return the persisted entity.
     */
    AttendanceManagement save(AttendanceManagement attendanceManagement);

    /**
     * Get all the attendanceManagements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AttendanceManagement> findAll(Pageable pageable);


    /**
     * Get the "id" attendanceManagement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttendanceManagement> findOne(Long id);

    /**
     * Delete the "id" attendanceManagement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
