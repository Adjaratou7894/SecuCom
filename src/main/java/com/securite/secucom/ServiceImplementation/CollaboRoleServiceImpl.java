package com.securite.secucom.ServiceImplementation;

import com.securite.secucom.Model.Collaborateur;
import com.securite.secucom.Model.Role;
import com.securite.secucom.Repository.CollaborateurRepository;
import com.securite.secucom.Repository.RoleRepository;
import com.securite.secucom.Service.CollaboRoleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
@AllArgsConstructor

public class CollaboRoleServiceImpl implements CollaboRoleService {
    // corepos est le sigle collaborateurRepository

    private CollaborateurRepository corepos;
    // rolepos est le sigle roleRepository

    private RoleRepository rolerepos;
    private PasswordEncoder passwordEncoder;


    @Override
    public Collaborateur ajouterCollaborateur(Collaborateur collaborateur) {
        String pw=collaborateur.getPassword();
        collaborateur.setPassword(passwordEncoder.encode(pw));

        return corepos.save(collaborateur);
    }

    @Override
    public Role ajouterRole(Role role) {
        return rolerepos.save(role);
    }

    @Override
    public void ajouterRoleUser(String username, String nomrole) {
        Collaborateur collaborateur = corepos.findByUsername(username);
        Role role =rolerepos.findByNomrole(nomrole);
        collaborateur.getRoles().add(role);

    }

    @Override
    public Collaborateur loadUserByUsername(String username) {
        return corepos.findByUsername(username);
    }

    @Override
    public List<Collaborateur> listCollaborateur() {
        return corepos.findAll() ;
    }
}
