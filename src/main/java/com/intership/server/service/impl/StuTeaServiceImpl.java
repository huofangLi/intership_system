package com.intership.server.service.impl;

import com.intership.server.service.StuTeaService;
import com.intership.server.domain.StuTea;
import com.intership.server.repository.StuTeaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StuTea}.
 */
@Service
@Transactional
public class StuTeaServiceImpl implements StuTeaService {

    private final Logger log = LoggerFactory.getLogger(StuTeaServiceImpl.class);

    private final StuTeaRepository stuTeaRepository;

    public StuTeaServiceImpl(StuTeaRepository stuTeaRepository) {
        this.stuTeaRepository = stuTeaRepository;
    }

    /**
     * Save a stuTea.
     *
     * @param stuTea the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StuTea save(StuTea stuTea) {
        log.debug("Request to save StuTea : {}", stuTea);
        return stuTeaRepository.save(stuTea);
    }

    /**
     * Get all the stuTeas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StuTea> findAll(Pageable pageable) {
        log.debug("Request to get all StuTeas");
        return stuTeaRepository.findAll(pageable);
    }


    /**
     * Get one stuTea by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StuTea> findOne(Long id) {
        log.debug("Request to get StuTea : {}", id);
        return stuTeaRepository.findById(id);
    }

    /**
     * Delete the stuTea by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StuTea : {}", id);
        stuTeaRepository.deleteById(id);
    }
}
