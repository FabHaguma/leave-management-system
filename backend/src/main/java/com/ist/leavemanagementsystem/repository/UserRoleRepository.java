package com.ist.leavemanagementsystem.repository;

import com.ist.leavemanagementsystem.model.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    // This method retrieves all UserRole entities from the database.
    @Query("SELECT ur FROM UserRole ur")
    List<UserRole> findAll();

}
