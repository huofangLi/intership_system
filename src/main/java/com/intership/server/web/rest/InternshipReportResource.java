package com.intership.server.web.rest;

import com.intership.server.domain.InternshipReport;
import com.intership.server.service.InternshipReportService;
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
 * REST controller for managing {@link com.intership.server.domain.InternshipReport}.
 */
@RestController
@RequestMapping("/api")
public class InternshipReportResource {

    private final Logger log = LoggerFactory.getLogger(InternshipReportResource.class);

    private static final String ENTITY_NAME = "internshipReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InternshipReportService internshipReportService;

    public InternshipReportResource(InternshipReportService internshipReportService) {
        this.internshipReportService = internshipReportService;
    }

    /**
     * {@code POST  /internship-reports} : Create a new internshipReport.
     *
     * @param internshipReport the internshipReport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new internshipReport, or with status {@code 400 (Bad Request)} if the internshipReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/internship-reports")
    public ResponseEntity<InternshipReport> createInternshipReport(@RequestBody InternshipReport internshipReport) throws URISyntaxException {
        log.debug("REST request to save InternshipReport : {}", internshipReport);
        if (internshipReport.getId() != null) {
            throw new BadRequestAlertException("A new internshipReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InternshipReport result = internshipReportService.save(internshipReport);
        return ResponseEntity.created(new URI("/api/internship-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /internship-reports} : Updates an existing internshipReport.
     *
     * @param internshipReport the internshipReport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated internshipReport,
     * or with status {@code 400 (Bad Request)} if the internshipReport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the internshipReport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/internship-reports")
    public ResponseEntity<InternshipReport> updateInternshipReport(@RequestBody InternshipReport internshipReport) throws URISyntaxException {
        log.debug("REST request to update InternshipReport : {}", internshipReport);
        if (internshipReport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InternshipReport result = internshipReportService.save(internshipReport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, internshipReport.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /internship-reports} : get all the internshipReports.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of internshipReports in body.
     */
    @GetMapping("/internship-reports")
    public ResponseEntity<List<InternshipReport>> getAllInternshipReports(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of InternshipReports");
        Page<InternshipReport> page = internshipReportService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /internship-reports/:id} : get the "id" internshipReport.
     *
     * @param id the id of the internshipReport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the internshipReport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/internship-reports/{id}")
    public ResponseEntity<InternshipReport> getInternshipReport(@PathVariable Long id) {
        log.debug("REST request to get InternshipReport : {}", id);
        Optional<InternshipReport> internshipReport = internshipReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(internshipReport);
    }

    /**
     * {@code DELETE  /internship-reports/:id} : delete the "id" internshipReport.
     *
     * @param id the id of the internshipReport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/internship-reports/{id}")
    public ResponseEntity<Void> deleteInternshipReport(@PathVariable Long id) {
        log.debug("REST request to delete InternshipReport : {}", id);
        internshipReportService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * 根据实习表 ID 查询实习报告
     * @param interId
     * @return
     */
    @GetMapping("/internship-report/{interId}")
    public  ResponseEntity<List<InternshipReport>> getInternshipReportByInterId(@PathVariable Long interId){
        List<InternshipReport> list = internshipReportService.findByInterId(interId);
        return ResponseEntity.ok(list);
    }
}
