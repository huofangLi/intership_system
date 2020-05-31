package com.intership.server.repository;

import com.intership.server.domain.AttendanceManagement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the AttendanceManagement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendanceManagementRepository extends JpaRepository<AttendanceManagement, Long> {

    /**
     * 根据学生 ID 查询实习签到
     * @param stuId
     * @return
     */
    List<AttendanceManagement> findByStuIdId(Long stuId);

}
