package com.intership.server.web.rest;

import com.intership.server.domain.AttendanceManagement;
import com.intership.server.service.AttendanceManagementService;
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
 * REST controller for managing {@link com.intership.server.domain.AttendanceManagement}.
 */
@RestController
@RequestMapping("/api")
public class AttendanceManagementResource {

    private final Logger log = LoggerFactory.getLogger(AttendanceManagementResource.class);

    private static final String ENTITY_NAME = "attendanceManagement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttendanceManagementService attendanceManagementService;

    public AttendanceManagementResource(AttendanceManagementService attendanceManagementService) {
        this.attendanceManagementService = attendanceManagementService;
    }

    /**
     * {@code POST  /attendance-managements} : Create a new attendanceManagement.
     *
     * @param attendanceManagement the attendanceManagement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attendanceManagement, or with status {@code 400 (Bad Request)} if the attendanceManagement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attendance-managements")
    public ResponseEntity<AttendanceManagement> createAttendanceManagement(@RequestBody AttendanceManagement attendanceManagement) throws URISyntaxException {
        log.debug("REST request to save AttendanceManagement : {}", attendanceManagement);
        if (attendanceManagement.getId() != null) {
            throw new BadRequestAlertException("A new attendanceManagement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttendanceManagement result = attendanceManagementService.save(attendanceManagement);
        return ResponseEntity.created(new URI("/api/attendance-managements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attendance-managements} : Updates an existing attendanceManagement.
     *
     * @param attendanceManagement the attendanceManagement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attendanceManagement,
     * or with status {@code 400 (Bad Request)} if the attendanceManagement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attendanceManagement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attendance-managements")
    public ResponseEntity<AttendanceManagement> updateAttendanceManagement(@RequestBody AttendanceManagement attendanceManagement) throws URISyntaxException {
        log.debug("REST request to update AttendanceManagement : {}", attendanceManagement);
        if (attendanceManagement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AttendanceManagement result = attendanceManagementService.save(attendanceManagement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attendanceManagement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /attendance-managements} : get all the attendanceManagements.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attendanceManagements in body.
     */
    @GetMapping("/attendance-managements")
    public ResponseEntity<List<AttendanceManagement>> getAllAttendanceManagements(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of AttendanceManagements");
        Page<AttendanceManagement> page = attendanceManagementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attendance-managements/:id} : get the "id" attendanceManagement.
     *
     * @param id the id of the attendanceManagement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attendanceManagement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attendance-managements/{id}")
    public ResponseEntity<AttendanceManagement> getAttendanceManagement(@PathVariable Long id) {
        log.debug("REST request to get AttendanceManagement : {}", id);
        Optional<AttendanceManagement> attendanceManagement = attendanceManagementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attendanceManagement);
    }

    /**
     * {@code DELETE  /attendance-managements/:id} : delete the "id" attendanceManagement.
     *
     * @param id the id of the attendanceManagement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attendance-managements/{id}")
    public ResponseEntity<Void> deleteAttendanceManagement(@PathVariable Long id) {
        log.debug("REST request to delete AttendanceManagement : {}", id);
        attendanceManagementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * 根据学生 ID 查询实习签到
     * @param stuId
     * @return
     */
    @GetMapping("/attendance-management/{stuId}")
    public  ResponseEntity<List<AttendanceManagement>> getAttendanceManagementByStuId(@PathVariable Long stuId){
        List<AttendanceManagement> list = attendanceManagementService.findByStuId(stuId);
        return ResponseEntity.ok(list);
    }
}
