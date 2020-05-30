package com.intership.server.service;

import com.intership.server.domain.AlarmEvent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link AlarmEvent}.
 */
public interface AlarmEventService {

    /**
     * Save a alarmEvent.
     *
     * @param alarmEvent the entity to save.
     * @return the persisted entity.
     */
    AlarmEvent save(AlarmEvent alarmEvent);

    /**
     * Get all the alarmEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AlarmEvent> findAll(Pageable pageable);


    /**
     * Get the "id" alarmEvent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AlarmEvent> findOne(Long id);

    List<AlarmEvent> findByStuId(Long stuId);

    /**
     * Delete the "id" alarmEvent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
