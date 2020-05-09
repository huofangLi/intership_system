package com.intership.server.service;

import com.intership.server.domain.SharingCenter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link SharingCenter}.
 */
public interface SharingCenterService {

    /**
     * Save a sharingCenter.
     *
     * @param sharingCenter the entity to save.
     * @return the persisted entity.
     */
    SharingCenter save(SharingCenter sharingCenter);

    /**
     * Get all the sharingCenters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SharingCenter> findAll(Pageable pageable);


    /**
     * Get the "id" sharingCenter.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SharingCenter> findOne(Long id);

    /**
     * Delete the "id" sharingCenter.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
