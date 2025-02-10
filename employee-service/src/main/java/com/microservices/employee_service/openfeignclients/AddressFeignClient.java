package com.microservices.employee_service.openfeignclients;

import com.microservices.employee_service.model.response.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ADDRESS-SERVICE", path = "/app/address")
public interface AddressFeignClient {

    @GetMapping("/{employeeId}")
    public AddressResponse getAddressDetailsByEmployeeId(@PathVariable("employeeId") Long employeeId);


}