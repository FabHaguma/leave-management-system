package com.ist.leavemanagementsystem.repository;

import com.ist.leavemanagementsystem.model.Role;
import com.ist.leavemanagementsystem.model.User;
import com.ist.leavemanagementsystem.model.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    // This method retrieves all UserRole entities from the database.
    @Query("SELECT ur FROM UserRole ur")
    List<UserRole> findAll();

    // This method retrieves all roles associated with a specific user by their ID.
    @Query("SELECT r FROM Role r JOIN r.users u WHERE u.id = :userId")
    List<Role> findRolesByUserId(@Param("userId") Long userId);

    // This method retrieves all users associated with a specific role by its ID.
    // It uses a JOIN query to fetch users based on the role ID.
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.id = :roleId")
    List<User> findUsersByRoleId(@Param("roleId") Long roleId);

    // This method retrieves a UserRole entity based on the user and role objects.
    // It uses a JOIN query to find the UserRole that matches the given user and
    // role.
    @Query("SELECT ur FROM UserRole ur WHERE ur.user = :user AND ur.role = :role")
    UserRole findByUserAndRole(@Param("user") User user, @Param("role") Role role);
}
