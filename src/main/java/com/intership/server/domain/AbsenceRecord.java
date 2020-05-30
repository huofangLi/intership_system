package com.intership.server.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import springfox.documentation.spring.web.json.Json;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 缺勤记录
 */
@ApiModel(description = "缺勤记录")
@Entity
@Table(name = "absence_record")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AbsenceRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "absence_start_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime absenceStartTime;

    @Column(name = "absence_end_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime absenceEndTime;

    @Column(name = "absence_days")
    private Double absenceDays;

    @Column(name = "company")
    private String company;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime createdTime;

    @ManyToOne
    @JsonIgnoreProperties("absenceRecords")
    private Student stuId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getAbsenceStartTime() {
        return absenceStartTime;
    }

    public AbsenceRecord absenceStartTime(ZonedDateTime absenceStartTime) {
        this.absenceStartTime = absenceStartTime;
        return this;
    }

    public void setAbsenceStartTime(ZonedDateTime absenceStartTime) {
        this.absenceStartTime = absenceStartTime;
    }

    public ZonedDateTime getAbsenceEndTime() {
        return absenceEndTime;
    }

    public AbsenceRecord absenceEndTime(ZonedDateTime absenceEndTime) {
        this.absenceEndTime = absenceEndTime;
        return this;
    }

    public void setAbsenceEndTime(ZonedDateTime absenceEndTime) {
        this.absenceEndTime = absenceEndTime;
    }

    public Double getAbsenceDays() {
        return absenceDays;
    }

    public AbsenceRecord absenceDays(Double absenceDays) {
        this.absenceDays = absenceDays;
        return this;
    }

    public void setAbsenceDays(Double absenceDays) {
        this.absenceDays = absenceDays;
    }

    public String getCompany() {
        return company;
    }

    public AbsenceRecord company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRemarks() {
        return remarks;
    }

    public AbsenceRecord remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public AbsenceRecord createdTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Student getStuId() {
        return stuId;
    }

    public AbsenceRecord stuId(Student student) {
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
        if (!(o instanceof AbsenceRecord)) {
            return false;
        }
        return id != null && id.equals(((AbsenceRecord) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AbsenceRecord{" +
            "id=" + getId() +
            ", absenceStartTime='" + getAbsenceStartTime() + "'" +
            ", absenceEndTime='" + getAbsenceEndTime() + "'" +
            ", absenceDays=" + getAbsenceDays() +
            ", company='" + getCompany() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            "}";
    }
}
