package com.intership.server.web.rest;

import com.intership.server.IntershipSystemApp;
import com.intership.server.domain.AttendanceRecord;
import com.intership.server.repository.AttendanceRecordRepository;
import com.intership.server.service.AttendanceRecordService;
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
 * Integration tests for the {@Link AttendanceRecordResource} REST controller.
 */
@SpringBootTest(classes = IntershipSystemApp.class)
public class AttendanceRecordResourceIT {

    private static final String DEFAULT_INTERNSHIP_BATCH = "AAAAAAAAAA";
    private static final String UPDATED_INTERNSHIP_BATCH = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_PUNCH_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PUNCH_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_PUNCH_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_PUNCH_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_ATTENDANCE_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_ATTENDANCE_STATUS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MODIFY_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFY_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    @Autowired
    private AttendanceRecordService attendanceRecordService;

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

    private MockMvc restAttendanceRecordMockMvc;

    private AttendanceRecord attendanceRecord;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AttendanceRecordResource attendanceRecordResource = new AttendanceRecordResource(attendanceRecordService);
        this.restAttendanceRecordMockMvc = MockMvcBuilders.standaloneSetup(attendanceRecordResource)
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
    public static AttendanceRecord createEntity(EntityManager em) {
        AttendanceRecord attendanceRecord = new AttendanceRecord()
            .internshipBatch(DEFAULT_INTERNSHIP_BATCH)
            .punchTime(DEFAULT_PUNCH_TIME)
            .punchLocation(DEFAULT_PUNCH_LOCATION)
            .attendanceStatus(DEFAULT_ATTENDANCE_STATUS)
            .createdTime(DEFAULT_CREATED_TIME)
            .modifyTime(DEFAULT_MODIFY_TIME);
        return attendanceRecord;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttendanceRecord createUpdatedEntity(EntityManager em) {
        AttendanceRecord attendanceRecord = new AttendanceRecord()
            .internshipBatch(UPDATED_INTERNSHIP_BATCH)
            .punchTime(UPDATED_PUNCH_TIME)
            .punchLocation(UPDATED_PUNCH_LOCATION)
            .attendanceStatus(UPDATED_ATTENDANCE_STATUS)
            .createdTime(UPDATED_CREATED_TIME)
            .modifyTime(UPDATED_MODIFY_TIME);
        return attendanceRecord;
    }

    @BeforeEach
    public void initTest() {
        attendanceRecord = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttendanceRecord() throws Exception {
        int databaseSizeBeforeCreate = attendanceRecordRepository.findAll().size();

        // Create the AttendanceRecord
        restAttendanceRecordMockMvc.perform(post("/api/attendance-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceRecord)))
            .andExpect(status().isCreated());

        // Validate the AttendanceRecord in the database
        List<AttendanceRecord> attendanceRecordList = attendanceRecordRepository.findAll();
        assertThat(attendanceRecordList).hasSize(databaseSizeBeforeCreate + 1);
        AttendanceRecord testAttendanceRecord = attendanceRecordList.get(attendanceRecordList.size() - 1);
        assertThat(testAttendanceRecord.getInternshipBatch()).isEqualTo(DEFAULT_INTERNSHIP_BATCH);
        assertThat(testAttendanceRecord.getPunchTime()).isEqualTo(DEFAULT_PUNCH_TIME);
        assertThat(testAttendanceRecord.getPunchLocation()).isEqualTo(DEFAULT_PUNCH_LOCATION);
        assertThat(testAttendanceRecord.getAttendanceStatus()).isEqualTo(DEFAULT_ATTENDANCE_STATUS);
        assertThat(testAttendanceRecord.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testAttendanceRecord.getModifyTime()).isEqualTo(DEFAULT_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void createAttendanceRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attendanceRecordRepository.findAll().size();

        // Create the AttendanceRecord with an existing ID
        attendanceRecord.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendanceRecordMockMvc.perform(post("/api/attendance-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceRecord)))
            .andExpect(status().isBadRequest());

        // Validate the AttendanceRecord in the database
        List<AttendanceRecord> attendanceRecordList = attendanceRecordRepository.findAll();
        assertThat(attendanceRecordList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAttendanceRecords() throws Exception {
        // Initialize the database
        attendanceRecordRepository.saveAndFlush(attendanceRecord);

        // Get all the attendanceRecordList
        restAttendanceRecordMockMvc.perform(get("/api/attendance-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendanceRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].internshipBatch").value(hasItem(DEFAULT_INTERNSHIP_BATCH.toString())))
            .andExpect(jsonPath("$.[*].punchTime").value(hasItem(sameInstant(DEFAULT_PUNCH_TIME))))
            .andExpect(jsonPath("$.[*].punchLocation").value(hasItem(DEFAULT_PUNCH_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].attendanceStatus").value(hasItem(DEFAULT_ATTENDANCE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(sameInstant(DEFAULT_CREATED_TIME))))
            .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(sameInstant(DEFAULT_MODIFY_TIME))));
    }
    
    @Test
    @Transactional
    public void getAttendanceRecord() throws Exception {
        // Initialize the database
        attendanceRecordRepository.saveAndFlush(attendanceRecord);

        // Get the attendanceRecord
        restAttendanceRecordMockMvc.perform(get("/api/attendance-records/{id}", attendanceRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(attendanceRecord.getId().intValue()))
            .andExpect(jsonPath("$.internshipBatch").value(DEFAULT_INTERNSHIP_BATCH.toString()))
            .andExpect(jsonPath("$.punchTime").value(sameInstant(DEFAULT_PUNCH_TIME)))
            .andExpect(jsonPath("$.punchLocation").value(DEFAULT_PUNCH_LOCATION.toString()))
            .andExpect(jsonPath("$.attendanceStatus").value(DEFAULT_ATTENDANCE_STATUS.toString()))
            .andExpect(jsonPath("$.createdTime").value(sameInstant(DEFAULT_CREATED_TIME)))
            .andExpect(jsonPath("$.modifyTime").value(sameInstant(DEFAULT_MODIFY_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingAttendanceRecord() throws Exception {
        // Get the attendanceRecord
        restAttendanceRecordMockMvc.perform(get("/api/attendance-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttendanceRecord() throws Exception {
        // Initialize the database
        attendanceRecordService.save(attendanceRecord);

        int databaseSizeBeforeUpdate = attendanceRecordRepository.findAll().size();

        // Update the attendanceRecord
        AttendanceRecord updatedAttendanceRecord = attendanceRecordRepository.findById(attendanceRecord.getId()).get();
        // Disconnect from session so that the updates on updatedAttendanceRecord are not directly saved in db
        em.detach(updatedAttendanceRecord);
        updatedAttendanceRecord
            .internshipBatch(UPDATED_INTERNSHIP_BATCH)
            .punchTime(UPDATED_PUNCH_TIME)
            .punchLocation(UPDATED_PUNCH_LOCATION)
            .attendanceStatus(UPDATED_ATTENDANCE_STATUS)
            .createdTime(UPDATED_CREATED_TIME)
            .modifyTime(UPDATED_MODIFY_TIME);

        restAttendanceRecordMockMvc.perform(put("/api/attendance-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAttendanceRecord)))
            .andExpect(status().isOk());

        // Validate the AttendanceRecord in the database
        List<AttendanceRecord> attendanceRecordList = attendanceRecordRepository.findAll();
        assertThat(attendanceRecordList).hasSize(databaseSizeBeforeUpdate);
        AttendanceRecord testAttendanceRecord = attendanceRecordList.get(attendanceRecordList.size() - 1);
        assertThat(testAttendanceRecord.getInternshipBatch()).isEqualTo(UPDATED_INTERNSHIP_BATCH);
        assertThat(testAttendanceRecord.getPunchTime()).isEqualTo(UPDATED_PUNCH_TIME);
        assertThat(testAttendanceRecord.getPunchLocation()).isEqualTo(UPDATED_PUNCH_LOCATION);
        assertThat(testAttendanceRecord.getAttendanceStatus()).isEqualTo(UPDATED_ATTENDANCE_STATUS);
        assertThat(testAttendanceRecord.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testAttendanceRecord.getModifyTime()).isEqualTo(UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingAttendanceRecord() throws Exception {
        int databaseSizeBeforeUpdate = attendanceRecordRepository.findAll().size();

        // Create the AttendanceRecord

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendanceRecordMockMvc.perform(put("/api/attendance-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceRecord)))
            .andExpect(status().isBadRequest());

        // Validate the AttendanceRecord in the database
        List<AttendanceRecord> attendanceRecordList = attendanceRecordRepository.findAll();
        assertThat(attendanceRecordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAttendanceRecord() throws Exception {
        // Initialize the database
        attendanceRecordService.save(attendanceRecord);

        int databaseSizeBeforeDelete = attendanceRecordRepository.findAll().size();

        // Delete the attendanceRecord
        restAttendanceRecordMockMvc.perform(delete("/api/attendance-records/{id}", attendanceRecord.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttendanceRecord> attendanceRecordList = attendanceRecordRepository.findAll();
        assertThat(attendanceRecordList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttendanceRecord.class);
        AttendanceRecord attendanceRecord1 = new AttendanceRecord();
        attendanceRecord1.setId(1L);
        AttendanceRecord attendanceRecord2 = new AttendanceRecord();
        attendanceRecord2.setId(attendanceRecord1.getId());
        assertThat(attendanceRecord1).isEqualTo(attendanceRecord2);
        attendanceRecord2.setId(2L);
        assertThat(attendanceRecord1).isNotEqualTo(attendanceRecord2);
        attendanceRecord1.setId(null);
        assertThat(attendanceRecord1).isNotEqualTo(attendanceRecord2);
    }
}
