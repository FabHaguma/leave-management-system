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

    // TODO: Remove default user logic when re-enabling auth
    private static final String DEFAULT_DEV_EMAIL = "test.staff@ist.com"; // Example default user email

    public LeaveBalanceController(LeaveBalanceService leaveBalanceService, UserRepository userRepository) {
        this.leaveBalanceService = leaveBalanceService;
        this.userRepository = userRepository;
    }

    // Get current user's leave balances
    @GetMapping("/me")
    public List<LeaveBalanceDto> getMyLeaveBalances(@AuthenticationPrincipal OAuth2User principal) {
        String email;
        if (principal != null) {
            email = principal.getAttribute("email");
            if (email == null)
                throw new RuntimeException("No email in OAuth2User");
        } else {
            // TODO: Remove this else block when re-enabling auth
            // In dev mode without auth, use a default user
            email = DEFAULT_DEV_EMAIL;
            System.out.println("WARN: No authenticated principal found, using default dev user: " + email);
        }

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            // Optionally create the default user if they don't exist in the DB yet for
            // testing
            // For now, just throw an exception or return empty list
            throw new RuntimeException(
                    "User not found for email: " + email + ". Ensure the default dev user exists in the database.");
            // return List.of();
        }
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

    // (Admin) Update leave balance for a user - This endpoint might need role
    // checks later
    @PutMapping("/{userId}")
    // @PreAuthorize("hasRole('ADMIN')") // TODO: Re-enable authorization later
    public void updateLeaveBalance(@PathVariable Long userId, @RequestBody List<LeaveBalanceDto> balances) {
        // Consider adding logging here to know which user's balance is being updated
        System.out.println("INFO: Updating balances for user ID: " + userId);
        // Assuming leaveBalanceService.updateUserLeaveBalances exists and handles the
        // logic
        leaveBalanceService.updateUserLeaveBalances(userId, balances);
    }
}