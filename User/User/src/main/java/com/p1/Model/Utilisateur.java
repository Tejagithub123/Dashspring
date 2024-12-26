package com.p1.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
@EqualsAndHashCode
public class Utilisateur implements UserDetails {

    public enum Role {
        ROLE_ADMIN, ROLE_PERSONNEL, ROLE_ETUDIANT, ROLE_AGENT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String cin;

    private String nom;
    private String prenom;

    @Column(unique = true, nullable = false)
    private String email;

    @Temporal(TemporalType.DATE)
    private Date dateNaissance;

    private String mdp;

    @Enumerated(EnumType.STRING)
    @Column(insertable = false, updatable = false) // Managed by DiscriminatorColumn
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return mdp;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
