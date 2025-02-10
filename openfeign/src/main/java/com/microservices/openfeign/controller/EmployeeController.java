package com.microservices.openfeign.controller;

import com.microservices.openfeign.commons.response.BaseResponse;
import com.microservices.openfeign.exceptions.CustomException;
import com.microservices.openfeign.exceptions.GlobalExceptionHandling;
import com.microservices.openfeign.model.request.EmployeeRequest;
import com.microservices.openfeign.model.response.EmployeeResponse;
import com.microservices.openfeign.service.IEmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/employee")
@Tag(name = "Employee Service APIs")
public class EmployeeController {

    private final GlobalExceptionHandling globalExceptionHandling;
    private final IEmployeeService iEmployeeService;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse> createCustomException(CustomException ex) {
        return globalExceptionHandling.createBaseResponse(INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @Operation(
            description = "Get Employee Details By Id",
            summary = "Get Employee Details By Id",
            responses = {
                    @ApiResponse(
                            description = "SUCCESS",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/{employeeId}")
    public EmployeeResponse getEmployeeDetailsById(@PathVariable("employeeId") Long employeeId) {
        return iEmployeeService.getEmployeeDetailsById(employeeId);
    }

    @Operation(
            description = "Create Employee",
            summary = "Create Employee",
            responses = {
                    @ApiResponse(
                            description = "SUCCESS",
                            responseCode = "200"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeRequest request) {
        return iEmployeeService.createEmployee(request);
    }
}
