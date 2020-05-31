package com.intership.server.repository;

import com.intership.server.domain.InternshipReport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the InternshipReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InternshipReportRepository extends JpaRepository<InternshipReport, Long> {

    /**
     * 根据实习表 ID 查询实习报告
     * @param interId
     * @return
     */
    List<InternshipReport> findByInterIdId(Long interId);

}
