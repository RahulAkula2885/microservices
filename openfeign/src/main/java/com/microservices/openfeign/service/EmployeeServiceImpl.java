package com.microservices.openfeign.service;

import com.microservices.openfeign.commons.response.BaseResponse;
import com.microservices.openfeign.constants.MessageConstants;
import com.microservices.openfeign.constants.PropertyConfig;
import com.microservices.openfeign.exceptions.CustomException;
import com.microservices.openfeign.model.entity.Employee;
import com.microservices.openfeign.model.request.EmployeeRequest;
import com.microservices.openfeign.model.response.AddressResponse;
import com.microservices.openfeign.model.response.EmployeeResponse;
import com.microservices.openfeign.repository.IEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import static com.microservices.openfeign.commons.util.DateTimeUtil.getInstant;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {

    private final IEmployeeRepository iEmployeeRepository;
    private final PropertyConfig propertyConfig;
    private final AddressClient addressClient;

    @Override
    public EmployeeResponse getEmployeeDetailsById(Long employeeId) {

        AddressResponse addressResponse = AddressResponse.builder().build();

        Employee employee = iEmployeeRepository.findById(employeeId).orElseThrow(() -> new CustomException(String.format(MessageConstants.EMPLOYEE_NOT_FOUND, employeeId)));

        EmployeeResponse employeeResponse = EmployeeResponse.builder().build();
        BeanUtils.copyProperties(employee, employeeResponse);

        String url = propertyConfig.addressBaseUrl + "/" + employeeId;

        addressResponse = addressClient.getAddressDetailsByEmployeeId(employeeId);

        employeeResponse.setAddressResponse(addressResponse);
        return employeeResponse;
    }

    @Transactional
    @Override
    public ResponseEntity<?> createEmployee(EmployeeRequest request) {

        checkEmployeeValidation(request);

        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setMobileNo(request.getMobileNo());
        employee.setDeleted(false);
        employee.setCreatedTime(getInstant());
        employee.setModifiedTime(getInstant());
        iEmployeeRepository.save(employee);

        request.getAddressRequest().setEmployeeId(employee.getEmployeeId());
       // ResponseEntity<AddressRequest> responseEntity =  restTemplate.postForEntity(propertyConfig.addressBaseUrl, request.getAddressRequest(), AddressRequest.class);


        BaseResponse baseResponse = BaseResponse.builder()
                .status(false)
                .message(MessageConstants.SUCCESS)
                .systemTime(getInstant())
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
    }

    private void checkEmployeeValidation(EmployeeRequest request) {

        if (!StringUtils.hasText(request.getName())) {
            throw new CustomException("Name must not be null or empty");
        }
        if (!StringUtils.hasText(request.getEmail())) {
            throw new CustomException("Email must not be null or empty");
        }
        if (!StringUtils.hasText(request.getMobileNo())) {
            throw new CustomException("Mobile number must not be null or empty");
        }
        if (request.getAddressRequest() == null) {
            throw new CustomException("Address must not be null or empty");
        }
        if (!StringUtils.hasText(request.getAddressRequest().getCity())) {
            throw new CustomException("City must not be null or empty");
        }
        if (!StringUtils.hasText(request.getAddressRequest().getState())) {
            throw new CustomException("State must not be null or empty");
        }
        if (!StringUtils.hasText(request.getAddressRequest().getCountry())) {
            throw new CustomException("Country must not be null or empty");
        }
        if (!StringUtils.hasText(request.getAddressRequest().getPin())) {
            throw new CustomException("Pin must not be null or empty");
        }
        if (!StringUtils.hasText(request.getAddressRequest().getAddress())) {
            throw new CustomException("Address must not be null or empty");
        }
    }
}
