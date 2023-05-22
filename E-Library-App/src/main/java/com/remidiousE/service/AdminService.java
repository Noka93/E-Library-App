package com.remidiousE.service;

import com.remidiousE.Exceptions.AdminRegistrationException;
import com.remidiousE.dto.request.AdminLoginRequest;
import com.remidiousE.dto.request.AdminRegistrationRequest;
import com.remidiousE.dto.response.AdminLoginResponse;
import com.remidiousE.dto.response.AdminRegistrationResponse;
import com.remidiousE.model.Admin;
import com.remidiousE.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AdminService {

    AdminRegistrationResponse registerNewAdmin(AdminRegistrationRequest adminRequest) throws AdminRegistrationException;

    Optional<Admin> findAdminById(Long id);

    List<Admin> findAllAdmin();

    void deleteAdminById(Long id);

    AdminLoginResponse loginAdmin(AdminRegistrationRequest request);

    List<Book> searchBookByTitle(String title);
}
