package com.microservices.employee_service.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeResponse {

    public Long employeeId;
    public String name;
    public String email;
    public String mobileNo;
    public AddressResponse addressResponse;
}
