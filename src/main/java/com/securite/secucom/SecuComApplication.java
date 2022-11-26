package com.securite.secucom;

import com.securite.secucom.Model.Collaborateur;
import com.securite.secucom.Model.Role;
import com.securite.secucom.Service.CollaboRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class SecuComApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecuComApplication.class, args);
        //System.out.println("Bienvenue  sur cher admin");
        //System.out.println("Bienvenue  sur cher user");

    }
    @RestController
    class ELKController {
        private static final Logger LOG = Logger.getLogger(ELKController.class.getName());

        @Autowired
        RestTemplate restTemplete;

        @Bean
        RestTemplate restTemplate() {
            return new RestTemplate();
        }

        @RequestMapping(value = "/elkdemo")
        public String helloWorld() {
            String response = "Hello user ! " + new Date();
            LOG.log(Level.INFO, "/elkdemo - &gt; " + response);

            return response;
        }

        @RequestMapping(value = "/elk")
        public String helloWorld1() {

            String response = (String) restTemplete.exchange("http://localhost:8080/elkdemo", HttpMethod.GET, null, new ParameterizedTypeReference() {
            }).getBody();
            LOG.log(Level.INFO, "/elk - &gt; " + response);

            try {
                String exceptionrsp = (String) restTemplete.exchange("http://localhost:8080/exception", HttpMethod.GET, null, new ParameterizedTypeReference() {
                }).getBody();
                LOG.log(Level.INFO, "/elk trying to print exception - &gt; " + exceptionrsp);
                response = response + " === " + exceptionrsp;
            } catch (Exception e) {
                // exception should not reach here. Really bad practice :)
            }

            return response;
        }

        @RequestMapping(value = "/exception")
        public String exception() {
            String rsp = "";
            try {
                int i = 1 / 0;
                // should get exception
            } catch (Exception e) {
                e.printStackTrace();
               // LOG.error(e);

                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String sStackTrace = sw.toString(); // stack trace as a string
              //  LOG.error("Exception As String :: - &gt; "+sStackTrace);

                rsp = sStackTrace;
            }

            return rsp;
        }
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
