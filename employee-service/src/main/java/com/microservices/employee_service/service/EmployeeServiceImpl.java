package com.microservices.employee_service.service;

import com.microservices.employee_service.commons.response.BaseResponse;
import com.microservices.employee_service.constants.MessageConstants;
import com.microservices.employee_service.constants.PropertyConfig;
import com.microservices.employee_service.exceptions.CustomException;
import com.microservices.employee_service.model.entity.Employee;
import com.microservices.employee_service.model.request.AddressRequest;
import com.microservices.employee_service.model.request.EmployeeRequest;
import com.microservices.employee_service.model.response.AddressResponse;
import com.microservices.employee_service.model.response.EmployeeResponse;
import com.microservices.employee_service.openfeignclients.AddressFeignClient;
import com.microservices.employee_service.repository.IEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import static com.microservices.employee_service.commons.util.DateTimeUtil.getInstant;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {

    private final IEmployeeRepository iEmployeeRepository;
    private final RestTemplate restTemplate;
    private final WebClient webClient;
    private final PropertyConfig propertyConfig;
    private final DiscoveryClient discoveryClient;
    private final LoadBalancerClient loadBalancerClient;
    private final AddressFeignClient addressFeignClient;


    @Override
    public EmployeeResponse getEmployeeDetailsByIdUsingFeignClient(Long employeeId) {


//        ServiceInstance serviceInstance = loadBalancerClient.choose("address-service");
//        String uri = serviceInstance.getUri().toString();
//        String contextRoot = serviceInstance.getMetadata().get("configPath");
        //System.out.println(uri + contextRoot);

        Employee employee = iEmployeeRepository.findById(employeeId).orElseThrow(() -> new CustomException(String.format(MessageConstants.EMPLOYEE_NOT_FOUND, employeeId)));

        EmployeeResponse employeeResponse = EmployeeResponse.builder().build();
        BeanUtils.copyProperties(employee, employeeResponse);

        AddressResponse addressResponse = addressFeignClient.getAddressDetailsByEmployeeId(employeeId);

        employeeResponse.setAddressResponse(addressResponse);
        return employeeResponse;
    }

    @Override
    public EmployeeResponse getEmployeeDetailsById(Long employeeId) {

      /*  List<ServiceInstance> instances = discoveryClient.getInstances("address-service");

        ServiceInstance serviceInstance = instances.get(0);
        String uri = serviceInstance.getUri().toString();
        System.out.println("URI:- " + uri);*/

        ServiceInstance serviceInstance = loadBalancerClient.choose("address-service");
        String uri = serviceInstance.getUri().toString();
        String contextRoot = serviceInstance.getMetadata().get("configPath");
        //System.out.println(uri + contextRoot);

        AddressResponse addressResponse = AddressResponse.builder().build();

        Employee employee = iEmployeeRepository.findById(employeeId).orElseThrow(() -> new CustomException(String.format(MessageConstants.EMPLOYEE_NOT_FOUND, employeeId)));

        EmployeeResponse employeeResponse = EmployeeResponse.builder().build();
        BeanUtils.copyProperties(employee, employeeResponse);
        //String url = uri + "/app/address/" + employeeId;
        // String url = uri + contextRoot + employeeId;
        String url = "http://ADDRESS-SERVICE/app/address/" + employeeId;

        System.out.println(url);
        addressResponse = restTemplate.getForObject(url, AddressResponse.class);

        /*String url = propertyConfig.addressBaseUrl + "/" + employeeId;//"http://localhost:8081/app/address/" + employeeId;
        addressResponse = webClient.get()
                .uri(url)  // Dynamically adding employeeId to the URI
                .retrieve()
                .bodyToMono(AddressResponse.class)  // Deserializing response to AddressResponse
                .block();  // Blocks here and waits for the result*/

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
        ResponseEntity<AddressRequest> responseEntity = restTemplate.postForEntity(propertyConfig.addressBaseUrl, request.getAddressRequest(), AddressRequest.class);

       /* if (responseEntity.getStatusCode() != HttpStatus.CREATED) {
            // If the address creation failed, mark the transaction for rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new CustomException("Address creation failed, rolling back employee creation.");
        }*/

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
