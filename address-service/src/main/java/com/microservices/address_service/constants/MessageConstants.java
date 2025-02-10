package com.microservices.address_service.constants;

import org.springframework.stereotype.Component;

@Component
public class MessageConstants {
    public static final String USER_NOT_LOGGED_IN = "You need to log in to access this page.";
    public static final String SUCCESS = "SUCCESS";
    public static final String ISSUE = "Something went wrong. please try again after sometime";
    public static final String ERROR_LOGIN = "Error caught while login:: ";

    /**
     * ===============================================================================================================================================
     * Address service Error Messages
     * ===============================================================================================================================================
     * Error messages related to the Address service.
     */

    public static final String EMPLOYEE_ADDRESS_NOT_FOUND = "Address not found with employeeId: %d";
}
