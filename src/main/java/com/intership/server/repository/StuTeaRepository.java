package com.intership.server.repository;

import com.intership.server.domain.StuTea;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StuTea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StuTeaRepository extends JpaRepository<StuTea, Long> {

}
