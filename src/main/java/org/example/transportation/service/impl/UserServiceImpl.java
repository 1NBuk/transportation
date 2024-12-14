package org.example.transportation.service.impl;

import org.example.transportation.dto.UserDto;
import org.example.transportation.entity.Role;
import org.example.transportation.entity.User;
import org.example.transportation.repository.RoleRepository;
import org.example.transportation.repository.UserRepository;
import org.example.transportation.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    // Заранее установленный admin пароль (можно вынести в настройки приложения)
    private final String ADMIN_PASSWORD = "aaa";

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Логика присвоения роли
        Role role;

        // Проверяем введённый "admin пароль"
        if (ADMIN_PASSWORD.equals(userDto.getAdminPassword())) {
            // Если пароль совпадает, назначаем роль администратора
            role = roleRepository.findByName("ROLE_ADMIN");
            if (role == null) {
                role = createRoleIfNotExist("ROLE_ADMIN");
            }
        } else {
            // Если пароль не введён или не совпадает, назначаем роль пользователя
            role = roleRepository.findByName("ROLE_USER");
            if (role == null) {
                role = createRoleIfNotExist("ROLE_USER");
            }
        }

        user.setRoles(Arrays.asList(role)); // Присваиваем пользователю соответствующую роль
        userRepository.save(user); // Сохраняем пользователя в БД
    }


    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private Role createRoleIfNotExist(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }
}
