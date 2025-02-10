package com.microservices.openfeign.service;

import com.microservices.openfeign.model.response.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "address-service", url = "http://localhost:9000/app/address")
public interface AddressClient {

    @GetMapping("/{employeeId}")
    AddressResponse getAddressDetailsByEmployeeId(@PathVariable("employeeId") Long employeeId);
}
