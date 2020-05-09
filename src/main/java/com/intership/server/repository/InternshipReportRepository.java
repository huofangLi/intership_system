package com.intership.server.repository;

import com.intership.server.domain.InternshipReport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InternshipReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InternshipReportRepository extends JpaRepository<InternshipReport, Long> {

}
