package com.intership.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 请假记录
 */
@ApiModel(description = "请假记录")
@Entity
@Table(name = "jhi_leave")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Leave implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "submit_time")
    private ZonedDateTime submitTime;

    @Column(name = "leave_time")
    private ZonedDateTime leaveTime;

    @Column(name = "leave_days")
    private Double leaveDays;

    @Column(name = "company")
    private String company;

    @Column(name = "leave_reason")
    private String leaveReason;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "created_time")
    private ZonedDateTime createdTime;

    @ManyToOne
    @JsonIgnoreProperties("leaves")
    private Student stuId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getSubmitTime() {
        return submitTime;
    }

    public Leave submitTime(ZonedDateTime submitTime) {
        this.submitTime = submitTime;
        return this;
    }

    public void setSubmitTime(ZonedDateTime submitTime) {
        this.submitTime = submitTime;
    }

    public ZonedDateTime getLeaveTime() {
        return leaveTime;
    }

    public Leave leaveTime(ZonedDateTime leaveTime) {
        this.leaveTime = leaveTime;
        return this;
    }

    public void setLeaveTime(ZonedDateTime leaveTime) {
        this.leaveTime = leaveTime;
    }

    public Double getLeaveDays() {
        return leaveDays;
    }

    public Leave leaveDays(Double leaveDays) {
        this.leaveDays = leaveDays;
        return this;
    }

    public void setLeaveDays(Double leaveDays) {
        this.leaveDays = leaveDays;
    }

    public String getCompany() {
        return company;
    }

    public Leave company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public Leave leaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
        return this;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public Boolean isStatus() {
        return status;
    }

    public Leave status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public Leave createdTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Student getStuId() {
        return stuId;
    }

    public Leave stuId(Student student) {
        this.stuId = student;
        return this;
    }

    public void setStuId(Student student) {
        this.stuId = student;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Leave)) {
            return false;
        }
        return id != null && id.equals(((Leave) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Leave{" +
            "id=" + getId() +
            ", submitTime='" + getSubmitTime() + "'" +
            ", leaveTime='" + getLeaveTime() + "'" +
            ", leaveDays=" + getLeaveDays() +
            ", company='" + getCompany() + "'" +
            ", leaveReason='" + getLeaveReason() + "'" +
            ", status='" + isStatus() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            "}";
    }
}
