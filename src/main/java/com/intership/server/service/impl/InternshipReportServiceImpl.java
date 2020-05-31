package com.intership.server.service.impl;

import com.intership.server.service.InternshipReportService;
import com.intership.server.domain.InternshipReport;
import com.intership.server.repository.InternshipReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link InternshipReport}.
 */
@Service
@Transactional
public class InternshipReportServiceImpl implements InternshipReportService {

    private final Logger log = LoggerFactory.getLogger(InternshipReportServiceImpl.class);

    private final InternshipReportRepository internshipReportRepository;

    public InternshipReportServiceImpl(InternshipReportRepository internshipReportRepository) {
        this.internshipReportRepository = internshipReportRepository;
    }

    /**
     * Save a internshipReport.
     *
     * @param internshipReport the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InternshipReport save(InternshipReport internshipReport) {
        log.debug("Request to save InternshipReport : {}", internshipReport);
        return internshipReportRepository.save(internshipReport);
    }

    /**
     * Get all the internshipReports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InternshipReport> findAll(Pageable pageable) {
        log.debug("Request to get all InternshipReports");
        return internshipReportRepository.findAll(pageable);
    }


    /**
     * Get one internshipReport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InternshipReport> findOne(Long id) {
        log.debug("Request to get InternshipReport : {}", id);
        return internshipReportRepository.findById(id);
    }

    /**
     * Delete the internshipReport by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InternshipReport : {}", id);
        internshipReportRepository.deleteById(id);
    }

    /**
     * 根据实习表 ID 查询实习报告
     * @param interId
     * @return
     */
    @Override
    public List<InternshipReport> findByInterId(Long interId) {
        return internshipReportRepository.findByInterIdId(interId);
    }
}
