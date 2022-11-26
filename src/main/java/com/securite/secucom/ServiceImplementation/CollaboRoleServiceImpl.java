package com.securite.secucom.ServiceImplementation;

import com.securite.secucom.Model.Collaborateur;
import com.securite.secucom.Model.Role;
import com.securite.secucom.Repository.CollaborateurRepository;
import com.securite.secucom.Repository.RoleRepository;
import com.securite.secucom.Service.CollaboRoleService;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ConcreteTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @Override
    public String Supprimer(Long idCollaborateur) {
        corepos.deleteById(idCollaborateur);
        return "Supprimer avec succes";
    }

    @Override
    public Collaborateur getCollaborateurByUsernameAndPassword(String username, String password) {
        Collaborateur collaborateur = corepos.findByUsername(username);
        if (collaborateur != null) {
            if (passwordEncoder().matches(password, collaborateur.getPassword())) {
                return collaborateur;
            }
        }
        return null;

    }

    // Modification un utilisateur
    @Override
    public String Modifier(Collaborateur collaborateur, Long id) {
        return corepos.findById(id).map(
                col->{
                    col.setEmail(collaborateur.getEmail());
                    col.setUsername(collaborateur.getUsername());
                    col.setPassword(passwordEncoder.encode(collaborateur.getPassword()));

                    corepos.save(col);
                    return "Modification reussie avec succès";
                }
        ).orElseThrow(() -> new RuntimeException("Cet utilisateur n'existe pas"));

    }

    //Pour obtenir / rechercher un compte via son mail et son mdp, utillisée surtout pour notre SuperAdmin et le login


    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
