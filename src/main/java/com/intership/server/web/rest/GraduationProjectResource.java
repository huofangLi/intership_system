package com.intership.server.web.rest;

import com.intership.server.domain.GraduationProject;
import com.intership.server.service.GraduationProjectService;
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
 * REST controller for managing {@link com.intership.server.domain.GraduationProject}.
 */
@RestController
@RequestMapping("/api")
public class GraduationProjectResource {

    private final Logger log = LoggerFactory.getLogger(GraduationProjectResource.class);

    private static final String ENTITY_NAME = "graduationProject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GraduationProjectService graduationProjectService;

    public GraduationProjectResource(GraduationProjectService graduationProjectService) {
        this.graduationProjectService = graduationProjectService;
    }

    @PostMapping("/graduation-projects")
    public ResponseEntity<GraduationProject> createGraduationProject(@RequestBody GraduationProject graduationProject) throws URISyntaxException {
        log.debug("REST request to save GraduationProject : {}", graduationProject);
        if (graduationProject.getId() != null) {
            throw new BadRequestAlertException("A new graduationProject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GraduationProject result = graduationProjectService.save(graduationProject);
        return ResponseEntity.created(new URI("/api/graduation-projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/graduation-projects")
    public ResponseEntity<GraduationProject> updateGraduationProject(@RequestBody GraduationProject graduationProject) throws URISyntaxException {
        log.debug("REST request to update GraduationProject : {}", graduationProject);
        if (graduationProject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GraduationProject result = graduationProjectService.save(graduationProject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, graduationProject.getId().toString()))
            .body(result);
    }

    @GetMapping("/graduation-projects")
    public ResponseEntity<List<GraduationProject>> getAllGraduationProjects(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of GraduationProjects");
        Page<GraduationProject> page = graduationProjectService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/graduation-projects/{id}")
    public ResponseEntity<GraduationProject> getGraduationProject(@PathVariable Long id) {
        log.debug("REST request to get GraduationProject : {}", id);
        Optional<GraduationProject> graduationProject = graduationProjectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(graduationProject);
    }

    @DeleteMapping("/graduation-projects/{id}")
    public ResponseEntity<Void> deleteGraduationProject(@PathVariable Long id) {
        log.debug("REST request to delete GraduationProject : {}", id);
        graduationProjectService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
