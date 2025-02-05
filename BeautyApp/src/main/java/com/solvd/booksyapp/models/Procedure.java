package com.solvd.booksyapp.models;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "procedure")
@XmlAccessorType(XmlAccessType.NONE)
public class Procedure {
    private Long id;
    private Long categoryId;
    private String name;
    private String description;
    private Integer duration;

    public Procedure() {}

    public Procedure(Long categoryId, String name, String description, Integer duration) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.duration = duration;
    }

    @XmlAttribute(name = "id")
    public Long getId() {
        return id;
    }

    @XmlElement(name = "categoryId")
    public Long getCategoryId() {
        return categoryId;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    @XmlElement(name = "description")
    public String getDescription() {
        return description;
    }

    @XmlElement(name = "duration")
    public Integer getDuration() {
        return duration;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Procedure{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                '}';
    }
}
