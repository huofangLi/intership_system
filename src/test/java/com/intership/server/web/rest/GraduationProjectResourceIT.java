package com.intership.server.web.rest;

import com.intership.server.IntershipSystemApp;
import com.intership.server.domain.GraduationProject;
import com.intership.server.repository.GraduationProjectRepository;
import com.intership.server.service.GraduationProjectService;
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
 * Integration tests for the {@Link GraduationProjectResource} REST controller.
 */
@SpringBootTest(classes = IntershipSystemApp.class)
public class GraduationProjectResourceIT {

    private static final String DEFAULT_GRADUATION_PROJECT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_GRADUATION_PROJECT_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_TASK_BOOK = "AAAAAAAAAA";
    private static final String UPDATED_TASK_BOOK = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TASK_BOOK_CHECK = false;
    private static final Boolean UPDATED_TASK_BOOK_CHECK = true;

    private static final String DEFAULT_TASK_BOOK_REVIEWS = "AAAAAAAAAA";
    private static final String UPDATED_TASK_BOOK_REVIEWS = "BBBBBBBBBB";

    private static final String DEFAULT_OPENING_REPORT = "AAAAAAAAAA";
    private static final String UPDATED_OPENING_REPORT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_OPENING_REPORT_CHECK = false;
    private static final Boolean UPDATED_OPENING_REPORT_CHECK = true;

    private static final String DEFAULT_OPENING_REPORT_REVIEWS = "AAAAAAAAAA";
    private static final String UPDATED_OPENING_REPORT_REVIEWS = "BBBBBBBBBB";

    private static final String DEFAULT_MID_TERM_INSPECTION = "AAAAAAAAAA";
    private static final String UPDATED_MID_TERM_INSPECTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MID_TERM_INSPECTION_CHECK = false;
    private static final Boolean UPDATED_MID_TERM_INSPECTION_CHECK = true;

    private static final String DEFAULT_MID_TERM_INSPECTION_REVIEWS = "AAAAAAAAAA";
    private static final String UPDATED_MID_TERM_INSPECTION_REVIEWS = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_DRAFT = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_DRAFT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FIRST_DRAFT_CHECK = false;
    private static final Boolean UPDATED_FIRST_DRAFT_CHECK = true;

    private static final String DEFAULT_FIRST_DRAFT_REVIEWS = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_DRAFT_REVIEWS = "BBBBBBBBBB";

    private static final String DEFAULT_FINAL_DRAFT = "AAAAAAAAAA";
    private static final String UPDATED_FINAL_DRAFT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FINAL_DRAFT_CHECK = false;
    private static final Boolean UPDATED_FINAL_DRAFT_CHECK = true;

    private static final String DEFAULT_FINAL_DRAFT_REVIEWS = "AAAAAAAAAA";
    private static final String UPDATED_FINAL_DRAFT_REVIEWS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MODIFY_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFY_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private GraduationProjectRepository graduationProjectRepository;

    @Autowired
    private GraduationProjectService graduationProjectService;

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

    private MockMvc restGraduationProjectMockMvc;

