package com.intership.server.repository;

import com.intership.server.domain.Intership;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Intership entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntershipRepository extends JpaRepository<Intership, Long> {

    /**
     * 根据学生 ID 查询实习
     * @param stuId
     * @return
     */
    List<Intership> findByStuIdId(Long stuId);


}
