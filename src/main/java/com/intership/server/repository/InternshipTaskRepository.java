package com.intership.server.repository;

import com.intership.server.domain.InternshipTask;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sun.rmi.runtime.Log;

import java.util.List;


/**
 * Spring Data  repository for the InternshipTask entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InternshipTaskRepository extends JpaRepository<InternshipTask, Long> {

    /**
     * 根据实习表 ID 查询实习任务
     * @param interId
     * @return
     */
   List<InternshipTask> findByInterIdId(Long interId);

}
