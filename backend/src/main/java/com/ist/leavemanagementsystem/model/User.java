package com.ist.leavemanagementsystem.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import jakarta.persistence.*;
import lombok.*;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users") // Added table name mapping
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255) // Added length from schema
    private String name;

    @Column(unique = true, nullable = false, length = 255) // Added length from schema
    private String email;

    @Column(nullable = false, length = 255) // Added password field from schema
    private String password;

    @Transient
    private String role;

    @Column(length = 512) // Added profile_picture field from schema
    private String profilePicture;

    public List<GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.getRole()));
    }
}
