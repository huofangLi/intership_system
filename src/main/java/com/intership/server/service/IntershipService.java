package com.intership.server.service;

import com.intership.server.domain.Intership;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Intership}.
 */
public interface IntershipService {

    /**
     * Save a intership.
     *
     * @param intership the entity to save.
     * @return the persisted entity.
     */
    Intership save(Intership intership);

    /**
     * Get all the interships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Intership> findAll(Pageable pageable);


    /**
     * Get the "id" intership.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Intership> findOne(Long id);

    /**
     * Delete the "id" intership.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
