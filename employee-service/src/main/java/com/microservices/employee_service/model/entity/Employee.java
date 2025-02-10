package com.microservices.employee_service.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employeeId")
    public Long employeeId;

    @Column(name = "name")
    public String name;

    @Column(name = "email")
    public String email;

    @Column(name = "mobileNo")
    public String mobileNo;

    @Column(name = "deleted")
    public boolean deleted;

    @Column(name = "createdTime")
    public Instant createdTime;

    @Column(name = "modifiedTime")
    public Instant modifiedTime;
}
