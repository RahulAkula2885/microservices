package com.microservices.address_service.service;


import com.microservices.address_service.model.entity.Address;
import com.microservices.address_service.model.request.AddressRequest;
import com.microservices.address_service.model.response.AddressResponse;
import org.springframework.http.ResponseEntity;

public interface IAddressService {

    AddressResponse getAddressDetailsByEmployeeId(Long employeeId);

    ResponseEntity<?> createAddress(AddressRequest request);
}
