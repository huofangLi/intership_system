package com.intership.server.repository;

import com.intership.server.domain.AttendanceManagement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AttendanceManagement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendanceManagementRepository extends JpaRepository<AttendanceManagement, Long> {

}
