package com.intership.server.web.rest;

import com.intership.server.IntershipSystemApp;
import com.intership.server.domain.InternshipTask;
import com.intership.server.repository.InternshipTaskRepository;
import com.intership.server.service.InternshipTaskService;
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
 * Integration tests for the {@Link InternshipTaskResource} REST controller.
 */
@SpringBootTest(classes = IntershipSystemApp.class)
public class InternshipTaskResourceIT {

    private static final String DEFAULT_TASK_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TASK_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_FOUNDER = "AAAAAAAAAA";
    private static final String UPDATED_FOUNDER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_IMPORTANCE = "AAAAAAAAAA";
    private static final String UPDATED_IMPORTANCE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEXITY = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEXITY = "BBBBBBBBBB";

    private static final String DEFAULT_TASK_ANNEX = "AAAAAAAAAA";
    private static final String UPDATED_TASK_ANNEX = "BBBBBBBBBB";

    private static final String DEFAULT_STAR = "AAAAAAAAAA";
    private static final String UPDATED_STAR = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_OPERATING = "AAAAAAAAAA";
    private static final String UPDATED_OPERATING = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private InternshipTaskRepository internshipTaskRepository;

    @Autowired
    private InternshipTaskService internshipTaskService;

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

    private MockMvc restInternshipTaskMockMvc;

