package com.microservices.employee_service.service;


import com.microservices.employee_service.model.entity.Employee;
import com.microservices.employee_service.model.request.EmployeeRequest;
import com.microservices.employee_service.model.response.EmployeeResponse;
import org.springframework.http.ResponseEntity;

public interface IEmployeeService {
    EmployeeResponse getEmployeeDetailsById(Long employeeId);

    ResponseEntity<?> createEmployee(EmployeeRequest request);

    EmployeeResponse getEmployeeDetailsByIdUsingFeignClient(Long employeeId);
}
