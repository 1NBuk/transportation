package org.example.transportation.controller;

import org.example.transportation.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user_blog")
public class BlogUserController {

    @Autowired
    private BlogPostService blogPostService;

    // Главная страница пользовательского блога
    @GetMapping
    public String viewUserBlogPage(Model model) {
        model.addAttribute("blogPosts", blogPostService.getAllPosts());
        return "user_blog";
    }
}
