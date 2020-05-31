package com.intership.server.service.impl;

import com.intership.server.service.AbsenceRecordService;
import com.intership.server.domain.AbsenceRecord;
import com.intership.server.repository.AbsenceRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link AbsenceRecord}.
 */
@Service
@Transactional
public class AbsenceRecordServiceImpl implements AbsenceRecordService {

    private final Logger log = LoggerFactory.getLogger(AbsenceRecordServiceImpl.class);

    private final AbsenceRecordRepository absenceRecordRepository;

    public AbsenceRecordServiceImpl(AbsenceRecordRepository absenceRecordRepository) {
        this.absenceRecordRepository = absenceRecordRepository;
    }

    /**
     * Save a absenceRecord.
     *
     * @param absenceRecord the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AbsenceRecord save(AbsenceRecord absenceRecord) {
        log.debug("Request to save AbsenceRecord : {}", absenceRecord);
        return absenceRecordRepository.save(absenceRecord);
    }

    /**
     * Get all the absenceRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AbsenceRecord> findAll(Pageable pageable) {
        log.debug("Request to get all AbsenceRecords");
        return absenceRecordRepository.findAll(pageable);
    }


    /**
     * Get one absenceRecord by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AbsenceRecord> findOne(Long id) {
        log.debug("Request to get AbsenceRecord : {}", id);
        return absenceRecordRepository.findById(id);
    }

    /**
     * Delete the absenceRecord by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AbsenceRecord : {}", id);
        absenceRecordRepository.deleteById(id);
    }

    /**
     * 根据学生 ID 查询 实习缺勤记录
     * @param stuId
     * @return
     */
    @Override
    public List<AbsenceRecord> findByStuId(Long stuId) {
        return absenceRecordRepository.findByStuIdId(stuId);
    }

}
