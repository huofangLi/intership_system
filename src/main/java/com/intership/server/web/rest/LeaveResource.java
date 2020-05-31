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

    /**
     * {@code POST  /leaves} : Create a new leave.
     *
     * @param leave the leave to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leave, or with status {@code 400 (Bad Request)} if the leave has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
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

    /**
     * {@code PUT  /leaves} : Updates an existing leave.
     *
     * @param leave the leave to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leave,
     * or with status {@code 400 (Bad Request)} if the leave is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leave couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
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

    /**
     * {@code GET  /leaves} : get all the leaves.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaves in body.
     */
    @GetMapping("/leaves")
    public ResponseEntity<List<Leave>> getAllLeaves(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Leaves");
        Page<Leave> page = leaveService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leaves/:id} : get the "id" leave.
     *
     * @param id the id of the leave to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leave, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leaves/{id}")
    public ResponseEntity<Leave> getLeave(@PathVariable Long id) {
        log.debug("REST request to get Leave : {}", id);
        Optional<Leave> leave = leaveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leave);
    }

    /**
     * {@code DELETE  /leaves/:id} : delete the "id" leave.
     *
     * @param id the id of the leave to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leaves/{id}")
    public ResponseEntity<Void> deleteLeave(@PathVariable Long id) {
        log.debug("REST request to delete Leave : {}", id);
        leaveService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * 根据学生 ID 查询实习请假记录
     * @param stuId
     * @return
     */
    @GetMapping("/leave/{stuId}")
    public  ResponseEntity<List<Leave>> getLeaveByStuId(@PathVariable Long stuId){
        List<Leave> list = leaveService.findByStuId(stuId);
        return ResponseEntity.ok(list);
    }
}
