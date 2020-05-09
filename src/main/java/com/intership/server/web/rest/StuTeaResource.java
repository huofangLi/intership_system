package com.intership.server.web.rest;

import com.intership.server.domain.StuTea;
import com.intership.server.service.StuTeaService;
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
 * REST controller for managing {@link com.intership.server.domain.StuTea}.
 */
@RestController
@RequestMapping("/api")
public class StuTeaResource {

    private final Logger log = LoggerFactory.getLogger(StuTeaResource.class);

    private static final String ENTITY_NAME = "stuTea";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StuTeaService stuTeaService;

    public StuTeaResource(StuTeaService stuTeaService) {
        this.stuTeaService = stuTeaService;
    }

    /**
     * {@code POST  /stu-teas} : Create a new stuTea.
     *
     * @param stuTea the stuTea to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stuTea, or with status {@code 400 (Bad Request)} if the stuTea has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stu-teas")
    public ResponseEntity<StuTea> createStuTea(@RequestBody StuTea stuTea) throws URISyntaxException {
        log.debug("REST request to save StuTea : {}", stuTea);
        if (stuTea.getId() != null) {
            throw new BadRequestAlertException("A new stuTea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StuTea result = stuTeaService.save(stuTea);
        return ResponseEntity.created(new URI("/api/stu-teas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stu-teas} : Updates an existing stuTea.
     *
     * @param stuTea the stuTea to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stuTea,
     * or with status {@code 400 (Bad Request)} if the stuTea is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stuTea couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stu-teas")
    public ResponseEntity<StuTea> updateStuTea(@RequestBody StuTea stuTea) throws URISyntaxException {
        log.debug("REST request to update StuTea : {}", stuTea);
        if (stuTea.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StuTea result = stuTeaService.save(stuTea);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stuTea.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stu-teas} : get all the stuTeas.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stuTeas in body.
     */
    @GetMapping("/stu-teas")
    public ResponseEntity<List<StuTea>> getAllStuTeas(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of StuTeas");
        Page<StuTea> page = stuTeaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stu-teas/:id} : get the "id" stuTea.
     *
     * @param id the id of the stuTea to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stuTea, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stu-teas/{id}")
    public ResponseEntity<StuTea> getStuTea(@PathVariable Long id) {
        log.debug("REST request to get StuTea : {}", id);
        Optional<StuTea> stuTea = stuTeaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stuTea);
    }

    /**
     * {@code DELETE  /stu-teas/:id} : delete the "id" stuTea.
     *
     * @param id the id of the stuTea to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stu-teas/{id}")
    public ResponseEntity<Void> deleteStuTea(@PathVariable Long id) {
        log.debug("REST request to delete StuTea : {}", id);
        stuTeaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
