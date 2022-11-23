package com.securite.secucom.Repository;

import com.securite.secucom.Model.Collaborateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollaborateurRepository extends JpaRepository<Collaborateur,Long> {
    Collaborateur findByUsername(String username);
}
