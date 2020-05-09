package com.intership.server.web.rest;

import com.intership.server.domain.AlarmEvent;
import com.intership.server.service.AlarmEventService;
import com.intership.server.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.intership.server.domain.AlarmEvent}.
 */
@RestController
@RequestMapping("/api")
public class AlarmEventResource {

    private final Logger log = LoggerFactory.getLogger(AlarmEventResource.class);

    private static final String ENTITY_NAME = "alarmEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlarmEventService alarmEventService;

    public AlarmEventResource(AlarmEventService alarmEventService) {
        this.alarmEventService = alarmEventService;
    }

    /**
     * {@code POST  /alarm-events} : Create a new alarmEvent.
     *
     * @param alarmEvent the alarmEvent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alarmEvent, or with status {@code 400 (Bad Request)} if the alarmEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alarm-events")
    public ResponseEntity<AlarmEvent> createAlarmEvent(@RequestBody AlarmEvent alarmEvent) throws URISyntaxException {
        log.debug("REST request to save AlarmEvent : {}", alarmEvent);
        if (alarmEvent.getId() != null) {
            throw new BadRequestAlertException("A new alarmEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlarmEvent result = alarmEventService.save(alarmEvent);
        return ResponseEntity.created(new URI("/api/alarm-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alarm-events} : Updates an existing alarmEvent.
     *
     * @param alarmEvent the alarmEvent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alarmEvent,
     * or with status {@code 400 (Bad Request)} if the alarmEvent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alarmEvent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alarm-events")
    public ResponseEntity<AlarmEvent> updateAlarmEvent(@RequestBody AlarmEvent alarmEvent) throws URISyntaxException {
        log.debug("REST request to update AlarmEvent : {}", alarmEvent);
        if (alarmEvent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlarmEvent result = alarmEventService.save(alarmEvent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alarmEvent.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /alarm-events} : get all the alarmEvents.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alarmEvents in body.
     */
    @GetMapping("/alarm-events")
    public ResponseEntity<List<AlarmEvent>> getAllAlarmEvents(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of AlarmEvents");
        Page<AlarmEvent> page = alarmEventService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /alarm-events/:id} : get the "id" alarmEvent.
     *
     * @param id the id of the alarmEvent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alarmEvent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alarm-events/{id}")
    public ResponseEntity<AlarmEvent> getAlarmEvent(@PathVariable Long id) {
        log.debug("REST request to get AlarmEvent : {}", id);
        Optional<AlarmEvent> alarmEvent = alarmEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alarmEvent);
    }

    /**
     * {@code DELETE  /alarm-events/:id} : delete the "id" alarmEvent.
     *
     * @param id the id of the alarmEvent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alarm-events/{id}")
    public ResponseEntity<Void> deleteAlarmEvent(@PathVariable Long id) {
        log.debug("REST request to delete AlarmEvent : {}", id);
        alarmEventService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
