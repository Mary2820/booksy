package com.solvd.booksyapp.models;

public class Procedure {
    private Long id;
    private Long categoryId;
    private String name;
    private String description;
    private Integer duration;

    public Procedure(Long categoryId, String name, String description, Integer duration) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.duration = duration;
    }

    public Procedure() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
