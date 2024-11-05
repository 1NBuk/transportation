package org.example.transportation.controller;

import jakarta.validation.Valid;
import org.example.transportation.dto.UserDto;
import org.example.transportation.entity.User;
import org.example.transportation.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import java.util.List;

@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/post-login")
    public String redirectAfterLogin(Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "redirect:/index"; // Админ перенаправляется на /index
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            return "redirect:/index_user"; // Пользователь перенаправляется на /index_user
        }
        return "redirect:/login"; // Если роль неизвестна, перенаправление на логин
    }
    // handler method to handle home page request
    @GetMapping("/")
    public String home(){
        return "/";
    }

    // handler method to handle login request
    @GetMapping("/login")
    public String login() {
        return "login"; // отображение формы логина
    }

    // после логина перенаправляем на главную страницу с грузами
    // после логина перенаправляем на главную страницу с грузами
    @PostMapping("/login-success")
    public String loginSuccess() {
        // Получаем имя текущего пользователя из контекста безопасности
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Находим пользователя по его email (username)
        User currentUser = userService.findUserByEmail(username);

        if (currentUser != null) {
            // Проверяем роли пользователя
            boolean isAdmin = currentUser.getRoles().stream()
                    .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
            boolean isUser = currentUser.getRoles().stream()
                    .anyMatch(role -> role.getName().equals("ROLE_USER"));

            // Перенаправляем в зависимости от роли
            if (isAdmin) {
                return "redirect:/index"; // Админ перенаправляется на страницу index
            } else if (isUser) {
                return "redirect:/index_user"; // Обычный пользователь перенаправляется на страницу index_user
            }
        }

        // Если пользователь не найден или не имеет ролей, перенаправляем на страницу логина
        return "redirect:/login";
    }



    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }


}
