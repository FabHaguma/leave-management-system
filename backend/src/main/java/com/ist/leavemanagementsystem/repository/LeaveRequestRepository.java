package com.ist.leavemanagementsystem.repository;

import com.ist.leavemanagementsystem.model.LeaveRequest;
import com.ist.leavemanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByUser(User user);
}
