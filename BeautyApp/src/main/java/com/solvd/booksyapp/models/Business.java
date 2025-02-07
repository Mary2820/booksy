package com.solvd.booksyapp.models;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@JsonRootName(value = "business")
@XmlRootElement(name = "business")
@XmlAccessorType(XmlAccessType.NONE)
public class Business {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String postcode;

    public Business() {}

    public Business(String name, String address, String city, String postcode) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.postcode = postcode;
    }

    @XmlAttribute(name = "id")
    public Long getId() {
        return id;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    @XmlElement(name = "address")
    public String getAddress() {
        return address;
    }

    @XmlElement(name = "city")
    public String getCity() {
        return city;
    }

    @XmlElement(name = "postcode")
    public String getPostcode() {
        return postcode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public String toString() {
        return "Business{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", postcode='" + postcode + '\'' +
                '}';
    }
}
