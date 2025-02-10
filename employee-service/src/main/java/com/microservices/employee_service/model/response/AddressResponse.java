package com.microservices.employee_service.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponse {

    public Long addressId;
    public Long employeeId;
    public String city;
    public String state;
    public String country;
    public String address;
    public String pin;
}
