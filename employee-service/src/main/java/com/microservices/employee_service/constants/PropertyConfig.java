package com.microservices.employee_service.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertyConfig {

    @Value("${address-service.base-url}")
    public String addressBaseUrl;

}
