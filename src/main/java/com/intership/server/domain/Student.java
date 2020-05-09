package com.intership.server.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stu_id")
    private Long stuId;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "stu_class")
    private String stuClass;

    @Column(name = "stu_department")
    private String stuDepartment;

    @Column(name = "stu_profession")
    private String stuProfession;

    @Column(name = "phone")
    private String phone;

    @Column(name = "privince")
    private String privince;

    @Column(name = "privince_code")
    private String privinceCode;

    @Column(name = "city")
    private String city;

    @Column(name = "city_code")
    private String cityCode;

    @Column(name = "country")
    private String country;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "address")
    private String address;

    @Column(name = "hobby")
    private String hobby;

    @Column(name = "skill")
    private String skill;

    @Column(name = "self_evaluation")
    private String selfEvaluation;

    @Column(name = "skin")
    private String skin;

    @Column(name = "created_time")
    private ZonedDateTime createdTime;

    @Column(name = "modify_time")
    private ZonedDateTime modifyTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStuId() {
        return stuId;
    }

    public Student stuId(Long stuId) {
        this.stuId = stuId;
        return this;
    }

    public void setStuId(Long stuId) {
        this.stuId = stuId;
    }

    public Boolean isGender() {
        return gender;
    }

    public Student gender(Boolean gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getStuClass() {
        return stuClass;
    }

    public Student stuClass(String stuClass) {
        this.stuClass = stuClass;
        return this;
    }

    public void setStuClass(String stuClass) {
        this.stuClass = stuClass;
    }

    public String getStuDepartment() {
        return stuDepartment;
    }

    public Student stuDepartment(String stuDepartment) {
        this.stuDepartment = stuDepartment;
        return this;
    }

    public void setStuDepartment(String stuDepartment) {
        this.stuDepartment = stuDepartment;
    }

    public String getStuProfession() {
        return stuProfession;
    }

    public Student stuProfession(String stuProfession) {
        this.stuProfession = stuProfession;
        return this;
    }

    public void setStuProfession(String stuProfession) {
        this.stuProfession = stuProfession;
    }

    public String getPhone() {
        return phone;
    }

    public Student phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrivince() {
        return privince;
    }

    public Student privince(String privince) {
        this.privince = privince;
        return this;
    }

    public void setPrivince(String privince) {
        this.privince = privince;
    }

    public String getPrivinceCode() {
        return privinceCode;
    }

    public Student privinceCode(String privinceCode) {
        this.privinceCode = privinceCode;
        return this;
    }

    public void setPrivinceCode(String privinceCode) {
        this.privinceCode = privinceCode;
    }

    public String getCity() {
        return city;
    }

    public Student city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public Student cityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountry() {
        return country;
    }

    public Student country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Student countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAddress() {
        return address;
    }

    public Student address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHobby() {
        return hobby;
    }

    public Student hobby(String hobby) {
        this.hobby = hobby;
        return this;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getSkill() {
        return skill;
    }

    public Student skill(String skill) {
        this.skill = skill;
        return this;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getSelfEvaluation() {
        return selfEvaluation;
    }

    public Student selfEvaluation(String selfEvaluation) {
        this.selfEvaluation = selfEvaluation;
        return this;
    }

    public void setSelfEvaluation(String selfEvaluation) {
        this.selfEvaluation = selfEvaluation;
    }

    public String getSkin() {
        return skin;
    }

    public Student skin(String skin) {
        this.skin = skin;
        return this;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public Student createdTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public ZonedDateTime getModifyTime() {
        return modifyTime;
    }

    public Student modifyTime(ZonedDateTime modifyTime) {
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
        if (!(o instanceof Student)) {
            return false;
        }
        return id != null && id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", stuId=" + getStuId() +
            ", gender='" + isGender() + "'" +
            ", stuClass='" + getStuClass() + "'" +
            ", stuDepartment='" + getStuDepartment() + "'" +
            ", stuProfession='" + getStuProfession() + "'" +
            ", phone='" + getPhone() + "'" +
            ", privince='" + getPrivince() + "'" +
            ", privinceCode='" + getPrivinceCode() + "'" +
            ", city='" + getCity() + "'" +
            ", cityCode='" + getCityCode() + "'" +
            ", country='" + getCountry() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            ", address='" + getAddress() + "'" +
            ", hobby='" + getHobby() + "'" +
            ", skill='" + getSkill() + "'" +
            ", selfEvaluation='" + getSelfEvaluation() + "'" +
            ", skin='" + getSkin() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", modifyTime='" + getModifyTime() + "'" +
            "}";
    }
}
