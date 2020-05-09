package com.intership.server.service;

import com.intership.server.domain.Leave;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Leave}.
 */
public interface LeaveService {

    /**
     * Save a leave.
     *
     * @param leave the entity to save.
     * @return the persisted entity.
     */
    Leave save(Leave leave);

    /**
     * Get all the leaves.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Leave> findAll(Pageable pageable);


    /**
     * Get the "id" leave.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Leave> findOne(Long id);

    /**
     * Delete the "id" leave.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
