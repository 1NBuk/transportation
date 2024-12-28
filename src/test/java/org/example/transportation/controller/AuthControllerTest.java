package org.example.transportation.controller;

import org.example.transportation.dto.UserDto;
import org.example.transportation.entity.User;
import org.example.transportation.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void redirectAfterLogin_UnknownRole() {

        when(authentication.getAuthorities()).thenReturn(Collections.emptyList());


        String result = authController.redirectAfterLogin(authentication);


        assertEquals("redirect:/login", result);
    }



    @Test
    void login_NotAuthenticated() {

        when(authentication).thenReturn(null);


        String result = authController.login(authentication, model);


        assertEquals("login", result);
        verify(model, times(1)).addAttribute(eq("user"), any(UserDto.class));
    }


    @Test
    void saveUser_ValidUser() {

        UserDto userDto = new UserDto();
        userDto.setEmail("newuser@example.com");

        when(userService.findUserByEmail("newuser@example.com")).thenReturn(null);
        when(bindingResult.hasErrors()).thenReturn(false);


        String result = authController.saveUser(userDto, bindingResult, model);


        assertEquals("redirect:/login?success", result);
        verify(userService, times(1)).saveUser(userDto);
    }

    @Test
    void saveUser_InvalidForm() {

        UserDto userDto = new UserDto();
        userDto.setEmail("invalid@example.com");

        when(bindingResult.hasErrors()).thenReturn(true);


        String result = authController.saveUser(userDto, bindingResult, model);


        assertEquals("register", result);
        verify(model, times(1)).addAttribute("user", userDto);
        verify(userService, never()).saveUser(any(UserDto.class));
    }
}
