package org.example.transportation.service.impl;

import org.example.transportation.dto.UserDto;
import org.example.transportation.entity.Role;
import org.example.transportation.entity.User;
import org.example.transportation.repository.RoleRepository;
import org.example.transportation.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private final String ADMIN_PASSWORD = "aaa";

    @Test
    void saveUser_shouldAssignAdminRole_whenAdminPasswordIsCorrect() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("admin@example.com");
        userDto.setPassword("password");
        userDto.setAdminPassword(ADMIN_PASSWORD);

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");

        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(adminRole);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // Act
        userService.saveUser(userDto);

        // Assert
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertThat(savedUser.getRoles()).hasSize(1);
        assertThat(savedUser.getRoles().get(0).getName()).isEqualTo("ROLE_ADMIN");
        assertThat(savedUser.getPassword()).isEqualTo("encodedPassword");
    }

    @Test
    void saveUser_shouldAssignUserRole_whenAdminPasswordIsIncorrect() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setFirstName("Jane");
        userDto.setLastName("Smith");
        userDto.setEmail("user@example.com");
        userDto.setPassword("password");
        userDto.setAdminPassword("wrongPassword");

        Role userRole = new Role();
        userRole.setName("ROLE_USER");

        when(roleRepository.findByName("ROLE_USER")).thenReturn(userRole);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // Act
        userService.saveUser(userDto);

        // Assert
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertThat(savedUser.getRoles()).hasSize(1);
        assertThat(savedUser.getRoles().get(0).getName()).isEqualTo("ROLE_USER");
        assertThat(savedUser.getPassword()).isEqualTo("encodedPassword");
    }

    @Test
    void findUserByEmail_shouldReturnUser_whenUserExists() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        // Act
        User foundUser = userService.findUserByEmail("test@example.com");

        // Assert
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void findUserByEmail_shouldReturnNull_whenUserDoesNotExist() {
        // Arrange
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(null);

        // Act
        User foundUser = userService.findUserByEmail("nonexistent@example.com");

        // Assert
        assertThat(foundUser).isNull();
    }
}
