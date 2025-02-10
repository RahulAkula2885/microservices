package com.microservices.address_service.repository;

import com.microservices.address_service.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT o from Address o where o.employeeId =?1 and o.deleted = false")
    Optional<Address> findByEmployeeId(Long employeeId);
}
