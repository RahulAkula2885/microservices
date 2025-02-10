package com.microservices.employee_service.model.request;

import lombok.Data;

@Data
public class EmployeeRequest {

    public String name;
    public String email;
    public String mobileNo;
    public AddressRequest addressRequest;
}
