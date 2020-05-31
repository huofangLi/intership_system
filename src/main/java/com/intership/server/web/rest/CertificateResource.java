package com.intership.server.web.rest;

import com.intership.server.domain.Certificate;
import com.intership.server.service.CertificateService;
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
 * REST controller for managing {@link com.intership.server.domain.Certificate}.
 */
@RestController
@RequestMapping("/api")
public class CertificateResource {

    private final Logger log = LoggerFactory.getLogger(CertificateResource.class);

    private static final String ENTITY_NAME = "certificate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CertificateService certificateService;

    public CertificateResource(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    /**
     * {@code POST  /certificates} : Create a new certificate.
     *
     * @param certificate the certificate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new certificate, or with status {@code 400 (Bad Request)} if the certificate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/certificates")
    public ResponseEntity<Certificate> createCertificate(@RequestBody Certificate certificate) throws URISyntaxException {
        log.debug("REST request to save Certificate : {}", certificate);
        if (certificate.getId() != null) {
            throw new BadRequestAlertException("A new certificate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Certificate result = certificateService.save(certificate);
        return ResponseEntity.created(new URI("/api/certificates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /certificates} : Updates an existing certificate.
     *
     * @param certificate the certificate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated certificate,
     * or with status {@code 400 (Bad Request)} if the certificate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the certificate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/certificates")
    public ResponseEntity<Certificate> updateCertificate(@RequestBody Certificate certificate) throws URISyntaxException {
        log.debug("REST request to update Certificate : {}", certificate);
        if (certificate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Certificate result = certificateService.save(certificate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, certificate.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /certificates} : get all the certificates.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of certificates in body.
     */
    @GetMapping("/certificates")
    public ResponseEntity<List<Certificate>> getAllCertificates(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Certificates");
        Page<Certificate> page = certificateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /certificates/:id} : get the "id" certificate.
     *
     * @param id the id of the certificate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the certificate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/certificates/{id}")
    public ResponseEntity<Certificate> getCertificate(@PathVariable Long id) {
        log.debug("REST request to get Certificate : {}", id);
        Optional<Certificate> certificate = certificateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(certificate);
    }

    /**
     * {@code DELETE  /certificates/:id} : delete the "id" certificate.
     *
     * @param id the id of the certificate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/certificates/{id}")
    public ResponseEntity<Void> deleteCertificate(@PathVariable Long id) {
        log.debug("REST request to delete Certificate : {}", id);
        certificateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     *通过学生 ID 查找证书
     * @param stuId
     * @return
     */
    @GetMapping("/certificate/{stuId}")
    public  ResponseEntity<List<Certificate>> getCertificateByStuId(@PathVariable Long stuId){
        List<Certificate> list = certificateService.findByStuId(stuId);
        return ResponseEntity.ok(list);
    }

}
