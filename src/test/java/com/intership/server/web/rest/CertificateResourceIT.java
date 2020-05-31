package com.intership.server.web.rest;

import com.intership.server.IntershipSystemApp;
import com.intership.server.domain.Certificate;
import com.intership.server.domain.Student;
import com.intership.server.repository.CertificateRepository;
import com.intership.server.service.CertificateService;
import com.intership.server.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.intership.server.web.rest.TestUtil.sameInstant;
import static com.intership.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link CertificateResource} REST controller.
 */
@SpringBootTest(classes = IntershipSystemApp.class)
public class CertificateResourceIT {

    private static final String DEFAULT_CERTIFICATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CERTIFICATE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CERTIFICATE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CERTIFICATE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CERTIFICATE_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_CERTIFICATE_LEVEL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CERTIFICATE_ACQUISITION_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CERTIFICATE_ACQUISITION_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CERTIFICATE_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_CERTIFICATE_PHOTO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MODIFY_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFY_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCertificateMockMvc;

    private Certificate certificate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CertificateResource certificateResource = new CertificateResource(certificateService);
        this.restCertificateMockMvc = MockMvcBuilders.standaloneSetup(certificateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Certificate createEntity(EntityManager em) {
        Certificate certificate = new Certificate()
            .certificateName(DEFAULT_CERTIFICATE_NAME)
            .certificateType(DEFAULT_CERTIFICATE_TYPE)
            .certificateLevel(DEFAULT_CERTIFICATE_LEVEL)
            .certificateAcquisitionTime(DEFAULT_CERTIFICATE_ACQUISITION_TIME)
            .certificatePhoto(DEFAULT_CERTIFICATE_PHOTO)
            .createdTime(DEFAULT_CREATED_TIME)
            .modifyTime(DEFAULT_MODIFY_TIME);
        return certificate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Certificate createUpdatedEntity(EntityManager em) {
        Certificate certificate = new Certificate()
            .certificateName(UPDATED_CERTIFICATE_NAME)
            .certificateType(UPDATED_CERTIFICATE_TYPE)
            .certificateLevel(UPDATED_CERTIFICATE_LEVEL)
            .certificateAcquisitionTime(UPDATED_CERTIFICATE_ACQUISITION_TIME)
            .certificatePhoto(UPDATED_CERTIFICATE_PHOTO)
            .createdTime(UPDATED_CREATED_TIME)
            .modifyTime(UPDATED_MODIFY_TIME);
        return certificate;
    }

    @BeforeEach
    public void initTest() {
        certificate = createEntity(em);
    }

    @Test
    @Transactional
    public void createCertificate() throws Exception {
        int databaseSizeBeforeCreate = certificateRepository.findAll().size();

        // Create the Certificate
        restCertificateMockMvc.perform(post("/api/certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificate)))
            .andExpect(status().isCreated());

        // Validate the Certificate in the database
        List<Certificate> certificateList = certificateRepository.findAll();
        assertThat(certificateList).hasSize(databaseSizeBeforeCreate + 1);
        Certificate testCertificate = certificateList.get(certificateList.size() - 1);
        assertThat(testCertificate.getCertificateName()).isEqualTo(DEFAULT_CERTIFICATE_NAME);
        assertThat(testCertificate.getCertificateType()).isEqualTo(DEFAULT_CERTIFICATE_TYPE);
        assertThat(testCertificate.getCertificateLevel()).isEqualTo(DEFAULT_CERTIFICATE_LEVEL);
        assertThat(testCertificate.getCertificateAcquisitionTime()).isEqualTo(DEFAULT_CERTIFICATE_ACQUISITION_TIME);
        assertThat(testCertificate.getCertificatePhoto()).isEqualTo(DEFAULT_CERTIFICATE_PHOTO);
        assertThat(testCertificate.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testCertificate.getModifyTime()).isEqualTo(DEFAULT_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void createCertificateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = certificateRepository.findAll().size();

        // Create the Certificate with an existing ID
        certificate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCertificateMockMvc.perform(post("/api/certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificate)))
            .andExpect(status().isBadRequest());

        // Validate the Certificate in the database
        List<Certificate> certificateList = certificateRepository.findAll();
        assertThat(certificateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCertificates() throws Exception {
        // Initialize the database
        certificateRepository.saveAndFlush(certificate);

        // Get all the certificateList
        restCertificateMockMvc.perform(get("/api/certificates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(certificate.getId().intValue())))
            .andExpect(jsonPath("$.[*].certificateName").value(hasItem(DEFAULT_CERTIFICATE_NAME.toString())))
            .andExpect(jsonPath("$.[*].certificateType").value(hasItem(DEFAULT_CERTIFICATE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].certificateLevel").value(hasItem(DEFAULT_CERTIFICATE_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].certificateAcquisitionTime").value(hasItem(sameInstant(DEFAULT_CERTIFICATE_ACQUISITION_TIME))))
            .andExpect(jsonPath("$.[*].certificatePhoto").value(hasItem(DEFAULT_CERTIFICATE_PHOTO.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(sameInstant(DEFAULT_CREATED_TIME))))
            .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(sameInstant(DEFAULT_MODIFY_TIME))));
    }

    @Test
    @Transactional
    public void getCertificate() throws Exception {
        // Initialize the database
        certificateRepository.saveAndFlush(certificate);

        // Get the certificate
        restCertificateMockMvc.perform(get("/api/certificates/{id}", certificate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(certificate.getId().intValue()))
            .andExpect(jsonPath("$.certificateName").value(DEFAULT_CERTIFICATE_NAME.toString()))
            .andExpect(jsonPath("$.certificateType").value(DEFAULT_CERTIFICATE_TYPE.toString()))
            .andExpect(jsonPath("$.certificateLevel").value(DEFAULT_CERTIFICATE_LEVEL.toString()))
            .andExpect(jsonPath("$.certificateAcquisitionTime").value(sameInstant(DEFAULT_CERTIFICATE_ACQUISITION_TIME)))
            .andExpect(jsonPath("$.certificatePhoto").value(DEFAULT_CERTIFICATE_PHOTO.toString()))
            .andExpect(jsonPath("$.createdTime").value(sameInstant(DEFAULT_CREATED_TIME)))
            .andExpect(jsonPath("$.modifyTime").value(sameInstant(DEFAULT_MODIFY_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingCertificate() throws Exception {
        // Get the certificate
        restCertificateMockMvc.perform(get("/api/certificates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCertificate() throws Exception {
        // Initialize the database
        certificateService.save(certificate);

        int databaseSizeBeforeUpdate = certificateRepository.findAll().size();

        // Update the certificate
        Certificate updatedCertificate = certificateRepository.findById(certificate.getId()).get();
        // Disconnect from session so that the updates on updatedCertificate are not directly saved in db
        em.detach(updatedCertificate);
        updatedCertificate
            .certificateName(UPDATED_CERTIFICATE_NAME)
            .certificateType(UPDATED_CERTIFICATE_TYPE)
            .certificateLevel(UPDATED_CERTIFICATE_LEVEL)
            .certificateAcquisitionTime(UPDATED_CERTIFICATE_ACQUISITION_TIME)
            .certificatePhoto(UPDATED_CERTIFICATE_PHOTO)
            .createdTime(UPDATED_CREATED_TIME)
            .modifyTime(UPDATED_MODIFY_TIME);

        restCertificateMockMvc.perform(put("/api/certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCertificate)))
            .andExpect(status().isOk());

        // Validate the Certificate in the database
        List<Certificate> certificateList = certificateRepository.findAll();
        assertThat(certificateList).hasSize(databaseSizeBeforeUpdate);
        Certificate testCertificate = certificateList.get(certificateList.size() - 1);
        assertThat(testCertificate.getCertificateName()).isEqualTo(UPDATED_CERTIFICATE_NAME);
        assertThat(testCertificate.getCertificateType()).isEqualTo(UPDATED_CERTIFICATE_TYPE);
        assertThat(testCertificate.getCertificateLevel()).isEqualTo(UPDATED_CERTIFICATE_LEVEL);
        assertThat(testCertificate.getCertificateAcquisitionTime()).isEqualTo(UPDATED_CERTIFICATE_ACQUISITION_TIME);
        assertThat(testCertificate.getCertificatePhoto()).isEqualTo(UPDATED_CERTIFICATE_PHOTO);
        assertThat(testCertificate.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testCertificate.getModifyTime()).isEqualTo(UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingCertificate() throws Exception {
        int databaseSizeBeforeUpdate = certificateRepository.findAll().size();

        // Create the Certificate

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCertificateMockMvc.perform(put("/api/certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificate)))
            .andExpect(status().isBadRequest());

        // Validate the Certificate in the database
        List<Certificate> certificateList = certificateRepository.findAll();
        assertThat(certificateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCertificate() throws Exception {
        // Initialize the database
        certificateService.save(certificate);

        int databaseSizeBeforeDelete = certificateRepository.findAll().size();

        // Delete the certificate
        restCertificateMockMvc.perform(delete("/api/certificates/{id}", certificate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Certificate> certificateList = certificateRepository.findAll();
        assertThat(certificateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Certificate.class);
        Certificate certificate1 = new Certificate();
        certificate1.setId(1L);
        Certificate certificate2 = new Certificate();
        certificate2.setId(certificate1.getId());
        assertThat(certificate1).isEqualTo(certificate2);
        certificate2.setId(2L);
        assertThat(certificate1).isNotEqualTo(certificate2);
        certificate1.setId((Student) null);
        assertThat(certificate1).isNotEqualTo(certificate2);
    }
}
