package com.ist.leavemanagementsystem.repository;

import com.ist.leavemanagementsystem.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByUserId(Long userId);

    List<LeaveRequest> findByStatus(String status);

    List<LeaveRequest> findByUserIdAndStatus(Long userId, String status);

    @Query("SELECT lr FROM LeaveRequest lr WHERE lr.userId = :userId ORDER BY lr.createdAt DESC")
    LeaveRequest findTopByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId);

    @Query("SELECT lr FROM LeaveRequest lr WHERE lr.userId = :userId ORDER BY lr.updatedAt DESC")
    LeaveRequest findTopByUserIdOrderByUpdatedAtDesc(@Param("userId") Long userId);

}
