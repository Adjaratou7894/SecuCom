package com.securite.secucom.Security;

import com.securite.secucom.Filter.JwtAuthenticationFilter;
import com.securite.secucom.Filter.JwtAuthorizationFilter;
import com.securite.secucom.Model.Collaborateur;
import com.securite.secucom.Service.CollaboRoleService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity
@Data
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CollaboRoleService collaboRoleService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Collaborateur collaborateur =collaboRoleService.loadUserByUsername(username);
                Collection<GrantedAuthority> authorities =new ArrayList<>();
                collaborateur.getRoles().forEach(r->{
                    authorities.add(new SimpleGrantedAuthority(r.getNomrole()));
                } );
                return new User(collaborateur.getUsername(),collaborateur.getPassword(),authorities);
            }
        });


    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       //http.csrf().disable();
       //pour ne pa utiliser les sessions coté serveur
       http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        /*http.headers().frameOptions().disable();*/
        //http.formLogin();
        http.cors().and().csrf().disable();

       http.authorizeRequests().anyRequest().authenticated();
       http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean()));
       http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}
