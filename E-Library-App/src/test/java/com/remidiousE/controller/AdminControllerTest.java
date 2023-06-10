package com.remidiousE.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.remidiousE.dto.request.AdminLoginRequest;
import com.remidiousE.dto.request.AdminRegistrationRequest;
import com.remidiousE.dto.response.AdminLoginResponse;
import com.remidiousE.dto.response.AdminRegistrationResponse;
import com.remidiousE.model.Admin;
import com.remidiousE.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    private AdminRegistrationRequest adminRegistrationRequest;

    @BeforeEach
    void setUp() {
        adminRegistrationRequest = AdminRegistrationRequest.builder()
                .firstName("Remigious")
                .lastName("Enefola")
                .email("remy@gmail.com")
                .username("Remy02")
                .role("Libarian")
                .phoneNumber("098133738890")
                .houseNumber("10")
                .street("Ayobo")
                .lga("Alawo")
                .state("Lagos")
                .build();
    }

    @Test
    public void testRegisterNewAdmin() throws Exception {
        AdminRegistrationRequest adminRegistrationRequest = AdminRegistrationRequest.builder()
                .firstName("Remigious")
                .lastName("Enefola")
                .email("remy@gmail.com")
                .username("Remy02")
                .role("Libarian")
                .phoneNumber("098133738890")
                .houseNumber("10")
                .street("Ayobo")
                .lga("Alawo")
                .state("Lagos")
                .build();

        AdminRegistrationResponse adminRegistrationResponse = new AdminRegistrationResponse();
        adminRegistrationResponse.setMessage("You have successfully registered");

        Mockito.when(adminService.registerNewAdmin(adminRegistrationRequest)).thenReturn(adminRegistrationResponse);


        mockMvc.perform(post("/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\":\"Remidious\",\n" +
                                "    \"lastName\":\"Enefola\",\n" +
                                "    \"userName\":\"Remy02\",\n" +
                                "    \"password\":\"2234\",\n" +
                                "    \"email\":\"remyenefola@gmail.com\",\n" +
                                "    \"phoneNumber\":\"0813456\",\n" +
                                "    \"role\":\"Librarian\",\n" +
                                "    \"houseNumber\":\"10\",\n" +
                                "    \"street\":\"Ayobo\",\n" +
                                "    \"lga\":\"Akoko\",\n" +
                                "    \"state\":\"FCT\"\n" +
                                "}"))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/admin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(adminRegistrationResponse.getMessage()));
    }
    @Test
    public void testFindAdminById() throws Exception {
        Long adminId = 1L;
        Admin admin = new Admin();
        adminService.findAdminById(adminId);

        Mockito.when(adminService.findAdminById(adminId)).thenReturn(Optional.of(admin));

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/{id}", adminId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(admin.getAdminId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(admin.getUsername()));
    }

    @Test
    public void testFindAllAdmin() throws Exception {
        AdminRegistrationRequest admin1 = new AdminRegistrationRequest();
        AdminRegistrationRequest admin2 = new AdminRegistrationRequest();
        adminService.registerNewAdmin(admin1);
        adminService.registerNewAdmin(admin2);

        List<Admin> admins = adminService.findAllAdmin();

        Mockito.when(adminService.findAllAdmin()).thenReturn(admins);

        mockMvc.perform(MockMvcRequestBuilders.get("/admins"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].username").value(admin1.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].username").value(admin2.getUsername()));
    }

    @Test
    public void testLoginAdmin() throws Exception {
        String login = "admin";
        AdminLoginRequest request = new AdminLoginRequest();
        AdminLoginResponse response = new AdminLoginResponse();

        Mockito.when(adminService.loginAdmin(request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/{login}", login)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(response.getMessage()));
    }




    }