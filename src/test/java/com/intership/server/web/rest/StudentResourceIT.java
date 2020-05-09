package com.intership.server.web.rest;

import com.intership.server.IntershipSystemApp;
import com.intership.server.domain.Student;
import com.intership.server.repository.StudentRepository;
import com.intership.server.service.StudentService;
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
 * Integration tests for the {@Link StudentResource} REST controller.
 */
@SpringBootTest(classes = IntershipSystemApp.class)
public class StudentResourceIT {

    private static final Long DEFAULT_STU_ID = 1L;
    private static final Long UPDATED_STU_ID = 2L;

    private static final Boolean DEFAULT_GENDER = false;
    private static final Boolean UPDATED_GENDER = true;

    private static final String DEFAULT_STU_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_STU_CLASS = "BBBBBBBBBB";

    private static final String DEFAULT_STU_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_STU_DEPARTMENT = "BBBBBBBBBB";

    private static final String DEFAULT_STU_PROFESSION = "AAAAAAAAAA";
    private static final String UPDATED_STU_PROFESSION = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIVINCE = "AAAAAAAAAA";
    private static final String UPDATED_PRIVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIVINCE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PRIVINCE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CITY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_HOBBY = "AAAAAAAAAA";
    private static final String UPDATED_HOBBY = "BBBBBBBBBB";

    private static final String DEFAULT_SKILL = "AAAAAAAAAA";
    private static final String UPDATED_SKILL = "BBBBBBBBBB";

    private static final String DEFAULT_SELF_EVALUATION = "AAAAAAAAAA";
    private static final String UPDATED_SELF_EVALUATION = "BBBBBBBBBB";

    private static final String DEFAULT_SKIN = "AAAAAAAAAA";
    private static final String UPDATED_SKIN = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MODIFY_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFY_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

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

    private MockMvc restStudentMockMvc;

