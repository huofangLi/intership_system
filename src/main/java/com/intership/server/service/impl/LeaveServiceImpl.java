package com.intership.server.service.impl;

import com.intership.server.service.LeaveService;
import com.intership.server.domain.Leave;
import com.intership.server.repository.LeaveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Leave}.
 */
@Service
@Transactional
public class LeaveServiceImpl implements LeaveService {

    private final Logger log = LoggerFactory.getLogger(LeaveServiceImpl.class);

    private final LeaveRepository leaveRepository;

    public LeaveServiceImpl(LeaveRepository leaveRepository) {
        this.leaveRepository = leaveRepository;
    }

    /**
     * Save a leave.
     *
     * @param leave the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Leave save(Leave leave) {
        log.debug("Request to save Leave : {}", leave);
        return leaveRepository.save(leave);
    }

    /**
     * Get all the leaves.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Leave> findAll(Pageable pageable) {
        log.debug("Request to get all Leaves");
        return leaveRepository.findAll(pageable);
    }


    /**
     * Get one leave by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Leave> findOne(Long id) {
        log.debug("Request to get Leave : {}", id);
        return leaveRepository.findById(id);
    }

    /**
     * Delete the leave by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Leave : {}", id);
        leaveRepository.deleteById(id);
    }
}
