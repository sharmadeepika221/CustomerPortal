package com.assessment.customerPortal.model;

import javax.persistence.*;

@Entity(name="address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private String id;
    @Column(name = "flat_no")
    private String flatNo;
    @Column(name = "street_name")
    private String streetName;
    @Column(name = "city")
    private String city;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "country")
    private String country;
    @OneToOne(mappedBy = "currentAddress", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Customer customer;

    public Address() {
    }

    public Address(String id, String flatNo, String streetName, String city, String postalCode, String country) {
        this.id = id;
        this.flatNo = flatNo;
        this.streetName = streetName;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
