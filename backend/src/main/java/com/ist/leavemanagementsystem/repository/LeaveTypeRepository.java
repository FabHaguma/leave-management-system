package com.ist.leavemanagementsystem.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ist.leavemanagementsystem.model.LeaveType;
public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long>{
}
