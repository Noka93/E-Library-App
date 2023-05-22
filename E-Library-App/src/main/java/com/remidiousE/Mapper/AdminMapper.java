package com.remidiousE.Mapper;

import com.remidiousE.dto.request.*;
import com.remidiousE.model.*;

public class AdminMapper {
    public static Admin map(AdminRegistrationRequest request) {
        Admin admin = new Admin();
        admin.setFullName(request.getFullName());
        admin.setEmail(request.getEmail());
        admin.setPassword(request.getPassword());
        admin.setRole(request.getRole());
        admin.setUsername(request.getUsername());
        admin.setPhoneNumber(request.getPhoneNumber());
        admin.setAddress(userAddress());
        return admin;
    }
    public  static Address userAddress(){
        Address address = new Address();
        address.setHouseNumber(address.getHouseNumber());
        address.setStreet(address.getStreet());
        address.setLocalGovernmentArea(address.getLocalGovernmentArea());
        address.setState(address.getState());
        return address;
    }

    public static Admin map(AdminLoginRequest adminLoginRequest){
        Admin admin = new Admin();
        admin.setUsername(adminLoginRequest.getUserName());
        admin.setPassword(admin.getPassword());
        return admin;
    }

    public static Book map(BookSearchByTitleRequest bookSearchByTitleRequest){
        BookSearchByTitleRequest searchByTitleRequest = new BookSearchByTitleRequest();
        Book book = new Book();
        book.setTitle(bookSearchByTitleRequest.getTitle());

        return book;
    }

}


