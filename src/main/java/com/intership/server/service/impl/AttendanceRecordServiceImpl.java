package com.intership.server.service.impl;

import com.intership.server.service.AttendanceRecordService;
import com.intership.server.domain.AttendanceRecord;
import com.intership.server.repository.AttendanceRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AttendanceRecord}.
 */
@Service
@Transactional
public class AttendanceRecordServiceImpl implements AttendanceRecordService {

    private final Logger log = LoggerFactory.getLogger(AttendanceRecordServiceImpl.class);

    private final AttendanceRecordRepository attendanceRecordRepository;

    public AttendanceRecordServiceImpl(AttendanceRecordRepository attendanceRecordRepository) {
        this.attendanceRecordRepository = attendanceRecordRepository;
    }

    /**
     * Save a attendanceRecord.
     *
     * @param attendanceRecord the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AttendanceRecord save(AttendanceRecord attendanceRecord) {
        log.debug("Request to save AttendanceRecord : {}", attendanceRecord);
        return attendanceRecordRepository.save(attendanceRecord);
    }

    /**
     * Get all the attendanceRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AttendanceRecord> findAll(Pageable pageable) {
        log.debug("Request to get all AttendanceRecords");
        return attendanceRecordRepository.findAll(pageable);
    }


    /**
     * Get one attendanceRecord by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AttendanceRecord> findOne(Long id) {
        log.debug("Request to get AttendanceRecord : {}", id);
        return attendanceRecordRepository.findById(id);
    }

    /**
     * Delete the attendanceRecord by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AttendanceRecord : {}", id);
        attendanceRecordRepository.deleteById(id);
    }
}
