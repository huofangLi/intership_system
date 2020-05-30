package com.intership.server.repository;

import com.intership.server.domain.SharingCenter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SharingCenter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SharingCenterRepository extends JpaRepository<SharingCenter, Long> {
    //    @Cacheable
//    Page findAllByUploadedByAndDepartment();
    Page<SharingCenter> findByUploadedByOrDepartment(String uploadBy, String department, Pageable pageable);
//    Page<SharingCenter> findByDepartment(String department, Pageable pageable);
}
