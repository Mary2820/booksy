package com.solvd.booksyapp.enums;

import java.util.Arrays;
import java.util.List;

public enum Category {
    HAIR(1L, "Hair"),
    BARBERS(2L, "Barbers"),
    NAILS(3L, "Nails"),
    BROWS_AND_LASHES(4L, "Brows & Lashes"),
    MAKEUP_ARTISTS(5L, "Makeup Artists"),
    HAIR_REMOVAL(6L, "Hair Removal"),
    AESTHETICIANS(7L, "Aestheticians"),
    TATTOOISTS(8L, "Tattooists"),
    PIERCISTS(9L, "Piercists"),
    TATTOO_REMOVAL(10L, "Tattoo Removal"),
    MASSAGE_THERAPISTS(11L, "Massage Therapists"),
    SPAS(12L, "Spas"),
    WEIGHT_MANAGEMENT_SPECIALISTS(13L, "Weight Management Specialists"),
    FITNESS_INSTRUCTORS(14L, "Fitness Instructors"),
    PHYSIOTHERAPISTS(15L, "Physiotherapists"),
    DENTISTS(16L, "Dentists"),
    PET_SERVICES(17L, "Pet Services"),
    HOME_SERVICES(18L, "Home Services"),
    OTHER(19L, "Other");

    private final Long id;
    private final String name;

    Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Category getById(Long id) {
        for (Category category : values()) {
            if (category.getId().equals(id)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No category found with id: " + id);
    }

    public static Category getByName(String name) {
        for (Category category : values()) {
            if (category.getName().equalsIgnoreCase(name)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No category found with name: " + name);
    }

    public static List<Category> getAllCategories() {
        return Arrays.asList(Category.values());
    }
}
