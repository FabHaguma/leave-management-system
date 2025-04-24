package com.ist.leavemanagementsystem.repository;

import com.ist.leavemanagementsystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    Role findById(long id);

    @Query("SELECT r FROM Role r")
    List<Role> findAll();

    @Query("SELECT r.name FROM Role r JOIN r.users u WHERE u.id = :userId")
    List<String> findRoleNamesByUserId(@Param("userId") Long userId);
}