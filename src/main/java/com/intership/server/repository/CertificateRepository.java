package com.intership.server.repository;

import com.intership.server.domain.Certificate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;



/**
 * Spring Data  repository for the Certificate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    /**
     * 通过学生 ID 查找证书
     * @param stuId
     * @return
     */
    List<Certificate> findByStuIdId(Long stuId);
}