    private InternshipTask internshipTask;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InternshipTaskResource internshipTaskResource = new InternshipTaskResource(internshipTaskService);
        this.restInternshipTaskMockMvc = MockMvcBuilders.standaloneSetup(internshipTaskResource)
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
    public static InternshipTask createEntity(EntityManager em) {
        InternshipTask internshipTask = new InternshipTask()
            .taskTitle(DEFAULT_TASK_TITLE)
            .founder(DEFAULT_FOUNDER)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .importance(DEFAULT_IMPORTANCE)
            .complexity(DEFAULT_COMPLEXITY)
            .taskAnnex(DEFAULT_TASK_ANNEX)
            .star(DEFAULT_STAR)
            .status(DEFAULT_STATUS)
            .operating(DEFAULT_OPERATING)
            .createTime(DEFAULT_CREATE_TIME);
        return internshipTask;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InternshipTask createUpdatedEntity(EntityManager em) {
        InternshipTask internshipTask = new InternshipTask()
            .taskTitle(UPDATED_TASK_TITLE)
            .founder(UPDATED_FOUNDER)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .importance(UPDATED_IMPORTANCE)
            .complexity(UPDATED_COMPLEXITY)
            .taskAnnex(UPDATED_TASK_ANNEX)
            .star(UPDATED_STAR)
            .status(UPDATED_STATUS)
            .operating(UPDATED_OPERATING)
            .createTime(UPDATED_CREATE_TIME);
        return internshipTask;
    }

    @BeforeEach
    public void initTest() {
        internshipTask = createEntity(em);
    }

    @Test
    @Transactional
    public void createInternshipTask() throws Exception {
        int databaseSizeBeforeCreate = internshipTaskRepository.findAll().size();

        // Create the InternshipTask
        restInternshipTaskMockMvc.perform(post("/api/internship-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internshipTask)))
            .andExpect(status().isCreated());

        // Validate the InternshipTask in the database
        List<InternshipTask> internshipTaskList = internshipTaskRepository.findAll();
        assertThat(internshipTaskList).hasSize(databaseSizeBeforeCreate + 1);
        InternshipTask testInternshipTask = internshipTaskList.get(internshipTaskList.size() - 1);
        assertThat(testInternshipTask.getTaskTitle()).isEqualTo(DEFAULT_TASK_TITLE);
        assertThat(testInternshipTask.getFounder()).isEqualTo(DEFAULT_FOUNDER);
        assertThat(testInternshipTask.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testInternshipTask.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testInternshipTask.getImportance()).isEqualTo(DEFAULT_IMPORTANCE);
        assertThat(testInternshipTask.getComplexity()).isEqualTo(DEFAULT_COMPLEXITY);
        assertThat(testInternshipTask.getTaskAnnex()).isEqualTo(DEFAULT_TASK_ANNEX);
        assertThat(testInternshipTask.getStar()).isEqualTo(DEFAULT_STAR);
        assertThat(testInternshipTask.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testInternshipTask.getOperating()).isEqualTo(DEFAULT_OPERATING);
        assertThat(testInternshipTask.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    public void createInternshipTaskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = internshipTaskRepository.findAll().size();

        // Create the InternshipTask with an existing ID
        internshipTask.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInternshipTaskMockMvc.perform(post("/api/internship-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internshipTask)))
            .andExpect(status().isBadRequest());

        // Validate the InternshipTask in the database
        List<InternshipTask> internshipTaskList = internshipTaskRepository.findAll();
        assertThat(internshipTaskList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInternshipTasks() throws Exception {
        // Initialize the database
        internshipTaskRepository.saveAndFlush(internshipTask);

        // Get all the internshipTaskList
        restInternshipTaskMockMvc.perform(get("/api/internship-tasks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(internshipTask.getId().intValue())))
            .andExpect(jsonPath("$.[*].taskTitle").value(hasItem(DEFAULT_TASK_TITLE.toString())))
            .andExpect(jsonPath("$.[*].founder").value(hasItem(DEFAULT_FOUNDER.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(sameInstant(DEFAULT_START_TIME))))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(sameInstant(DEFAULT_END_TIME))))
            .andExpect(jsonPath("$.[*].importance").value(hasItem(DEFAULT_IMPORTANCE.toString())))
            .andExpect(jsonPath("$.[*].complexity").value(hasItem(DEFAULT_COMPLEXITY.toString())))
            .andExpect(jsonPath("$.[*].taskAnnex").value(hasItem(DEFAULT_TASK_ANNEX.toString())))
            .andExpect(jsonPath("$.[*].star").value(hasItem(DEFAULT_STAR.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].operating").value(hasItem(DEFAULT_OPERATING.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))));
    }
    
    @Test
    @Transactional
    public void getInternshipTask() throws Exception {
        // Initialize the database
        internshipTaskRepository.saveAndFlush(internshipTask);

        // Get the internshipTask
        restInternshipTaskMockMvc.perform(get("/api/internship-tasks/{id}", internshipTask.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(internshipTask.getId().intValue()))
            .andExpect(jsonPath("$.taskTitle").value(DEFAULT_TASK_TITLE.toString()))
            .andExpect(jsonPath("$.founder").value(DEFAULT_FOUNDER.toString()))
            .andExpect(jsonPath("$.startTime").value(sameInstant(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.endTime").value(sameInstant(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.importance").value(DEFAULT_IMPORTANCE.toString()))
            .andExpect(jsonPath("$.complexity").value(DEFAULT_COMPLEXITY.toString()))
            .andExpect(jsonPath("$.taskAnnex").value(DEFAULT_TASK_ANNEX.toString()))
            .andExpect(jsonPath("$.star").value(DEFAULT_STAR.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.operating").value(DEFAULT_OPERATING.toString()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingInternshipTask() throws Exception {
        // Get the internshipTask
        restInternshipTaskMockMvc.perform(get("/api/internship-tasks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInternshipTask() throws Exception {
        // Initialize the database
        internshipTaskService.save(internshipTask);

        int databaseSizeBeforeUpdate = internshipTaskRepository.findAll().size();

        // Update the internshipTask
        InternshipTask updatedInternshipTask = internshipTaskRepository.findById(internshipTask.getId()).get();
        // Disconnect from session so that the updates on updatedInternshipTask are not directly saved in db
        em.detach(updatedInternshipTask);
        updatedInternshipTask
            .taskTitle(UPDATED_TASK_TITLE)
            .founder(UPDATED_FOUNDER)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .importance(UPDATED_IMPORTANCE)
            .complexity(UPDATED_COMPLEXITY)
            .taskAnnex(UPDATED_TASK_ANNEX)
            .star(UPDATED_STAR)
            .status(UPDATED_STATUS)
            .operating(UPDATED_OPERATING)
            .createTime(UPDATED_CREATE_TIME);

        restInternshipTaskMockMvc.perform(put("/api/internship-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInternshipTask)))
            .andExpect(status().isOk());

        // Validate the InternshipTask in the database
        List<InternshipTask> internshipTaskList = internshipTaskRepository.findAll();
        assertThat(internshipTaskList).hasSize(databaseSizeBeforeUpdate);
        InternshipTask testInternshipTask = internshipTaskList.get(internshipTaskList.size() - 1);
        assertThat(testInternshipTask.getTaskTitle()).isEqualTo(UPDATED_TASK_TITLE);
        assertThat(testInternshipTask.getFounder()).isEqualTo(UPDATED_FOUNDER);
        assertThat(testInternshipTask.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testInternshipTask.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testInternshipTask.getImportance()).isEqualTo(UPDATED_IMPORTANCE);
        assertThat(testInternshipTask.getComplexity()).isEqualTo(UPDATED_COMPLEXITY);
        assertThat(testInternshipTask.getTaskAnnex()).isEqualTo(UPDATED_TASK_ANNEX);
        assertThat(testInternshipTask.getStar()).isEqualTo(UPDATED_STAR);
        assertThat(testInternshipTask.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testInternshipTask.getOperating()).isEqualTo(UPDATED_OPERATING);
        assertThat(testInternshipTask.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingInternshipTask() throws Exception {
        int databaseSizeBeforeUpdate = internshipTaskRepository.findAll().size();

        // Create the InternshipTask

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInternshipTaskMockMvc.perform(put("/api/internship-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internshipTask)))
            .andExpect(status().isBadRequest());

        // Validate the InternshipTask in the database
        List<InternshipTask> internshipTaskList = internshipTaskRepository.findAll();
        assertThat(internshipTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInternshipTask() throws Exception {
        // Initialize the database
        internshipTaskService.save(internshipTask);

        int databaseSizeBeforeDelete = internshipTaskRepository.findAll().size();

        // Delete the internshipTask
        restInternshipTaskMockMvc.perform(delete("/api/internship-tasks/{id}", internshipTask.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InternshipTask> internshipTaskList = internshipTaskRepository.findAll();
        assertThat(internshipTaskList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InternshipTask.class);
        InternshipTask internshipTask1 = new InternshipTask();
        internshipTask1.setId(1L);
        InternshipTask internshipTask2 = new InternshipTask();
        internshipTask2.setId(internshipTask1.getId());
        assertThat(internshipTask1).isEqualTo(internshipTask2);
        internshipTask2.setId(2L);
        assertThat(internshipTask1).isNotEqualTo(internshipTask2);
        internshipTask1.setId(null);
        assertThat(internshipTask1).isNotEqualTo(internshipTask2);
    }
}
