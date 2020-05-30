package com.intership.server.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Teacher.
 */
@Entity
@Table(name = "teacher")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tea_id")
    private Long teaId;

    @Column(name = "tea_name")
    private String teaName;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "tea_department")
    private String teaDepartment;

    @Column(name = "skin")
    private String skin;

    @Column(name = "created_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime createdTime;

    @Column(name = "modify_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime modifyTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeaId() {
        return teaId;
    }

    public Teacher teaId(Long teaId) {
        this.teaId = teaId;
        return this;
    }

    public void setTeaId(Long teaId) {
        this.teaId = teaId;
    }

    public String getTeaName() {
        return teaName;
    }

    public Teacher teaName(String teaName) {
        this.teaName = teaName;
        return this;
    }

    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }

    public Boolean isGender() {
        return gender;
    }

    public Teacher gender(Boolean gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getTeaDepartment() {
        return teaDepartment;
    }

    public Teacher teaDepartment(String teaDepartment) {
        this.teaDepartment = teaDepartment;
        return this;
    }

    public void setTeaDepartment(String teaDepartment) {
        this.teaDepartment = teaDepartment;
    }

    public String getSkin() {
        return skin;
    }

    public Teacher skin(String skin) {
        this.skin = skin;
        return this;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public Teacher createdTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public ZonedDateTime getModifyTime() {
        return modifyTime;
    }

    public Teacher modifyTime(ZonedDateTime modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public void setModifyTime(ZonedDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Teacher)) {
            return false;
        }
        return id != null && id.equals(((Teacher) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Teacher{" +
            "id=" + getId() +
            ", teaId=" + getTeaId() +
            ", teaName='" + getTeaName() + "'" +
            ", gender='" + isGender() + "'" +
            ", teaDepartment='" + getTeaDepartment() + "'" +
            ", skin='" + getSkin() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", modifyTime='" + getModifyTime() + "'" +
            "}";
    }
}
