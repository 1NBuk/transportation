package org.example.transportation.controller;

import org.example.transportation.entity.Role;
import org.example.transportation.entity.User;
import org.example.transportation.repository.RoleRepository;
import org.example.transportation.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private Model model;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listUsers_ShouldReturnManageUsersTemplate() {

        List<User> users = new ArrayList<>();
        List<Role> roles = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(users);
        when(roleRepository.findAll()).thenReturn(roles);


        String viewName = userController.listUsers(model);


        assertEquals("manage_users", viewName);
        verify(userRepository, times(1)).findAll();
        verify(roleRepository, times(1)).findAll();
        verify(model, times(1)).addAttribute("users", users);
        verify(model, times(1)).addAttribute("allRoles", roles);
    }

    @Test
    void changeUserRole_ShouldRedirectToManageUsers() {

        Long userId = 1L;
        String newRoleName = "ROLE_ADMIN";

        User user = new User();
        user.setId(userId);
        Role role = new Role();
        role.setName(newRoleName);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(roleRepository.findByName(newRoleName)).thenReturn(role);


        String redirectUrl = userController.changeUserRole(userId, newRoleName);


        assertEquals("redirect:/manage_users", redirectUrl);
        verify(userRepository, times(1)).findById(userId);
        verify(roleRepository, times(1)).findByName(newRoleName);
        verify(userRepository, times(1)).save(user);
        assertEquals(1, user.getRoles().size());
        assertEquals(role, user.getRoles().iterator().next());
    }

    @Test
    void changeUserRole_ShouldThrowExceptionWhenUserNotFound() {

        Long userId = 1L;
        String newRoleName = "ROLE_ADMIN";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());


        try {
            userController.changeUserRole(userId, newRoleName);
        } catch (IllegalArgumentException e) {
            assertEquals("User not found with ID: " + userId, e.getMessage());
        }

        verify(userRepository, times(1)).findById(userId);
        verify(roleRepository, never()).findByName(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void changeUserRole_ShouldThrowExceptionWhenRoleNotFound() {

        Long userId = 1L;
        String newRoleName = "ROLE_NON_EXISTENT";

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(roleRepository.findByName(newRoleName)).thenReturn(null);


        try {
            userController.changeUserRole(userId, newRoleName);
        } catch (IllegalArgumentException e) {
            assertEquals("Role not found: " + newRoleName, e.getMessage());
        }

        verify(userRepository, times(1)).findById(userId);
        verify(roleRepository, times(1)).findByName(newRoleName);
        verify(userRepository, never()).save(any(User.class));
    }
}
