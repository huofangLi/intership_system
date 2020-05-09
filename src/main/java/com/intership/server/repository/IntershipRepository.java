package com.intership.server.repository;

import com.intership.server.domain.Intership;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Intership entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntershipRepository extends JpaRepository<Intership, Long> {

}
