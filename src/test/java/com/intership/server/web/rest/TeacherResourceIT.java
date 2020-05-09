package com.intership.server.web.rest;

import com.intership.server.IntershipSystemApp;
import com.intership.server.domain.Teacher;
import com.intership.server.repository.TeacherRepository;
import com.intership.server.service.TeacherService;
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
 * Integration tests for the {@Link TeacherResource} REST controller.
 */
@SpringBootTest(classes = IntershipSystemApp.class)
public class TeacherResourceIT {

    private static final Long DEFAULT_TEA_ID = 1L;
    private static final Long UPDATED_TEA_ID = 2L;

    private static final String DEFAULT_TEA_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TEA_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GENDER = false;
    private static final Boolean UPDATED_GENDER = true;

    private static final String DEFAULT_TEA_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_TEA_DEPARTMENT = "BBBBBBBBBB";

    private static final String DEFAULT_SKIN = "AAAAAAAAAA";
    private static final String UPDATED_SKIN = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MODIFY_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFY_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherService teacherService;

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

    private MockMvc restTeacherMockMvc;

    private Teacher teacher;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TeacherResource teacherResource = new TeacherResource(teacherService);
        this.restTeacherMockMvc = MockMvcBuilders.standaloneSetup(teacherResource)
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
    public static Teacher createEntity(EntityManager em) {
        Teacher teacher = new Teacher()
            .teaId(DEFAULT_TEA_ID)
            .teaName(DEFAULT_TEA_NAME)
            .gender(DEFAULT_GENDER)
            .teaDepartment(DEFAULT_TEA_DEPARTMENT)
            .skin(DEFAULT_SKIN)
            .createdTime(DEFAULT_CREATED_TIME)
            .modifyTime(DEFAULT_MODIFY_TIME);
        return teacher;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Teacher createUpdatedEntity(EntityManager em) {
        Teacher teacher = new Teacher()
            .teaId(UPDATED_TEA_ID)
            .teaName(UPDATED_TEA_NAME)
            .gender(UPDATED_GENDER)
            .teaDepartment(UPDATED_TEA_DEPARTMENT)
            .skin(UPDATED_SKIN)
            .createdTime(UPDATED_CREATED_TIME)
            .modifyTime(UPDATED_MODIFY_TIME);
        return teacher;
    }

    @BeforeEach
    public void initTest() {
        teacher = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeacher() throws Exception {
        int databaseSizeBeforeCreate = teacherRepository.findAll().size();

        // Create the Teacher
        restTeacherMockMvc.perform(post("/api/teachers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacher)))
            .andExpect(status().isCreated());

        // Validate the Teacher in the database
        List<Teacher> teacherList = teacherRepository.findAll();
        assertThat(teacherList).hasSize(databaseSizeBeforeCreate + 1);
        Teacher testTeacher = teacherList.get(teacherList.size() - 1);
        assertThat(testTeacher.getTeaId()).isEqualTo(DEFAULT_TEA_ID);
        assertThat(testTeacher.getTeaName()).isEqualTo(DEFAULT_TEA_NAME);
        assertThat(testTeacher.isGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testTeacher.getTeaDepartment()).isEqualTo(DEFAULT_TEA_DEPARTMENT);
        assertThat(testTeacher.getSkin()).isEqualTo(DEFAULT_SKIN);
        assertThat(testTeacher.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testTeacher.getModifyTime()).isEqualTo(DEFAULT_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void createTeacherWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teacherRepository.findAll().size();

        // Create the Teacher with an existing ID
        teacher.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeacherMockMvc.perform(post("/api/teachers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacher)))
            .andExpect(status().isBadRequest());

        // Validate the Teacher in the database
        List<Teacher> teacherList = teacherRepository.findAll();
        assertThat(teacherList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTeachers() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList
        restTeacherMockMvc.perform(get("/api/teachers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teacher.getId().intValue())))
            .andExpect(jsonPath("$.[*].teaId").value(hasItem(DEFAULT_TEA_ID.intValue())))
            .andExpect(jsonPath("$.[*].teaName").value(hasItem(DEFAULT_TEA_NAME.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.booleanValue())))
            .andExpect(jsonPath("$.[*].teaDepartment").value(hasItem(DEFAULT_TEA_DEPARTMENT.toString())))
            .andExpect(jsonPath("$.[*].skin").value(hasItem(DEFAULT_SKIN.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(sameInstant(DEFAULT_CREATED_TIME))))
            .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(sameInstant(DEFAULT_MODIFY_TIME))));
    }
    
    @Test
    @Transactional
    public void getTeacher() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get the teacher
        restTeacherMockMvc.perform(get("/api/teachers/{id}", teacher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(teacher.getId().intValue()))
            .andExpect(jsonPath("$.teaId").value(DEFAULT_TEA_ID.intValue()))
            .andExpect(jsonPath("$.teaName").value(DEFAULT_TEA_NAME.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.booleanValue()))
            .andExpect(jsonPath("$.teaDepartment").value(DEFAULT_TEA_DEPARTMENT.toString()))
            .andExpect(jsonPath("$.skin").value(DEFAULT_SKIN.toString()))
            .andExpect(jsonPath("$.createdTime").value(sameInstant(DEFAULT_CREATED_TIME)))
            .andExpect(jsonPath("$.modifyTime").value(sameInstant(DEFAULT_MODIFY_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingTeacher() throws Exception {
        // Get the teacher
        restTeacherMockMvc.perform(get("/api/teachers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeacher() throws Exception {
        // Initialize the database
        teacherService.save(teacher);

        int databaseSizeBeforeUpdate = teacherRepository.findAll().size();

        // Update the teacher
        Teacher updatedTeacher = teacherRepository.findById(teacher.getId()).get();
        // Disconnect from session so that the updates on updatedTeacher are not directly saved in db
        em.detach(updatedTeacher);
        updatedTeacher
            .teaId(UPDATED_TEA_ID)
            .teaName(UPDATED_TEA_NAME)
            .gender(UPDATED_GENDER)
            .teaDepartment(UPDATED_TEA_DEPARTMENT)
            .skin(UPDATED_SKIN)
            .createdTime(UPDATED_CREATED_TIME)
            .modifyTime(UPDATED_MODIFY_TIME);

        restTeacherMockMvc.perform(put("/api/teachers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTeacher)))
            .andExpect(status().isOk());

        // Validate the Teacher in the database
        List<Teacher> teacherList = teacherRepository.findAll();
        assertThat(teacherList).hasSize(databaseSizeBeforeUpdate);
        Teacher testTeacher = teacherList.get(teacherList.size() - 1);
        assertThat(testTeacher.getTeaId()).isEqualTo(UPDATED_TEA_ID);
        assertThat(testTeacher.getTeaName()).isEqualTo(UPDATED_TEA_NAME);
        assertThat(testTeacher.isGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testTeacher.getTeaDepartment()).isEqualTo(UPDATED_TEA_DEPARTMENT);
        assertThat(testTeacher.getSkin()).isEqualTo(UPDATED_SKIN);
        assertThat(testTeacher.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testTeacher.getModifyTime()).isEqualTo(UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingTeacher() throws Exception {
        int databaseSizeBeforeUpdate = teacherRepository.findAll().size();

        // Create the Teacher

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeacherMockMvc.perform(put("/api/teachers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacher)))
            .andExpect(status().isBadRequest());

        // Validate the Teacher in the database
        List<Teacher> teacherList = teacherRepository.findAll();
        assertThat(teacherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTeacher() throws Exception {
        // Initialize the database
        teacherService.save(teacher);

        int databaseSizeBeforeDelete = teacherRepository.findAll().size();

        // Delete the teacher
        restTeacherMockMvc.perform(delete("/api/teachers/{id}", teacher.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Teacher> teacherList = teacherRepository.findAll();
        assertThat(teacherList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Teacher.class);
        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        Teacher teacher2 = new Teacher();
        teacher2.setId(teacher1.getId());
        assertThat(teacher1).isEqualTo(teacher2);
        teacher2.setId(2L);
        assertThat(teacher1).isNotEqualTo(teacher2);
        teacher1.setId(null);
        assertThat(teacher1).isNotEqualTo(teacher2);
    }
}
