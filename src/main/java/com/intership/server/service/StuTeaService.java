package com.intership.server.service;

import com.intership.server.domain.StuTea;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link StuTea}.
 */
public interface StuTeaService {

    /**
     * Save a stuTea.
     *
     * @param stuTea the entity to save.
     * @return the persisted entity.
     */
    StuTea save(StuTea stuTea);

    /**
     * Get all the stuTeas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StuTea> findAll(Pageable pageable);


    /**
     * Get the "id" stuTea.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StuTea> findOne(Long id);

    /**
     * Delete the "id" stuTea.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
