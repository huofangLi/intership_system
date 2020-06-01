package com.intership.server.web.rest;

import com.intership.server.domain.JobChangeRecords;
import com.intership.server.service.JobChangeRecordsService;
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
 * REST controller for managing {@link com.intership.server.domain.JobChangeRecords}.
 */
@RestController
@RequestMapping("/api")
public class JobChangeRecordsResource {

    private final Logger log = LoggerFactory.getLogger(JobChangeRecordsResource.class);

    private static final String ENTITY_NAME = "jobChangeRecords";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobChangeRecordsService jobChangeRecordsService;

    public JobChangeRecordsResource(JobChangeRecordsService jobChangeRecordsService) {
        this.jobChangeRecordsService = jobChangeRecordsService;
    }

    /**
     * {@code POST  /job-change-records} : Create a new jobChangeRecords.
     *
     * @param jobChangeRecords the jobChangeRecords to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobChangeRecords, or with status {@code 400 (Bad Request)} if the jobChangeRecords has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-change-records")
    public ResponseEntity<JobChangeRecords> createJobChangeRecords(@RequestBody JobChangeRecords jobChangeRecords) throws URISyntaxException {
        log.debug("REST request to save JobChangeRecords : {}", jobChangeRecords);
        if (jobChangeRecords.getId() != null) {
            throw new BadRequestAlertException("A new jobChangeRecords cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobChangeRecords result = jobChangeRecordsService.save(jobChangeRecords);
        return ResponseEntity.created(new URI("/api/job-change-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-change-records} : Updates an existing jobChangeRecords.
     *
     * @param jobChangeRecords the jobChangeRecords to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobChangeRecords,
     * or with status {@code 400 (Bad Request)} if the jobChangeRecords is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobChangeRecords couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-change-records")
    public ResponseEntity<JobChangeRecords> updateJobChangeRecords(@RequestBody JobChangeRecords jobChangeRecords) throws URISyntaxException {
        log.debug("REST request to update JobChangeRecords : {}", jobChangeRecords);
        if (jobChangeRecords.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JobChangeRecords result = jobChangeRecordsService.save(jobChangeRecords);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobChangeRecords.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /job-change-records} : get all the jobChangeRecords.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobChangeRecords in body.
     */
    @GetMapping("/job-change-records")
    public ResponseEntity<List<JobChangeRecords>> getAllJobChangeRecords(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of JobChangeRecords");
        Page<JobChangeRecords> page = jobChangeRecordsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /job-change-records/:id} : get the "id" jobChangeRecords.
     *
     * @param id the id of the jobChangeRecords to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobChangeRecords, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-change-records/{id}")
    public ResponseEntity<JobChangeRecords> getJobChangeRecords(@PathVariable Long id) {
        log.debug("REST request to get JobChangeRecords : {}", id);
        Optional<JobChangeRecords> jobChangeRecords = jobChangeRecordsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobChangeRecords);
    }

    /**
     * {@code DELETE  /job-change-records/:id} : delete the "id" jobChangeRecords.
     *
     * @param id the id of the jobChangeRecords to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-change-records/{id}")
    public ResponseEntity<Void> deleteJobChangeRecords(@PathVariable Long id) {
        log.debug("REST request to delete JobChangeRecords : {}", id);
        jobChangeRecordsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * 根据实习表 ID 查询变更记录
     * @param interId
     * @return
     */
    @GetMapping("/job-change-record/{interId}")
    public ResponseEntity<List<JobChangeRecords>> getInternshipTaskByInterId(@PathVariable Long interId) {
        List<JobChangeRecords> list = jobChangeRecordsService.findByInterId(interId);
        return ResponseEntity.ok(list);
    }
}
