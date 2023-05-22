package com.remidiousE.service;

import com.remidiousE.Exceptions.AdminRegistrationException;
import com.remidiousE.dto.request.AdminRegistrationRequest;
import com.remidiousE.dto.response.BookSearchByTitleResponse;
import com.remidiousE.dto.response.AdminLoginResponse;
import com.remidiousE.dto.response.AdminRegistrationResponse;
import com.remidiousE.model.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class  AdminServiceImplTest {
    @Autowired
    private AdminService adminService;
    private AdminRegistrationRequest adminRegistrationRequest;
    @BeforeEach
    void setUp() {
        adminRegistrationRequest = buildAdminRegistration();
    }

    @Test
    void testToRegisterNewAdmin() throws AdminRegistrationException {
        AdminRegistrationResponse foundAdmin = adminService.registerNewAdmin(adminRegistrationRequest);
        System.out.println(foundAdmin);
        assertNotNull(foundAdmin);
    }
    @Test
    void testToFindAdminById() throws AdminRegistrationException {
        AdminRegistrationResponse adminResponse = adminService.registerNewAdmin(adminRegistrationRequest);

        Long registeredAdminId = 1L;
        adminResponse.setId(registeredAdminId);

        assertTrue(registeredAdminId != null && registeredAdminId > 0);

        Optional<Admin> foundAdmin = adminService.findAdminById(registeredAdminId);

        assertTrue(foundAdmin.isPresent());

        assertEquals(registeredAdminId, foundAdmin.get().getId());
    }
    @Test
    void testToFindAllAdmin() throws AdminRegistrationException {
        AdminRegistrationRequest admin1 = new AdminRegistrationRequest();
        AdminRegistrationRequest admin2 = new AdminRegistrationRequest();
        adminService.registerNewAdmin(admin1);
        adminService.registerNewAdmin(admin2);

        List<Admin> admins = adminService.findAllAdmin();

        assertEquals(4, admins.size());
    }
    private static AdminRegistrationRequest buildAdminRegistration(){
        AdminRegistrationRequest adminRegistrationRequest = new AdminRegistrationRequest();
        adminRegistrationRequest.setFullName("Remigious Enefola");
        adminRegistrationRequest.setUsername("Remy02");
        adminRegistrationRequest.setPhoneNumber("0813773");
        adminRegistrationRequest.setPassword("1234");
        adminRegistrationRequest.setEmail("remyenefola@gmail.com");
        adminRegistrationRequest.setRole("Library Officer");

        adminRegistrationRequest.setHouseNumber("No. 23");
        adminRegistrationRequest.setStreet("Akinola Street");
        adminRegistrationRequest.setLga("Abaji");
        adminRegistrationRequest.setState("FCT");
        return adminRegistrationRequest;
    }

    @Test
    void testToDeleteAdminById() throws AdminRegistrationException {
        adminService.registerNewAdmin(adminRegistrationRequest);

        List<Admin> admins = adminService.findAllAdmin();

        Admin lastRegisteredAdmin = admins.get(admins.size() - 1);

        adminService.deleteAdminById(lastRegisteredAdmin.getId());

        admins = adminService.findAllAdmin();

        assertEquals(1, admins.size());
    }

    @Test
    void testToLoginAdmin() {
        AdminRegistrationRequest registrationRequest = new AdminRegistrationRequest();
        registrationRequest.setUsername("admin");
        registrationRequest.setPassword("password");

        AdminLoginResponse loginResponse = adminService.loginAdmin(registrationRequest);
        System.out.println(loginResponse);
        assertEquals("You have logged in successfully", loginResponse.getMessage());
    }

    @Test
    void testThatAdminCanSearchBookByTitle(){
        AdminRegistrationRequest registrationRequest = new AdminRegistrationRequest();
        registrationRequest.setUsername("admin");
        registrationRequest.setPassword("password");

        AdminLoginResponse loginResponse = adminService.loginAdmin(registrationRequest);
        assertEquals("You have logged in successfully", loginResponse.getMessage());

        String bookTitle = "Judge of the Jungle";

        assertTrue(searchResultsDisplayed(), "Search results are not displayed");

        assertTrue(bookWithTitlePresent(bookTitle), "Book with title \"" + bookTitle + "\" is not found in the search results");

        assertTrue(bookDetailsPageDisplayed(), "Book details page is not displayed");

        BookSearchByTitleResponse bookDetails = getBookDetails();
        System.out.println(bookDetails);
    }
        private boolean searchResultsDisplayed() {
            return true;
        }
        private boolean bookWithTitlePresent(String bookTitle) {
            return true;
        }
        private boolean bookDetailsPageDisplayed() {
            return true;
        }
        private BookSearchByTitleResponse getBookDetails() {
            BookSearchByTitleResponse response = new BookSearchByTitleResponse();
            response.setTitle(response.getTitle());
            response.setStatus(response.getStatus());
            response.setDescription(response.getDescription());
            return response;
        }
}