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
import org.springframework.web.bind.annotation.PathVariable;

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
        return "user_blog"; // Убедитесь, что шаблон user_blog.html существует
    }

    @GetMapping("/")
    public String homePage() {
        return "index_user"; // Убедитесь, что шаблон index_user.html существует
    }

    @GetMapping("/search")
    public String searchBlogPosts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String createdBy, // Добавленный параметр
            Model model) {

        // Вызов метода с правильным количеством параметров
        List<BlogPost> searchResults = blogPostService.searchBlogPosts(title, content, date, createdBy);
        model.addAttribute("blogPosts", searchResults);
        return "user_blog";
    }

    // Просмотр полного текста блога
    @GetMapping("/{id}")
    public String viewBlogDetails(@PathVariable("id") Long id, Model model) {
        BlogPost blogPost = blogPostService.getPostById(id);
        model.addAttribute("blogPost", blogPost);
        return "blog_details";
    }

}
