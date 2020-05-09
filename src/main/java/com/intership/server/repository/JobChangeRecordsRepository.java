package com.intership.server.repository;

import com.intership.server.domain.JobChangeRecords;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the JobChangeRecords entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobChangeRecordsRepository extends JpaRepository<JobChangeRecords, Long> {

}
