package com.intership.server.web.rest;

import com.intership.server.IntershipSystemApp;
import com.intership.server.domain.Intership;
import com.intership.server.repository.IntershipRepository;
import com.intership.server.service.IntershipService;
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
 * Integration tests for the {@Link IntershipResource} REST controller.
 */
@SpringBootTest(classes = IntershipSystemApp.class)
public class IntershipResourceIT {

    private static final String DEFAULT_BATCH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BATCH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRACTICE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PRACTICE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE = "AAAAAAAAAA";
    private static final String UPDATED_COURSE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_BEGIN_INTERSHIP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BEGIN_INTERSHIP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_INTERSHIP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_INTERSHIP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_TUTOR_SCORE = 1;
    private static final Integer UPDATED_TUTOR_SCORE = 2;

    private static final Integer DEFAULT_MASTER_SCORE = 1;
    private static final Integer UPDATED_MASTER_SCORE = 2;

    private static final Integer DEFAULT_INTERSHIP_SCORE = 1;
    private static final Integer UPDATED_INTERSHIP_SCORE = 2;

    private static final String DEFAULT_COMPANY_CREDIT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CREDIT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNSHIP = "AAAAAAAAAA";
    private static final String UPDATED_INTERNSHIP = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_CONTACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CONTACT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_CONTACT_JOB = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CONTACT_JOB = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_CONTACT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CONTACT_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_MASTER_CONTACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MASTER_CONTACT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MASTER_CONTACT_SKILL = "AAAAAAAAAA";
    private static final String UPDATED_MASTER_CONTACT_SKILL = "BBBBBBBBBB";

    private static final String DEFAULT_MASTER_CONTACT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_MASTER_CONTACT_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_URGENT_CONTACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_URGENT_CONTACT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_URGENT_CONTACT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_URGENT_CONTACT_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_URGENT_CONTACT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_URGENT_CONTACT_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOMMODATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOMMODATION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOMMODATION_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ACCOMMODATION_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_DETAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_DETAIL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NATURE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NATURE = "BBBBBBBBBB";

    private static final String DEFAULT_SCALE = "AAAAAAAAAA";
    private static final String UPDATED_SCALE = "BBBBBBBBBB";

    private static final String DEFAULT_INDUSTRY = "AAAAAAAAAA";
    private static final String UPDATED_INDUSTRY = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_PROFILE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_PROFILE = "BBBBBBBBBB";

    private static final String DEFAULT_IS_INSURANCE = "AAAAAAAAAA";
    private static final String UPDATED_IS_INSURANCE = "BBBBBBBBBB";

    private static final String DEFAULT_INSURANCE_COMPANY_AND_POLICY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_INSURANCE_COMPANY_AND_POLICY_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_INTERNSHIP_AGREEMENT = false;
    private static final Boolean UPDATED_IS_INTERNSHIP_AGREEMENT = true;

    private static final String DEFAULT_ANNEX = "AAAAAAAAAA";
    private static final String UPDATED_ANNEX = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MODIFY_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFY_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private IntershipRepository intershipRepository;

    @Autowired
    private IntershipService intershipService;

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

    private MockMvc restIntershipMockMvc;

