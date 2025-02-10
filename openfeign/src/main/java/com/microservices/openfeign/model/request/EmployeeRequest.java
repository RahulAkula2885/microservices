package com.microservices.openfeign.model.request;

import com.microservices.openfeign.model.request.AddressRequest;
import lombok.Data;

@Data
public class EmployeeRequest {

    public String name;
    public String email;
    public String mobileNo;
    public AddressRequest addressRequest;
}
