package com.intership.server.web.rest;

import com.intership.server.IntershipSystemApp;
import com.intership.server.domain.InternshipReport;
import com.intership.server.repository.InternshipReportRepository;
import com.intership.server.service.InternshipReportService;
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
 * Integration tests for the {@Link InternshipReportResource} REST controller.
 */
@SpringBootTest(classes = IntershipSystemApp.class)
public class InternshipReportResourceIT {

    private static final String DEFAULT_REPORT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_REPORT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INTERSHIP_STAGE = "AAAAAAAAAA";
    private static final String UPDATED_INTERSHIP_STAGE = "BBBBBBBBBB";

    private static final String DEFAULT_ANNEX = "AAAAAAAAAA";
    private static final String UPDATED_ANNEX = "BBBBBBBBBB";

    private static final String DEFAULT_REPORT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_REPORT_CONTENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_STAR = "AAAAAAAAAA";
    private static final String UPDATED_STAR = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_OPERATING = "AAAAAAAAAA";
    private static final String UPDATED_OPERATING = "BBBBBBBBBB";

    @Autowired
    private InternshipReportRepository internshipReportRepository;

    @Autowired
    private InternshipReportService internshipReportService;

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

    private MockMvc restInternshipReportMockMvc;

    private InternshipReport internshipReport;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InternshipReportResource internshipReportResource = new InternshipReportResource(internshipReportService);
        this.restInternshipReportMockMvc = MockMvcBuilders.standaloneSetup(internshipReportResource)
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
    public static InternshipReport createEntity(EntityManager em) {
        InternshipReport internshipReport = new InternshipReport()
            .reportType(DEFAULT_REPORT_TYPE)
            .projectName(DEFAULT_PROJECT_NAME)
            .intershipStage(DEFAULT_INTERSHIP_STAGE)
            .annex(DEFAULT_ANNEX)
            .reportContent(DEFAULT_REPORT_CONTENT)
            .createTime(DEFAULT_CREATE_TIME)
            .star(DEFAULT_STAR)
            .status(DEFAULT_STATUS)
            .operating(DEFAULT_OPERATING);
        return internshipReport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InternshipReport createUpdatedEntity(EntityManager em) {
        InternshipReport internshipReport = new InternshipReport()
            .reportType(UPDATED_REPORT_TYPE)
            .projectName(UPDATED_PROJECT_NAME)
            .intershipStage(UPDATED_INTERSHIP_STAGE)
            .annex(UPDATED_ANNEX)
            .reportContent(UPDATED_REPORT_CONTENT)
            .createTime(UPDATED_CREATE_TIME)
            .star(UPDATED_STAR)
            .status(UPDATED_STATUS)
            .operating(UPDATED_OPERATING);
        return internshipReport;
    }

    @BeforeEach
    public void initTest() {
        internshipReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createInternshipReport() throws Exception {
        int databaseSizeBeforeCreate = internshipReportRepository.findAll().size();

        // Create the InternshipReport
        restInternshipReportMockMvc.perform(post("/api/internship-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internshipReport)))
            .andExpect(status().isCreated());

        // Validate the InternshipReport in the database
        List<InternshipReport> internshipReportList = internshipReportRepository.findAll();
        assertThat(internshipReportList).hasSize(databaseSizeBeforeCreate + 1);
        InternshipReport testInternshipReport = internshipReportList.get(internshipReportList.size() - 1);
        assertThat(testInternshipReport.getReportType()).isEqualTo(DEFAULT_REPORT_TYPE);
        assertThat(testInternshipReport.getProjectName()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testInternshipReport.getIntershipStage()).isEqualTo(DEFAULT_INTERSHIP_STAGE);
        assertThat(testInternshipReport.getAnnex()).isEqualTo(DEFAULT_ANNEX);
        assertThat(testInternshipReport.getReportContent()).isEqualTo(DEFAULT_REPORT_CONTENT);
        assertThat(testInternshipReport.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testInternshipReport.getStar()).isEqualTo(DEFAULT_STAR);
        assertThat(testInternshipReport.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testInternshipReport.getOperating()).isEqualTo(DEFAULT_OPERATING);
    }

    @Test
    @Transactional
    public void createInternshipReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = internshipReportRepository.findAll().size();

        // Create the InternshipReport with an existing ID
        internshipReport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInternshipReportMockMvc.perform(post("/api/internship-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internshipReport)))
            .andExpect(status().isBadRequest());

        // Validate the InternshipReport in the database
        List<InternshipReport> internshipReportList = internshipReportRepository.findAll();
        assertThat(internshipReportList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInternshipReports() throws Exception {
        // Initialize the database
        internshipReportRepository.saveAndFlush(internshipReport);

        // Get all the internshipReportList
        restInternshipReportMockMvc.perform(get("/api/internship-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(internshipReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].reportType").value(hasItem(DEFAULT_REPORT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].projectName").value(hasItem(DEFAULT_PROJECT_NAME.toString())))
            .andExpect(jsonPath("$.[*].intershipStage").value(hasItem(DEFAULT_INTERSHIP_STAGE.toString())))
            .andExpect(jsonPath("$.[*].annex").value(hasItem(DEFAULT_ANNEX.toString())))
            .andExpect(jsonPath("$.[*].reportContent").value(hasItem(DEFAULT_REPORT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].star").value(hasItem(DEFAULT_STAR.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].operating").value(hasItem(DEFAULT_OPERATING.toString())));
    }
    
    @Test
    @Transactional
    public void getInternshipReport() throws Exception {
        // Initialize the database
        internshipReportRepository.saveAndFlush(internshipReport);

        // Get the internshipReport
        restInternshipReportMockMvc.perform(get("/api/internship-reports/{id}", internshipReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(internshipReport.getId().intValue()))
            .andExpect(jsonPath("$.reportType").value(DEFAULT_REPORT_TYPE.toString()))
            .andExpect(jsonPath("$.projectName").value(DEFAULT_PROJECT_NAME.toString()))
            .andExpect(jsonPath("$.intershipStage").value(DEFAULT_INTERSHIP_STAGE.toString()))
            .andExpect(jsonPath("$.annex").value(DEFAULT_ANNEX.toString()))
            .andExpect(jsonPath("$.reportContent").value(DEFAULT_REPORT_CONTENT.toString()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.star").value(DEFAULT_STAR.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.operating").value(DEFAULT_OPERATING.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInternshipReport() throws Exception {
        // Get the internshipReport
        restInternshipReportMockMvc.perform(get("/api/internship-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInternshipReport() throws Exception {
        // Initialize the database
        internshipReportService.save(internshipReport);

        int databaseSizeBeforeUpdate = internshipReportRepository.findAll().size();

        // Update the internshipReport
        InternshipReport updatedInternshipReport = internshipReportRepository.findById(internshipReport.getId()).get();
        // Disconnect from session so that the updates on updatedInternshipReport are not directly saved in db
        em.detach(updatedInternshipReport);
        updatedInternshipReport
            .reportType(UPDATED_REPORT_TYPE)
            .projectName(UPDATED_PROJECT_NAME)
            .intershipStage(UPDATED_INTERSHIP_STAGE)
            .annex(UPDATED_ANNEX)
            .reportContent(UPDATED_REPORT_CONTENT)
            .createTime(UPDATED_CREATE_TIME)
            .star(UPDATED_STAR)
            .status(UPDATED_STATUS)
            .operating(UPDATED_OPERATING);

        restInternshipReportMockMvc.perform(put("/api/internship-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInternshipReport)))
            .andExpect(status().isOk());

        // Validate the InternshipReport in the database
        List<InternshipReport> internshipReportList = internshipReportRepository.findAll();
        assertThat(internshipReportList).hasSize(databaseSizeBeforeUpdate);
        InternshipReport testInternshipReport = internshipReportList.get(internshipReportList.size() - 1);
        assertThat(testInternshipReport.getReportType()).isEqualTo(UPDATED_REPORT_TYPE);
        assertThat(testInternshipReport.getProjectName()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testInternshipReport.getIntershipStage()).isEqualTo(UPDATED_INTERSHIP_STAGE);
        assertThat(testInternshipReport.getAnnex()).isEqualTo(UPDATED_ANNEX);
        assertThat(testInternshipReport.getReportContent()).isEqualTo(UPDATED_REPORT_CONTENT);
        assertThat(testInternshipReport.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testInternshipReport.getStar()).isEqualTo(UPDATED_STAR);
        assertThat(testInternshipReport.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testInternshipReport.getOperating()).isEqualTo(UPDATED_OPERATING);
    }

    @Test
    @Transactional
    public void updateNonExistingInternshipReport() throws Exception {
        int databaseSizeBeforeUpdate = internshipReportRepository.findAll().size();

        // Create the InternshipReport

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInternshipReportMockMvc.perform(put("/api/internship-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internshipReport)))
            .andExpect(status().isBadRequest());

        // Validate the InternshipReport in the database
        List<InternshipReport> internshipReportList = internshipReportRepository.findAll();
        assertThat(internshipReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInternshipReport() throws Exception {
        // Initialize the database
        internshipReportService.save(internshipReport);

        int databaseSizeBeforeDelete = internshipReportRepository.findAll().size();

        // Delete the internshipReport
        restInternshipReportMockMvc.perform(delete("/api/internship-reports/{id}", internshipReport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InternshipReport> internshipReportList = internshipReportRepository.findAll();
        assertThat(internshipReportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InternshipReport.class);
        InternshipReport internshipReport1 = new InternshipReport();
        internshipReport1.setId(1L);
        InternshipReport internshipReport2 = new InternshipReport();
        internshipReport2.setId(internshipReport1.getId());
        assertThat(internshipReport1).isEqualTo(internshipReport2);
        internshipReport2.setId(2L);
        assertThat(internshipReport1).isNotEqualTo(internshipReport2);
        internshipReport1.setId(null);
        assertThat(internshipReport1).isNotEqualTo(internshipReport2);
    }
}
