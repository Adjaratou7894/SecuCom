package com.securite.secucom.Service;

import com.securite.secucom.Model.Collaborateur;
import com.securite.secucom.Model.Role;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface CollaboRoleService {
    Collaborateur ajouterCollaborateur(Collaborateur collaborateur);
    Role ajouterRole(Role role);
    String Supprimer(Long idCollaborateur);  // LA METHODE PERMETTANT DE SUPPRIMER UN COLLABORATEUR
    //pour trouver un compte via son mail et password
    Collaborateur getCollaborateurByUsernameAndPassword(String username, String password);

    String Modifier(Collaborateur collaborateur, Long id);   // LA METHODE PERMETTANT DE MODIFIER UN COLLABORATEUR
    void ajouterRoleUser(String username,String nomrole);
    Collaborateur loadUserByUsername(String username);
    List<Collaborateur> listCollaborateur();
}
