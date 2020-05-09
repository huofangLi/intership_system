package com.intership.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 毕业设计
 */
@ApiModel(description = "毕业设计")
@Entity
@Table(name = "graduation_project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GraduationProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "graduation_project_title")
    private String graduationProjectTitle;

    @Column(name = "source")
    private String source;

    @Column(name = "task_book")
    private String taskBook;

    @Column(name = "task_book_check")
    private Boolean taskBookCheck;

    @Column(name = "task_book_reviews")
    private String taskBookReviews;

    @Column(name = "opening_report")
    private String openingReport;

    @Column(name = "opening_report_check")
    private Boolean openingReportCheck;

    @Column(name = "opening_report_reviews")
    private String openingReportReviews;

    @Column(name = "mid_term_inspection")
    private String midTermInspection;

    @Column(name = "mid_term_inspection_check")
    private Boolean midTermInspectionCheck;

    @Column(name = "mid_term_inspection_reviews")
    private String midTermInspectionReviews;

    @Column(name = "first_draft")
    private String firstDraft;

    @Column(name = "first_draft_check")
    private Boolean firstDraftCheck;

    @Column(name = "first_draft_reviews")
    private String firstDraftReviews;

    @Column(name = "final_draft")
    private String finalDraft;

    @Column(name = "final_draft_check")
    private Boolean finalDraftCheck;

    @Column(name = "final_draft_reviews")
    private String finalDraftReviews;

    @Column(name = "created_time")
    private ZonedDateTime createdTime;

    @Column(name = "modify_time")
    private ZonedDateTime modifyTime;

    @ManyToOne
    @JsonIgnoreProperties("graduationProjects")
    private Student stuId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGraduationProjectTitle() {
        return graduationProjectTitle;
    }

    public GraduationProject graduationProjectTitle(String graduationProjectTitle) {
        this.graduationProjectTitle = graduationProjectTitle;
        return this;
    }

    public void setGraduationProjectTitle(String graduationProjectTitle) {
        this.graduationProjectTitle = graduationProjectTitle;
    }

    public String getSource() {
        return source;
    }

    public GraduationProject source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTaskBook() {
        return taskBook;
    }

    public GraduationProject taskBook(String taskBook) {
        this.taskBook = taskBook;
        return this;
    }

    public void setTaskBook(String taskBook) {
        this.taskBook = taskBook;
    }

    public Boolean isTaskBookCheck() {
        return taskBookCheck;
    }

    public GraduationProject taskBookCheck(Boolean taskBookCheck) {
        this.taskBookCheck = taskBookCheck;
        return this;
    }

    public void setTaskBookCheck(Boolean taskBookCheck) {
        this.taskBookCheck = taskBookCheck;
    }

    public String getTaskBookReviews() {
        return taskBookReviews;
    }

    public GraduationProject taskBookReviews(String taskBookReviews) {
        this.taskBookReviews = taskBookReviews;
        return this;
    }

    public void setTaskBookReviews(String taskBookReviews) {
        this.taskBookReviews = taskBookReviews;
    }

    public String getOpeningReport() {
        return openingReport;
    }

    public GraduationProject openingReport(String openingReport) {
        this.openingReport = openingReport;
        return this;
    }

    public void setOpeningReport(String openingReport) {
        this.openingReport = openingReport;
    }

    public Boolean isOpeningReportCheck() {
        return openingReportCheck;
    }

    public GraduationProject openingReportCheck(Boolean openingReportCheck) {
        this.openingReportCheck = openingReportCheck;
        return this;
    }

    public void setOpeningReportCheck(Boolean openingReportCheck) {
        this.openingReportCheck = openingReportCheck;
    }

    public String getOpeningReportReviews() {
        return openingReportReviews;
    }

    public GraduationProject openingReportReviews(String openingReportReviews) {
        this.openingReportReviews = openingReportReviews;
        return this;
    }

    public void setOpeningReportReviews(String openingReportReviews) {
        this.openingReportReviews = openingReportReviews;
    }

    public String getMidTermInspection() {
        return midTermInspection;
    }

    public GraduationProject midTermInspection(String midTermInspection) {
        this.midTermInspection = midTermInspection;
        return this;
    }

    public void setMidTermInspection(String midTermInspection) {
        this.midTermInspection = midTermInspection;
    }

    public Boolean isMidTermInspectionCheck() {
        return midTermInspectionCheck;
    }

    public GraduationProject midTermInspectionCheck(Boolean midTermInspectionCheck) {
        this.midTermInspectionCheck = midTermInspectionCheck;
        return this;
    }

    public void setMidTermInspectionCheck(Boolean midTermInspectionCheck) {
        this.midTermInspectionCheck = midTermInspectionCheck;
    }

    public String getMidTermInspectionReviews() {
        return midTermInspectionReviews;
    }

    public GraduationProject midTermInspectionReviews(String midTermInspectionReviews) {
        this.midTermInspectionReviews = midTermInspectionReviews;
        return this;
    }

    public void setMidTermInspectionReviews(String midTermInspectionReviews) {
        this.midTermInspectionReviews = midTermInspectionReviews;
    }

    public String getFirstDraft() {
        return firstDraft;
    }

    public GraduationProject firstDraft(String firstDraft) {
        this.firstDraft = firstDraft;
        return this;
    }

    public void setFirstDraft(String firstDraft) {
        this.firstDraft = firstDraft;
    }

    public Boolean isFirstDraftCheck() {
        return firstDraftCheck;
    }

    public GraduationProject firstDraftCheck(Boolean firstDraftCheck) {
        this.firstDraftCheck = firstDraftCheck;
        return this;
    }

    public void setFirstDraftCheck(Boolean firstDraftCheck) {
        this.firstDraftCheck = firstDraftCheck;
    }

    public String getFirstDraftReviews() {
        return firstDraftReviews;
    }

    public GraduationProject firstDraftReviews(String firstDraftReviews) {
        this.firstDraftReviews = firstDraftReviews;
        return this;
    }

    public void setFirstDraftReviews(String firstDraftReviews) {
        this.firstDraftReviews = firstDraftReviews;
    }

    public String getFinalDraft() {
        return finalDraft;
    }

    public GraduationProject finalDraft(String finalDraft) {
        this.finalDraft = finalDraft;
        return this;
    }

    public void setFinalDraft(String finalDraft) {
        this.finalDraft = finalDraft;
    }

    public Boolean isFinalDraftCheck() {
        return finalDraftCheck;
    }

    public GraduationProject finalDraftCheck(Boolean finalDraftCheck) {
        this.finalDraftCheck = finalDraftCheck;
        return this;
    }

    public void setFinalDraftCheck(Boolean finalDraftCheck) {
        this.finalDraftCheck = finalDraftCheck;
    }

    public String getFinalDraftReviews() {
        return finalDraftReviews;
    }

    public GraduationProject finalDraftReviews(String finalDraftReviews) {
        this.finalDraftReviews = finalDraftReviews;
        return this;
    }

    public void setFinalDraftReviews(String finalDraftReviews) {
        this.finalDraftReviews = finalDraftReviews;
    }

    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public GraduationProject createdTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public ZonedDateTime getModifyTime() {
        return modifyTime;
    }

    public GraduationProject modifyTime(ZonedDateTime modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public void setModifyTime(ZonedDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Student getStuId() {
        return stuId;
    }

    public GraduationProject stuId(Student student) {
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
        if (!(o instanceof GraduationProject)) {
            return false;
        }
        return id != null && id.equals(((GraduationProject) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GraduationProject{" +
            "id=" + getId() +
            ", graduationProjectTitle='" + getGraduationProjectTitle() + "'" +
            ", source='" + getSource() + "'" +
            ", taskBook='" + getTaskBook() + "'" +
            ", taskBookCheck='" + isTaskBookCheck() + "'" +
            ", taskBookReviews='" + getTaskBookReviews() + "'" +
            ", openingReport='" + getOpeningReport() + "'" +
            ", openingReportCheck='" + isOpeningReportCheck() + "'" +
            ", openingReportReviews='" + getOpeningReportReviews() + "'" +
            ", midTermInspection='" + getMidTermInspection() + "'" +
            ", midTermInspectionCheck='" + isMidTermInspectionCheck() + "'" +
            ", midTermInspectionReviews='" + getMidTermInspectionReviews() + "'" +
            ", firstDraft='" + getFirstDraft() + "'" +
            ", firstDraftCheck='" + isFirstDraftCheck() + "'" +
            ", firstDraftReviews='" + getFirstDraftReviews() + "'" +
            ", finalDraft='" + getFinalDraft() + "'" +
            ", finalDraftCheck='" + isFinalDraftCheck() + "'" +
            ", finalDraftReviews='" + getFinalDraftReviews() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", modifyTime='" + getModifyTime() + "'" +
            "}";
    }
}
