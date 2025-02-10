package com.microservices.address_service.model.request;

import lombok.Data;

@Data
public class AddressRequest {

    public Long employeeId;
    public String city;
    public String state;
    public String country;
    public String address;
    public String pin;
}