    private Student student;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentResource studentResource = new StudentResource(studentService);
        this.restStudentMockMvc = MockMvcBuilders.standaloneSetup(studentResource)
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
    public static Student createEntity(EntityManager em) {
        Student student = new Student()
            .stuId(DEFAULT_STU_ID)
            .gender(DEFAULT_GENDER)
            .stuClass(DEFAULT_STU_CLASS)
            .stuDepartment(DEFAULT_STU_DEPARTMENT)
            .stuProfession(DEFAULT_STU_PROFESSION)
            .phone(DEFAULT_PHONE)
            .privince(DEFAULT_PRIVINCE)
            .privinceCode(DEFAULT_PRIVINCE_CODE)
            .city(DEFAULT_CITY)
            .cityCode(DEFAULT_CITY_CODE)
            .country(DEFAULT_COUNTRY)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .address(DEFAULT_ADDRESS)
            .hobby(DEFAULT_HOBBY)
            .skill(DEFAULT_SKILL)
            .selfEvaluation(DEFAULT_SELF_EVALUATION)
            .skin(DEFAULT_SKIN)
            .createdTime(DEFAULT_CREATED_TIME)
            .modifyTime(DEFAULT_MODIFY_TIME);
        return student;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Student createUpdatedEntity(EntityManager em) {
        Student student = new Student()
            .stuId(UPDATED_STU_ID)
            .gender(UPDATED_GENDER)
            .stuClass(UPDATED_STU_CLASS)
            .stuDepartment(UPDATED_STU_DEPARTMENT)
            .stuProfession(UPDATED_STU_PROFESSION)
            .phone(UPDATED_PHONE)
            .privince(UPDATED_PRIVINCE)
            .privinceCode(UPDATED_PRIVINCE_CODE)
            .city(UPDATED_CITY)
            .cityCode(UPDATED_CITY_CODE)
            .country(UPDATED_COUNTRY)
            .countryCode(UPDATED_COUNTRY_CODE)
            .address(UPDATED_ADDRESS)
            .hobby(UPDATED_HOBBY)
            .skill(UPDATED_SKILL)
            .selfEvaluation(UPDATED_SELF_EVALUATION)
            .skin(UPDATED_SKIN)
            .createdTime(UPDATED_CREATED_TIME)
            .modifyTime(UPDATED_MODIFY_TIME);
        return student;
    }

    @BeforeEach
    public void initTest() {
        student = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudent() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // Create the Student
        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isCreated());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate + 1);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getStuId()).isEqualTo(DEFAULT_STU_ID);
        assertThat(testStudent.isGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testStudent.getStuClass()).isEqualTo(DEFAULT_STU_CLASS);
        assertThat(testStudent.getStuDepartment()).isEqualTo(DEFAULT_STU_DEPARTMENT);
        assertThat(testStudent.getStuProfession()).isEqualTo(DEFAULT_STU_PROFESSION);
        assertThat(testStudent.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testStudent.getPrivince()).isEqualTo(DEFAULT_PRIVINCE);
        assertThat(testStudent.getPrivinceCode()).isEqualTo(DEFAULT_PRIVINCE_CODE);
        assertThat(testStudent.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testStudent.getCityCode()).isEqualTo(DEFAULT_CITY_CODE);
        assertThat(testStudent.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testStudent.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testStudent.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testStudent.getHobby()).isEqualTo(DEFAULT_HOBBY);
        assertThat(testStudent.getSkill()).isEqualTo(DEFAULT_SKILL);
        assertThat(testStudent.getSelfEvaluation()).isEqualTo(DEFAULT_SELF_EVALUATION);
        assertThat(testStudent.getSkin()).isEqualTo(DEFAULT_SKIN);
        assertThat(testStudent.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testStudent.getModifyTime()).isEqualTo(DEFAULT_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void createStudentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // Create the Student with an existing ID
        student.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStudents() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList
        restStudentMockMvc.perform(get("/api/students?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
            .andExpect(jsonPath("$.[*].stuId").value(hasItem(DEFAULT_STU_ID.intValue())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.booleanValue())))
            .andExpect(jsonPath("$.[*].stuClass").value(hasItem(DEFAULT_STU_CLASS.toString())))
            .andExpect(jsonPath("$.[*].stuDepartment").value(hasItem(DEFAULT_STU_DEPARTMENT.toString())))
            .andExpect(jsonPath("$.[*].stuProfession").value(hasItem(DEFAULT_STU_PROFESSION.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].privince").value(hasItem(DEFAULT_PRIVINCE.toString())))
            .andExpect(jsonPath("$.[*].privinceCode").value(hasItem(DEFAULT_PRIVINCE_CODE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].cityCode").value(hasItem(DEFAULT_CITY_CODE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].hobby").value(hasItem(DEFAULT_HOBBY.toString())))
            .andExpect(jsonPath("$.[*].skill").value(hasItem(DEFAULT_SKILL.toString())))
            .andExpect(jsonPath("$.[*].selfEvaluation").value(hasItem(DEFAULT_SELF_EVALUATION.toString())))
            .andExpect(jsonPath("$.[*].skin").value(hasItem(DEFAULT_SKIN.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(sameInstant(DEFAULT_CREATED_TIME))))
            .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(sameInstant(DEFAULT_MODIFY_TIME))));
    }
    
    @Test
    @Transactional
    public void getStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", student.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(student.getId().intValue()))
            .andExpect(jsonPath("$.stuId").value(DEFAULT_STU_ID.intValue()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.booleanValue()))
            .andExpect(jsonPath("$.stuClass").value(DEFAULT_STU_CLASS.toString()))
            .andExpect(jsonPath("$.stuDepartment").value(DEFAULT_STU_DEPARTMENT.toString()))
            .andExpect(jsonPath("$.stuProfession").value(DEFAULT_STU_PROFESSION.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.privince").value(DEFAULT_PRIVINCE.toString()))
            .andExpect(jsonPath("$.privinceCode").value(DEFAULT_PRIVINCE_CODE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.cityCode").value(DEFAULT_CITY_CODE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.hobby").value(DEFAULT_HOBBY.toString()))
            .andExpect(jsonPath("$.skill").value(DEFAULT_SKILL.toString()))
            .andExpect(jsonPath("$.selfEvaluation").value(DEFAULT_SELF_EVALUATION.toString()))
            .andExpect(jsonPath("$.skin").value(DEFAULT_SKIN.toString()))
            .andExpect(jsonPath("$.createdTime").value(sameInstant(DEFAULT_CREATED_TIME)))
            .andExpect(jsonPath("$.modifyTime").value(sameInstant(DEFAULT_MODIFY_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingStudent() throws Exception {
        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudent() throws Exception {
        // Initialize the database
        studentService.save(student);

        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Update the student
        Student updatedStudent = studentRepository.findById(student.getId()).get();
        // Disconnect from session so that the updates on updatedStudent are not directly saved in db
        em.detach(updatedStudent);
        updatedStudent
            .stuId(UPDATED_STU_ID)
            .gender(UPDATED_GENDER)
            .stuClass(UPDATED_STU_CLASS)
            .stuDepartment(UPDATED_STU_DEPARTMENT)
            .stuProfession(UPDATED_STU_PROFESSION)
            .phone(UPDATED_PHONE)
            .privince(UPDATED_PRIVINCE)
            .privinceCode(UPDATED_PRIVINCE_CODE)
            .city(UPDATED_CITY)
            .cityCode(UPDATED_CITY_CODE)
            .country(UPDATED_COUNTRY)
            .countryCode(UPDATED_COUNTRY_CODE)
            .address(UPDATED_ADDRESS)
            .hobby(UPDATED_HOBBY)
            .skill(UPDATED_SKILL)
            .selfEvaluation(UPDATED_SELF_EVALUATION)
            .skin(UPDATED_SKIN)
            .createdTime(UPDATED_CREATED_TIME)
            .modifyTime(UPDATED_MODIFY_TIME);

        restStudentMockMvc.perform(put("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudent)))
            .andExpect(status().isOk());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getStuId()).isEqualTo(UPDATED_STU_ID);
        assertThat(testStudent.isGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testStudent.getStuClass()).isEqualTo(UPDATED_STU_CLASS);
        assertThat(testStudent.getStuDepartment()).isEqualTo(UPDATED_STU_DEPARTMENT);
        assertThat(testStudent.getStuProfession()).isEqualTo(UPDATED_STU_PROFESSION);
        assertThat(testStudent.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testStudent.getPrivince()).isEqualTo(UPDATED_PRIVINCE);
        assertThat(testStudent.getPrivinceCode()).isEqualTo(UPDATED_PRIVINCE_CODE);
        assertThat(testStudent.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testStudent.getCityCode()).isEqualTo(UPDATED_CITY_CODE);
        assertThat(testStudent.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testStudent.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testStudent.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testStudent.getHobby()).isEqualTo(UPDATED_HOBBY);
        assertThat(testStudent.getSkill()).isEqualTo(UPDATED_SKILL);
        assertThat(testStudent.getSelfEvaluation()).isEqualTo(UPDATED_SELF_EVALUATION);
        assertThat(testStudent.getSkin()).isEqualTo(UPDATED_SKIN);
        assertThat(testStudent.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testStudent.getModifyTime()).isEqualTo(UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Create the Student

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentMockMvc.perform(put("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudent() throws Exception {
        // Initialize the database
        studentService.save(student);

        int databaseSizeBeforeDelete = studentRepository.findAll().size();

        // Delete the student
        restStudentMockMvc.perform(delete("/api/students/{id}", student.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Student.class);
        Student student1 = new Student();
        student1.setId(1L);
        Student student2 = new Student();
        student2.setId(student1.getId());
        assertThat(student1).isEqualTo(student2);
        student2.setId(2L);
        assertThat(student1).isNotEqualTo(student2);
        student1.setId(null);
        assertThat(student1).isNotEqualTo(student2);
    }
}
