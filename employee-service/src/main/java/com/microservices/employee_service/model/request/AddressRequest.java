package com.microservices.employee_service.model.request;

import jakarta.persistence.Transient;
import lombok.Data;

@Data
public class AddressRequest {

    @Transient
    public Long employeeId;
    public String city;
    public String state;
    public String country;
    public String address;
    public String pin;
}
