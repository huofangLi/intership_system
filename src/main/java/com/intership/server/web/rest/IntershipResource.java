package com.intership.server.web.rest;

import com.intership.server.domain.Intership;
import com.intership.server.service.IntershipService;
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
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing {@link com.intership.server.domain.Intership}.
 */
@RestController
@RequestMapping("/api")
public class IntershipResource {

    private final Logger log = LoggerFactory.getLogger(IntershipResource.class);

    private static final String ENTITY_NAME = "intership";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntershipService intershipService;

    public IntershipResource(IntershipService intershipService) {
        this.intershipService = intershipService;
    }

    /**
     * {@code POST  /interships} : Create a new intership.
     *
     * @param intership the intership to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new intership, or with status {@code 400 (Bad Request)} if the intership has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/interships")
    public ResponseEntity<Intership> createIntership(@RequestBody Intership intership) throws URISyntaxException {
        log.debug("REST request to save Intership : {}", intership);
        if (intership.getId() != null) {
            throw new BadRequestAlertException("A new intership cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Intership result = intershipService.save(intership);
        return ResponseEntity.created(new URI("/api/interships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /interships} : Updates an existing intership.
     *
     * @param intership the intership to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated intership,
     * or with status {@code 400 (Bad Request)} if the intership is not valid,
     * or with status {@code 500 (Internal Server Error)} if the intership couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/interships")
    public ResponseEntity<Intership> updateIntership(@RequestBody Intership intership) throws URISyntaxException {
        log.debug("REST request to update Intership : {}", intership);
        if (intership.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Intership result = intershipService.save(intership);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, intership.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /interships} : get all the interships.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interships in body.
     */
    @GetMapping("/interships")
    public ResponseEntity<List<Intership>> getAllInterships(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Interships");
        Page<Intership> page = intershipService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /interships/:id} : get the "id" intership.
     *
     * @param id the id of the intership to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the intership, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/interships/{id}")
    public ResponseEntity<Intership> getIntership(@PathVariable Long id) {
        log.debug("REST request to get Intership : {}", id);
        Optional<Intership> intership = intershipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(intership);
    }

    /**
     * {@code DELETE  /interships/:id} : delete the "id" intership.
     *
     * @param id the id of the intership to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/interships/{id}")
    public ResponseEntity<Void> deleteIntership(@PathVariable Long id) {
        log.debug("REST request to delete Intership : {}", id);
        intershipService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * 根据学生 ID 查询实习
     * @param stuId
     * @return
     */
    @GetMapping("/intership/{stuId}")
    public  ResponseEntity<List<Intership>> getIntershipByStuId(@PathVariable Long stuId){
        List<Intership> list= intershipService.findByStuId(stuId);
        return ResponseEntity.ok(list);
    }

    /**
     * 根据学生 ID 查询实习成绩
     * @param stuId
     * @return
     */
    @GetMapping("/grade/{stuId}")
    public ResponseEntity<List<Map<String, Object>>> getGradeByStuId(@PathVariable Long stuId) {
        List<Map<String, Object>> grade = intershipService.findGradeByStuId(stuId);
        return ResponseEntity.ok(grade);
    }
}
