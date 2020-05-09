package com.intership.server.web.rest;

import com.intership.server.IntershipSystemApp;
import com.intership.server.domain.AlarmEvent;
import com.intership.server.repository.AlarmEventRepository;
import com.intership.server.service.AlarmEventService;
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
 * Integration tests for the {@Link AlarmEventResource} REST controller.
 */
@SpringBootTest(classes = IntershipSystemApp.class)
public class AlarmEventResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AlarmEventRepository alarmEventRepository;

    @Autowired
    private AlarmEventService alarmEventService;

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

    private MockMvc restAlarmEventMockMvc;

    private AlarmEvent alarmEvent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlarmEventResource alarmEventResource = new AlarmEventResource(alarmEventService);
        this.restAlarmEventMockMvc = MockMvcBuilders.standaloneSetup(alarmEventResource)
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
    public static AlarmEvent createEntity(EntityManager em) {
        AlarmEvent alarmEvent = new AlarmEvent()
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .remarks(DEFAULT_REMARKS)
            .createdTime(DEFAULT_CREATED_TIME);
        return alarmEvent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlarmEvent createUpdatedEntity(EntityManager em) {
        AlarmEvent alarmEvent = new AlarmEvent()
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .remarks(UPDATED_REMARKS)
            .createdTime(UPDATED_CREATED_TIME);
        return alarmEvent;
    }

    @BeforeEach
    public void initTest() {
        alarmEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlarmEvent() throws Exception {
        int databaseSizeBeforeCreate = alarmEventRepository.findAll().size();

        // Create the AlarmEvent
        restAlarmEventMockMvc.perform(post("/api/alarm-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alarmEvent)))
            .andExpect(status().isCreated());

        // Validate the AlarmEvent in the database
        List<AlarmEvent> alarmEventList = alarmEventRepository.findAll();
        assertThat(alarmEventList).hasSize(databaseSizeBeforeCreate + 1);
        AlarmEvent testAlarmEvent = alarmEventList.get(alarmEventList.size() - 1);
        assertThat(testAlarmEvent.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testAlarmEvent.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testAlarmEvent.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testAlarmEvent.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
    }

    @Test
    @Transactional
    public void createAlarmEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alarmEventRepository.findAll().size();

        // Create the AlarmEvent with an existing ID
        alarmEvent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlarmEventMockMvc.perform(post("/api/alarm-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alarmEvent)))
            .andExpect(status().isBadRequest());

        // Validate the AlarmEvent in the database
        List<AlarmEvent> alarmEventList = alarmEventRepository.findAll();
        assertThat(alarmEventList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAlarmEvents() throws Exception {
        // Initialize the database
        alarmEventRepository.saveAndFlush(alarmEvent);

        // Get all the alarmEventList
        restAlarmEventMockMvc.perform(get("/api/alarm-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alarmEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(sameInstant(DEFAULT_CREATED_TIME))));
    }
    
    @Test
    @Transactional
    public void getAlarmEvent() throws Exception {
        // Initialize the database
        alarmEventRepository.saveAndFlush(alarmEvent);

        // Get the alarmEvent
        restAlarmEventMockMvc.perform(get("/api/alarm-events/{id}", alarmEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alarmEvent.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.createdTime").value(sameInstant(DEFAULT_CREATED_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingAlarmEvent() throws Exception {
        // Get the alarmEvent
        restAlarmEventMockMvc.perform(get("/api/alarm-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlarmEvent() throws Exception {
        // Initialize the database
        alarmEventService.save(alarmEvent);

        int databaseSizeBeforeUpdate = alarmEventRepository.findAll().size();

        // Update the alarmEvent
        AlarmEvent updatedAlarmEvent = alarmEventRepository.findById(alarmEvent.getId()).get();
        // Disconnect from session so that the updates on updatedAlarmEvent are not directly saved in db
        em.detach(updatedAlarmEvent);
        updatedAlarmEvent
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .remarks(UPDATED_REMARKS)
            .createdTime(UPDATED_CREATED_TIME);

        restAlarmEventMockMvc.perform(put("/api/alarm-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAlarmEvent)))
            .andExpect(status().isOk());

        // Validate the AlarmEvent in the database
        List<AlarmEvent> alarmEventList = alarmEventRepository.findAll();
        assertThat(alarmEventList).hasSize(databaseSizeBeforeUpdate);
        AlarmEvent testAlarmEvent = alarmEventList.get(alarmEventList.size() - 1);
        assertThat(testAlarmEvent.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAlarmEvent.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testAlarmEvent.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testAlarmEvent.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingAlarmEvent() throws Exception {
        int databaseSizeBeforeUpdate = alarmEventRepository.findAll().size();

        // Create the AlarmEvent

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlarmEventMockMvc.perform(put("/api/alarm-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alarmEvent)))
            .andExpect(status().isBadRequest());

        // Validate the AlarmEvent in the database
        List<AlarmEvent> alarmEventList = alarmEventRepository.findAll();
        assertThat(alarmEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlarmEvent() throws Exception {
        // Initialize the database
        alarmEventService.save(alarmEvent);

        int databaseSizeBeforeDelete = alarmEventRepository.findAll().size();

        // Delete the alarmEvent
        restAlarmEventMockMvc.perform(delete("/api/alarm-events/{id}", alarmEvent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AlarmEvent> alarmEventList = alarmEventRepository.findAll();
        assertThat(alarmEventList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlarmEvent.class);
        AlarmEvent alarmEvent1 = new AlarmEvent();
        alarmEvent1.setId(1L);
        AlarmEvent alarmEvent2 = new AlarmEvent();
        alarmEvent2.setId(alarmEvent1.getId());
        assertThat(alarmEvent1).isEqualTo(alarmEvent2);
        alarmEvent2.setId(2L);
        assertThat(alarmEvent1).isNotEqualTo(alarmEvent2);
        alarmEvent1.setId(null);
        assertThat(alarmEvent1).isNotEqualTo(alarmEvent2);
    }
}
