package com.intership.server.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 我的实习
 */
@ApiModel(description = "我的实习")
@Entity
@Table(name = "intership")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Intership implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "batch_name")
    private String batchName;

    @Column(name = "practice_type")
    private String practiceType;

    @Column(name = "course")
    private String course;

    @Column(name = "begin_intership")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime beginIntership;

    @Column(name = "end_intership")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime endIntership;

    @Column(name = "tutor_score")
    private Integer tutorScore;

    @Column(name = "master_score")
    private Integer masterScore;

    @Column(name = "intership_score")
    private Integer intershipScore;

    @Column(name = "company_credit_code")
    private String companyCreditCode;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "internship")
    private String internship;

    @Column(name = "company_contact_name")
    private String companyContactName;

    @Column(name = "company_contact_job")
    private String companyContactJob;

    @Column(name = "company_contact_phone")
    private String companyContactPhone;

    @Column(name = "master_contact_name")
    private String masterContactName;

    @Column(name = "master_contact_skill")
    private String masterContactSkill;

    @Column(name = "master_contact_phone")
    private String masterContactPhone;

    @Column(name = "urgent_contact_name")
    private String urgentContactName;

    @Column(name = "urgent_contact_phone")
    private String urgentContactPhone;

    @Column(name = "urgent_contact_address")
    private String urgentContactAddress;

    @Column(name = "accommodation_type")
    private String accommodationType;

    @Column(name = "accommodation_address")
    private String accommodationAddress;

    @Column(name = "company_address")
    private String companyAddress;

    @Column(name = "company_detail_address")
    private String companyDetailAddress;

    @Column(name = "company_nature")
    private String companyNature;

    @Column(name = "scale")
    private String scale;

    @Column(name = "industry")
    private String industry;

    @Column(name = "company_profile")
    private String companyProfile;

    @Column(name = "is_insurance")
    private String isInsurance;

    @Column(name = "insurance_company_and_policy_number")
    private String insuranceCompanyAndPolicyNumber;

    /**
     * 是否签订实习协议
     */
    @ApiModelProperty(value = "是否签订实习协议")
    @Column(name = "is_internship_agreement")
    private Boolean isInternshipAgreement;

    @Column(name = "annex")
    private String annex;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @Column(name = "modify_time")
    private ZonedDateTime modifyTime;

    @ManyToOne
    @JsonIgnoreProperties("interships")
    private Student stuId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBatchName() {
        return batchName;
    }

    public Intership batchName(String batchName) {
        this.batchName = batchName;
        return this;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getPracticeType() {
        return practiceType;
    }

    public Intership practiceType(String practiceType) {
        this.practiceType = practiceType;
        return this;
    }

    public void setPracticeType(String practiceType) {
        this.practiceType = practiceType;
    }

    public String getCourse() {
        return course;
    }

    public Intership course(String course) {
        this.course = course;
        return this;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public ZonedDateTime getBeginIntership() {
        return beginIntership;
    }

    public Intership beginIntership(ZonedDateTime beginIntership) {
        this.beginIntership = beginIntership;
        return this;
    }

    public void setBeginIntership(ZonedDateTime beginIntership) {
        this.beginIntership = beginIntership;
    }

    public ZonedDateTime getEndIntership() {
        return endIntership;
    }

    public Intership endIntership(ZonedDateTime endIntership) {
        this.endIntership = endIntership;
        return this;
    }

    public void setEndIntership(ZonedDateTime endIntership) {
        this.endIntership = endIntership;
    }

    public Integer getTutorScore() {
        return tutorScore;
    }

    public Intership tutorScore(Integer tutorScore) {
        this.tutorScore = tutorScore;
        return this;
    }

    public void setTutorScore(Integer tutorScore) {
        this.tutorScore = tutorScore;
    }

    public Integer getMasterScore() {
        return masterScore;
    }

    public Intership masterScore(Integer masterScore) {
        this.masterScore = masterScore;
        return this;
    }

    public void setMasterScore(Integer masterScore) {
        this.masterScore = masterScore;
    }

    public Integer getIntershipScore() {
        return intershipScore;
    }

    public Intership intershipScore(Integer intershipScore) {
        this.intershipScore = intershipScore;
        return this;
    }

    public void setIntershipScore(Integer intershipScore) {
        this.intershipScore = intershipScore;
    }

    public String getCompanyCreditCode() {
        return companyCreditCode;
    }

    public Intership companyCreditCode(String companyCreditCode) {
        this.companyCreditCode = companyCreditCode;
        return this;
    }

    public void setCompanyCreditCode(String companyCreditCode) {
        this.companyCreditCode = companyCreditCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Intership companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getInternship() {
        return internship;
    }

    public Intership internship(String internship) {
        this.internship = internship;
        return this;
    }

    public void setInternship(String internship) {
        this.internship = internship;
    }

    public String getCompanyContactName() {
        return companyContactName;
    }

    public Intership companyContactName(String companyContactName) {
        this.companyContactName = companyContactName;
        return this;
    }

    public void setCompanyContactName(String companyContactName) {
        this.companyContactName = companyContactName;
    }

    public String getCompanyContactJob() {
        return companyContactJob;
    }

    public Intership companyContactJob(String companyContactJob) {
        this.companyContactJob = companyContactJob;
        return this;
    }

    public void setCompanyContactJob(String companyContactJob) {
        this.companyContactJob = companyContactJob;
    }

    public String getCompanyContactPhone() {
        return companyContactPhone;
    }

    public Intership companyContactPhone(String companyContactPhone) {
        this.companyContactPhone = companyContactPhone;
        return this;
    }

    public void setCompanyContactPhone(String companyContactPhone) {
        this.companyContactPhone = companyContactPhone;
    }

    public String getMasterContactName() {
        return masterContactName;
    }

    public Intership masterContactName(String masterContactName) {
        this.masterContactName = masterContactName;
        return this;
    }

    public void setMasterContactName(String masterContactName) {
        this.masterContactName = masterContactName;
    }

    public String getMasterContactSkill() {
        return masterContactSkill;
    }

    public Intership masterContactSkill(String masterContactSkill) {
        this.masterContactSkill = masterContactSkill;
        return this;
    }

    public void setMasterContactSkill(String masterContactSkill) {
        this.masterContactSkill = masterContactSkill;
    }

    public String getMasterContactPhone() {
        return masterContactPhone;
    }

    public Intership masterContactPhone(String masterContactPhone) {
        this.masterContactPhone = masterContactPhone;
        return this;
    }

    public void setMasterContactPhone(String masterContactPhone) {
        this.masterContactPhone = masterContactPhone;
    }

    public String getUrgentContactName() {
        return urgentContactName;
    }

    public Intership urgentContactName(String urgentContactName) {
        this.urgentContactName = urgentContactName;
        return this;
    }

    public void setUrgentContactName(String urgentContactName) {
        this.urgentContactName = urgentContactName;
    }

    public String getUrgentContactPhone() {
        return urgentContactPhone;
    }

    public Intership urgentContactPhone(String urgentContactPhone) {
        this.urgentContactPhone = urgentContactPhone;
        return this;
    }

    public void setUrgentContactPhone(String urgentContactPhone) {
        this.urgentContactPhone = urgentContactPhone;
    }

    public String getUrgentContactAddress() {
        return urgentContactAddress;
    }

    public Intership urgentContactAddress(String urgentContactAddress) {
        this.urgentContactAddress = urgentContactAddress;
        return this;
    }

    public void setUrgentContactAddress(String urgentContactAddress) {
        this.urgentContactAddress = urgentContactAddress;
    }

    public String getAccommodationType() {
        return accommodationType;
    }

    public Intership accommodationType(String accommodationType) {
        this.accommodationType = accommodationType;
        return this;
    }

    public void setAccommodationType(String accommodationType) {
        this.accommodationType = accommodationType;
    }

    public String getAccommodationAddress() {
        return accommodationAddress;
    }

    public Intership accommodationAddress(String accommodationAddress) {
        this.accommodationAddress = accommodationAddress;
        return this;
    }

    public void setAccommodationAddress(String accommodationAddress) {
        this.accommodationAddress = accommodationAddress;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public Intership companyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
        return this;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyDetailAddress() {
        return companyDetailAddress;
    }

    public Intership companyDetailAddress(String companyDetailAddress) {
        this.companyDetailAddress = companyDetailAddress;
        return this;
    }

    public void setCompanyDetailAddress(String companyDetailAddress) {
        this.companyDetailAddress = companyDetailAddress;
    }

    public String getCompanyNature() {
        return companyNature;
    }

    public Intership companyNature(String companyNature) {
        this.companyNature = companyNature;
        return this;
    }

    public void setCompanyNature(String companyNature) {
        this.companyNature = companyNature;
    }

    public String getScale() {
        return scale;
    }

    public Intership scale(String scale) {
        this.scale = scale;
        return this;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getIndustry() {
        return industry;
    }

    public Intership industry(String industry) {
        this.industry = industry;
        return this;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCompanyProfile() {
        return companyProfile;
    }

    public Intership companyProfile(String companyProfile) {
        this.companyProfile = companyProfile;
        return this;
    }

    public void setCompanyProfile(String companyProfile) {
        this.companyProfile = companyProfile;
    }

    public String getIsInsurance() {
        return isInsurance;
    }

    public Intership isInsurance(String isInsurance) {
        this.isInsurance = isInsurance;
        return this;
    }

    public void setIsInsurance(String isInsurance) {
        this.isInsurance = isInsurance;
    }

    public String getInsuranceCompanyAndPolicyNumber() {
        return insuranceCompanyAndPolicyNumber;
    }

    public Intership insuranceCompanyAndPolicyNumber(String insuranceCompanyAndPolicyNumber) {
        this.insuranceCompanyAndPolicyNumber = insuranceCompanyAndPolicyNumber;
        return this;
    }

    public void setInsuranceCompanyAndPolicyNumber(String insuranceCompanyAndPolicyNumber) {
        this.insuranceCompanyAndPolicyNumber = insuranceCompanyAndPolicyNumber;
    }

    public Boolean isIsInternshipAgreement() {
        return isInternshipAgreement;
    }

    public Intership isInternshipAgreement(Boolean isInternshipAgreement) {
        this.isInternshipAgreement = isInternshipAgreement;
        return this;
    }

    public void setIsInternshipAgreement(Boolean isInternshipAgreement) {
        this.isInternshipAgreement = isInternshipAgreement;
    }

    public String getAnnex() {
        return annex;
    }

    public Intership annex(String annex) {
        this.annex = annex;
        return this;
    }

    public void setAnnex(String annex) {
        this.annex = annex;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public Intership createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getModifyTime() {
        return modifyTime;
    }

    public Intership modifyTime(ZonedDateTime modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public void setModifyTime(ZonedDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Student getStuId() {
        return stuId;
    }

    public Intership stuId(Student student) {
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
        if (!(o instanceof Intership)) {
            return false;
        }
        return id != null && id.equals(((Intership) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Intership{" +
            "id=" + getId() +
            ", batchName='" + getBatchName() + "'" +
            ", practiceType='" + getPracticeType() + "'" +
            ", course='" + getCourse() + "'" +
            ", beginIntership='" + getBeginIntership() + "'" +
            ", endIntership='" + getEndIntership() + "'" +
            ", tutorScore=" + getTutorScore() +
            ", masterScore=" + getMasterScore() +
            ", intershipScore=" + getIntershipScore() +
            ", companyCreditCode='" + getCompanyCreditCode() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", internship='" + getInternship() + "'" +
            ", companyContactName='" + getCompanyContactName() + "'" +
            ", companyContactJob='" + getCompanyContactJob() + "'" +
            ", companyContactPhone='" + getCompanyContactPhone() + "'" +
            ", masterContactName='" + getMasterContactName() + "'" +
            ", masterContactSkill='" + getMasterContactSkill() + "'" +
            ", masterContactPhone='" + getMasterContactPhone() + "'" +
            ", urgentContactName='" + getUrgentContactName() + "'" +
            ", urgentContactPhone='" + getUrgentContactPhone() + "'" +
            ", urgentContactAddress='" + getUrgentContactAddress() + "'" +
            ", accommodationType='" + getAccommodationType() + "'" +
            ", accommodationAddress='" + getAccommodationAddress() + "'" +
            ", companyAddress='" + getCompanyAddress() + "'" +
            ", companyDetailAddress='" + getCompanyDetailAddress() + "'" +
            ", companyNature='" + getCompanyNature() + "'" +
            ", scale='" + getScale() + "'" +
            ", industry='" + getIndustry() + "'" +
            ", companyProfile='" + getCompanyProfile() + "'" +
            ", isInsurance='" + getIsInsurance() + "'" +
            ", insuranceCompanyAndPolicyNumber='" + getInsuranceCompanyAndPolicyNumber() + "'" +
            ", isInternshipAgreement='" + isIsInternshipAgreement() + "'" +
            ", annex='" + getAnnex() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", modifyTime='" + getModifyTime() + "'" +
            "}";
    }
}
