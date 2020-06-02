package com.intership.server.repository;

import com.intership.server.domain.JobChangeRecords;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the JobChangeRecords entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobChangeRecordsRepository extends JpaRepository<JobChangeRecords, Long> {

    /**
     * 根据实习表 ID 查询变更记录
     * @param interId
     * @return
     */
    List<JobChangeRecords> findByInterIdId(Long interId);

}
