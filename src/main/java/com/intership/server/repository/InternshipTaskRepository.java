package com.intership.server.repository;

import com.intership.server.domain.InternshipTask;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InternshipTask entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InternshipTaskRepository extends JpaRepository<InternshipTask, Long> {

}
