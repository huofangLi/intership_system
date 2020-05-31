package com.intership.server.service;

import com.intership.server.domain.AbsenceRecord;
import com.intership.server.domain.AttendanceRecord;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link AttendanceRecord}.
 */
public interface AttendanceRecordService {

    /**
     * Save a attendanceRecord.
     *
     * @param attendanceRecord the entity to save.
     * @return the persisted entity.
     */
    AttendanceRecord save(AttendanceRecord attendanceRecord);

    /**
     * Get all the attendanceRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AttendanceRecord> findAll(Pageable pageable);


    /**
     * Get the "id" attendanceRecord.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttendanceRecord> findOne(Long id);

    /**
     * Delete the "id" attendanceRecord.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * 通过学生 ID 查找考勤记录
     * @param stuId
     * @return
     */
    List<AttendanceRecord> findByStuId(Long stuId);
}
