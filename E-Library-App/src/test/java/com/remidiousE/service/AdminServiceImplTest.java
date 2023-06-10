package com.remidiousE.service;

import com.remidiousE.Exceptions.AdminNotFoundException;
import com.remidiousE.Exceptions.AdminRegistrationException;
import com.remidiousE.Exceptions.BookNotAvailableException;
import com.remidiousE.Exceptions.BookNotFoundException;
import com.remidiousE.dto.request.AdminLoginRequest;
import com.remidiousE.dto.request.AdminRegistrationRequest;
import com.remidiousE.dto.request.AuthorRegistrationRequest;
import com.remidiousE.dto.request.BookRegistrationRequest;
import com.remidiousE.dto.response.*;
import com.remidiousE.model.*;
import com.remidiousE.repositories.AdminRepository;
import com.remidiousE.repositories.AuthorRepository;
import com.remidiousE.repositories.BookRepository;
import com.remidiousE.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
class  AdminServiceImplTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private UserRepository userRepository;

    private AdminRegistrationRequest adminRegistrationRequest;

    private BookRegistrationRequest bookRegistrationRequest;

    private AuthorRegistrationRequest authorRegistrationRequest;

    @BeforeEach
    void setUp() {
        adminRegistrationRequest = buildAdminRegistration();
        bookRegistrationRequest = buildBook();
        authorRegistrationRequest = buildAuthor();
    }

    @Test
    void testToRegisterNewAdmin() throws AdminRegistrationException {
        AdminRegistrationResponse foundAdmin = adminService.registerNewAdmin(adminRegistrationRequest);
        System.out.println(foundAdmin);
        assertNotNull(foundAdmin);
    }
    @Test
    void testFindAdminById() throws AdminNotFoundException, AdminRegistrationException {
        AdminRegistrationResponse registeredAdmin = adminService.registerNewAdmin(adminRegistrationRequest);

        Optional<Admin> foundAdmin = adminService.findAdminById(registeredAdmin.getId());

        assertTrue(foundAdmin.isPresent());
        assertEquals(registeredAdmin.getId(), foundAdmin.get().getAdminId());
    }

    @Test
    void testFindAdminByUsername() throws AdminNotFoundException {
        String username = "admin123";
        Admin admin = new Admin();
        admin.setUsername(username);
        adminRepository.save(admin);

        Optional<Admin> foundAdmin = adminService.findAdminByUsername(username);

        assertTrue(foundAdmin.isPresent());
        assertEquals(username, foundAdmin.get().getUsername());
    }
    @Test
    void testToFindAllAdmin() throws AdminRegistrationException {
        AdminRegistrationRequest admin1 = new AdminRegistrationRequest();
        AdminRegistrationRequest admin2 = new AdminRegistrationRequest();
        adminService.registerNewAdmin(admin1);
        adminService.registerNewAdmin(admin2);

        List<Admin> admins = adminService.findAllAdmin();

        assertEquals(2, admins.size());
    }
    private static AdminRegistrationRequest buildAdminRegistration(){
        AdminRegistrationRequest adminRegistrationRequest = new AdminRegistrationRequest();
        adminRegistrationRequest.setFirstName("Remigious");
        adminRegistrationRequest.setLastName("Enefola");
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
    void testUpdateAdminProfileById() throws AdminNotFoundException {
        Admin admin = new Admin();
        admin.setFirstName("Femi");
        admin.setLastName("Michael");
        admin.setEmail("femi@gmail.com");
        admin.setUsername("Femo02");
        admin.setPhoneNumber("08134757992");

        Admin savedAdmin = adminRepository.save(admin);

        Admin updatedAdmin = new Admin();
        updatedAdmin.setFirstName("Femz");
        updatedAdmin.setEmail("femi@example.com");

        Admin returnedAdmin = adminService.updateAdminById(savedAdmin.getAdminId(), updatedAdmin);

        assertNotNull(returnedAdmin);
        assertEquals(savedAdmin.getAdminId(), returnedAdmin.getAdminId());
        assertEquals(updatedAdmin.getFirstName(), returnedAdmin.getFirstName());
        assertEquals(savedAdmin.getLastName(), returnedAdmin.getLastName());
        assertEquals(updatedAdmin.getEmail(), returnedAdmin.getEmail());
        assertEquals(savedAdmin.getUsername(), returnedAdmin.getUsername());
        assertEquals(savedAdmin.getPhoneNumber(), returnedAdmin.getPhoneNumber());
    }
    @Test
    void testSearchBookByTitle() throws BookNotFoundException {
        Book book1 = new Book();
        Book book2 = new Book();

        book1.setTitle("Let Love Lead");
        book2.setTitle("Love is greater");

        bookRepository.save(book1);
        bookRepository.save(book2);

        String titleToSearch = "Let Love Lead";
        List<Book> foundBooks = bookService.searchBookByTitle(titleToSearch);

        assertEquals(1, foundBooks.size());
        assertEquals(book1.getTitle(), foundBooks.get(0).getTitle());
    }
    @Test
    void testToDeleteAdminById() throws AdminRegistrationException, AdminNotFoundException {
        adminService.registerNewAdmin(adminRegistrationRequest);

        List<Admin> admins = adminService.findAllAdmin();

        Admin lastRegisteredAdmin = admins.get(admins.size() - 1);

        adminService.deleteAdminById(lastRegisteredAdmin.getAdminId());

        admins = adminService.findAllAdmin();

        assertEquals(0, admins.size());
    }
    @Test
    void testToLoginAdmin() {
        AdminLoginRequest registrationRequest = new AdminLoginRequest();
        registrationRequest.setUserName("admin");
        registrationRequest.setPassword("password");

        AdminLoginResponse loginResponse = adminService.loginAdmin(registrationRequest);
        System.out.println(loginResponse);
        assertEquals("You have logged in successfully", loginResponse.getMessage());
    }

        private static BookRegistrationRequest buildBook(){
        BookRegistrationRequest bookRegistrationRequest = new BookRegistrationRequest();
        bookRegistrationRequest.setTitle("The God's are mad");
        bookRegistrationRequest.setIsbn("34567");
        bookRegistrationRequest.setYear(1990);
        bookRegistrationRequest.setDescription("This book is a fantastic Book");
        return bookRegistrationRequest;
    }
    private static AuthorRegistrationRequest buildAuthor(){
        AuthorRegistrationRequest authorRegistrationRequest = new AuthorRegistrationRequest();
        authorRegistrationRequest.setFirstName("Jasmine");
        authorRegistrationRequest.setLastName("Mary");
        return authorRegistrationRequest;
    }
    @Test
    void testAdminCanReserveABook() throws BookNotFoundException, BookNotAvailableException {
        Admin admin = new Admin();
        adminRepository.save(admin);

        Book book = new Book();
        book.setStatus(Status.AVAILABLE);
        bookRepository.save(book);

        Long adminId = admin.getAdminId();
        Long bookId = book.getId();

        BookReservationResponse response = bookService.reserveBook(adminId, bookId);

        assertNotNull(response);
        assertEquals("Book reserved successfully.", response.getMessage());

        Optional<Book> reservedBook = bookRepository.findById(bookId);
        assertTrue(reservedBook.isPresent());
        assertEquals(Status.RESERVED, reservedBook.get().getStatus());
        assertEquals(adminId, reservedBook.get().getReservedBy());
        assertNotNull(reservedBook.get().getReservationTime());
    }
    @Test
    @Transactional
    public void testAdminCanDeleteBook() {
        Book book = new Book();
        book.setTitle("Best day ever");

        Book savedBook = bookRepository.save(book);
        Long bookId = savedBook.getId();

        adminService.deleteBookById(bookId);

        Optional<Book> deletedBook = bookRepository.findById(bookId);
        assertFalse(deletedBook.isPresent());
    }

    @Test
    public void testAdminCanDeleteUser() {
        User user = new User();
        user.setFirstName("Joel");
        user.setLastName("Grace");

        User savedUser = userRepository.save(user);
        Long userId = savedUser.getUserId();

        adminService.deleteUserById(userId);

        Optional<User> deletedUser = userRepository.findById(userId);
        assertFalse(deletedUser.isPresent());
    }
    @Test
    void testAdminCanDeleteAuthor() throws AdminRegistrationException {
        AdminRegistrationRequest adminRegistrationRequest = buildAdminRegistration();
        AdminRegistrationResponse registeredAdmin = adminService.registerNewAdmin(adminRegistrationRequest);

        Author author = new Author();
        authorRepository.save(author);

        Long authorId = author.getId();

        adminService.deleteAuthorById(authorId);

        assertFalse(authorRepository.existsById(authorId));
    }


    @Test
    void testAdminCanCheckoutBook() throws BookNotFoundException, BookNotAvailableException {
        Book book = new Book();
        book.setTitle("Sample Book");
        book.setAvailable(true);
        entityManager.persist(book);
        entityManager.flush();

        Long bookId = book.getId();

        BookCheckoutResponse response = adminService.checkoutBook(bookId);

        assertNotNull(response);
        assertEquals(bookId, response.getBookId());
        assertEquals("Sample Book", response.getTitle());
        assertNotNull(response.getCheckedOutBy());
    }
}