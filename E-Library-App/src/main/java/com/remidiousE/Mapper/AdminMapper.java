package com.remidiousE.Mapper;

import com.remidiousE.dto.request.*;
import com.remidiousE.dto.response.AdminRegistrationResponse;
import com.remidiousE.model.*;

public class AdminMapper {
    public static Admin map(AdminRegistrationRequest request) {
        Admin admin = new Admin();
        admin.setFirstName(request.getFirstName());
        admin.setLastName(request.getLastName());
        admin.setEmail(request.getEmail());
        admin.setPassword(request.getPassword());
        admin.setRole(request.getRole());
        admin.setUsername(request.getUsername());
        admin.setPhoneNumber(request.getPhoneNumber());
        admin.setHouseNumber(request.getHouseNumber());
        admin.setStreet(request.getStreet());
        admin.setLocalGovernmentArea(request.getLga());
        admin.setState(request.getState());
        return admin;
    }

    public static AdminRegistrationResponse map (Admin admin){
        AdminRegistrationResponse registrationResponse = new AdminRegistrationResponse();
        registrationResponse.setId(admin.getAdminId());
        registrationResponse.setMessage("Welcome!!! You have successfully registered");
        return registrationResponse;
    }


    public static Admin map(AdminLoginRequest adminLoginRequest){
        Admin admin = new Admin();
        admin.setUsername(adminLoginRequest.getUserName());
        admin.setPassword(admin.getPassword());
        return admin;
    }

}