    private Intership intership;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IntershipResource intershipResource = new IntershipResource(intershipService);
        this.restIntershipMockMvc = MockMvcBuilders.standaloneSetup(intershipResource)
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
    public static Intership createEntity(EntityManager em) {
        Intership intership = new Intership()
            .batchName(DEFAULT_BATCH_NAME)
            .practiceType(DEFAULT_PRACTICE_TYPE)
            .course(DEFAULT_COURSE)
            .beginIntership(DEFAULT_BEGIN_INTERSHIP)
            .endIntership(DEFAULT_END_INTERSHIP)
            .tutorScore(DEFAULT_TUTOR_SCORE)
            .masterScore(DEFAULT_MASTER_SCORE)
            .intershipScore(DEFAULT_INTERSHIP_SCORE)
            .companyCreditCode(DEFAULT_COMPANY_CREDIT_CODE)
            .companyName(DEFAULT_COMPANY_NAME)
            .internship(DEFAULT_INTERNSHIP)
            .companyContactName(DEFAULT_COMPANY_CONTACT_NAME)
            .companyContactJob(DEFAULT_COMPANY_CONTACT_JOB)
            .companyContactPhone(DEFAULT_COMPANY_CONTACT_PHONE)
            .masterContactName(DEFAULT_MASTER_CONTACT_NAME)
            .masterContactSkill(DEFAULT_MASTER_CONTACT_SKILL)
            .masterContactPhone(DEFAULT_MASTER_CONTACT_PHONE)
            .urgentContactName(DEFAULT_URGENT_CONTACT_NAME)
            .urgentContactPhone(DEFAULT_URGENT_CONTACT_PHONE)
            .urgentContactAddress(DEFAULT_URGENT_CONTACT_ADDRESS)
            .accommodationType(DEFAULT_ACCOMMODATION_TYPE)
            .accommodationAddress(DEFAULT_ACCOMMODATION_ADDRESS)
            .companyAddress(DEFAULT_COMPANY_ADDRESS)
            .companyDetailAddress(DEFAULT_COMPANY_DETAIL_ADDRESS)
            .companyNature(DEFAULT_COMPANY_NATURE)
            .scale(DEFAULT_SCALE)
            .industry(DEFAULT_INDUSTRY)
            .companyProfile(DEFAULT_COMPANY_PROFILE)
            .isInsurance(DEFAULT_IS_INSURANCE)
            .insuranceCompanyAndPolicyNumber(DEFAULT_INSURANCE_COMPANY_AND_POLICY_NUMBER)
            .isInternshipAgreement(DEFAULT_IS_INTERNSHIP_AGREEMENT)
            .annex(DEFAULT_ANNEX)
            .createTime(DEFAULT_CREATE_TIME)
            .modifyTime(DEFAULT_MODIFY_TIME);
        return intership;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Intership createUpdatedEntity(EntityManager em) {
        Intership intership = new Intership()
            .batchName(UPDATED_BATCH_NAME)
            .practiceType(UPDATED_PRACTICE_TYPE)
            .course(UPDATED_COURSE)
            .beginIntership(UPDATED_BEGIN_INTERSHIP)
            .endIntership(UPDATED_END_INTERSHIP)
            .tutorScore(UPDATED_TUTOR_SCORE)
            .masterScore(UPDATED_MASTER_SCORE)
            .intershipScore(UPDATED_INTERSHIP_SCORE)
            .companyCreditCode(UPDATED_COMPANY_CREDIT_CODE)
            .companyName(UPDATED_COMPANY_NAME)
            .internship(UPDATED_INTERNSHIP)
            .companyContactName(UPDATED_COMPANY_CONTACT_NAME)
            .companyContactJob(UPDATED_COMPANY_CONTACT_JOB)
            .companyContactPhone(UPDATED_COMPANY_CONTACT_PHONE)
            .masterContactName(UPDATED_MASTER_CONTACT_NAME)
            .masterContactSkill(UPDATED_MASTER_CONTACT_SKILL)
            .masterContactPhone(UPDATED_MASTER_CONTACT_PHONE)
            .urgentContactName(UPDATED_URGENT_CONTACT_NAME)
            .urgentContactPhone(UPDATED_URGENT_CONTACT_PHONE)
            .urgentContactAddress(UPDATED_URGENT_CONTACT_ADDRESS)
            .accommodationType(UPDATED_ACCOMMODATION_TYPE)
            .accommodationAddress(UPDATED_ACCOMMODATION_ADDRESS)
            .companyAddress(UPDATED_COMPANY_ADDRESS)
            .companyDetailAddress(UPDATED_COMPANY_DETAIL_ADDRESS)
            .companyNature(UPDATED_COMPANY_NATURE)
            .scale(UPDATED_SCALE)
            .industry(UPDATED_INDUSTRY)
            .companyProfile(UPDATED_COMPANY_PROFILE)
            .isInsurance(UPDATED_IS_INSURANCE)
            .insuranceCompanyAndPolicyNumber(UPDATED_INSURANCE_COMPANY_AND_POLICY_NUMBER)
            .isInternshipAgreement(UPDATED_IS_INTERNSHIP_AGREEMENT)
            .annex(UPDATED_ANNEX)
            .createTime(UPDATED_CREATE_TIME)
            .modifyTime(UPDATED_MODIFY_TIME);
        return intership;
    }

    @BeforeEach
    public void initTest() {
        intership = createEntity(em);
    }

    @Test
    @Transactional
    public void createIntership() throws Exception {
        int databaseSizeBeforeCreate = intershipRepository.findAll().size();

        // Create the Intership
        restIntershipMockMvc.perform(post("/api/interships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intership)))
            .andExpect(status().isCreated());

        // Validate the Intership in the database
        List<Intership> intershipList = intershipRepository.findAll();
        assertThat(intershipList).hasSize(databaseSizeBeforeCreate + 1);
        Intership testIntership = intershipList.get(intershipList.size() - 1);
        assertThat(testIntership.getBatchName()).isEqualTo(DEFAULT_BATCH_NAME);
        assertThat(testIntership.getPracticeType()).isEqualTo(DEFAULT_PRACTICE_TYPE);
        assertThat(testIntership.getCourse()).isEqualTo(DEFAULT_COURSE);
        assertThat(testIntership.getBeginIntership()).isEqualTo(DEFAULT_BEGIN_INTERSHIP);
        assertThat(testIntership.getEndIntership()).isEqualTo(DEFAULT_END_INTERSHIP);
        assertThat(testIntership.getTutorScore()).isEqualTo(DEFAULT_TUTOR_SCORE);
        assertThat(testIntership.getMasterScore()).isEqualTo(DEFAULT_MASTER_SCORE);
        assertThat(testIntership.getIntershipScore()).isEqualTo(DEFAULT_INTERSHIP_SCORE);
        assertThat(testIntership.getCompanyCreditCode()).isEqualTo(DEFAULT_COMPANY_CREDIT_CODE);
        assertThat(testIntership.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testIntership.getInternship()).isEqualTo(DEFAULT_INTERNSHIP);
        assertThat(testIntership.getCompanyContactName()).isEqualTo(DEFAULT_COMPANY_CONTACT_NAME);
        assertThat(testIntership.getCompanyContactJob()).isEqualTo(DEFAULT_COMPANY_CONTACT_JOB);
        assertThat(testIntership.getCompanyContactPhone()).isEqualTo(DEFAULT_COMPANY_CONTACT_PHONE);
        assertThat(testIntership.getMasterContactName()).isEqualTo(DEFAULT_MASTER_CONTACT_NAME);
        assertThat(testIntership.getMasterContactSkill()).isEqualTo(DEFAULT_MASTER_CONTACT_SKILL);
        assertThat(testIntership.getMasterContactPhone()).isEqualTo(DEFAULT_MASTER_CONTACT_PHONE);
        assertThat(testIntership.getUrgentContactName()).isEqualTo(DEFAULT_URGENT_CONTACT_NAME);
        assertThat(testIntership.getUrgentContactPhone()).isEqualTo(DEFAULT_URGENT_CONTACT_PHONE);
        assertThat(testIntership.getUrgentContactAddress()).isEqualTo(DEFAULT_URGENT_CONTACT_ADDRESS);
        assertThat(testIntership.getAccommodationType()).isEqualTo(DEFAULT_ACCOMMODATION_TYPE);
        assertThat(testIntership.getAccommodationAddress()).isEqualTo(DEFAULT_ACCOMMODATION_ADDRESS);
        assertThat(testIntership.getCompanyAddress()).isEqualTo(DEFAULT_COMPANY_ADDRESS);
        assertThat(testIntership.getCompanyDetailAddress()).isEqualTo(DEFAULT_COMPANY_DETAIL_ADDRESS);
        assertThat(testIntership.getCompanyNature()).isEqualTo(DEFAULT_COMPANY_NATURE);
        assertThat(testIntership.getScale()).isEqualTo(DEFAULT_SCALE);
        assertThat(testIntership.getIndustry()).isEqualTo(DEFAULT_INDUSTRY);
        assertThat(testIntership.getCompanyProfile()).isEqualTo(DEFAULT_COMPANY_PROFILE);
        assertThat(testIntership.getIsInsurance()).isEqualTo(DEFAULT_IS_INSURANCE);
        assertThat(testIntership.getInsuranceCompanyAndPolicyNumber()).isEqualTo(DEFAULT_INSURANCE_COMPANY_AND_POLICY_NUMBER);
        assertThat(testIntership.isIsInternshipAgreement()).isEqualTo(DEFAULT_IS_INTERNSHIP_AGREEMENT);
        assertThat(testIntership.getAnnex()).isEqualTo(DEFAULT_ANNEX);
        assertThat(testIntership.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testIntership.getModifyTime()).isEqualTo(DEFAULT_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void createIntershipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = intershipRepository.findAll().size();

        // Create the Intership with an existing ID
        intership.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntershipMockMvc.perform(post("/api/interships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intership)))
            .andExpect(status().isBadRequest());

        // Validate the Intership in the database
        List<Intership> intershipList = intershipRepository.findAll();
        assertThat(intershipList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInterships() throws Exception {
        // Initialize the database
        intershipRepository.saveAndFlush(intership);

        // Get all the intershipList
        restIntershipMockMvc.perform(get("/api/interships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intership.getId().intValue())))
            .andExpect(jsonPath("$.[*].batchName").value(hasItem(DEFAULT_BATCH_NAME.toString())))
            .andExpect(jsonPath("$.[*].practiceType").value(hasItem(DEFAULT_PRACTICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].course").value(hasItem(DEFAULT_COURSE.toString())))
            .andExpect(jsonPath("$.[*].beginIntership").value(hasItem(sameInstant(DEFAULT_BEGIN_INTERSHIP))))
            .andExpect(jsonPath("$.[*].endIntership").value(hasItem(sameInstant(DEFAULT_END_INTERSHIP))))
            .andExpect(jsonPath("$.[*].tutorScore").value(hasItem(DEFAULT_TUTOR_SCORE)))
            .andExpect(jsonPath("$.[*].masterScore").value(hasItem(DEFAULT_MASTER_SCORE)))
            .andExpect(jsonPath("$.[*].intershipScore").value(hasItem(DEFAULT_INTERSHIP_SCORE)))
            .andExpect(jsonPath("$.[*].companyCreditCode").value(hasItem(DEFAULT_COMPANY_CREDIT_CODE.toString())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].internship").value(hasItem(DEFAULT_INTERNSHIP.toString())))
            .andExpect(jsonPath("$.[*].companyContactName").value(hasItem(DEFAULT_COMPANY_CONTACT_NAME.toString())))
            .andExpect(jsonPath("$.[*].companyContactJob").value(hasItem(DEFAULT_COMPANY_CONTACT_JOB.toString())))
            .andExpect(jsonPath("$.[*].companyContactPhone").value(hasItem(DEFAULT_COMPANY_CONTACT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].masterContactName").value(hasItem(DEFAULT_MASTER_CONTACT_NAME.toString())))
            .andExpect(jsonPath("$.[*].masterContactSkill").value(hasItem(DEFAULT_MASTER_CONTACT_SKILL.toString())))
            .andExpect(jsonPath("$.[*].masterContactPhone").value(hasItem(DEFAULT_MASTER_CONTACT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].urgentContactName").value(hasItem(DEFAULT_URGENT_CONTACT_NAME.toString())))
            .andExpect(jsonPath("$.[*].urgentContactPhone").value(hasItem(DEFAULT_URGENT_CONTACT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].urgentContactAddress").value(hasItem(DEFAULT_URGENT_CONTACT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].accommodationType").value(hasItem(DEFAULT_ACCOMMODATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].accommodationAddress").value(hasItem(DEFAULT_ACCOMMODATION_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].companyAddress").value(hasItem(DEFAULT_COMPANY_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].companyDetailAddress").value(hasItem(DEFAULT_COMPANY_DETAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].companyNature").value(hasItem(DEFAULT_COMPANY_NATURE.toString())))
            .andExpect(jsonPath("$.[*].scale").value(hasItem(DEFAULT_SCALE.toString())))
            .andExpect(jsonPath("$.[*].industry").value(hasItem(DEFAULT_INDUSTRY.toString())))
            .andExpect(jsonPath("$.[*].companyProfile").value(hasItem(DEFAULT_COMPANY_PROFILE.toString())))
            .andExpect(jsonPath("$.[*].isInsurance").value(hasItem(DEFAULT_IS_INSURANCE.toString())))
            .andExpect(jsonPath("$.[*].insuranceCompanyAndPolicyNumber").value(hasItem(DEFAULT_INSURANCE_COMPANY_AND_POLICY_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].isInternshipAgreement").value(hasItem(DEFAULT_IS_INTERNSHIP_AGREEMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].annex").value(hasItem(DEFAULT_ANNEX.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(sameInstant(DEFAULT_MODIFY_TIME))));
    }
    
    @Test
    @Transactional
    public void getIntership() throws Exception {
        // Initialize the database
        intershipRepository.saveAndFlush(intership);

        // Get the intership
        restIntershipMockMvc.perform(get("/api/interships/{id}", intership.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(intership.getId().intValue()))
            .andExpect(jsonPath("$.batchName").value(DEFAULT_BATCH_NAME.toString()))
            .andExpect(jsonPath("$.practiceType").value(DEFAULT_PRACTICE_TYPE.toString()))
            .andExpect(jsonPath("$.course").value(DEFAULT_COURSE.toString()))
            .andExpect(jsonPath("$.beginIntership").value(sameInstant(DEFAULT_BEGIN_INTERSHIP)))
            .andExpect(jsonPath("$.endIntership").value(sameInstant(DEFAULT_END_INTERSHIP)))
            .andExpect(jsonPath("$.tutorScore").value(DEFAULT_TUTOR_SCORE))
            .andExpect(jsonPath("$.masterScore").value(DEFAULT_MASTER_SCORE))
            .andExpect(jsonPath("$.intershipScore").value(DEFAULT_INTERSHIP_SCORE))
            .andExpect(jsonPath("$.companyCreditCode").value(DEFAULT_COMPANY_CREDIT_CODE.toString()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.internship").value(DEFAULT_INTERNSHIP.toString()))
            .andExpect(jsonPath("$.companyContactName").value(DEFAULT_COMPANY_CONTACT_NAME.toString()))
            .andExpect(jsonPath("$.companyContactJob").value(DEFAULT_COMPANY_CONTACT_JOB.toString()))
            .andExpect(jsonPath("$.companyContactPhone").value(DEFAULT_COMPANY_CONTACT_PHONE.toString()))
            .andExpect(jsonPath("$.masterContactName").value(DEFAULT_MASTER_CONTACT_NAME.toString()))
            .andExpect(jsonPath("$.masterContactSkill").value(DEFAULT_MASTER_CONTACT_SKILL.toString()))
            .andExpect(jsonPath("$.masterContactPhone").value(DEFAULT_MASTER_CONTACT_PHONE.toString()))
            .andExpect(jsonPath("$.urgentContactName").value(DEFAULT_URGENT_CONTACT_NAME.toString()))
            .andExpect(jsonPath("$.urgentContactPhone").value(DEFAULT_URGENT_CONTACT_PHONE.toString()))
            .andExpect(jsonPath("$.urgentContactAddress").value(DEFAULT_URGENT_CONTACT_ADDRESS.toString()))
            .andExpect(jsonPath("$.accommodationType").value(DEFAULT_ACCOMMODATION_TYPE.toString()))
            .andExpect(jsonPath("$.accommodationAddress").value(DEFAULT_ACCOMMODATION_ADDRESS.toString()))
            .andExpect(jsonPath("$.companyAddress").value(DEFAULT_COMPANY_ADDRESS.toString()))
            .andExpect(jsonPath("$.companyDetailAddress").value(DEFAULT_COMPANY_DETAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.companyNature").value(DEFAULT_COMPANY_NATURE.toString()))
            .andExpect(jsonPath("$.scale").value(DEFAULT_SCALE.toString()))
            .andExpect(jsonPath("$.industry").value(DEFAULT_INDUSTRY.toString()))
            .andExpect(jsonPath("$.companyProfile").value(DEFAULT_COMPANY_PROFILE.toString()))
            .andExpect(jsonPath("$.isInsurance").value(DEFAULT_IS_INSURANCE.toString()))
            .andExpect(jsonPath("$.insuranceCompanyAndPolicyNumber").value(DEFAULT_INSURANCE_COMPANY_AND_POLICY_NUMBER.toString()))
            .andExpect(jsonPath("$.isInternshipAgreement").value(DEFAULT_IS_INTERNSHIP_AGREEMENT.booleanValue()))
            .andExpect(jsonPath("$.annex").value(DEFAULT_ANNEX.toString()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.modifyTime").value(sameInstant(DEFAULT_MODIFY_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingIntership() throws Exception {
        // Get the intership
        restIntershipMockMvc.perform(get("/api/interships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIntership() throws Exception {
        // Initialize the database
        intershipService.save(intership);

        int databaseSizeBeforeUpdate = intershipRepository.findAll().size();

        // Update the intership
        Intership updatedIntership = intershipRepository.findById(intership.getId()).get();
        // Disconnect from session so that the updates on updatedIntership are not directly saved in db
        em.detach(updatedIntership);
        updatedIntership
            .batchName(UPDATED_BATCH_NAME)
            .practiceType(UPDATED_PRACTICE_TYPE)
            .course(UPDATED_COURSE)
            .beginIntership(UPDATED_BEGIN_INTERSHIP)
            .endIntership(UPDATED_END_INTERSHIP)
            .tutorScore(UPDATED_TUTOR_SCORE)
            .masterScore(UPDATED_MASTER_SCORE)
            .intershipScore(UPDATED_INTERSHIP_SCORE)
            .companyCreditCode(UPDATED_COMPANY_CREDIT_CODE)
            .companyName(UPDATED_COMPANY_NAME)
            .internship(UPDATED_INTERNSHIP)
            .companyContactName(UPDATED_COMPANY_CONTACT_NAME)
            .companyContactJob(UPDATED_COMPANY_CONTACT_JOB)
            .companyContactPhone(UPDATED_COMPANY_CONTACT_PHONE)
            .masterContactName(UPDATED_MASTER_CONTACT_NAME)
            .masterContactSkill(UPDATED_MASTER_CONTACT_SKILL)
            .masterContactPhone(UPDATED_MASTER_CONTACT_PHONE)
            .urgentContactName(UPDATED_URGENT_CONTACT_NAME)
            .urgentContactPhone(UPDATED_URGENT_CONTACT_PHONE)
            .urgentContactAddress(UPDATED_URGENT_CONTACT_ADDRESS)
            .accommodationType(UPDATED_ACCOMMODATION_TYPE)
            .accommodationAddress(UPDATED_ACCOMMODATION_ADDRESS)
            .companyAddress(UPDATED_COMPANY_ADDRESS)
            .companyDetailAddress(UPDATED_COMPANY_DETAIL_ADDRESS)
            .companyNature(UPDATED_COMPANY_NATURE)
            .scale(UPDATED_SCALE)
            .industry(UPDATED_INDUSTRY)
            .companyProfile(UPDATED_COMPANY_PROFILE)
            .isInsurance(UPDATED_IS_INSURANCE)
            .insuranceCompanyAndPolicyNumber(UPDATED_INSURANCE_COMPANY_AND_POLICY_NUMBER)
            .isInternshipAgreement(UPDATED_IS_INTERNSHIP_AGREEMENT)
            .annex(UPDATED_ANNEX)
            .createTime(UPDATED_CREATE_TIME)
            .modifyTime(UPDATED_MODIFY_TIME);

        restIntershipMockMvc.perform(put("/api/interships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIntership)))
            .andExpect(status().isOk());

        // Validate the Intership in the database
        List<Intership> intershipList = intershipRepository.findAll();
        assertThat(intershipList).hasSize(databaseSizeBeforeUpdate);
        Intership testIntership = intershipList.get(intershipList.size() - 1);
        assertThat(testIntership.getBatchName()).isEqualTo(UPDATED_BATCH_NAME);
        assertThat(testIntership.getPracticeType()).isEqualTo(UPDATED_PRACTICE_TYPE);
        assertThat(testIntership.getCourse()).isEqualTo(UPDATED_COURSE);
        assertThat(testIntership.getBeginIntership()).isEqualTo(UPDATED_BEGIN_INTERSHIP);
        assertThat(testIntership.getEndIntership()).isEqualTo(UPDATED_END_INTERSHIP);
        assertThat(testIntership.getTutorScore()).isEqualTo(UPDATED_TUTOR_SCORE);
        assertThat(testIntership.getMasterScore()).isEqualTo(UPDATED_MASTER_SCORE);
        assertThat(testIntership.getIntershipScore()).isEqualTo(UPDATED_INTERSHIP_SCORE);
        assertThat(testIntership.getCompanyCreditCode()).isEqualTo(UPDATED_COMPANY_CREDIT_CODE);
        assertThat(testIntership.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testIntership.getInternship()).isEqualTo(UPDATED_INTERNSHIP);
        assertThat(testIntership.getCompanyContactName()).isEqualTo(UPDATED_COMPANY_CONTACT_NAME);
        assertThat(testIntership.getCompanyContactJob()).isEqualTo(UPDATED_COMPANY_CONTACT_JOB);
        assertThat(testIntership.getCompanyContactPhone()).isEqualTo(UPDATED_COMPANY_CONTACT_PHONE);
        assertThat(testIntership.getMasterContactName()).isEqualTo(UPDATED_MASTER_CONTACT_NAME);
        assertThat(testIntership.getMasterContactSkill()).isEqualTo(UPDATED_MASTER_CONTACT_SKILL);
        assertThat(testIntership.getMasterContactPhone()).isEqualTo(UPDATED_MASTER_CONTACT_PHONE);
        assertThat(testIntership.getUrgentContactName()).isEqualTo(UPDATED_URGENT_CONTACT_NAME);
        assertThat(testIntership.getUrgentContactPhone()).isEqualTo(UPDATED_URGENT_CONTACT_PHONE);
        assertThat(testIntership.getUrgentContactAddress()).isEqualTo(UPDATED_URGENT_CONTACT_ADDRESS);
        assertThat(testIntership.getAccommodationType()).isEqualTo(UPDATED_ACCOMMODATION_TYPE);
        assertThat(testIntership.getAccommodationAddress()).isEqualTo(UPDATED_ACCOMMODATION_ADDRESS);
        assertThat(testIntership.getCompanyAddress()).isEqualTo(UPDATED_COMPANY_ADDRESS);
        assertThat(testIntership.getCompanyDetailAddress()).isEqualTo(UPDATED_COMPANY_DETAIL_ADDRESS);
        assertThat(testIntership.getCompanyNature()).isEqualTo(UPDATED_COMPANY_NATURE);
        assertThat(testIntership.getScale()).isEqualTo(UPDATED_SCALE);
        assertThat(testIntership.getIndustry()).isEqualTo(UPDATED_INDUSTRY);
        assertThat(testIntership.getCompanyProfile()).isEqualTo(UPDATED_COMPANY_PROFILE);
        assertThat(testIntership.getIsInsurance()).isEqualTo(UPDATED_IS_INSURANCE);
        assertThat(testIntership.getInsuranceCompanyAndPolicyNumber()).isEqualTo(UPDATED_INSURANCE_COMPANY_AND_POLICY_NUMBER);
        assertThat(testIntership.isIsInternshipAgreement()).isEqualTo(UPDATED_IS_INTERNSHIP_AGREEMENT);
        assertThat(testIntership.getAnnex()).isEqualTo(UPDATED_ANNEX);
        assertThat(testIntership.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testIntership.getModifyTime()).isEqualTo(UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingIntership() throws Exception {
        int databaseSizeBeforeUpdate = intershipRepository.findAll().size();

        // Create the Intership

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntershipMockMvc.perform(put("/api/interships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intership)))
            .andExpect(status().isBadRequest());

        // Validate the Intership in the database
        List<Intership> intershipList = intershipRepository.findAll();
        assertThat(intershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIntership() throws Exception {
        // Initialize the database
        intershipService.save(intership);

        int databaseSizeBeforeDelete = intershipRepository.findAll().size();

        // Delete the intership
        restIntershipMockMvc.perform(delete("/api/interships/{id}", intership.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Intership> intershipList = intershipRepository.findAll();
        assertThat(intershipList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Intership.class);
        Intership intership1 = new Intership();
        intership1.setId(1L);
        Intership intership2 = new Intership();
        intership2.setId(intership1.getId());
        assertThat(intership1).isEqualTo(intership2);
        intership2.setId(2L);
        assertThat(intership1).isNotEqualTo(intership2);
        intership1.setId(null);
        assertThat(intership1).isNotEqualTo(intership2);
    }
}
