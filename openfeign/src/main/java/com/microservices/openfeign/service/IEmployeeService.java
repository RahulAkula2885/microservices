package com.microservices.openfeign.service;


import com.microservices.openfeign.model.request.EmployeeRequest;
import com.microservices.openfeign.model.response.EmployeeResponse;
import org.springframework.http.ResponseEntity;

public interface IEmployeeService {
    EmployeeResponse getEmployeeDetailsById(Long employeeId);

    ResponseEntity<?> createEmployee(EmployeeRequest request);
}
