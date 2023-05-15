package com.remidiousE.repositories;

import com.remidiousE.model.Address;
import com.remidiousE.model.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@DataJpaTest
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class}) // Exclude DataSourceAutoConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class AdminRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AddressRepository addressRepository;
    private Admin savedAdmin;

    @BeforeEach
    void setUp() {;
        Admin admin = builderTestForAdmin();
        savedAdmin = adminRepository.save(admin);

    }

    @Test
    void testSaveNewAdmin() {
        Admin admin = builderTestForAdmin();
        savedAdmin = adminRepository.save(admin);
        Admin foundAdmin = entityManager.find(Admin.class, savedAdmin.getId());
        assertEquals(admin.getEmail(), foundAdmin.getEmail());
    }

    private Admin builderTestForAdmin(){
        Admin admin = new Admin();
        Address address = new Address();
        admin.setFullName("Enefola Remigious");
        admin.setRole("Liberian");
        admin.setEmail("remigiousenefola@gmail.com");
        address.setHouseNumber("No 30");
        address.setStreet("Emily Akinola");
        address.setLocalGovernmentArea("Somolu");
        address.setState("Lagos");
//        admin.setAddress(address);
        return admin;
    }
    @Test
    void findAdminByAdminId() {
        Optional<Admin> foundAdmin = adminRepository.findById(savedAdmin.getId());
        assertNotNull(foundAdmin);
    }

    @Test
    void findAllAdmin() {
        Address address = new Address();
        address.setId(123L);
        addressRepository.save(address);

        Admin adminOne = new Admin();
//        adminOne.setAddress(address);
//        address.getAdmins().add(adminOne);
        adminRepository.save(adminOne);
//
//        Admin adminTwo = new Admin();
//        adminTwo.setAddress(address);
//        address.getAdmins().add(adminTwo);
//        adminRepository.save(adminTwo);

        List<Admin> findAllAdmin = adminRepository.findAll();
        assertEquals(1, findAllAdmin.size());
    }
//
//    @Test
//    void updateAdminByAdminId() {
//    }
//
//    @Test
//    void deleteAdminByAdminId() {
//    }
}