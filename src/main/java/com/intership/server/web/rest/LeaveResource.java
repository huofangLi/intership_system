package com.intership.server.web.rest;

import com.intership.server.domain.Leave;
import com.intership.server.service.LeaveService;
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
 * REST controller for managing {@link com.intership.server.domain.Leave}.
 */
@RestController
@RequestMapping("/api")
public class LeaveResource {

    private final Logger log = LoggerFactory.getLogger(LeaveResource.class);

    private static final String ENTITY_NAME = "leave";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveService leaveService;

    public LeaveResource(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping("/leaves")
    public ResponseEntity<Leave> createLeave(@RequestBody Leave leave) throws URISyntaxException {
        log.debug("REST request to save Leave : {}", leave);
        if (leave.getId() != null) {
            throw new BadRequestAlertException("A new leave cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Leave result = leaveService.save(leave);
        return ResponseEntity.created(new URI("/api/leaves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/leaves")
    public ResponseEntity<Leave> updateLeave(@RequestBody Leave leave) throws URISyntaxException {
        log.debug("REST request to update Leave : {}", leave);
        if (leave.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Leave result = leaveService.save(leave);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, leave.getId().toString()))
            .body(result);
    }

    @GetMapping("/leaves")
    public ResponseEntity<List<Leave>> getAllLeaves(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Leaves");
        Page<Leave> page = leaveService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/leaves/{id}")
    public ResponseEntity<Leave> getLeave(@PathVariable Long id) {
        log.debug("REST request to get Leave : {}", id);
        Optional<Leave> leave = leaveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leave);
    }

    @DeleteMapping("/leaves/{id}")
    public ResponseEntity<Void> deleteLeave(@PathVariable Long id) {
        log.debug("REST request to delete Leave : {}", id);
        leaveService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/leave/{stuId}")
    public  ResponseEntity<List<Leave>> getLeaveByStuId(@PathVariable Long stuId){
        List<Leave> list = leaveService.findByStuId(stuId);
        return ResponseEntity.ok(list);
    }
}
