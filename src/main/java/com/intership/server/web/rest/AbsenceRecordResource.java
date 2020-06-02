package com.intership.server.web.rest;

import com.intership.server.domain.AbsenceRecord;
import com.intership.server.service.AbsenceRecordService;
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
 * REST controller for managing {@link com.intership.server.domain.AbsenceRecord}.
 */
@RestController
@RequestMapping("/api")
public class AbsenceRecordResource {

    private final Logger log = LoggerFactory.getLogger(AbsenceRecordResource.class);

    private static final String ENTITY_NAME = "absenceRecord";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AbsenceRecordService absenceRecordService;

    public AbsenceRecordResource(AbsenceRecordService absenceRecordService) {
        this.absenceRecordService = absenceRecordService;
    }

    @PostMapping("/absence-records")
    public ResponseEntity<AbsenceRecord> createAbsenceRecord(@RequestBody AbsenceRecord absenceRecord) throws URISyntaxException {
        log.debug("REST request to save AbsenceRecord : {}", absenceRecord);
        if (absenceRecord.getId() != null) {
            throw new BadRequestAlertException("A new absenceRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AbsenceRecord result = absenceRecordService.save(absenceRecord);
        return ResponseEntity.created(new URI("/api/absence-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/absence-records")
    public ResponseEntity<AbsenceRecord> updateAbsenceRecord(@RequestBody AbsenceRecord absenceRecord) throws URISyntaxException {
        log.debug("REST request to update AbsenceRecord : {}", absenceRecord);
        if (absenceRecord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AbsenceRecord result = absenceRecordService.save(absenceRecord);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, absenceRecord.getId().toString()))
            .body(result);
    }

    @GetMapping("/absence-records")
    public ResponseEntity<List<AbsenceRecord>> getAllAbsenceRecords(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of AbsenceRecords");
        Page<AbsenceRecord> page = absenceRecordService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/absence-records/{id}")
    public ResponseEntity<AbsenceRecord> getAbsenceRecord(@PathVariable Long id) {
        log.debug("REST request to get AbsenceRecord : {}", id);
        Optional<AbsenceRecord> absenceRecord = absenceRecordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(absenceRecord);
    }

    @DeleteMapping("/absence-records/{id}")
    public ResponseEntity<Void> deleteAbsenceRecord(@PathVariable Long id) {
        log.debug("REST request to delete AbsenceRecord : {}", id);
        absenceRecordService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }


    @GetMapping("/absence-record/{stuId}")
    public  ResponseEntity<List<AbsenceRecord>> getCAbsenceRecordByStuId(@PathVariable Long stuId){
        List<AbsenceRecord> list =  absenceRecordService.findByStuId(stuId);
        return ResponseEntity.ok(list);
    }


}
