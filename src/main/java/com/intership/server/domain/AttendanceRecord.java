package com.intership.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 事件管理——考勤记录
 */
@ApiModel(description = "事件管理——考勤记录")
@Entity
@Table(name = "attendance_record")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AttendanceRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "internship_batch")
    private String internshipBatch;

    @Column(name = "punch_time")
    private ZonedDateTime punchTime;

    @Column(name = "punch_location")
    private String punchLocation;

    @Column(name = "attendance_status")
    private String attendanceStatus;

    @Column(name = "created_time")
    private ZonedDateTime createdTime;

    @Column(name = "modify_time")
    private ZonedDateTime modifyTime;

    @ManyToOne
    @JsonIgnoreProperties("attendanceRecords")
    private Student stuId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInternshipBatch() {
        return internshipBatch;
    }

    public AttendanceRecord internshipBatch(String internshipBatch) {
        this.internshipBatch = internshipBatch;
        return this;
    }

    public void setInternshipBatch(String internshipBatch) {
        this.internshipBatch = internshipBatch;
    }

    public ZonedDateTime getPunchTime() {
        return punchTime;
    }

    public AttendanceRecord punchTime(ZonedDateTime punchTime) {
        this.punchTime = punchTime;
        return this;
    }

    public void setPunchTime(ZonedDateTime punchTime) {
        this.punchTime = punchTime;
    }

    public String getPunchLocation() {
        return punchLocation;
    }

    public AttendanceRecord punchLocation(String punchLocation) {
        this.punchLocation = punchLocation;
        return this;
    }

    public void setPunchLocation(String punchLocation) {
        this.punchLocation = punchLocation;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public AttendanceRecord attendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
        return this;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public AttendanceRecord createdTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public ZonedDateTime getModifyTime() {
        return modifyTime;
    }

    public AttendanceRecord modifyTime(ZonedDateTime modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public void setModifyTime(ZonedDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Student getStuId() {
        return stuId;
    }

    public AttendanceRecord stuId(Student student) {
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
        if (!(o instanceof AttendanceRecord)) {
            return false;
        }
        return id != null && id.equals(((AttendanceRecord) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AttendanceRecord{" +
            "id=" + getId() +
            ", internshipBatch='" + getInternshipBatch() + "'" +
            ", punchTime='" + getPunchTime() + "'" +
            ", punchLocation='" + getPunchLocation() + "'" +
            ", attendanceStatus='" + getAttendanceStatus() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", modifyTime='" + getModifyTime() + "'" +
            "}";
    }
}
