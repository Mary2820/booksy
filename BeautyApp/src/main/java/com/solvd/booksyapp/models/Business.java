package com.solvd.booksyapp.models;

public class Business {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String postcode;

    public Business(String name, String address, String city, String postcode) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.postcode = postcode;
    }

    public Business() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
