package com.ist.leavemanagementsystem.controller;

import com.ist.leavemanagementsystem.dto.LeaveBalanceDto;
import com.ist.leavemanagementsystem.model.LeaveBalance;
import com.ist.leavemanagementsystem.model.User;
import com.ist.leavemanagementsystem.repository.UserRepository;
import com.ist.leavemanagementsystem.service.LeaveBalanceService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/leave-balances")
public class LeaveBalanceController {

    private final LeaveBalanceService leaveBalanceService;
    private final UserRepository userRepository;

    public LeaveBalanceController(LeaveBalanceService leaveBalanceService, UserRepository userRepository) {
        this.leaveBalanceService = leaveBalanceService;
        this.userRepository = userRepository;
    }

    // Get current user's leave balances
    @GetMapping("/me")
    public List<LeaveBalanceDto> getMyLeaveBalances(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        if (email == null)
            throw new RuntimeException("No email in OAuth2User");
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty())
            throw new RuntimeException("User not found for email: " + email);
        User user = userOpt.get();
        List<LeaveBalance> balances = leaveBalanceService.getUserLeaveBalances(user);
        // Update the mapping to use new fields and include leave type info
        return balances.stream().map(b -> new LeaveBalanceDto(
                b.getId(),
                b.getUser() != null ? b.getUser().getId() : null,
                b.getLeaveType() != null ? b.getLeaveType().getId() : null, // Use leaveType ID
                b.getLeaveType() != null ? b.getLeaveType().getName() : null, // Include leaveType Name
                b.getEntitlement(), // Use new field name
                b.getUsed(), // Use new field name
                b.getRemaining() // Include remaining balance
        )).toList();
    }

    // (Admin) Update leave balance for a user
    @PutMapping("/{userId}")
    public void updateLeaveBalance(@PathVariable Long userId, @RequestBody List<LeaveBalanceDto> balances) {
        leaveBalanceService.updateUserLeaveBalances(userId, balances);
    }
}