package com.intership.server.web.rest;

import com.intership.server.domain.InternshipTask;
import com.intership.server.service.InternshipTaskService;
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
 * REST controller for managing {@link com.intership.server.domain.InternshipTask}.
 */
@RestController
@RequestMapping("/api")
public class InternshipTaskResource {

    private final Logger log = LoggerFactory.getLogger(InternshipTaskResource.class);

    private static final String ENTITY_NAME = "internshipTask";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InternshipTaskService internshipTaskService;

    public InternshipTaskResource(InternshipTaskService internshipTaskService) {
        this.internshipTaskService = internshipTaskService;
    }

    @PostMapping("/internship-tasks")
    public ResponseEntity<InternshipTask> createInternshipTask(@RequestBody InternshipTask internshipTask) throws URISyntaxException {
        log.debug("REST request to save InternshipTask : {}", internshipTask);
        if (internshipTask.getId() != null) {
            throw new BadRequestAlertException("A new internshipTask cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InternshipTask result = internshipTaskService.save(internshipTask);
        return ResponseEntity.created(new URI("/api/internship-tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/internship-tasks")
    public ResponseEntity<InternshipTask> updateInternshipTask(@RequestBody InternshipTask internshipTask) throws URISyntaxException {
        log.debug("REST request to update InternshipTask : {}", internshipTask);
        if (internshipTask.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InternshipTask result = internshipTaskService.save(internshipTask);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, internshipTask.getId().toString()))
            .body(result);
    }

    @GetMapping("/internship-tasks")
    public ResponseEntity<List<InternshipTask>> getAllInternshipTasks(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of InternshipTasks");
        Page<InternshipTask> page = internshipTaskService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/internship-tasks/{id}")
    public ResponseEntity<InternshipTask> getInternshipTask(@PathVariable Long id) {
        log.debug("REST request to get InternshipTask : {}", id);
        Optional<InternshipTask> internshipTask = internshipTaskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(internshipTask);
    }

    @DeleteMapping("/internship-tasks/{id}")
    public ResponseEntity<Void> deleteInternshipTask(@PathVariable Long id) {
        log.debug("REST request to delete InternshipTask : {}", id);
        internshipTaskService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/internship-task/{interId}")
    public ResponseEntity<List<InternshipTask>> getInternshipTaskByInterId(@PathVariable Long interId) {
        List<InternshipTask> list = internshipTaskService.findByInterId(interId);
        return ResponseEntity.ok(list);
    }
}
