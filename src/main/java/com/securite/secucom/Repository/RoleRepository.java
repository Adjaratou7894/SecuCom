package com.securite.secucom.Repository;

import com.securite.secucom.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByNomrole(String nomrole);
}
