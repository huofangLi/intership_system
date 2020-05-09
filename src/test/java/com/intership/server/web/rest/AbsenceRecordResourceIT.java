package com.intership.server.web.rest;

import com.intership.server.IntershipSystemApp;
import com.intership.server.domain.AbsenceRecord;
import com.intership.server.repository.AbsenceRecordRepository;
import com.intership.server.service.AbsenceRecordService;
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
 * Integration tests for the {@Link AbsenceRecordResource} REST controller.
 */
@SpringBootTest(classes = IntershipSystemApp.class)
public class AbsenceRecordResourceIT {

    private static final ZonedDateTime DEFAULT_ABSENCE_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ABSENCE_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_ABSENCE_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ABSENCE_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_ABSENCE_DAYS = 1D;
    private static final Double UPDATED_ABSENCE_DAYS = 2D;

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AbsenceRecordRepository absenceRecordRepository;

    @Autowired
    private AbsenceRecordService absenceRecordService;

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

    private MockMvc restAbsenceRecordMockMvc;

    private AbsenceRecord absenceRecord;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AbsenceRecordResource absenceRecordResource = new AbsenceRecordResource(absenceRecordService);
        this.restAbsenceRecordMockMvc = MockMvcBuilders.standaloneSetup(absenceRecordResource)
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
    public static AbsenceRecord createEntity(EntityManager em) {
        AbsenceRecord absenceRecord = new AbsenceRecord()
            .absenceStartTime(DEFAULT_ABSENCE_START_TIME)
            .absenceEndTime(DEFAULT_ABSENCE_END_TIME)
            .absenceDays(DEFAULT_ABSENCE_DAYS)
            .company(DEFAULT_COMPANY)
            .remarks(DEFAULT_REMARKS)
            .createdTime(DEFAULT_CREATED_TIME);
        return absenceRecord;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AbsenceRecord createUpdatedEntity(EntityManager em) {
        AbsenceRecord absenceRecord = new AbsenceRecord()
            .absenceStartTime(UPDATED_ABSENCE_START_TIME)
            .absenceEndTime(UPDATED_ABSENCE_END_TIME)
            .absenceDays(UPDATED_ABSENCE_DAYS)
            .company(UPDATED_COMPANY)
            .remarks(UPDATED_REMARKS)
            .createdTime(UPDATED_CREATED_TIME);
        return absenceRecord;
    }

    @BeforeEach
    public void initTest() {
        absenceRecord = createEntity(em);
    }

    @Test
    @Transactional
    public void createAbsenceRecord() throws Exception {
        int databaseSizeBeforeCreate = absenceRecordRepository.findAll().size();

        // Create the AbsenceRecord
        restAbsenceRecordMockMvc.perform(post("/api/absence-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(absenceRecord)))
            .andExpect(status().isCreated());

        // Validate the AbsenceRecord in the database
        List<AbsenceRecord> absenceRecordList = absenceRecordRepository.findAll();
        assertThat(absenceRecordList).hasSize(databaseSizeBeforeCreate + 1);
        AbsenceRecord testAbsenceRecord = absenceRecordList.get(absenceRecordList.size() - 1);
        assertThat(testAbsenceRecord.getAbsenceStartTime()).isEqualTo(DEFAULT_ABSENCE_START_TIME);
        assertThat(testAbsenceRecord.getAbsenceEndTime()).isEqualTo(DEFAULT_ABSENCE_END_TIME);
        assertThat(testAbsenceRecord.getAbsenceDays()).isEqualTo(DEFAULT_ABSENCE_DAYS);
        assertThat(testAbsenceRecord.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testAbsenceRecord.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testAbsenceRecord.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
    }

    @Test
    @Transactional
    public void createAbsenceRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = absenceRecordRepository.findAll().size();

        // Create the AbsenceRecord with an existing ID
        absenceRecord.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAbsenceRecordMockMvc.perform(post("/api/absence-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(absenceRecord)))
            .andExpect(status().isBadRequest());

        // Validate the AbsenceRecord in the database
        List<AbsenceRecord> absenceRecordList = absenceRecordRepository.findAll();
        assertThat(absenceRecordList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAbsenceRecords() throws Exception {
        // Initialize the database
        absenceRecordRepository.saveAndFlush(absenceRecord);

        // Get all the absenceRecordList
        restAbsenceRecordMockMvc.perform(get("/api/absence-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(absenceRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].absenceStartTime").value(hasItem(sameInstant(DEFAULT_ABSENCE_START_TIME))))
            .andExpect(jsonPath("$.[*].absenceEndTime").value(hasItem(sameInstant(DEFAULT_ABSENCE_END_TIME))))
            .andExpect(jsonPath("$.[*].absenceDays").value(hasItem(DEFAULT_ABSENCE_DAYS.doubleValue())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(sameInstant(DEFAULT_CREATED_TIME))));
    }
    
    @Test
    @Transactional
    public void getAbsenceRecord() throws Exception {
        // Initialize the database
        absenceRecordRepository.saveAndFlush(absenceRecord);

        // Get the absenceRecord
        restAbsenceRecordMockMvc.perform(get("/api/absence-records/{id}", absenceRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(absenceRecord.getId().intValue()))
            .andExpect(jsonPath("$.absenceStartTime").value(sameInstant(DEFAULT_ABSENCE_START_TIME)))
            .andExpect(jsonPath("$.absenceEndTime").value(sameInstant(DEFAULT_ABSENCE_END_TIME)))
            .andExpect(jsonPath("$.absenceDays").value(DEFAULT_ABSENCE_DAYS.doubleValue()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.createdTime").value(sameInstant(DEFAULT_CREATED_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingAbsenceRecord() throws Exception {
        // Get the absenceRecord
        restAbsenceRecordMockMvc.perform(get("/api/absence-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAbsenceRecord() throws Exception {
        // Initialize the database
        absenceRecordService.save(absenceRecord);

        int databaseSizeBeforeUpdate = absenceRecordRepository.findAll().size();

        // Update the absenceRecord
        AbsenceRecord updatedAbsenceRecord = absenceRecordRepository.findById(absenceRecord.getId()).get();
        // Disconnect from session so that the updates on updatedAbsenceRecord are not directly saved in db
        em.detach(updatedAbsenceRecord);
        updatedAbsenceRecord
            .absenceStartTime(UPDATED_ABSENCE_START_TIME)
            .absenceEndTime(UPDATED_ABSENCE_END_TIME)
            .absenceDays(UPDATED_ABSENCE_DAYS)
            .company(UPDATED_COMPANY)
            .remarks(UPDATED_REMARKS)
            .createdTime(UPDATED_CREATED_TIME);

        restAbsenceRecordMockMvc.perform(put("/api/absence-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAbsenceRecord)))
            .andExpect(status().isOk());

        // Validate the AbsenceRecord in the database
        List<AbsenceRecord> absenceRecordList = absenceRecordRepository.findAll();
        assertThat(absenceRecordList).hasSize(databaseSizeBeforeUpdate);
        AbsenceRecord testAbsenceRecord = absenceRecordList.get(absenceRecordList.size() - 1);
        assertThat(testAbsenceRecord.getAbsenceStartTime()).isEqualTo(UPDATED_ABSENCE_START_TIME);
        assertThat(testAbsenceRecord.getAbsenceEndTime()).isEqualTo(UPDATED_ABSENCE_END_TIME);
        assertThat(testAbsenceRecord.getAbsenceDays()).isEqualTo(UPDATED_ABSENCE_DAYS);
        assertThat(testAbsenceRecord.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testAbsenceRecord.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testAbsenceRecord.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingAbsenceRecord() throws Exception {
        int databaseSizeBeforeUpdate = absenceRecordRepository.findAll().size();

        // Create the AbsenceRecord

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAbsenceRecordMockMvc.perform(put("/api/absence-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(absenceRecord)))
            .andExpect(status().isBadRequest());

        // Validate the AbsenceRecord in the database
        List<AbsenceRecord> absenceRecordList = absenceRecordRepository.findAll();
        assertThat(absenceRecordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAbsenceRecord() throws Exception {
        // Initialize the database
        absenceRecordService.save(absenceRecord);

        int databaseSizeBeforeDelete = absenceRecordRepository.findAll().size();

        // Delete the absenceRecord
        restAbsenceRecordMockMvc.perform(delete("/api/absence-records/{id}", absenceRecord.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AbsenceRecord> absenceRecordList = absenceRecordRepository.findAll();
        assertThat(absenceRecordList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AbsenceRecord.class);
        AbsenceRecord absenceRecord1 = new AbsenceRecord();
        absenceRecord1.setId(1L);
        AbsenceRecord absenceRecord2 = new AbsenceRecord();
        absenceRecord2.setId(absenceRecord1.getId());
        assertThat(absenceRecord1).isEqualTo(absenceRecord2);
        absenceRecord2.setId(2L);
        assertThat(absenceRecord1).isNotEqualTo(absenceRecord2);
        absenceRecord1.setId(null);
        assertThat(absenceRecord1).isNotEqualTo(absenceRecord2);
    }
}
