package com.intership.server.web.rest;

import com.intership.server.domain.SharingCenter;
import com.intership.server.service.SharingCenterService;
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
 * REST controller for managing {@link com.intership.server.domain.SharingCenter}.
 */
@RestController
@RequestMapping("/api")
public class SharingCenterResource {

    private final Logger log = LoggerFactory.getLogger(SharingCenterResource.class);

    private static final String ENTITY_NAME = "sharingCenter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SharingCenterService sharingCenterService;

    public SharingCenterResource(SharingCenterService sharingCenterService) {
        this.sharingCenterService = sharingCenterService;
    }

    @PostMapping("/sharing-centers")
    public ResponseEntity<SharingCenter> createSharingCenter(@RequestBody SharingCenter sharingCenter) throws URISyntaxException {
        log.debug("REST request to save SharingCenter : {}", sharingCenter);
        if (sharingCenter.getId() != null) {
            throw new BadRequestAlertException("A new sharingCenter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SharingCenter result = sharingCenterService.save(sharingCenter);
        return ResponseEntity.created(new URI("/api/sharing-centers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/sharing-centers")
    public ResponseEntity<SharingCenter> updateSharingCenter(@RequestBody SharingCenter sharingCenter) throws URISyntaxException {
        log.debug("REST request to update SharingCenter : {}", sharingCenter);
        if (sharingCenter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SharingCenter result = sharingCenterService.save(sharingCenter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sharingCenter.getId().toString()))
            .body(result);
    }

    @GetMapping("/sharing-centers")
    public ResponseEntity<List<SharingCenter>> getAllSharingCenters(
        Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        Page<SharingCenter> page = sharingCenterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/sharing-center")
    public ResponseEntity<Page<SharingCenter>> getSharingCentersByUploadAndDep(
        Pageable pageable, @RequestParam(required = false) String upload, @RequestParam(required = false) String dep) {
        Page<SharingCenter> page = null;
        if (upload != null || dep != null) {
            page = sharingCenterService.findByUploadAndDep(pageable, upload, dep);
        } else {
            page = sharingCenterService.findAll(pageable);
        }
        return ResponseEntity.ok(page);
    }

    @GetMapping("/sharing-centers/{id}")
    public ResponseEntity<SharingCenter> getSharingCenter(@PathVariable Long id) {
        log.debug("REST request to get SharingCenter : {}", id);
        Optional<SharingCenter> sharingCenter = sharingCenterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sharingCenter);
    }

    @DeleteMapping("/sharing-centers/{id}")
    public ResponseEntity<Void> deleteSharingCenter(@PathVariable Long id) {
        log.debug("REST request to delete SharingCenter : {}", id);
        sharingCenterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
