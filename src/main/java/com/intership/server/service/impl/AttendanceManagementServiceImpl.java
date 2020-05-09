package com.intership.server.service.impl;

import com.intership.server.service.AttendanceManagementService;
import com.intership.server.domain.AttendanceManagement;
import com.intership.server.repository.AttendanceManagementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AttendanceManagement}.
 */
@Service
@Transactional
public class AttendanceManagementServiceImpl implements AttendanceManagementService {

    private final Logger log = LoggerFactory.getLogger(AttendanceManagementServiceImpl.class);

    private final AttendanceManagementRepository attendanceManagementRepository;

    public AttendanceManagementServiceImpl(AttendanceManagementRepository attendanceManagementRepository) {
        this.attendanceManagementRepository = attendanceManagementRepository;
    }

    /**
     * Save a attendanceManagement.
     *
     * @param attendanceManagement the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AttendanceManagement save(AttendanceManagement attendanceManagement) {
        log.debug("Request to save AttendanceManagement : {}", attendanceManagement);
        return attendanceManagementRepository.save(attendanceManagement);
    }

    /**
     * Get all the attendanceManagements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AttendanceManagement> findAll(Pageable pageable) {
        log.debug("Request to get all AttendanceManagements");
        return attendanceManagementRepository.findAll(pageable);
    }


    /**
     * Get one attendanceManagement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AttendanceManagement> findOne(Long id) {
        log.debug("Request to get AttendanceManagement : {}", id);
        return attendanceManagementRepository.findById(id);
    }

    /**
     * Delete the attendanceManagement by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AttendanceManagement : {}", id);
        attendanceManagementRepository.deleteById(id);
    }
}
