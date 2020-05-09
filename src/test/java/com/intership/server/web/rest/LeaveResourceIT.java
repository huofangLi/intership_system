package com.intership.server.web.rest;

import com.intership.server.IntershipSystemApp;
import com.intership.server.domain.Leave;
import com.intership.server.repository.LeaveRepository;
import com.intership.server.service.LeaveService;
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
 * Integration tests for the {@Link LeaveResource} REST controller.
 */
@SpringBootTest(classes = IntershipSystemApp.class)
public class LeaveResourceIT {

    private static final ZonedDateTime DEFAULT_SUBMIT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SUBMIT_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LEAVE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LEAVE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_LEAVE_DAYS = 1D;
    private static final Double UPDATED_LEAVE_DAYS = 2D;

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_LEAVE_REASON = "AAAAAAAAAA";
    private static final String UPDATED_LEAVE_REASON = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final ZonedDateTime DEFAULT_CREATED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private LeaveService leaveService;

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

    private MockMvc restLeaveMockMvc;

    private Leave leave;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LeaveResource leaveResource = new LeaveResource(leaveService);
        this.restLeaveMockMvc = MockMvcBuilders.standaloneSetup(leaveResource)
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
    public static Leave createEntity(EntityManager em) {
        Leave leave = new Leave()
            .submitTime(DEFAULT_SUBMIT_TIME)
            .leaveTime(DEFAULT_LEAVE_TIME)
            .leaveDays(DEFAULT_LEAVE_DAYS)
            .company(DEFAULT_COMPANY)
            .leaveReason(DEFAULT_LEAVE_REASON)
            .status(DEFAULT_STATUS)
            .createdTime(DEFAULT_CREATED_TIME);
        return leave;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leave createUpdatedEntity(EntityManager em) {
        Leave leave = new Leave()
            .submitTime(UPDATED_SUBMIT_TIME)
            .leaveTime(UPDATED_LEAVE_TIME)
            .leaveDays(UPDATED_LEAVE_DAYS)
            .company(UPDATED_COMPANY)
            .leaveReason(UPDATED_LEAVE_REASON)
            .status(UPDATED_STATUS)
            .createdTime(UPDATED_CREATED_TIME);
        return leave;
    }

    @BeforeEach
    public void initTest() {
        leave = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeave() throws Exception {
        int databaseSizeBeforeCreate = leaveRepository.findAll().size();

        // Create the Leave
        restLeaveMockMvc.perform(post("/api/leaves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leave)))
            .andExpect(status().isCreated());

        // Validate the Leave in the database
        List<Leave> leaveList = leaveRepository.findAll();
        assertThat(leaveList).hasSize(databaseSizeBeforeCreate + 1);
        Leave testLeave = leaveList.get(leaveList.size() - 1);
        assertThat(testLeave.getSubmitTime()).isEqualTo(DEFAULT_SUBMIT_TIME);
        assertThat(testLeave.getLeaveTime()).isEqualTo(DEFAULT_LEAVE_TIME);
        assertThat(testLeave.getLeaveDays()).isEqualTo(DEFAULT_LEAVE_DAYS);
        assertThat(testLeave.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testLeave.getLeaveReason()).isEqualTo(DEFAULT_LEAVE_REASON);
        assertThat(testLeave.isStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLeave.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
    }

    @Test
    @Transactional
    public void createLeaveWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leaveRepository.findAll().size();

        // Create the Leave with an existing ID
        leave.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveMockMvc.perform(post("/api/leaves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leave)))
            .andExpect(status().isBadRequest());

        // Validate the Leave in the database
        List<Leave> leaveList = leaveRepository.findAll();
        assertThat(leaveList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLeaves() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList
        restLeaveMockMvc.perform(get("/api/leaves?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leave.getId().intValue())))
            .andExpect(jsonPath("$.[*].submitTime").value(hasItem(sameInstant(DEFAULT_SUBMIT_TIME))))
            .andExpect(jsonPath("$.[*].leaveTime").value(hasItem(sameInstant(DEFAULT_LEAVE_TIME))))
            .andExpect(jsonPath("$.[*].leaveDays").value(hasItem(DEFAULT_LEAVE_DAYS.doubleValue())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.toString())))
            .andExpect(jsonPath("$.[*].leaveReason").value(hasItem(DEFAULT_LEAVE_REASON.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(sameInstant(DEFAULT_CREATED_TIME))));
    }
    
    @Test
    @Transactional
    public void getLeave() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get the leave
        restLeaveMockMvc.perform(get("/api/leaves/{id}", leave.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(leave.getId().intValue()))
            .andExpect(jsonPath("$.submitTime").value(sameInstant(DEFAULT_SUBMIT_TIME)))
            .andExpect(jsonPath("$.leaveTime").value(sameInstant(DEFAULT_LEAVE_TIME)))
            .andExpect(jsonPath("$.leaveDays").value(DEFAULT_LEAVE_DAYS.doubleValue()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY.toString()))
            .andExpect(jsonPath("$.leaveReason").value(DEFAULT_LEAVE_REASON.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createdTime").value(sameInstant(DEFAULT_CREATED_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingLeave() throws Exception {
        // Get the leave
        restLeaveMockMvc.perform(get("/api/leaves/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeave() throws Exception {
        // Initialize the database
        leaveService.save(leave);

        int databaseSizeBeforeUpdate = leaveRepository.findAll().size();

        // Update the leave
        Leave updatedLeave = leaveRepository.findById(leave.getId()).get();
        // Disconnect from session so that the updates on updatedLeave are not directly saved in db
        em.detach(updatedLeave);
        updatedLeave
            .submitTime(UPDATED_SUBMIT_TIME)
            .leaveTime(UPDATED_LEAVE_TIME)
            .leaveDays(UPDATED_LEAVE_DAYS)
            .company(UPDATED_COMPANY)
            .leaveReason(UPDATED_LEAVE_REASON)
            .status(UPDATED_STATUS)
            .createdTime(UPDATED_CREATED_TIME);

        restLeaveMockMvc.perform(put("/api/leaves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLeave)))
            .andExpect(status().isOk());

        // Validate the Leave in the database
        List<Leave> leaveList = leaveRepository.findAll();
        assertThat(leaveList).hasSize(databaseSizeBeforeUpdate);
        Leave testLeave = leaveList.get(leaveList.size() - 1);
        assertThat(testLeave.getSubmitTime()).isEqualTo(UPDATED_SUBMIT_TIME);
        assertThat(testLeave.getLeaveTime()).isEqualTo(UPDATED_LEAVE_TIME);
        assertThat(testLeave.getLeaveDays()).isEqualTo(UPDATED_LEAVE_DAYS);
        assertThat(testLeave.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testLeave.getLeaveReason()).isEqualTo(UPDATED_LEAVE_REASON);
        assertThat(testLeave.isStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLeave.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingLeave() throws Exception {
        int databaseSizeBeforeUpdate = leaveRepository.findAll().size();

        // Create the Leave

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveMockMvc.perform(put("/api/leaves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leave)))
            .andExpect(status().isBadRequest());

        // Validate the Leave in the database
        List<Leave> leaveList = leaveRepository.findAll();
        assertThat(leaveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLeave() throws Exception {
        // Initialize the database
        leaveService.save(leave);

        int databaseSizeBeforeDelete = leaveRepository.findAll().size();

        // Delete the leave
        restLeaveMockMvc.perform(delete("/api/leaves/{id}", leave.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Leave> leaveList = leaveRepository.findAll();
        assertThat(leaveList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Leave.class);
        Leave leave1 = new Leave();
        leave1.setId(1L);
        Leave leave2 = new Leave();
        leave2.setId(leave1.getId());
        assertThat(leave1).isEqualTo(leave2);
        leave2.setId(2L);
        assertThat(leave1).isNotEqualTo(leave2);
        leave1.setId(null);
        assertThat(leave1).isNotEqualTo(leave2);
    }
}
