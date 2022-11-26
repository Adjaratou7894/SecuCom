package com.securite.secucom.Controller;

import com.securite.secucom.Model.Collaborateur;
import com.securite.secucom.Model.Role;
import com.securite.secucom.Repository.CollaborateurRepository;
import com.securite.secucom.Service.CollaboRoleService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
//@RequestMapping("/collaborateur")
public class CollaboRoleController {
    // coser est le sigle de colloroleService
    @Autowired
    private  CollaboRoleService coser;

    @Autowired
    CollaborateurRepository collaborateurRepository;

    private OAuth2AuthorizedClientService authorizedClientService;
    // une methode pour identifier pour le collaborateur qui est connecté.
    @PostMapping("/connexion/{username}/{password}")
    public String login(@PathVariable(value = "username") String username, @PathVariable(value = "password") String password){
        System.out.println(username);
        System.out.println(password);
        Collaborateur collaborateur = coser.getCollaborateurByUsernameAndPassword(username,password);

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || collaborateur == null) {
            return "username ou mot de passe incorrect.";
        }
        if (collaborateur != null) {
            Collaborateur collaborateur1 = collaborateurRepository.findByUsername(username);
           return "Bienvenue " + collaborateur1.getRoles().stream().map(Role::getNomrole).
                   collect(Collectors.toList()).toString().substring(1, collaborateur1.getRoles().stream().map(Role::getNomrole).
                           collect(Collectors.toList()).toString().length()-1);
        } else {
            return "Vous n'avez pas le droit de vous connecter.";
        }

    }

  // afficher la liste des utilisateurs

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
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping({"/modifier"})
    public String ModierUser(@PathVariable Long id, @RequestBody Collaborateur collaborateur){

        coser.Modifier(collaborateur,id);
        return "Modification reussie avec succès";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/Supprimer/{idCollaborateur}")
    public String Supprimer(@PathVariable("idCollaborateur") Long id_users){
        coser.Supprimer(id_users);
        return "Suppression reussie";
    }




    @RequestMapping("/*")
    public String getUserInfo(Principal user) {
        StringBuffer userInfo= new StringBuffer();
        if(user instanceof UsernamePasswordAuthenticationToken){
            userInfo.append(getUsernamePasswordLoginInfo(user));
        }
        else if(user instanceof OAuth2AuthenticationToken){
            userInfo.append(getOauth2LoginInfo(user));
        }
        return userInfo.toString();
    }

    @RequestMapping("/**")
    private StringBuffer getUsernamePasswordLoginInfo(Principal user)
    {
        StringBuffer usernameInfo = new StringBuffer();

        UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) user);
        if(token.isAuthenticated()){
            User u = (User) token.getPrincipal();
            usernameInfo.append("Welcome, " + u.getUsername());
        }
        else{
            usernameInfo.append("NA");
        }
        return usernameInfo;
    }
    private StringBuffer getOauth2LoginInfo(Principal user){

        StringBuffer protectedInfo = new StringBuffer();

        OAuth2AuthenticationToken authToken = ((OAuth2AuthenticationToken) user);
        OAuth2AuthorizedClient authClient = this.authorizedClientService.loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(), authToken.getName());
        if(authToken.isAuthenticated()){

            Map<String,Object> userAttributes = ((DefaultOAuth2User) authToken.getPrincipal()).getAttributes();

            String userToken = authClient.getAccessToken().getTokenValue();
            protectedInfo.append("Welcome, " + userAttributes.get("name")+"<br><br>");
            protectedInfo.append("e-mail: " + userAttributes.get("email")+"<br><br>");
            protectedInfo.append("Access Token: " + userToken+"<br><br>");

        }
        else{
            protectedInfo.append("NA");
        }
        return protectedInfo;
    }
    private OidcIdToken getIdToken(OAuth2User principal){
        if(principal instanceof DefaultOidcUser) {
            DefaultOidcUser oidcUser = (DefaultOidcUser)principal;
            return oidcUser.getIdToken();
        }
        return null;
    }
}
@Data
class RoleUserForm{
    private String username;
    private String nomrole;
}
