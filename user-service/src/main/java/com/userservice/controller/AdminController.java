package com.userservice.controller;

import com.userservice.dto.DateRequestDto;
import com.userservice.dto.UserResponseDto;
import com.userservice.service.UserService;
import com.userservice.shared.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAll")
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/verifyUser/{userId}")
    public String verifyUser(@PathVariable Long userId) {
        return userService.changeStatusToActive(userId);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getByStatus/{status}")
    public List<UserResponseDto> getUsersByStatus(@PathVariable String status) {
        return userService.findByStatus(Status.valueOf(status));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/filterByDate")
    public List<UserResponseDto> filterUsersByDate(@RequestBody DateRequestDto dateDto) {
        return userService.findUserByTime(dateDto.getStartDate(), dateDto.getEndDate());
    }
}
