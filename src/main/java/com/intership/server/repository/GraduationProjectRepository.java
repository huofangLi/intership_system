package com.intership.server.repository;

import com.intership.server.domain.GraduationProject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GraduationProject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GraduationProjectRepository extends JpaRepository<GraduationProject, Long> {

}
