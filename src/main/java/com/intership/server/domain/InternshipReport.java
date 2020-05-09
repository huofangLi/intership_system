package com.intership.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 实习报告
 */
@ApiModel(description = "实习报告")
@Entity
@Table(name = "internship_report")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InternshipReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_type")
    private String reportType;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "intership_stage")
    private String intershipStage;

    @Column(name = "annex")
    private String annex;

    @Column(name = "report_content")
    private String reportContent;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @Column(name = "star")
    private String star;

    @Column(name = "status")
    private String status;

    @Column(name = "operating")
    private String operating;

    @ManyToOne
    @JsonIgnoreProperties("internshipReports")
    private Intership interId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportType() {
        return reportType;
    }

    public InternshipReport reportType(String reportType) {
        this.reportType = reportType;
        return this;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getProjectName() {
        return projectName;
    }

    public InternshipReport projectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getIntershipStage() {
        return intershipStage;
    }

    public InternshipReport intershipStage(String intershipStage) {
        this.intershipStage = intershipStage;
        return this;
    }

    public void setIntershipStage(String intershipStage) {
        this.intershipStage = intershipStage;
    }

    public String getAnnex() {
        return annex;
    }

    public InternshipReport annex(String annex) {
        this.annex = annex;
        return this;
    }

    public void setAnnex(String annex) {
        this.annex = annex;
    }

    public String getReportContent() {
        return reportContent;
    }

    public InternshipReport reportContent(String reportContent) {
        this.reportContent = reportContent;
        return this;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public InternshipReport createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public String getStar() {
        return star;
    }

    public InternshipReport star(String star) {
        this.star = star;
        return this;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getStatus() {
        return status;
    }

    public InternshipReport status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperating() {
        return operating;
    }

    public InternshipReport operating(String operating) {
        this.operating = operating;
        return this;
    }

    public void setOperating(String operating) {
        this.operating = operating;
    }

    public Intership getInterId() {
        return interId;
    }

    public InternshipReport interId(Intership intership) {
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
        if (!(o instanceof InternshipReport)) {
            return false;
        }
        return id != null && id.equals(((InternshipReport) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InternshipReport{" +
            "id=" + getId() +
            ", reportType='" + getReportType() + "'" +
            ", projectName='" + getProjectName() + "'" +
            ", intershipStage='" + getIntershipStage() + "'" +
            ", annex='" + getAnnex() + "'" +
            ", reportContent='" + getReportContent() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", star='" + getStar() + "'" +
            ", status='" + getStatus() + "'" +
            ", operating='" + getOperating() + "'" +
            "}";
    }
}
