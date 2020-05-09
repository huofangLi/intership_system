package com.intership.server.service.impl;

import com.intership.server.service.GraduationProjectService;
import com.intership.server.domain.GraduationProject;
import com.intership.server.repository.GraduationProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GraduationProject}.
 */
@Service
@Transactional
public class GraduationProjectServiceImpl implements GraduationProjectService {

    private final Logger log = LoggerFactory.getLogger(GraduationProjectServiceImpl.class);

    private final GraduationProjectRepository graduationProjectRepository;

    public GraduationProjectServiceImpl(GraduationProjectRepository graduationProjectRepository) {
        this.graduationProjectRepository = graduationProjectRepository;
    }

    /**
     * Save a graduationProject.
     *
     * @param graduationProject the entity to save.
     * @return the persisted entity.
     */
    @Override
    public GraduationProject save(GraduationProject graduationProject) {
        log.debug("Request to save GraduationProject : {}", graduationProject);
        return graduationProjectRepository.save(graduationProject);
    }

    /**
     * Get all the graduationProjects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GraduationProject> findAll(Pageable pageable) {
        log.debug("Request to get all GraduationProjects");
        return graduationProjectRepository.findAll(pageable);
    }


    /**
     * Get one graduationProject by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GraduationProject> findOne(Long id) {
        log.debug("Request to get GraduationProject : {}", id);
        return graduationProjectRepository.findById(id);
    }

    /**
     * Delete the graduationProject by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GraduationProject : {}", id);
        graduationProjectRepository.deleteById(id);
    }
}