    private GraduationProject graduationProject;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GraduationProjectResource graduationProjectResource = new GraduationProjectResource(graduationProjectService);
        this.restGraduationProjectMockMvc = MockMvcBuilders.standaloneSetup(graduationProjectResource)
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
    public static GraduationProject createEntity(EntityManager em) {
        GraduationProject graduationProject = new GraduationProject()
            .graduationProjectTitle(DEFAULT_GRADUATION_PROJECT_TITLE)
            .source(DEFAULT_SOURCE)
            .taskBook(DEFAULT_TASK_BOOK)
            .taskBookCheck(DEFAULT_TASK_BOOK_CHECK)
            .taskBookReviews(DEFAULT_TASK_BOOK_REVIEWS)
            .openingReport(DEFAULT_OPENING_REPORT)
            .openingReportCheck(DEFAULT_OPENING_REPORT_CHECK)
            .openingReportReviews(DEFAULT_OPENING_REPORT_REVIEWS)
            .midTermInspection(DEFAULT_MID_TERM_INSPECTION)
            .midTermInspectionCheck(DEFAULT_MID_TERM_INSPECTION_CHECK)
            .midTermInspectionReviews(DEFAULT_MID_TERM_INSPECTION_REVIEWS)
            .firstDraft(DEFAULT_FIRST_DRAFT)
            .firstDraftCheck(DEFAULT_FIRST_DRAFT_CHECK)
            .firstDraftReviews(DEFAULT_FIRST_DRAFT_REVIEWS)
            .finalDraft(DEFAULT_FINAL_DRAFT)
            .finalDraftCheck(DEFAULT_FINAL_DRAFT_CHECK)
            .finalDraftReviews(DEFAULT_FINAL_DRAFT_REVIEWS)
            .createdTime(DEFAULT_CREATED_TIME)
            .modifyTime(DEFAULT_MODIFY_TIME);
        return graduationProject;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GraduationProject createUpdatedEntity(EntityManager em) {
        GraduationProject graduationProject = new GraduationProject()
            .graduationProjectTitle(UPDATED_GRADUATION_PROJECT_TITLE)
            .source(UPDATED_SOURCE)
            .taskBook(UPDATED_TASK_BOOK)
            .taskBookCheck(UPDATED_TASK_BOOK_CHECK)
            .taskBookReviews(UPDATED_TASK_BOOK_REVIEWS)
            .openingReport(UPDATED_OPENING_REPORT)
            .openingReportCheck(UPDATED_OPENING_REPORT_CHECK)
            .openingReportReviews(UPDATED_OPENING_REPORT_REVIEWS)
            .midTermInspection(UPDATED_MID_TERM_INSPECTION)
            .midTermInspectionCheck(UPDATED_MID_TERM_INSPECTION_CHECK)
            .midTermInspectionReviews(UPDATED_MID_TERM_INSPECTION_REVIEWS)
            .firstDraft(UPDATED_FIRST_DRAFT)
            .firstDraftCheck(UPDATED_FIRST_DRAFT_CHECK)
            .firstDraftReviews(UPDATED_FIRST_DRAFT_REVIEWS)
            .finalDraft(UPDATED_FINAL_DRAFT)
            .finalDraftCheck(UPDATED_FINAL_DRAFT_CHECK)
            .finalDraftReviews(UPDATED_FINAL_DRAFT_REVIEWS)
            .createdTime(UPDATED_CREATED_TIME)
            .modifyTime(UPDATED_MODIFY_TIME);
        return graduationProject;
    }

    @BeforeEach
    public void initTest() {
        graduationProject = createEntity(em);
    }

    @Test
    @Transactional
    public void createGraduationProject() throws Exception {
        int databaseSizeBeforeCreate = graduationProjectRepository.findAll().size();

        // Create the GraduationProject
        restGraduationProjectMockMvc.perform(post("/api/graduation-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(graduationProject)))
            .andExpect(status().isCreated());

        // Validate the GraduationProject in the database
        List<GraduationProject> graduationProjectList = graduationProjectRepository.findAll();
        assertThat(graduationProjectList).hasSize(databaseSizeBeforeCreate + 1);
        GraduationProject testGraduationProject = graduationProjectList.get(graduationProjectList.size() - 1);
        assertThat(testGraduationProject.getGraduationProjectTitle()).isEqualTo(DEFAULT_GRADUATION_PROJECT_TITLE);
        assertThat(testGraduationProject.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testGraduationProject.getTaskBook()).isEqualTo(DEFAULT_TASK_BOOK);
        assertThat(testGraduationProject.isTaskBookCheck()).isEqualTo(DEFAULT_TASK_BOOK_CHECK);
        assertThat(testGraduationProject.getTaskBookReviews()).isEqualTo(DEFAULT_TASK_BOOK_REVIEWS);
        assertThat(testGraduationProject.getOpeningReport()).isEqualTo(DEFAULT_OPENING_REPORT);
        assertThat(testGraduationProject.isOpeningReportCheck()).isEqualTo(DEFAULT_OPENING_REPORT_CHECK);
        assertThat(testGraduationProject.getOpeningReportReviews()).isEqualTo(DEFAULT_OPENING_REPORT_REVIEWS);
        assertThat(testGraduationProject.getMidTermInspection()).isEqualTo(DEFAULT_MID_TERM_INSPECTION);
        assertThat(testGraduationProject.isMidTermInspectionCheck()).isEqualTo(DEFAULT_MID_TERM_INSPECTION_CHECK);
        assertThat(testGraduationProject.getMidTermInspectionReviews()).isEqualTo(DEFAULT_MID_TERM_INSPECTION_REVIEWS);
        assertThat(testGraduationProject.getFirstDraft()).isEqualTo(DEFAULT_FIRST_DRAFT);
        assertThat(testGraduationProject.isFirstDraftCheck()).isEqualTo(DEFAULT_FIRST_DRAFT_CHECK);
        assertThat(testGraduationProject.getFirstDraftReviews()).isEqualTo(DEFAULT_FIRST_DRAFT_REVIEWS);
        assertThat(testGraduationProject.getFinalDraft()).isEqualTo(DEFAULT_FINAL_DRAFT);
        assertThat(testGraduationProject.isFinalDraftCheck()).isEqualTo(DEFAULT_FINAL_DRAFT_CHECK);
        assertThat(testGraduationProject.getFinalDraftReviews()).isEqualTo(DEFAULT_FINAL_DRAFT_REVIEWS);
        assertThat(testGraduationProject.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testGraduationProject.getModifyTime()).isEqualTo(DEFAULT_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void createGraduationProjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = graduationProjectRepository.findAll().size();

        // Create the GraduationProject with an existing ID
        graduationProject.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGraduationProjectMockMvc.perform(post("/api/graduation-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(graduationProject)))
            .andExpect(status().isBadRequest());

        // Validate the GraduationProject in the database
        List<GraduationProject> graduationProjectList = graduationProjectRepository.findAll();
        assertThat(graduationProjectList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGraduationProjects() throws Exception {
        // Initialize the database
        graduationProjectRepository.saveAndFlush(graduationProject);

        // Get all the graduationProjectList
        restGraduationProjectMockMvc.perform(get("/api/graduation-projects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(graduationProject.getId().intValue())))
            .andExpect(jsonPath("$.[*].graduationProjectTitle").value(hasItem(DEFAULT_GRADUATION_PROJECT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].taskBook").value(hasItem(DEFAULT_TASK_BOOK.toString())))
            .andExpect(jsonPath("$.[*].taskBookCheck").value(hasItem(DEFAULT_TASK_BOOK_CHECK.booleanValue())))
            .andExpect(jsonPath("$.[*].taskBookReviews").value(hasItem(DEFAULT_TASK_BOOK_REVIEWS.toString())))
            .andExpect(jsonPath("$.[*].openingReport").value(hasItem(DEFAULT_OPENING_REPORT.toString())))
            .andExpect(jsonPath("$.[*].openingReportCheck").value(hasItem(DEFAULT_OPENING_REPORT_CHECK.booleanValue())))
            .andExpect(jsonPath("$.[*].openingReportReviews").value(hasItem(DEFAULT_OPENING_REPORT_REVIEWS.toString())))
            .andExpect(jsonPath("$.[*].midTermInspection").value(hasItem(DEFAULT_MID_TERM_INSPECTION.toString())))
            .andExpect(jsonPath("$.[*].midTermInspectionCheck").value(hasItem(DEFAULT_MID_TERM_INSPECTION_CHECK.booleanValue())))
            .andExpect(jsonPath("$.[*].midTermInspectionReviews").value(hasItem(DEFAULT_MID_TERM_INSPECTION_REVIEWS.toString())))
            .andExpect(jsonPath("$.[*].firstDraft").value(hasItem(DEFAULT_FIRST_DRAFT.toString())))
            .andExpect(jsonPath("$.[*].firstDraftCheck").value(hasItem(DEFAULT_FIRST_DRAFT_CHECK.booleanValue())))
            .andExpect(jsonPath("$.[*].firstDraftReviews").value(hasItem(DEFAULT_FIRST_DRAFT_REVIEWS.toString())))
            .andExpect(jsonPath("$.[*].finalDraft").value(hasItem(DEFAULT_FINAL_DRAFT.toString())))
            .andExpect(jsonPath("$.[*].finalDraftCheck").value(hasItem(DEFAULT_FINAL_DRAFT_CHECK.booleanValue())))
            .andExpect(jsonPath("$.[*].finalDraftReviews").value(hasItem(DEFAULT_FINAL_DRAFT_REVIEWS.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(sameInstant(DEFAULT_CREATED_TIME))))
            .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(sameInstant(DEFAULT_MODIFY_TIME))));
    }
    
    @Test
    @Transactional
    public void getGraduationProject() throws Exception {
        // Initialize the database
        graduationProjectRepository.saveAndFlush(graduationProject);

        // Get the graduationProject
        restGraduationProjectMockMvc.perform(get("/api/graduation-projects/{id}", graduationProject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(graduationProject.getId().intValue()))
            .andExpect(jsonPath("$.graduationProjectTitle").value(DEFAULT_GRADUATION_PROJECT_TITLE.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.taskBook").value(DEFAULT_TASK_BOOK.toString()))
            .andExpect(jsonPath("$.taskBookCheck").value(DEFAULT_TASK_BOOK_CHECK.booleanValue()))
            .andExpect(jsonPath("$.taskBookReviews").value(DEFAULT_TASK_BOOK_REVIEWS.toString()))
            .andExpect(jsonPath("$.openingReport").value(DEFAULT_OPENING_REPORT.toString()))
            .andExpect(jsonPath("$.openingReportCheck").value(DEFAULT_OPENING_REPORT_CHECK.booleanValue()))
            .andExpect(jsonPath("$.openingReportReviews").value(DEFAULT_OPENING_REPORT_REVIEWS.toString()))
            .andExpect(jsonPath("$.midTermInspection").value(DEFAULT_MID_TERM_INSPECTION.toString()))
            .andExpect(jsonPath("$.midTermInspectionCheck").value(DEFAULT_MID_TERM_INSPECTION_CHECK.booleanValue()))
            .andExpect(jsonPath("$.midTermInspectionReviews").value(DEFAULT_MID_TERM_INSPECTION_REVIEWS.toString()))
            .andExpect(jsonPath("$.firstDraft").value(DEFAULT_FIRST_DRAFT.toString()))
            .andExpect(jsonPath("$.firstDraftCheck").value(DEFAULT_FIRST_DRAFT_CHECK.booleanValue()))
            .andExpect(jsonPath("$.firstDraftReviews").value(DEFAULT_FIRST_DRAFT_REVIEWS.toString()))
            .andExpect(jsonPath("$.finalDraft").value(DEFAULT_FINAL_DRAFT.toString()))
            .andExpect(jsonPath("$.finalDraftCheck").value(DEFAULT_FINAL_DRAFT_CHECK.booleanValue()))
            .andExpect(jsonPath("$.finalDraftReviews").value(DEFAULT_FINAL_DRAFT_REVIEWS.toString()))
            .andExpect(jsonPath("$.createdTime").value(sameInstant(DEFAULT_CREATED_TIME)))
            .andExpect(jsonPath("$.modifyTime").value(sameInstant(DEFAULT_MODIFY_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingGraduationProject() throws Exception {
        // Get the graduationProject
        restGraduationProjectMockMvc.perform(get("/api/graduation-projects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGraduationProject() throws Exception {
        // Initialize the database
        graduationProjectService.save(graduationProject);

        int databaseSizeBeforeUpdate = graduationProjectRepository.findAll().size();

        // Update the graduationProject
        GraduationProject updatedGraduationProject = graduationProjectRepository.findById(graduationProject.getId()).get();
        // Disconnect from session so that the updates on updatedGraduationProject are not directly saved in db
        em.detach(updatedGraduationProject);
        updatedGraduationProject
            .graduationProjectTitle(UPDATED_GRADUATION_PROJECT_TITLE)
            .source(UPDATED_SOURCE)
            .taskBook(UPDATED_TASK_BOOK)
            .taskBookCheck(UPDATED_TASK_BOOK_CHECK)
            .taskBookReviews(UPDATED_TASK_BOOK_REVIEWS)
            .openingReport(UPDATED_OPENING_REPORT)
            .openingReportCheck(UPDATED_OPENING_REPORT_CHECK)
            .openingReportReviews(UPDATED_OPENING_REPORT_REVIEWS)
            .midTermInspection(UPDATED_MID_TERM_INSPECTION)
            .midTermInspectionCheck(UPDATED_MID_TERM_INSPECTION_CHECK)
            .midTermInspectionReviews(UPDATED_MID_TERM_INSPECTION_REVIEWS)
            .firstDraft(UPDATED_FIRST_DRAFT)
            .firstDraftCheck(UPDATED_FIRST_DRAFT_CHECK)
            .firstDraftReviews(UPDATED_FIRST_DRAFT_REVIEWS)
            .finalDraft(UPDATED_FINAL_DRAFT)
            .finalDraftCheck(UPDATED_FINAL_DRAFT_CHECK)
            .finalDraftReviews(UPDATED_FINAL_DRAFT_REVIEWS)
            .createdTime(UPDATED_CREATED_TIME)
            .modifyTime(UPDATED_MODIFY_TIME);

        restGraduationProjectMockMvc.perform(put("/api/graduation-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGraduationProject)))
            .andExpect(status().isOk());

        // Validate the GraduationProject in the database
        List<GraduationProject> graduationProjectList = graduationProjectRepository.findAll();
        assertThat(graduationProjectList).hasSize(databaseSizeBeforeUpdate);
        GraduationProject testGraduationProject = graduationProjectList.get(graduationProjectList.size() - 1);
        assertThat(testGraduationProject.getGraduationProjectTitle()).isEqualTo(UPDATED_GRADUATION_PROJECT_TITLE);
        assertThat(testGraduationProject.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testGraduationProject.getTaskBook()).isEqualTo(UPDATED_TASK_BOOK);
        assertThat(testGraduationProject.isTaskBookCheck()).isEqualTo(UPDATED_TASK_BOOK_CHECK);
        assertThat(testGraduationProject.getTaskBookReviews()).isEqualTo(UPDATED_TASK_BOOK_REVIEWS);
        assertThat(testGraduationProject.getOpeningReport()).isEqualTo(UPDATED_OPENING_REPORT);
        assertThat(testGraduationProject.isOpeningReportCheck()).isEqualTo(UPDATED_OPENING_REPORT_CHECK);
        assertThat(testGraduationProject.getOpeningReportReviews()).isEqualTo(UPDATED_OPENING_REPORT_REVIEWS);
        assertThat(testGraduationProject.getMidTermInspection()).isEqualTo(UPDATED_MID_TERM_INSPECTION);
        assertThat(testGraduationProject.isMidTermInspectionCheck()).isEqualTo(UPDATED_MID_TERM_INSPECTION_CHECK);
        assertThat(testGraduationProject.getMidTermInspectionReviews()).isEqualTo(UPDATED_MID_TERM_INSPECTION_REVIEWS);
        assertThat(testGraduationProject.getFirstDraft()).isEqualTo(UPDATED_FIRST_DRAFT);
        assertThat(testGraduationProject.isFirstDraftCheck()).isEqualTo(UPDATED_FIRST_DRAFT_CHECK);
        assertThat(testGraduationProject.getFirstDraftReviews()).isEqualTo(UPDATED_FIRST_DRAFT_REVIEWS);
        assertThat(testGraduationProject.getFinalDraft()).isEqualTo(UPDATED_FINAL_DRAFT);
        assertThat(testGraduationProject.isFinalDraftCheck()).isEqualTo(UPDATED_FINAL_DRAFT_CHECK);
        assertThat(testGraduationProject.getFinalDraftReviews()).isEqualTo(UPDATED_FINAL_DRAFT_REVIEWS);
        assertThat(testGraduationProject.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testGraduationProject.getModifyTime()).isEqualTo(UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingGraduationProject() throws Exception {
        int databaseSizeBeforeUpdate = graduationProjectRepository.findAll().size();

        // Create the GraduationProject

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGraduationProjectMockMvc.perform(put("/api/graduation-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(graduationProject)))
            .andExpect(status().isBadRequest());

        // Validate the GraduationProject in the database
        List<GraduationProject> graduationProjectList = graduationProjectRepository.findAll();
        assertThat(graduationProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGraduationProject() throws Exception {
        // Initialize the database
        graduationProjectService.save(graduationProject);

        int databaseSizeBeforeDelete = graduationProjectRepository.findAll().size();

        // Delete the graduationProject
        restGraduationProjectMockMvc.perform(delete("/api/graduation-projects/{id}", graduationProject.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GraduationProject> graduationProjectList = graduationProjectRepository.findAll();
        assertThat(graduationProjectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GraduationProject.class);
        GraduationProject graduationProject1 = new GraduationProject();
        graduationProject1.setId(1L);
        GraduationProject graduationProject2 = new GraduationProject();
        graduationProject2.setId(graduationProject1.getId());
        assertThat(graduationProject1).isEqualTo(graduationProject2);
        graduationProject2.setId(2L);
        assertThat(graduationProject1).isNotEqualTo(graduationProject2);
        graduationProject1.setId(null);
        assertThat(graduationProject1).isNotEqualTo(graduationProject2);
    }
}
