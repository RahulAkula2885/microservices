package com.microservices.address_service.service;

import com.microservices.address_service.commons.response.BaseResponse;
import com.microservices.address_service.constants.MessageConstants;
import com.microservices.address_service.exceptions.CustomException;
import com.microservices.address_service.model.entity.Address;
import com.microservices.address_service.model.request.AddressRequest;
import com.microservices.address_service.model.response.AddressResponse;
import com.microservices.address_service.repository.IAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static com.microservices.address_service.commons.util.DateTimeUtil.getInstant;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements IAddressService {

    private final IAddressRepository iAddressRepository;


    @Override
    public AddressResponse getAddressDetailsByEmployeeId(Long employeeId) {
        Optional<Address> address = iAddressRepository.findByEmployeeId(employeeId);
        if (address.isPresent()) {
            AddressResponse addressResponse = AddressResponse.builder().build();

            BeanUtils.copyProperties(address.get(), addressResponse);

            return addressResponse;
        } else {
            throw new CustomException(String.format(MessageConstants.EMPLOYEE_ADDRESS_NOT_FOUND, employeeId));
        }
    }

    @Transactional
    @Override
    public ResponseEntity<?> createAddress(AddressRequest request) {

        System.out.println(request);

        checkAddressValidation(request);

        Address address = new Address();
        address.setEmployeeId(request.getEmployeeId());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setPin(request.getPin());
        address.setAddress(request.getAddress());
        address.setDeleted(false);
        address.setCreatedTime(getInstant());
        address.setModifiedTime(getInstant());

        iAddressRepository.save(address);


        BaseResponse baseResponse = BaseResponse.builder()
                .status(false)
                .message(MessageConstants.SUCCESS)
                .systemTime(getInstant())
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
    }

    private void checkAddressValidation(AddressRequest request) {
        if (request.getEmployeeId() == null || request.getEmployeeId() == 0) {
            throw new CustomException("Employee id must not be null or 0");
        }
        if (!StringUtils.hasText(request.getCity())) {
            throw new CustomException("City must not be null or empty");
        }
        if (!StringUtils.hasText(request.getState())) {
            throw new CustomException("State must not be null or empty");
        }
        if (!StringUtils.hasText(request.getCountry())) {
            throw new CustomException("Country must not be null or empty");
        }
        if (!StringUtils.hasText(request.getPin())) {
            throw new CustomException("Pin must not be null or empty");
        }
        if (!StringUtils.hasText(request.getAddress())) {
            throw new CustomException("Address must not be null or empty");
        }
    }
}
