package com.securite.secucom;

import com.securite.secucom.Model.Collaborateur;
import com.securite.secucom.Model.Role;
import com.securite.secucom.Service.CollaboRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class SecuComApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecuComApplication.class, args);
        //System.out.println("Bienvenue  sur cher admin");
        //System.out.println("Bienvenue  sur cher user");

    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
   @Bean
    CommandLineRunner start(final CollaboRoleService collaboRoleService){
        return  args ->{
            collaboRoleService.ajouterRole(new Role(null,"ADMIN"));
            collaboRoleService.ajouterRole(new Role(null,"USER"));

            collaboRoleService.ajouterCollaborateur(new Collaborateur(null,"Hadja","adjarasira@gmail.com","haja","1234", new ArrayList<>()));
            collaboRoleService.ajouterCollaborateur(new Collaborateur(null,"Sira","adjarasira2@gmail.com","admin","1234", new ArrayList<>()));
            collaboRoleService.ajouterCollaborateur(new Collaborateur(null,"Khadidia","adjarasira3@gmail.com","User1","1234", new ArrayList<>()));
            collaboRoleService.ajouterCollaborateur(new Collaborateur(null,"Haoua","adjarasira4@gmail.com","User2","1234", new ArrayList<>()));
            collaboRoleService.ajouterCollaborateur(new Collaborateur(null,"Oumarou","adjarasira5@gmail.com","User3","1234", new ArrayList<>()));

            collaboRoleService.ajouterRoleUser("haja","ADMIN");
            collaboRoleService.ajouterRoleUser("haja","USER");
            collaboRoleService.ajouterRoleUser("admin","ADMIN");
            collaboRoleService.ajouterRoleUser("admin","USER");
            collaboRoleService.ajouterRoleUser("User1","USER");
            collaboRoleService.ajouterRoleUser("User2","USER");
            collaboRoleService.ajouterRoleUser("User3","USER");

        };
    }
}
