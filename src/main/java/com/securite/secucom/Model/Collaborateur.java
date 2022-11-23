package com.securite.secucom.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;


@Data
@Entity
@Table(name = "collaborateur")
@NoArgsConstructor
@AllArgsConstructor
public class Collaborateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_collaborateur", nullable = false)
    private Long idCollaborateur;
    private String nom;
    private String email;
    private String username;
    private String password;
   @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
}
