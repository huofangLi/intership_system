package com.intership.server.service.impl;

import com.intership.server.service.InternshipTaskService;
import com.intership.server.domain.InternshipTask;
import com.intership.server.repository.InternshipTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link InternshipTask}.
 */
@Service
@Transactional
public class InternshipTaskServiceImpl implements InternshipTaskService {

    private final Logger log = LoggerFactory.getLogger(InternshipTaskServiceImpl.class);

    private final InternshipTaskRepository internshipTaskRepository;

    public InternshipTaskServiceImpl(InternshipTaskRepository internshipTaskRepository) {
        this.internshipTaskRepository = internshipTaskRepository;
    }

    /**
     * Save a internshipTask.
     *
     * @param internshipTask the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InternshipTask save(InternshipTask internshipTask) {
        log.debug("Request to save InternshipTask : {}", internshipTask);
        return internshipTaskRepository.save(internshipTask);
    }

    /**
     * Get all the internshipTasks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InternshipTask> findAll(Pageable pageable) {
        log.debug("Request to get all InternshipTasks");
        return internshipTaskRepository.findAll(pageable);
    }


    /**
     * Get one internshipTask by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InternshipTask> findOne(Long id) {
        log.debug("Request to get InternshipTask : {}", id);
        return internshipTaskRepository.findById(id);
    }

    /**
     * Delete the internshipTask by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InternshipTask : {}", id);
        internshipTaskRepository.deleteById(id);
    }

    /**
     * 根据实习表 ID 查询实习任务
     * @param interId
     * @return
     */
    @Override
    public List<InternshipTask> findByInterId(Long interId) {
        return internshipTaskRepository.findByInterIdId(interId);
    }
}
