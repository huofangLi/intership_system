package com.intership.server.repository;

import com.intership.server.domain.AbsenceRecord;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the AbsenceRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AbsenceRecordRepository extends JpaRepository<AbsenceRecord, Long> {

    /**
     * 根据学生 ID 查询 实习缺勤记录
     * @param stuId
     * @return
     */
    List<AbsenceRecord> findByStuIdId(Long stuId);

}
