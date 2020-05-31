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
 * 我的证书
 */
@ApiModel(description = "我的证书")
@Entity
@Table(name = "certificate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Certificate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "certificate_name")
    private String certificateName;

    @Column(name = "certificate_type")
    private String certificateType;

    @Column(name = "certificate_level")
    private String certificateLevel;

    @Column(name = "certificate_acquisition_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime certificateAcquisitionTime;

    @Column(name = "certificate_photo")
    private String certificatePhoto;

    @Column(name = "created_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime createdTime;

    @Column(name = "modify_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime modifyTime;

    @ManyToOne
    @JsonIgnoreProperties("certificates")
    private Student stuId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public Certificate certificateName(String certificateName) {
        this.certificateName = certificateName;
        return this;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public Certificate certificateType(String certificateType) {
        this.certificateType = certificateType;
        return this;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateLevel() {
        return certificateLevel;
    }

    public Certificate certificateLevel(String certificateLevel) {
        this.certificateLevel = certificateLevel;
        return this;
    }

    public void setCertificateLevel(String certificateLevel) {
        this.certificateLevel = certificateLevel;
    }

    public ZonedDateTime getCertificateAcquisitionTime() {
        return certificateAcquisitionTime;
    }

    public Certificate certificateAcquisitionTime(ZonedDateTime certificateAcquisitionTime) {
        this.certificateAcquisitionTime = certificateAcquisitionTime;
        return this;
    }

    public void setCertificateAcquisitionTime(ZonedDateTime certificateAcquisitionTime) {
        this.certificateAcquisitionTime = certificateAcquisitionTime;
    }

    public String getCertificatePhoto() {
        return certificatePhoto;
    }

    public Certificate certificatePhoto(String certificatePhoto) {
        this.certificatePhoto = certificatePhoto;
        return this;
    }

    public void setCertificatePhoto(String certificatePhoto) {
        this.certificatePhoto = certificatePhoto;
    }

    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public Certificate createdTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public ZonedDateTime getModifyTime() {
        return modifyTime;
    }

    public Certificate modifyTime(ZonedDateTime modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public Student getGetStuId(){
        return stuId;
    }

    public Certificate setId(Student student){
        this.stuId = student;
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
        if (!(o instanceof Certificate)) {
            return false;
        }
        return id != null && id.equals(((Certificate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Certificate{" +
            "id=" + getId() +
            ", certificateName='" + getCertificateName() + "'" +
            ", certificateType='" + getCertificateType() + "'" +
            ", certificateLevel='" + getCertificateLevel() + "'" +
            ", certificateAcquisitionTime='" + getCertificateAcquisitionTime() + "'" +
            ", certificatePhoto='" + getCertificatePhoto() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", modifyTime='" + getModifyTime() + "'" +
            "}";
    }
}
