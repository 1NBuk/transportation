package org.example.transportation.controller;

import org.example.transportation.entity.BlogPost;
import org.example.transportation.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

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
    @GetMapping("/")
    public String homePage() {
        return "index_user"; // Убедитесь, что шаблон index.html существует
    }

    @GetMapping("/search")
    public String searchBlogPosts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {

        List<BlogPost> searchResults = blogPostService.searchBlogPosts(title, content, date);
        model.addAttribute("blogPosts", searchResults);
        return "user_blog";
    }
}
