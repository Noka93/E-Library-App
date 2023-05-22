package com.remidiousE.repositories;

import com.remidiousE.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long > {
//    Admin saveAdmin(Admin admin);
//    List<Admin> findAllAdmin();
//    void deleteAdminById(Long id);
//    Admin findAdminById(Long id);
}
