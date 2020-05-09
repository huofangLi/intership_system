package com.intership.server.web.rest;

import com.intership.server.IntershipSystemApp;
import com.intership.server.domain.SharingCenter;
import com.intership.server.repository.SharingCenterRepository;
import com.intership.server.service.SharingCenterService;
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
 * Integration tests for the {@Link SharingCenterResource} REST controller.
 */
@SpringBootTest(classes = IntershipSystemApp.class)
public class SharingCenterResourceIT {

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_FILE_SIZE = "BBBBBBBBBB";

    private static final String DEFAULT_UPLOADED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPLOADED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT = "BBBBBBBBBB";

    private static final String DEFAULT_OPERATING = "AAAAAAAAAA";
    private static final String UPDATED_OPERATING = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MODIFY_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFY_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SharingCenterRepository sharingCenterRepository;

    @Autowired
    private SharingCenterService sharingCenterService;

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

    private MockMvc restSharingCenterMockMvc;

    private SharingCenter sharingCenter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SharingCenterResource sharingCenterResource = new SharingCenterResource(sharingCenterService);
        this.restSharingCenterMockMvc = MockMvcBuilders.standaloneSetup(sharingCenterResource)
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
    public static SharingCenter createEntity(EntityManager em) {
        SharingCenter sharingCenter = new SharingCenter()
            .fileName(DEFAULT_FILE_NAME)
            .fileSize(DEFAULT_FILE_SIZE)
            .uploadedBy(DEFAULT_UPLOADED_BY)
            .department(DEFAULT_DEPARTMENT)
            .operating(DEFAULT_OPERATING)
            .createdTime(DEFAULT_CREATED_TIME)
            .modifyTime(DEFAULT_MODIFY_TIME);
        return sharingCenter;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SharingCenter createUpdatedEntity(EntityManager em) {
        SharingCenter sharingCenter = new SharingCenter()
            .fileName(UPDATED_FILE_NAME)
            .fileSize(UPDATED_FILE_SIZE)
            .uploadedBy(UPDATED_UPLOADED_BY)
            .department(UPDATED_DEPARTMENT)
            .operating(UPDATED_OPERATING)
            .createdTime(UPDATED_CREATED_TIME)
            .modifyTime(UPDATED_MODIFY_TIME);
        return sharingCenter;
    }

    @BeforeEach
    public void initTest() {
        sharingCenter = createEntity(em);
    }

    @Test
    @Transactional
    public void createSharingCenter() throws Exception {
        int databaseSizeBeforeCreate = sharingCenterRepository.findAll().size();

        // Create the SharingCenter
        restSharingCenterMockMvc.perform(post("/api/sharing-centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sharingCenter)))
            .andExpect(status().isCreated());

        // Validate the SharingCenter in the database
        List<SharingCenter> sharingCenterList = sharingCenterRepository.findAll();
        assertThat(sharingCenterList).hasSize(databaseSizeBeforeCreate + 1);
        SharingCenter testSharingCenter = sharingCenterList.get(sharingCenterList.size() - 1);
        assertThat(testSharingCenter.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testSharingCenter.getFileSize()).isEqualTo(DEFAULT_FILE_SIZE);
        assertThat(testSharingCenter.getUploadedBy()).isEqualTo(DEFAULT_UPLOADED_BY);
        assertThat(testSharingCenter.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testSharingCenter.getOperating()).isEqualTo(DEFAULT_OPERATING);
        assertThat(testSharingCenter.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testSharingCenter.getModifyTime()).isEqualTo(DEFAULT_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void createSharingCenterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sharingCenterRepository.findAll().size();

        // Create the SharingCenter with an existing ID
        sharingCenter.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSharingCenterMockMvc.perform(post("/api/sharing-centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sharingCenter)))
            .andExpect(status().isBadRequest());

        // Validate the SharingCenter in the database
        List<SharingCenter> sharingCenterList = sharingCenterRepository.findAll();
        assertThat(sharingCenterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSharingCenters() throws Exception {
        // Initialize the database
        sharingCenterRepository.saveAndFlush(sharingCenter);

        // Get all the sharingCenterList
        restSharingCenterMockMvc.perform(get("/api/sharing-centers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sharingCenter.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].fileSize").value(hasItem(DEFAULT_FILE_SIZE.toString())))
            .andExpect(jsonPath("$.[*].uploadedBy").value(hasItem(DEFAULT_UPLOADED_BY.toString())))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT.toString())))
            .andExpect(jsonPath("$.[*].operating").value(hasItem(DEFAULT_OPERATING.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(sameInstant(DEFAULT_CREATED_TIME))))
            .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(sameInstant(DEFAULT_MODIFY_TIME))));
    }
    
    @Test
    @Transactional
    public void getSharingCenter() throws Exception {
        // Initialize the database
        sharingCenterRepository.saveAndFlush(sharingCenter);

        // Get the sharingCenter
        restSharingCenterMockMvc.perform(get("/api/sharing-centers/{id}", sharingCenter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sharingCenter.getId().intValue()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()))
            .andExpect(jsonPath("$.fileSize").value(DEFAULT_FILE_SIZE.toString()))
            .andExpect(jsonPath("$.uploadedBy").value(DEFAULT_UPLOADED_BY.toString()))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT.toString()))
            .andExpect(jsonPath("$.operating").value(DEFAULT_OPERATING.toString()))
            .andExpect(jsonPath("$.createdTime").value(sameInstant(DEFAULT_CREATED_TIME)))
            .andExpect(jsonPath("$.modifyTime").value(sameInstant(DEFAULT_MODIFY_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingSharingCenter() throws Exception {
        // Get the sharingCenter
        restSharingCenterMockMvc.perform(get("/api/sharing-centers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSharingCenter() throws Exception {
        // Initialize the database
        sharingCenterService.save(sharingCenter);

        int databaseSizeBeforeUpdate = sharingCenterRepository.findAll().size();

        // Update the sharingCenter
        SharingCenter updatedSharingCenter = sharingCenterRepository.findById(sharingCenter.getId()).get();
        // Disconnect from session so that the updates on updatedSharingCenter are not directly saved in db
        em.detach(updatedSharingCenter);
        updatedSharingCenter
            .fileName(UPDATED_FILE_NAME)
            .fileSize(UPDATED_FILE_SIZE)
            .uploadedBy(UPDATED_UPLOADED_BY)
            .department(UPDATED_DEPARTMENT)
            .operating(UPDATED_OPERATING)
            .createdTime(UPDATED_CREATED_TIME)
            .modifyTime(UPDATED_MODIFY_TIME);

        restSharingCenterMockMvc.perform(put("/api/sharing-centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSharingCenter)))
            .andExpect(status().isOk());

        // Validate the SharingCenter in the database
        List<SharingCenter> sharingCenterList = sharingCenterRepository.findAll();
        assertThat(sharingCenterList).hasSize(databaseSizeBeforeUpdate);
        SharingCenter testSharingCenter = sharingCenterList.get(sharingCenterList.size() - 1);
        assertThat(testSharingCenter.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testSharingCenter.getFileSize()).isEqualTo(UPDATED_FILE_SIZE);
        assertThat(testSharingCenter.getUploadedBy()).isEqualTo(UPDATED_UPLOADED_BY);
        assertThat(testSharingCenter.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testSharingCenter.getOperating()).isEqualTo(UPDATED_OPERATING);
        assertThat(testSharingCenter.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testSharingCenter.getModifyTime()).isEqualTo(UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingSharingCenter() throws Exception {
        int databaseSizeBeforeUpdate = sharingCenterRepository.findAll().size();

        // Create the SharingCenter

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSharingCenterMockMvc.perform(put("/api/sharing-centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sharingCenter)))
            .andExpect(status().isBadRequest());

        // Validate the SharingCenter in the database
        List<SharingCenter> sharingCenterList = sharingCenterRepository.findAll();
        assertThat(sharingCenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSharingCenter() throws Exception {
        // Initialize the database
        sharingCenterService.save(sharingCenter);

        int databaseSizeBeforeDelete = sharingCenterRepository.findAll().size();

        // Delete the sharingCenter
        restSharingCenterMockMvc.perform(delete("/api/sharing-centers/{id}", sharingCenter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SharingCenter> sharingCenterList = sharingCenterRepository.findAll();
        assertThat(sharingCenterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SharingCenter.class);
        SharingCenter sharingCenter1 = new SharingCenter();
        sharingCenter1.setId(1L);
        SharingCenter sharingCenter2 = new SharingCenter();
        sharingCenter2.setId(sharingCenter1.getId());
        assertThat(sharingCenter1).isEqualTo(sharingCenter2);
        sharingCenter2.setId(2L);
        assertThat(sharingCenter1).isNotEqualTo(sharingCenter2);
        sharingCenter1.setId(null);
        assertThat(sharingCenter1).isNotEqualTo(sharingCenter2);
    }
}
