package com.remidiousE.controller;

import com.remidiousE.Exceptions.BookNotFoundException;
import com.remidiousE.Exceptions.UserNotFoundException;
import com.remidiousE.Exceptions.UserRegistrationException;
import com.remidiousE.dto.request.UserRegistrationRequest;
import com.remidiousE.dto.response.UserRegistrationResponse;
import com.remidiousE.model.Book;
import com.remidiousE.model.User;
import com.remidiousE.service.BookService;
import com.remidiousE.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @PostMapping("/create-user")
    public UserRegistrationResponse registerNewUser(@RequestBody UserRegistrationRequest userRegistrationRequest) throws UserRegistrationException {
        return userService.registerNewUser(userRegistrationRequest);
    }

    @GetMapping("/find-user/{id}")
    public Optional<User> findUserById(@PathVariable("id") Long userId) throws UserNotFoundException {
        return userService.findUserById(userId);
    }

    @GetMapping("/get-users")
    public List<User> findAllUser() {
        return userService.findAllUsers();
    }

    @GetMapping("/user-find-book/{title}")
    public List<Book> searchBookByTitle(@PathVariable("title") String title) throws BookNotFoundException {
        return bookService.searchBookByTitle(title);
    }
    @PutMapping("/update-user/{id}")
    public User updateUserById(@PathVariable("id") Long userId, @RequestBody User user) throws UserNotFoundException {
        return userService.updateUserProfileById(userId, user);
    }

    @DeleteMapping("/delete-user/{id}")
    public String deleteUserById(@PathVariable("id") Long userId) throws UserNotFoundException {
        userService.deleteUserById(userId);
        return "User has been deleted successfully";
    }
}
