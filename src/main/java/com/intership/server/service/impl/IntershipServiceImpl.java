package com.intership.server.service.impl;

import com.intership.server.service.IntershipService;
import com.intership.server.domain.Intership;
import com.intership.server.repository.IntershipRepository;
import liquibase.executor.LoggingExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Intership}.
 */
@Service
@Transactional
public class IntershipServiceImpl implements IntershipService {

    private final Logger log = LoggerFactory.getLogger(IntershipServiceImpl.class);

    private final IntershipRepository intershipRepository;
    private final JdbcTemplate jdbcTemplate;

    public IntershipServiceImpl(IntershipRepository intershipRepository, JdbcTemplate jdbcTemplate) {
        this.intershipRepository = intershipRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Save a intership.
     *
     * @param intership the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Intership save(Intership intership) {
        log.debug("Request to save Intership : {}", intership);
        return intershipRepository.save(intership);
    }

    /**
     * Get all the interships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Intership> findAll(Pageable pageable) {
        log.debug("Request to get all Interships");
        return intershipRepository.findAll(pageable);
    }


    /**
     * Get one intership by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Intership> findOne(Long id) {
        log.debug("Request to get Intership : {}", id);
        return intershipRepository.findById(id);
    }

    /**
     * Delete the intership by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Intership : {}", id);
        intershipRepository.deleteById(id);
    }

    /**
     * 根据学生 ID 查询实习
     * @param stuId
     * @return
     */
    @Override
    public List<Intership> findByStuId(Long stuId) {
        return intershipRepository.findByStuIdId(stuId);
    }

    /**
     * 根据学生 ID 查询实习成绩
     * @param stuId
     * @return
     */
    @Override
    public List<Map<String, Object>> findGradeByStuId(Long stuId) {
        String sql = "SELECT tutor_score,master_score,intership_score FROM intership WHERE stu_id_id =" + stuId;
        List<Map<String, Object>> Grade = jdbcTemplate.queryForList(sql);
        return Grade;
    }
}
