package com.intership.server.service.impl;

import com.intership.server.service.SharingCenterService;
import com.intership.server.domain.SharingCenter;
import com.intership.server.repository.SharingCenterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.SharedSecrets;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Implementation for managing {@link SharingCenter}.
 */
@Service
@Transactional
public class SharingCenterServiceImpl implements SharingCenterService {

    private final Logger log = LoggerFactory.getLogger(SharingCenterServiceImpl.class);

    private final SharingCenterRepository sharingCenterRepository;

    private final JdbcTemplate jdbcTemplate;

    public SharingCenterServiceImpl(SharingCenterRepository sharingCenterRepository, JdbcTemplate jdbcTemplate) {
        this.sharingCenterRepository = sharingCenterRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Save a sharingCenter.
     *
     * @param sharingCenter the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SharingCenter save(SharingCenter sharingCenter) {
        log.debug("Request to save SharingCenter : {}", sharingCenter);
        return sharingCenterRepository.save(sharingCenter);
    }

    /**
     * Get all the sharingCenters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SharingCenter> findAll(Pageable pageable) {
        log.debug("Request to get all SharingCenters");
        return sharingCenterRepository.findAll(pageable);
    }

    /**
     * Get one sharingCenter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SharingCenter> findOne(Long id) {
        log.debug("Request to get SharingCenter : {}", id);
        return sharingCenterRepository.findById(id);
    }

    @Override
    public Page<SharingCenter> findByUploadAndDep(Pageable pageable, String upload, String dep) {
//        String sql = "SELECT * FROM `sharing_center `";
//        }
        Page<SharingCenter> page = sharingCenterRepository.findByUploadedByOrDepartment(upload, dep, pageable);
//        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return page;
    }

    /**
     * Delete the sharingCenter by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SharingCenter : {}", id);
        sharingCenterRepository.deleteById(id);
    }
}
