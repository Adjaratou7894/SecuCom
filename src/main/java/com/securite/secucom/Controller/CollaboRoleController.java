package com.securite.secucom.Controller;

import com.securite.secucom.Model.Collaborateur;
import com.securite.secucom.Model.Role;
import com.securite.secucom.Service.CollaboRoleService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/collaborateur")
public class CollaboRoleController {
    // coser est le sigle de colloroleService
    @Autowired
    private  CollaboRoleService coser;
    @GetMapping("/liste")
    public List<Collaborateur>collaborateurs(){
        return  coser.listCollaborateur();
    }
    @PostMapping("/ajouter")
     public Collaborateur ajouterUser(@RequestBody Collaborateur collaborateur){
        return coser.ajouterCollaborateur(collaborateur);
    }
    @PostMapping("/ajouterrole")
    public Role ajouterRole( @RequestBody Role role){
        return coser.ajouterRole(role);
    }
    @PostMapping("/ajouteroleUser")
    public void ajouterRoleUser(@RequestBody RoleUserForm roleUserForm){
         coser.ajouterRoleUser(roleUserForm.getUsername(),roleUserForm.getNomrole());
    }

}
@Data
class RoleUserForm{
    private String username;
    private String nomrole;
}
