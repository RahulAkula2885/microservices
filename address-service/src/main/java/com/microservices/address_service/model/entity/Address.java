package com.microservices.address_service.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addressId")
    public Long addressId;

    @Column(name = "employeeId")
    public Long employeeId;

    @Column(name = "city")
    public String city;

    @Column(name = "state")
    public String state;

    @Column(name = "country")
    public String country;

    @Column(name = "pin")
    public String pin;

    @Column(name = "address")
    public String address;

    @Column(name = "deleted")
    public boolean deleted;

    @Column(name = "createdTime")
    public Instant createdTime;

    @Column(name = "modifiedTime")
    public Instant modifiedTime;
}
