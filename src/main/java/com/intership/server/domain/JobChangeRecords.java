package com.intership.server.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 岗位变更记录
 */
@ApiModel(description = "岗位变更记录")
@Entity
@Table(name = "job_change_records")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JobChangeRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unit_change")
    private String unitChange;

    @Column(name = "position_change")
    private String positionChange;

    @Column(name = "create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime createTime;

    @ManyToOne
    @JsonIgnoreProperties("jobChangeRecords")
    private Intership interId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnitChange() {
        return unitChange;
    }

    public JobChangeRecords unitChange(String unitChange) {
        this.unitChange = unitChange;
        return this;
    }

    public void setUnitChange(String unitChange) {
        this.unitChange = unitChange;
    }

    public String getPositionChange() {
        return positionChange;
    }

    public JobChangeRecords positionChange(String positionChange) {
        this.positionChange = positionChange;
        return this;
    }

    public void setPositionChange(String positionChange) {
        this.positionChange = positionChange;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public JobChangeRecords createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public Intership getInterId() {
        return interId;
    }

    public JobChangeRecords interId(Intership intership) {
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
        if (!(o instanceof JobChangeRecords)) {
            return false;
        }
        return id != null && id.equals(((JobChangeRecords) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "JobChangeRecords{" +
            "id=" + getId() +
            ", unitChange='" + getUnitChange() + "'" +
            ", positionChange='" + getPositionChange() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
