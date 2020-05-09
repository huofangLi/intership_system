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

    /**
     * {@code POST  /sharing-centers} : Create a new sharingCenter.
     *
     * @param sharingCenter the sharingCenter to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sharingCenter, or with status {@code 400 (Bad Request)} if the sharingCenter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
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

    /**
     * {@code PUT  /sharing-centers} : Updates an existing sharingCenter.
     *
     * @param sharingCenter the sharingCenter to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sharingCenter,
     * or with status {@code 400 (Bad Request)} if the sharingCenter is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sharingCenter couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
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

    /**
     * {@code GET  /sharing-centers} : get all the sharingCenters.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sharingCenters in body.
     */
    @GetMapping("/sharing-centers")
    public ResponseEntity<List<SharingCenter>> getAllSharingCenters(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of SharingCenters");
        Page<SharingCenter> page = sharingCenterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sharing-centers/:id} : get the "id" sharingCenter.
     *
     * @param id the id of the sharingCenter to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sharingCenter, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sharing-centers/{id}")
    public ResponseEntity<SharingCenter> getSharingCenter(@PathVariable Long id) {
        log.debug("REST request to get SharingCenter : {}", id);
        Optional<SharingCenter> sharingCenter = sharingCenterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sharingCenter);
    }

    /**
     * {@code DELETE  /sharing-centers/:id} : delete the "id" sharingCenter.
     *
     * @param id the id of the sharingCenter to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sharing-centers/{id}")
    public ResponseEntity<Void> deleteSharingCenter(@PathVariable Long id) {
        log.debug("REST request to delete SharingCenter : {}", id);
        sharingCenterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
