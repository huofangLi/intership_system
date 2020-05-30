package com.intership.server.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A StuTea.
 */
@Entity
@Table(name = "stu_tea")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StuTea implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime createTime;

    @ManyToOne
    @JsonIgnoreProperties("stuTeas")
    private Student stuId;

    @ManyToOne
    @JsonIgnoreProperties("stuTeas")
    private Teacher teaId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public StuTea createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public Student getStuId() {
        return stuId;
    }

    public StuTea stuId(Student student) {
        this.stuId = student;
        return this;
    }

    public void setStuId(Student student) {
        this.stuId = student;
    }

    public Teacher getTeaId() {
        return teaId;
    }

    public StuTea teaId(Teacher teacher) {
        this.teaId = teacher;
        return this;
    }

    public void setTeaId(Teacher teacher) {
        this.teaId = teacher;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StuTea)) {
            return false;
        }
        return id != null && id.equals(((StuTea) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StuTea{" +
            "id=" + getId() +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
