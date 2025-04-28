package com.ist.leavemanagementsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles") // Added table name mapping
@Data // Added Lombok
@NoArgsConstructor // Added Lombok
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255) // Added length based on schema
    private String name; // "STAFF", "MANAGER", "ADMIN"
}
