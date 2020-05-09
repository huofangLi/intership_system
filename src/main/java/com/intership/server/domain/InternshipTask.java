package com.intership.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 实习任务
 */
@ApiModel(description = "实习任务")
@Entity
@Table(name = "internship_task")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InternshipTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_title")
    private String taskTitle;

    @Column(name = "founder")
    private String founder;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @Column(name = "importance")
    private String importance;

    @Column(name = "complexity")
    private String complexity;

    @Column(name = "task_annex")
    private String taskAnnex;

    @Column(name = "star")
    private String star;

    @Column(name = "status")
    private String status;

    @Column(name = "operating")
    private String operating;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @ManyToOne
    @JsonIgnoreProperties("internshipTasks")
    private Intership interId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public InternshipTask taskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
        return this;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getFounder() {
        return founder;
    }

    public InternshipTask founder(String founder) {
        this.founder = founder;
        return this;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public InternshipTask startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public InternshipTask endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public String getImportance() {
        return importance;
    }

    public InternshipTask importance(String importance) {
        this.importance = importance;
        return this;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getComplexity() {
        return complexity;
    }

    public InternshipTask complexity(String complexity) {
        this.complexity = complexity;
        return this;
    }

    public void setComplexity(String complexity) {
        this.complexity = complexity;
    }

    public String getTaskAnnex() {
        return taskAnnex;
    }

    public InternshipTask taskAnnex(String taskAnnex) {
        this.taskAnnex = taskAnnex;
        return this;
    }

    public void setTaskAnnex(String taskAnnex) {
        this.taskAnnex = taskAnnex;
    }

    public String getStar() {
        return star;
    }

    public InternshipTask star(String star) {
        this.star = star;
        return this;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getStatus() {
        return status;
    }

    public InternshipTask status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperating() {
        return operating;
    }

    public InternshipTask operating(String operating) {
        this.operating = operating;
        return this;
    }

    public void setOperating(String operating) {
        this.operating = operating;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public InternshipTask createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public Intership getInterId() {
        return interId;
    }

    public InternshipTask interId(Intership intership) {
        this.interId = intership;
        return this;
    }

    public void setInterId(Intership intership) {
        this.interId = intership;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InternshipTask)) {
            return false;
        }
        return id != null && id.equals(((InternshipTask) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InternshipTask{" +
            "id=" + getId() +
            ", taskTitle='" + getTaskTitle() + "'" +
            ", founder='" + getFounder() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", importance='" + getImportance() + "'" +
            ", complexity='" + getComplexity() + "'" +
            ", taskAnnex='" + getTaskAnnex() + "'" +
            ", star='" + getStar() + "'" +
            ", status='" + getStatus() + "'" +
            ", operating='" + getOperating() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
