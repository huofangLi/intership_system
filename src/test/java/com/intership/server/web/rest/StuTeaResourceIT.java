package com.intership.server.web.rest;

import com.intership.server.IntershipSystemApp;
import com.intership.server.domain.StuTea;
import com.intership.server.repository.StuTeaRepository;
import com.intership.server.service.StuTeaService;
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
 * Integration tests for the {@Link StuTeaResource} REST controller.
 */
@SpringBootTest(classes = IntershipSystemApp.class)
public class StuTeaResourceIT {

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private StuTeaRepository stuTeaRepository;

    @Autowired
    private StuTeaService stuTeaService;

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

    private MockMvc restStuTeaMockMvc;

    private StuTea stuTea;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StuTeaResource stuTeaResource = new StuTeaResource(stuTeaService);
        this.restStuTeaMockMvc = MockMvcBuilders.standaloneSetup(stuTeaResource)
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
    public static StuTea createEntity(EntityManager em) {
        StuTea stuTea = new StuTea()
            .createTime(DEFAULT_CREATE_TIME);
        return stuTea;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StuTea createUpdatedEntity(EntityManager em) {
        StuTea stuTea = new StuTea()
            .createTime(UPDATED_CREATE_TIME);
        return stuTea;
    }

    @BeforeEach
    public void initTest() {
        stuTea = createEntity(em);
    }

    @Test
    @Transactional
    public void createStuTea() throws Exception {
        int databaseSizeBeforeCreate = stuTeaRepository.findAll().size();

        // Create the StuTea
        restStuTeaMockMvc.perform(post("/api/stu-teas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stuTea)))
            .andExpect(status().isCreated());

        // Validate the StuTea in the database
        List<StuTea> stuTeaList = stuTeaRepository.findAll();
        assertThat(stuTeaList).hasSize(databaseSizeBeforeCreate + 1);
        StuTea testStuTea = stuTeaList.get(stuTeaList.size() - 1);
        assertThat(testStuTea.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    public void createStuTeaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stuTeaRepository.findAll().size();

        // Create the StuTea with an existing ID
        stuTea.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStuTeaMockMvc.perform(post("/api/stu-teas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stuTea)))
            .andExpect(status().isBadRequest());

        // Validate the StuTea in the database
        List<StuTea> stuTeaList = stuTeaRepository.findAll();
        assertThat(stuTeaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStuTeas() throws Exception {
        // Initialize the database
        stuTeaRepository.saveAndFlush(stuTea);

        // Get all the stuTeaList
        restStuTeaMockMvc.perform(get("/api/stu-teas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stuTea.getId().intValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))));
    }
    
    @Test
    @Transactional
    public void getStuTea() throws Exception {
        // Initialize the database
        stuTeaRepository.saveAndFlush(stuTea);

        // Get the stuTea
        restStuTeaMockMvc.perform(get("/api/stu-teas/{id}", stuTea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stuTea.getId().intValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingStuTea() throws Exception {
        // Get the stuTea
        restStuTeaMockMvc.perform(get("/api/stu-teas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStuTea() throws Exception {
        // Initialize the database
        stuTeaService.save(stuTea);

        int databaseSizeBeforeUpdate = stuTeaRepository.findAll().size();

        // Update the stuTea
        StuTea updatedStuTea = stuTeaRepository.findById(stuTea.getId()).get();
        // Disconnect from session so that the updates on updatedStuTea are not directly saved in db
        em.detach(updatedStuTea);
        updatedStuTea
            .createTime(UPDATED_CREATE_TIME);

        restStuTeaMockMvc.perform(put("/api/stu-teas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStuTea)))
            .andExpect(status().isOk());

        // Validate the StuTea in the database
        List<StuTea> stuTeaList = stuTeaRepository.findAll();
        assertThat(stuTeaList).hasSize(databaseSizeBeforeUpdate);
        StuTea testStuTea = stuTeaList.get(stuTeaList.size() - 1);
        assertThat(testStuTea.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingStuTea() throws Exception {
        int databaseSizeBeforeUpdate = stuTeaRepository.findAll().size();

        // Create the StuTea

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStuTeaMockMvc.perform(put("/api/stu-teas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stuTea)))
            .andExpect(status().isBadRequest());

        // Validate the StuTea in the database
        List<StuTea> stuTeaList = stuTeaRepository.findAll();
        assertThat(stuTeaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStuTea() throws Exception {
        // Initialize the database
        stuTeaService.save(stuTea);

        int databaseSizeBeforeDelete = stuTeaRepository.findAll().size();

        // Delete the stuTea
        restStuTeaMockMvc.perform(delete("/api/stu-teas/{id}", stuTea.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StuTea> stuTeaList = stuTeaRepository.findAll();
        assertThat(stuTeaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StuTea.class);
        StuTea stuTea1 = new StuTea();
        stuTea1.setId(1L);
        StuTea stuTea2 = new StuTea();
        stuTea2.setId(stuTea1.getId());
        assertThat(stuTea1).isEqualTo(stuTea2);
        stuTea2.setId(2L);
        assertThat(stuTea1).isNotEqualTo(stuTea2);
        stuTea1.setId(null);
        assertThat(stuTea1).isNotEqualTo(stuTea2);
    }
}
