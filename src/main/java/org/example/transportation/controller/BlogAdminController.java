package org.example.transportation.controller;

import org.example.transportation.entity.BlogPost;
import org.example.transportation.service.BlogPostService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin_blog")
public class BlogAdminController {

    private final BlogPostService blogPostService;

    public BlogAdminController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    // Страница всех блогов для администратора
    @GetMapping
    @Secured("ROLE_ADMIN")
    public String viewAdminBlogPage(Model model) {
        model.addAttribute("blogPosts", blogPostService.getAllPosts());
        return "admin_blog";  // Шаблон для страницы админов
    }

    // Переход на страницу добавления блога
    @GetMapping("/new")
    @Secured("ROLE_ADMIN")
    public String showNewBlogForm(Model model) {
        model.addAttribute("blogPost", new BlogPost());
        return "add_blog";  // Шаблон для добавления блога
    }

    // Обработка формы добавления блога
    @PostMapping("/new")
    @Secured("ROLE_ADMIN")
    public String addNewBlog(@ModelAttribute BlogPost blogPost) {
        blogPost.setCreatedDate(LocalDateTime.now());
        blogPost.setCreatedBy("Admin");  // Это можно заменить на текущего пользователя
        blogPostService.saveBlogPost(blogPost);
        return "redirect:/admin_blog";
    }

    // Переход на страницу редактирования блога
    @GetMapping("/edit/{id}")
    @Secured("ROLE_ADMIN")
    public String editBlog(@PathVariable("id") Long id, Model model) {
        BlogPost blogPost = blogPostService.getPostById(id);
        model.addAttribute("blogPost", blogPost);
        return "edit_blog";  // Шаблон для редактирования блога
    }

    // Обработка формы редактирования блога
    @PostMapping("/edit/{id}")
    @Secured("ROLE_ADMIN")
    public String updateBlog(@PathVariable("id") Long id, @ModelAttribute BlogPost blogPost) {
        blogPost.setId(id);
        blogPost.setCreatedDate(LocalDateTime.now());  // Обновление даты публикации
        blogPost.setCreatedBy("Admin");
        blogPostService.saveBlogPost(blogPost);
        return "redirect:/admin_blog";
    }

    // Удаление блога
    @GetMapping("/delete/{id}")
    @Secured("ROLE_ADMIN")
    public String deleteBlog(@PathVariable("id") Long id) {
        blogPostService.deleteBlogPost(id);
        return "redirect:/admin_blog";
    }
    @GetMapping("/")
    public String homePage() {
        return "index"; // Убедитесь, что шаблон index.html существует
    }

    @GetMapping("/search")
    public String searchBlogPosts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {

        List<BlogPost> searchResults = blogPostService.searchBlogPosts(title, content, date);
        model.addAttribute("blogPosts", searchResults);
        return "admin_blog";
    }
}
