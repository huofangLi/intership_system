package com.intership.server.web.rest;

import com.intership.server.IntershipSystemApp;
import com.intership.server.domain.AttendanceManagement;
import com.intership.server.repository.AttendanceManagementRepository;
import com.intership.server.service.AttendanceManagementService;
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
 * Integration tests for the {@Link AttendanceManagementResource} REST controller.
 */
@SpringBootTest(classes = IntershipSystemApp.class)
public class AttendanceManagementResourceIT {

    private static final ZonedDateTime DEFAULT_SIGN_IN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SIGN_IN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_COORDINATE = "AAAAAAAAAA";
    private static final String UPDATED_COORDINATE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AttendanceManagementRepository attendanceManagementRepository;

    @Autowired
    private AttendanceManagementService attendanceManagementService;

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

    private MockMvc restAttendanceManagementMockMvc;

    private AttendanceManagement attendanceManagement;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AttendanceManagementResource attendanceManagementResource = new AttendanceManagementResource(attendanceManagementService);
        this.restAttendanceManagementMockMvc = MockMvcBuilders.standaloneSetup(attendanceManagementResource)
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
    public static AttendanceManagement createEntity(EntityManager em) {
        AttendanceManagement attendanceManagement = new AttendanceManagement()
            .signIn(DEFAULT_SIGN_IN)
            .position(DEFAULT_POSITION)
            .coordinate(DEFAULT_COORDINATE)
            .company(DEFAULT_COMPANY)
            .createdTime(DEFAULT_CREATED_TIME);
        return attendanceManagement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttendanceManagement createUpdatedEntity(EntityManager em) {
        AttendanceManagement attendanceManagement = new AttendanceManagement()
            .signIn(UPDATED_SIGN_IN)
            .position(UPDATED_POSITION)
            .coordinate(UPDATED_COORDINATE)
            .company(UPDATED_COMPANY)
            .createdTime(UPDATED_CREATED_TIME);
        return attendanceManagement;
    }

    @BeforeEach
    public void initTest() {
        attendanceManagement = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttendanceManagement() throws Exception {
        int databaseSizeBeforeCreate = attendanceManagementRepository.findAll().size();

        // Create the AttendanceManagement
        restAttendanceManagementMockMvc.perform(post("/api/attendance-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceManagement)))
            .andExpect(status().isCreated());

        // Validate the AttendanceManagement in the database
        List<AttendanceManagement> attendanceManagementList = attendanceManagementRepository.findAll();
        assertThat(attendanceManagementList).hasSize(databaseSizeBeforeCreate + 1);
        AttendanceManagement testAttendanceManagement = attendanceManagementList.get(attendanceManagementList.size() - 1);
        assertThat(testAttendanceManagement.getSignIn()).isEqualTo(DEFAULT_SIGN_IN);
        assertThat(testAttendanceManagement.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testAttendanceManagement.getCoordinate()).isEqualTo(DEFAULT_COORDINATE);
        assertThat(testAttendanceManagement.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testAttendanceManagement.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
    }

    @Test
    @Transactional
    public void createAttendanceManagementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attendanceManagementRepository.findAll().size();

        // Create the AttendanceManagement with an existing ID
        attendanceManagement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendanceManagementMockMvc.perform(post("/api/attendance-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceManagement)))
            .andExpect(status().isBadRequest());

        // Validate the AttendanceManagement in the database
        List<AttendanceManagement> attendanceManagementList = attendanceManagementRepository.findAll();
        assertThat(attendanceManagementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAttendanceManagements() throws Exception {
        // Initialize the database
        attendanceManagementRepository.saveAndFlush(attendanceManagement);

        // Get all the attendanceManagementList
        restAttendanceManagementMockMvc.perform(get("/api/attendance-managements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendanceManagement.getId().intValue())))
            .andExpect(jsonPath("$.[*].signIn").value(hasItem(sameInstant(DEFAULT_SIGN_IN))))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
            .andExpect(jsonPath("$.[*].coordinate").value(hasItem(DEFAULT_COORDINATE.toString())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(sameInstant(DEFAULT_CREATED_TIME))));
    }
    
    @Test
    @Transactional
    public void getAttendanceManagement() throws Exception {
        // Initialize the database
        attendanceManagementRepository.saveAndFlush(attendanceManagement);

        // Get the attendanceManagement
        restAttendanceManagementMockMvc.perform(get("/api/attendance-managements/{id}", attendanceManagement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(attendanceManagement.getId().intValue()))
            .andExpect(jsonPath("$.signIn").value(sameInstant(DEFAULT_SIGN_IN)))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.toString()))
            .andExpect(jsonPath("$.coordinate").value(DEFAULT_COORDINATE.toString()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY.toString()))
            .andExpect(jsonPath("$.createdTime").value(sameInstant(DEFAULT_CREATED_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingAttendanceManagement() throws Exception {
        // Get the attendanceManagement
        restAttendanceManagementMockMvc.perform(get("/api/attendance-managements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttendanceManagement() throws Exception {
        // Initialize the database
        attendanceManagementService.save(attendanceManagement);

        int databaseSizeBeforeUpdate = attendanceManagementRepository.findAll().size();

        // Update the attendanceManagement
        AttendanceManagement updatedAttendanceManagement = attendanceManagementRepository.findById(attendanceManagement.getId()).get();
        // Disconnect from session so that the updates on updatedAttendanceManagement are not directly saved in db
        em.detach(updatedAttendanceManagement);
        updatedAttendanceManagement
            .signIn(UPDATED_SIGN_IN)
            .position(UPDATED_POSITION)
            .coordinate(UPDATED_COORDINATE)
            .company(UPDATED_COMPANY)
            .createdTime(UPDATED_CREATED_TIME);

        restAttendanceManagementMockMvc.perform(put("/api/attendance-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAttendanceManagement)))
            .andExpect(status().isOk());

        // Validate the AttendanceManagement in the database
        List<AttendanceManagement> attendanceManagementList = attendanceManagementRepository.findAll();
        assertThat(attendanceManagementList).hasSize(databaseSizeBeforeUpdate);
        AttendanceManagement testAttendanceManagement = attendanceManagementList.get(attendanceManagementList.size() - 1);
        assertThat(testAttendanceManagement.getSignIn()).isEqualTo(UPDATED_SIGN_IN);
        assertThat(testAttendanceManagement.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testAttendanceManagement.getCoordinate()).isEqualTo(UPDATED_COORDINATE);
        assertThat(testAttendanceManagement.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testAttendanceManagement.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingAttendanceManagement() throws Exception {
        int databaseSizeBeforeUpdate = attendanceManagementRepository.findAll().size();

        // Create the AttendanceManagement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendanceManagementMockMvc.perform(put("/api/attendance-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceManagement)))
            .andExpect(status().isBadRequest());

        // Validate the AttendanceManagement in the database
        List<AttendanceManagement> attendanceManagementList = attendanceManagementRepository.findAll();
        assertThat(attendanceManagementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAttendanceManagement() throws Exception {
        // Initialize the database
        attendanceManagementService.save(attendanceManagement);

        int databaseSizeBeforeDelete = attendanceManagementRepository.findAll().size();

        // Delete the attendanceManagement
        restAttendanceManagementMockMvc.perform(delete("/api/attendance-managements/{id}", attendanceManagement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttendanceManagement> attendanceManagementList = attendanceManagementRepository.findAll();
        assertThat(attendanceManagementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttendanceManagement.class);
        AttendanceManagement attendanceManagement1 = new AttendanceManagement();
        attendanceManagement1.setId(1L);
        AttendanceManagement attendanceManagement2 = new AttendanceManagement();
        attendanceManagement2.setId(attendanceManagement1.getId());
        assertThat(attendanceManagement1).isEqualTo(attendanceManagement2);
        attendanceManagement2.setId(2L);
        assertThat(attendanceManagement1).isNotEqualTo(attendanceManagement2);
        attendanceManagement1.setId(null);
        assertThat(attendanceManagement1).isNotEqualTo(attendanceManagement2);
    }
}
