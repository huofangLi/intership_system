package com.intership.server.web.rest;

import com.intership.server.IntershipSystemApp;
import com.intership.server.domain.JobChangeRecords;
import com.intership.server.repository.JobChangeRecordsRepository;
import com.intership.server.service.JobChangeRecordsService;
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
 * Integration tests for the {@Link JobChangeRecordsResource} REST controller.
 */
@SpringBootTest(classes = IntershipSystemApp.class)
public class JobChangeRecordsResourceIT {

    private static final String DEFAULT_UNIT_CHANGE = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_CHANGE = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION_CHANGE = "AAAAAAAAAA";
    private static final String UPDATED_POSITION_CHANGE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private JobChangeRecordsRepository jobChangeRecordsRepository;

    @Autowired
    private JobChangeRecordsService jobChangeRecordsService;

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

    private MockMvc restJobChangeRecordsMockMvc;

    private JobChangeRecords jobChangeRecords;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JobChangeRecordsResource jobChangeRecordsResource = new JobChangeRecordsResource(jobChangeRecordsService);
        this.restJobChangeRecordsMockMvc = MockMvcBuilders.standaloneSetup(jobChangeRecordsResource)
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
    public static JobChangeRecords createEntity(EntityManager em) {
        JobChangeRecords jobChangeRecords = new JobChangeRecords()
            .unitChange(DEFAULT_UNIT_CHANGE)
            .positionChange(DEFAULT_POSITION_CHANGE)
            .createTime(DEFAULT_CREATE_TIME);
        return jobChangeRecords;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobChangeRecords createUpdatedEntity(EntityManager em) {
        JobChangeRecords jobChangeRecords = new JobChangeRecords()
            .unitChange(UPDATED_UNIT_CHANGE)
            .positionChange(UPDATED_POSITION_CHANGE)
            .createTime(UPDATED_CREATE_TIME);
        return jobChangeRecords;
    }

    @BeforeEach
    public void initTest() {
        jobChangeRecords = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobChangeRecords() throws Exception {
        int databaseSizeBeforeCreate = jobChangeRecordsRepository.findAll().size();

        // Create the JobChangeRecords
        restJobChangeRecordsMockMvc.perform(post("/api/job-change-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobChangeRecords)))
            .andExpect(status().isCreated());

        // Validate the JobChangeRecords in the database
        List<JobChangeRecords> jobChangeRecordsList = jobChangeRecordsRepository.findAll();
        assertThat(jobChangeRecordsList).hasSize(databaseSizeBeforeCreate + 1);
        JobChangeRecords testJobChangeRecords = jobChangeRecordsList.get(jobChangeRecordsList.size() - 1);
        assertThat(testJobChangeRecords.getUnitChange()).isEqualTo(DEFAULT_UNIT_CHANGE);
        assertThat(testJobChangeRecords.getPositionChange()).isEqualTo(DEFAULT_POSITION_CHANGE);
        assertThat(testJobChangeRecords.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    public void createJobChangeRecordsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobChangeRecordsRepository.findAll().size();

        // Create the JobChangeRecords with an existing ID
        jobChangeRecords.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobChangeRecordsMockMvc.perform(post("/api/job-change-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobChangeRecords)))
            .andExpect(status().isBadRequest());

        // Validate the JobChangeRecords in the database
        List<JobChangeRecords> jobChangeRecordsList = jobChangeRecordsRepository.findAll();
        assertThat(jobChangeRecordsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJobChangeRecords() throws Exception {
        // Initialize the database
        jobChangeRecordsRepository.saveAndFlush(jobChangeRecords);

        // Get all the jobChangeRecordsList
        restJobChangeRecordsMockMvc.perform(get("/api/job-change-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobChangeRecords.getId().intValue())))
            .andExpect(jsonPath("$.[*].unitChange").value(hasItem(DEFAULT_UNIT_CHANGE.toString())))
            .andExpect(jsonPath("$.[*].positionChange").value(hasItem(DEFAULT_POSITION_CHANGE.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))));
    }
    
    @Test
    @Transactional
    public void getJobChangeRecords() throws Exception {
        // Initialize the database
        jobChangeRecordsRepository.saveAndFlush(jobChangeRecords);

        // Get the jobChangeRecords
        restJobChangeRecordsMockMvc.perform(get("/api/job-change-records/{id}", jobChangeRecords.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobChangeRecords.getId().intValue()))
            .andExpect(jsonPath("$.unitChange").value(DEFAULT_UNIT_CHANGE.toString()))
            .andExpect(jsonPath("$.positionChange").value(DEFAULT_POSITION_CHANGE.toString()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingJobChangeRecords() throws Exception {
        // Get the jobChangeRecords
        restJobChangeRecordsMockMvc.perform(get("/api/job-change-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobChangeRecords() throws Exception {
        // Initialize the database
        jobChangeRecordsService.save(jobChangeRecords);

        int databaseSizeBeforeUpdate = jobChangeRecordsRepository.findAll().size();

        // Update the jobChangeRecords
        JobChangeRecords updatedJobChangeRecords = jobChangeRecordsRepository.findById(jobChangeRecords.getId()).get();
        // Disconnect from session so that the updates on updatedJobChangeRecords are not directly saved in db
        em.detach(updatedJobChangeRecords);
        updatedJobChangeRecords
            .unitChange(UPDATED_UNIT_CHANGE)
            .positionChange(UPDATED_POSITION_CHANGE)
            .createTime(UPDATED_CREATE_TIME);

        restJobChangeRecordsMockMvc.perform(put("/api/job-change-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobChangeRecords)))
            .andExpect(status().isOk());

        // Validate the JobChangeRecords in the database
        List<JobChangeRecords> jobChangeRecordsList = jobChangeRecordsRepository.findAll();
        assertThat(jobChangeRecordsList).hasSize(databaseSizeBeforeUpdate);
        JobChangeRecords testJobChangeRecords = jobChangeRecordsList.get(jobChangeRecordsList.size() - 1);
        assertThat(testJobChangeRecords.getUnitChange()).isEqualTo(UPDATED_UNIT_CHANGE);
        assertThat(testJobChangeRecords.getPositionChange()).isEqualTo(UPDATED_POSITION_CHANGE);
        assertThat(testJobChangeRecords.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingJobChangeRecords() throws Exception {
        int databaseSizeBeforeUpdate = jobChangeRecordsRepository.findAll().size();

        // Create the JobChangeRecords

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobChangeRecordsMockMvc.perform(put("/api/job-change-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobChangeRecords)))
            .andExpect(status().isBadRequest());

        // Validate the JobChangeRecords in the database
        List<JobChangeRecords> jobChangeRecordsList = jobChangeRecordsRepository.findAll();
        assertThat(jobChangeRecordsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJobChangeRecords() throws Exception {
        // Initialize the database
        jobChangeRecordsService.save(jobChangeRecords);

        int databaseSizeBeforeDelete = jobChangeRecordsRepository.findAll().size();

        // Delete the jobChangeRecords
        restJobChangeRecordsMockMvc.perform(delete("/api/job-change-records/{id}", jobChangeRecords.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobChangeRecords> jobChangeRecordsList = jobChangeRecordsRepository.findAll();
        assertThat(jobChangeRecordsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobChangeRecords.class);
        JobChangeRecords jobChangeRecords1 = new JobChangeRecords();
        jobChangeRecords1.setId(1L);
        JobChangeRecords jobChangeRecords2 = new JobChangeRecords();
        jobChangeRecords2.setId(jobChangeRecords1.getId());
        assertThat(jobChangeRecords1).isEqualTo(jobChangeRecords2);
        jobChangeRecords2.setId(2L);
        assertThat(jobChangeRecords1).isNotEqualTo(jobChangeRecords2);
        jobChangeRecords1.setId(null);
        assertThat(jobChangeRecords1).isNotEqualTo(jobChangeRecords2);
    }
}
