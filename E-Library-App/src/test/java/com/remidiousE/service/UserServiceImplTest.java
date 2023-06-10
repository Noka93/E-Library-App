package com.remidiousE.service;

import com.remidiousE.Exceptions.*;
import com.remidiousE.dto.request.UserLoginRequest;
import com.remidiousE.dto.request.UserRegistrationRequest;
import com.remidiousE.dto.response.*;
import com.remidiousE.model.Admin;
import com.remidiousE.model.Book;
import com.remidiousE.model.User;
import com.remidiousE.model.Status;
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
class UserServiceImplTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    private UserRegistrationRequest userRegistrationRequest;

    @BeforeEach
    void setUp() {
        userRegistrationRequest = buildUserRegistration();

    }
    @Test
    void testToRegisterNewAUser() throws UserRegistrationException {
        UserRegistrationResponse foundUser = userService.registerNewUser(userRegistrationRequest);
        assertNotNull(foundUser);
    }
    @Test
    void testFindUserById() throws UserNotFoundException {
        // Create a test user
        User user = new User();
        userRepository.save(user);

        // Retrieve the user by ID
        Long userId = user.getUserId();
        Optional<User> foundUser = userService.findUserById(userId);

        // Assert that the user is present and has the correct ID
        assertTrue(foundUser.isPresent());
        assertEquals(userId, foundUser.get().getUserId());
    }

    @Test
    void testToFindAllUsers() throws UserRegistrationException {
        UserRegistrationRequest user1 = new UserRegistrationRequest();
        UserRegistrationRequest user2 = new UserRegistrationRequest();
        userService.registerNewUser(user1);
        userService.registerNewUser(user2);

        List<User> users = userService.findAllUsers();

        assertEquals(2, users.size());
    }
    private static UserRegistrationRequest buildUserRegistration(){
        UserRegistrationRequest registrationRequest = new UserRegistrationRequest();
        registrationRequest.setFirstName("Favour");
        registrationRequest.setLastName("Chiemela");
        registrationRequest.setUsername("SexyFavour");
        registrationRequest.setPhoneNumber("0813773987");
        registrationRequest.setPassword("2233");
        registrationRequest.setEmail("favourChi@gmail.com");
        registrationRequest.setHouseNumber("No. 23");
        registrationRequest.setStreet("Akinola Street");
        registrationRequest.setLga("Abaji");
        registrationRequest.setState("FCT");
        return registrationRequest;
    }

    @Test
    void testUpdateAdminProfileById() throws AdminNotFoundException, UserNotFoundException {
        User user = new User();
        user.setFirstName("Femi");
        user.setLastName("Michael");
        user.setEmail("femi@gmail.com");
        user.setUsername("Femo02");
        user.setPhoneNumber("08134757992");

        User savedUser = userRepository.save(user);

        User updatedUser = new User();
        updatedUser.setFirstName("Femz");
        updatedUser.setEmail("femi@example.com");

        User returneduser = userService.updateUserProfileById(savedUser.getUserId(), updatedUser);

        assertNotNull(returneduser);
        assertEquals(savedUser.getUserId(), returneduser.getUserId());
        assertEquals(updatedUser.getFirstName(), returneduser.getFirstName());
        assertEquals(savedUser.getLastName(), returneduser.getLastName());
        assertEquals(updatedUser.getEmail(), returneduser.getEmail());
        assertEquals(savedUser.getUsername(), returneduser.getUsername());
        assertEquals(savedUser.getPhoneNumber(), returneduser.getPhoneNumber());
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
    void testToDeleteUserById()  {
        try {
            userService.registerNewUser(userRegistrationRequest);
        } catch (UserRegistrationException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        List<User> users = userService.findAllUsers();

        User lastRegisteredUser = users.get(users.size() - 1);
        try {
            userService.deleteUserById(lastRegisteredUser.getUserId());
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        users = userService.findAllUsers();

        assertEquals(0, users.size());
    }

    @Test
    void testToLoginUser() {
        UserLoginRequest registrationRequest = new UserLoginRequest();
        registrationRequest.setUserName("SexyFavour");
        registrationRequest.setPassword("2233");

        UserLoginResponse loginResponse = userService.loginUser(registrationRequest);
        assertEquals("You have logged in successfully", loginResponse.getMessage());
    }

    @Test
    void testUserCanReserveABook() throws BookNotFoundException, BookNotAvailableException {
        User user = new User();
        userRepository.save(user);

        Book book = new Book();
        book.setStatus(Status.AVAILABLE);
        bookRepository.save(book);

        Long userId = user.getUserId();
        Long bookId = book.getId();

        BookReservationResponse response = bookService.reserveBook(userId, bookId);

        assertNotNull(response);
        assertEquals("Book reserved successfully.", response.getMessage());

        Optional<Book> reservedBook = bookRepository.findById(bookId);
        assertTrue(reservedBook.isPresent());
        assertEquals(Status.RESERVED, reservedBook.get().getStatus());
        assertEquals(userId, reservedBook.get().getReservedBy());
        assertNotNull(reservedBook.get().getReservationTime());
    }
    @Test
    void testUserCanCheckoutBook() throws BookNotFoundException, BookNotAvailableException {
        Book book = new Book();
        book.setTitle("Sample Book");
        book.setAvailable(true);
        entityManager.persist(book);
        entityManager.flush();

        Long bookId = book.getId();
        BookCheckoutResponse response = userService.checkoutBook(bookId);

        assertNotNull(response);
        assertEquals(bookId, response.getBookId());
        assertEquals("Sample Book", response.getTitle());
    }


}