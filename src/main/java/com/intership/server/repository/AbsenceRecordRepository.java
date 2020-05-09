package com.intership.server.repository;

import com.intership.server.domain.AbsenceRecord;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AbsenceRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AbsenceRecordRepository extends JpaRepository<AbsenceRecord, Long> {

}
