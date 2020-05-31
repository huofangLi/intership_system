package com.intership.server.service;

import com.intership.server.domain.InternshipTask;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link InternshipTask}.
 */
public interface InternshipTaskService {

    /**
     * Save a internshipTask.
     *
     * @param internshipTask the entity to save.
     * @return the persisted entity.
     */
    InternshipTask save(InternshipTask internshipTask);

    /**
     * Get all the internshipTasks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InternshipTask> findAll(Pageable pageable);


    /**
     * Get the "id" internshipTask.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InternshipTask> findOne(Long id);

    /**
     * Delete the "id" internshipTask.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * 根据实习表 ID 查询实习任务
     * @param interId
     * @return
     */
    List<InternshipTask> findByInterId(Long interId);
}
