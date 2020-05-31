package com.intership.server.repository;

import com.intership.server.domain.Leave;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Leave entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {

    /**
     * 根据学生 ID 查询实习请假记录
     * @param stuId
     * @return
     */
    List<Leave> findByStuIdId(Long stuId);

}
