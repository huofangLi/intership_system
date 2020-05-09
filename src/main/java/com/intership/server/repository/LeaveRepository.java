package com.intership.server.repository;

import com.intership.server.domain.Leave;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Leave entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {

}
