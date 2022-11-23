package com.securite.secucom.Service;

import com.securite.secucom.Model.Collaborateur;
import com.securite.secucom.Model.Role;

import java.util.List;

public interface CollaboRoleService {
    Collaborateur ajouterCollaborateur(Collaborateur collaborateur);
    Role ajouterRole(Role role);
    void ajouterRoleUser(String username,String nomrole);
    Collaborateur recupererUserByUsername(String username);
    List<Collaborateur> listCollaborateur();
}
