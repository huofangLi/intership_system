package com.intership.server.repository;

import com.intership.server.domain.AttendanceRecord;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AttendanceRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {

}
