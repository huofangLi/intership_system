package com.intership.server.repository;

import com.intership.server.domain.AbsenceRecord;
import com.intership.server.domain.AttendanceRecord;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the AttendanceRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {

    /**
     * 通过学生 ID 查找考勤记录
     * @param stuId
     * @return
     */
    List<AttendanceRecord> findByStuIdId(Long stuId);

}
