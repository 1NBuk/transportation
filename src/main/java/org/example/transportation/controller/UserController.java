package org.example.transportation.controller;

import org.example.transportation.entity.Role;
import org.example.transportation.entity.User;
import org.example.transportation.repository.RoleRepository;
import org.example.transportation.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/manage_users")
    public String listUsers(Model model) {
        // Получаем всех пользователей из базы данных
        List<User> users = userRepository.findAll();
        List<Role> allRoles = roleRepository.findAll(); // Все доступные роли
        model.addAttribute("users", users);
        model.addAttribute("allRoles", allRoles); // Передаем роли в шаблон
        return "manage_users";
    }

    @PostMapping("/users/changeRole")
    public String changeUserRole(@RequestParam Long userId, @RequestParam String newRole) {
        // Найти пользователя
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User not found with ID: " + userId));

        // Найти роль
        Role role = roleRepository.findByName(newRole);
        if (role == null) {
            throw new IllegalArgumentException("Role not found: " + newRole);
        }

        // Установить новую роль
        user.getRoles().clear();
        user.getRoles().add(role);

        // Сохранить изменения
        userRepository.save(user);

        return "redirect:/manage_users";
    }

}
