package com.remidiousE.controller;

import com.remidiousE.Exceptions.*;
import com.remidiousE.dto.request.AdminRegistrationRequest;
import com.remidiousE.dto.request.BookRegistrationRequest;
import com.remidiousE.dto.response.AdminRegistrationResponse;
import com.remidiousE.dto.response.BookRegistrationResponse;
import com.remidiousE.model.Admin;
import com.remidiousE.model.Book;
import com.remidiousE.service.AdminService;
import com.remidiousE.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private BookService bookService;

    @PostMapping("/create-admin")
    public ResponseEntity<AdminRegistrationResponse> registerNewAdmin(@Valid @RequestBody AdminRegistrationRequest adminRegistrationRequest) throws AdminRegistrationException {
        AdminRegistrationResponse response = adminService.registerNewAdmin(adminRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-admin/{id}")
    public Optional<Admin> findAdminById(@Valid @PathVariable("id") Long adminId) throws AdminNotFoundException {
        return adminService.findAdminById(adminId);
    }

    @GetMapping("/find-admin/{username}")
    public Optional<Admin> findAdminByUsername(@Valid @PathVariable("username") String username) throws AdminNotFoundException {
        return adminService.findAdminByUsername(username);
    }

    @GetMapping("/get-admins")
    public List<Admin> findAllAdmin() {
        return adminService.findAllAdmin();
    }

    @GetMapping("/admin-find-book/{author_name}")
    public List<Book> findBooksByAuthorName(@PathVariable("author_name") String firstname, String lastname) throws AdminNotFoundException {
        return adminService.findBooksByAuthorName(firstname, lastname);
    }
    @GetMapping("/admin-search-book/{title}")
    public List<Book> searchBookByTitle(@PathVariable("title") String title) throws BookNotFoundException {
        return bookService.searchBookByTitle(title);

    }
    @PutMapping("/update-admin/{id}")
    public Admin updateAdminById(@PathVariable("id") Long adminId, @RequestBody Admin admin) throws AdminNotFoundException {
        return adminService.updateAdminById(adminId, admin);
    }

    @DeleteMapping("/delete-admin/{adminId}")
    public String deleteAdminById(@PathVariable("adminId") Long adminId) throws AdminNotFoundException {
        adminService.deleteAdminById(adminId);
        return "User has been deleted successfully";
    }
    @DeleteMapping("/admin-delete-author/{authorId}")
    public String deleteAuthorById(@PathVariable("authorId") Long authorId){
        adminService.deleteAuthorById(authorId);
        return "User has been deleted successfully";
    }
    @DeleteMapping("/admin-delete-user/{userId}")
    public String deleteUserById(@PathVariable("userId") Long userId) {
        adminService.deleteUserById(userId);
        return "Admin has been deleted successfully";
    }
}
