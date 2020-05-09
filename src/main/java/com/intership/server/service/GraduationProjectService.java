package com.intership.server.service;

import com.intership.server.domain.GraduationProject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link GraduationProject}.
 */
public interface GraduationProjectService {

    /**
     * Save a graduationProject.
     *
     * @param graduationProject the entity to save.
     * @return the persisted entity.
     */
    GraduationProject save(GraduationProject graduationProject);

    /**
     * Get all the graduationProjects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GraduationProject> findAll(Pageable pageable);


    /**
     * Get the "id" graduationProject.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GraduationProject> findOne(Long id);

    /**
     * Delete the "id" graduationProject.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
