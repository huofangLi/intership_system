package com.intership.server.service.impl;

import com.intership.server.service.AlarmEventService;
import com.intership.server.domain.AlarmEvent;
import com.intership.server.repository.AlarmEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AlarmEvent}.
 */
@Service
@Transactional
public class AlarmEventServiceImpl implements AlarmEventService {

    private final Logger log = LoggerFactory.getLogger(AlarmEventServiceImpl.class);

    private final AlarmEventRepository alarmEventRepository;

    public AlarmEventServiceImpl(AlarmEventRepository alarmEventRepository) {
        this.alarmEventRepository = alarmEventRepository;
    }

    /**
     * Save a alarmEvent.
     *
     * @param alarmEvent the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AlarmEvent save(AlarmEvent alarmEvent) {
        log.debug("Request to save AlarmEvent : {}", alarmEvent);
        return alarmEventRepository.save(alarmEvent);
    }

    /**
     * Get all the alarmEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AlarmEvent> findAll(Pageable pageable) {
        log.debug("Request to get all AlarmEvents");
        return alarmEventRepository.findAll(pageable);
    }


    /**
     * Get one alarmEvent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AlarmEvent> findOne(Long id) {
        log.debug("Request to get AlarmEvent : {}", id);
        return alarmEventRepository.findById(id);
    }

    /**
     * Delete the alarmEvent by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AlarmEvent : {}", id);
        alarmEventRepository.deleteById(id);
    }
}
