package com.remidiousE.service;

import com.remidiousE.Exceptions.AdminRegistrationException;
import com.remidiousE.Mapper.AdminMapper;
import com.remidiousE.dto.request.AdminRegistrationRequest;
import com.remidiousE.dto.response.AdminLoginResponse;
import com.remidiousE.dto.response.AdminRegistrationResponse;
import com.remidiousE.model.Address;
import com.remidiousE.model.Admin;
import com.remidiousE.model.Book;
import com.remidiousE.repositories.AddressRepository;
import com.remidiousE.repositories.AdminRepository;
import com.remidiousE.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor

public class AdminServiceImpl implements AdminService{


    private final AdminRepository adminRepository;

    private final AddressRepository addressRepository;

    private final BookRepository bookRepository;
    @Override
    public AdminRegistrationResponse registerNewAdmin(AdminRegistrationRequest adminRequest) throws AdminRegistrationException {
    Admin newAdmin = AdminMapper.map(adminRequest);
    Address address = newAdmin.getAddress();

    Address savedAddress = addressRepository.save(address);
    newAdmin.setAddress(savedAddress);

    adminRepository.save(newAdmin);

    AdminRegistrationResponse adminResponse = new AdminRegistrationResponse();
    adminResponse.setMessage("Your have successfully Registered");

    return adminResponse;
    }
    @Override
    public Optional<Admin> findAdminById(Long id) {
        Optional<Admin> foundAdmin = adminRepository.findById(id);
        return foundAdmin;
    }
    @Override
    public List<Admin> findAllAdmin() {
        return adminRepository.findAll();
    }
    @Override
    public void deleteAdminById(Long id) {
        adminRepository.deleteById(id);
    }
    @Override
    public AdminLoginResponse loginAdmin(AdminRegistrationRequest request) {
        AdminLoginResponse adminLoginResponse = new AdminLoginResponse();

        String username = request.getUsername();
        String password = request.getPassword();

        if (authenticate(username, password)) {
            adminLoginResponse.setMessage("You have logged in successfully");
        } else {
            adminLoginResponse.setMessage("Failed to log in");
        }

        return adminLoginResponse;
    }
    private boolean authenticate(String username, String password) {
        if (username.equals("admin") && password.equals("password")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Book> searchBookByTitle(String title) {
        List<Book> searchResults = bookRepository.searchByTitle(title);
        return searchResults;
    }



}


