package com.intership.server.web.rest;

import com.intership.server.domain.AttendanceRecord;
import com.intership.server.service.AttendanceRecordService;
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
 * REST controller for managing {@link com.intership.server.domain.AttendanceRecord}.
 */
@RestController
@RequestMapping("/api")
public class AttendanceRecordResource {

    private final Logger log = LoggerFactory.getLogger(AttendanceRecordResource.class);

    private static final String ENTITY_NAME = "attendanceRecord";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttendanceRecordService attendanceRecordService;

    public AttendanceRecordResource(AttendanceRecordService attendanceRecordService) {
        this.attendanceRecordService = attendanceRecordService;
    }

    /**
     * {@code POST  /attendance-records} : Create a new attendanceRecord.
     *
     * @param attendanceRecord the attendanceRecord to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attendanceRecord, or with status {@code 400 (Bad Request)} if the attendanceRecord has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attendance-records")
    public ResponseEntity<AttendanceRecord> createAttendanceRecord(@RequestBody AttendanceRecord attendanceRecord) throws URISyntaxException {
        log.debug("REST request to save AttendanceRecord : {}", attendanceRecord);
        if (attendanceRecord.getId() != null) {
            throw new BadRequestAlertException("A new attendanceRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttendanceRecord result = attendanceRecordService.save(attendanceRecord);
        return ResponseEntity.created(new URI("/api/attendance-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attendance-records} : Updates an existing attendanceRecord.
     *
     * @param attendanceRecord the attendanceRecord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attendanceRecord,
     * or with status {@code 400 (Bad Request)} if the attendanceRecord is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attendanceRecord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attendance-records")
    public ResponseEntity<AttendanceRecord> updateAttendanceRecord(@RequestBody AttendanceRecord attendanceRecord) throws URISyntaxException {
        log.debug("REST request to update AttendanceRecord : {}", attendanceRecord);
        if (attendanceRecord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AttendanceRecord result = attendanceRecordService.save(attendanceRecord);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attendanceRecord.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /attendance-records} : get all the attendanceRecords.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attendanceRecords in body.
     */
    @GetMapping("/attendance-records")
    public ResponseEntity<List<AttendanceRecord>> getAllAttendanceRecords(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of AttendanceRecords");
        Page<AttendanceRecord> page = attendanceRecordService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attendance-records/:id} : get the "id" attendanceRecord.
     *
     * @param id the id of the attendanceRecord to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attendanceRecord, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attendance-records/{id}")
    public ResponseEntity<AttendanceRecord> getAttendanceRecord(@PathVariable Long id) {
        log.debug("REST request to get AttendanceRecord : {}", id);
        Optional<AttendanceRecord> attendanceRecord = attendanceRecordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attendanceRecord);
    }

    /**
     * {@code DELETE  /attendance-records/:id} : delete the "id" attendanceRecord.
     *
     * @param id the id of the attendanceRecord to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attendance-records/{id}")
    public ResponseEntity<Void> deleteAttendanceRecord(@PathVariable Long id) {
        log.debug("REST request to delete AttendanceRecord : {}", id);
        attendanceRecordService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
