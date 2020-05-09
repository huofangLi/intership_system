package com.intership.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 查看考勤管理：签到记录
 */
@ApiModel(description = "查看考勤管理：签到记录")
@Entity
@Table(name = "attendance_management")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AttendanceManagement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sign_in")
    private ZonedDateTime signIn;

    /**
     * 签到位置
     */
    @ApiModelProperty(value = "签到位置")
    @Column(name = "position")
    private String position;

    /**
     * 签到坐标
     */
    @ApiModelProperty(value = "签到坐标")
    @Column(name = "coordinate")
    private String coordinate;

    @Column(name = "company")
    private String company;

    @Column(name = "created_time")
    private ZonedDateTime createdTime;

    @ManyToOne
    @JsonIgnoreProperties("attendanceManagements")
    private Student stuId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getSignIn() {
        return signIn;
    }

    public AttendanceManagement signIn(ZonedDateTime signIn) {
        this.signIn = signIn;
        return this;
    }

    public void setSignIn(ZonedDateTime signIn) {
        this.signIn = signIn;
    }

    public String getPosition() {
        return position;
    }

    public AttendanceManagement position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public AttendanceManagement coordinate(String coordinate) {
        this.coordinate = coordinate;
        return this;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getCompany() {
        return company;
    }

    public AttendanceManagement company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public AttendanceManagement createdTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Student getStuId() {
        return stuId;
    }

    public AttendanceManagement stuId(Student student) {
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
        if (!(o instanceof AttendanceManagement)) {
            return false;
        }
        return id != null && id.equals(((AttendanceManagement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AttendanceManagement{" +
            "id=" + getId() +
            ", signIn='" + getSignIn() + "'" +
            ", position='" + getPosition() + "'" +
            ", coordinate='" + getCoordinate() + "'" +
            ", company='" + getCompany() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            "}";
    }
}
