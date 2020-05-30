package com.intership.server.repository;

import com.intership.server.domain.AlarmEvent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the AlarmEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlarmEventRepository extends JpaRepository<AlarmEvent, Long> {
    List<AlarmEvent> findByStuIdId(Long stuId);
}
