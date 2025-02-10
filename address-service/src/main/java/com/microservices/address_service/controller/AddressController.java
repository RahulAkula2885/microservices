package com.microservices.address_service.controller;

import com.microservices.address_service.commons.response.BaseResponse;
import com.microservices.address_service.exceptions.CustomException;
import com.microservices.address_service.exceptions.GlobalExceptionHandling;
import com.microservices.address_service.model.entity.Address;
import com.microservices.address_service.model.request.AddressRequest;
import com.microservices.address_service.model.response.AddressResponse;
import com.microservices.address_service.service.IAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/address")
@Tag(name = "Address Service APIs")
public class AddressController {

    private final GlobalExceptionHandling globalExceptionHandling;
    private final IAddressService iAddressService;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse> createCustomException(CustomException ex) {
        return globalExceptionHandling.createBaseResponse(INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @Operation(
            description = "Get Address Details By Id",
            summary = "Get Address Details By Id",
            responses = {
                    @ApiResponse(
                            description = "SUCCESS",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/{employeeId}")
    public AddressResponse getAddressDetailsByEmployeeId(@PathVariable("employeeId") Long employeeId) {
        return iAddressService.getAddressDetailsByEmployeeId(employeeId);
    }

    @Operation(
            description = "Create Address",
            summary = "Create Address",
            responses = {
                    @ApiResponse(
                            description = "SUCCESS",
                            responseCode = "200"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<?> createAddress(@RequestBody AddressRequest request) {
        return iAddressService.createAddress(request);
    }

}
