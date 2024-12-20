package org.example.transportation.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.transportation.entity.BlogPost;
import org.example.transportation.service.BlogPostService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

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
    public String viewAdminBlogPage(
            @RequestParam(value = "lang", required = false) String lang,
            Model model) {
        if (lang != null) {
            model.addAttribute("lang", lang);
        }
        model.addAttribute("blogPosts", blogPostService.getAllPosts());
        return "admin_blog"; // Шаблон для страницы админов
    }

    // Переход на страницу добавления блога
    @GetMapping("/new")
    @Secured("ROLE_ADMIN")
    public String showNewBlogForm(
            @RequestParam(value = "lang", required = false) String lang,
            Model model) {
        if (lang != null) {
            model.addAttribute("lang", lang);
        }
        model.addAttribute("blogPost", new BlogPost());
        return "add_blog"; // Шаблон для добавления блога
    }

    // Обработка формы добавления блога
    @PostMapping("/new")
    @Secured("ROLE_ADMIN")
    public String addNewBlog(@ModelAttribute BlogPost blogPost) {
        blogPost.setCreatedDate(LocalDateTime.now());
        blogPost.setCreatedBy("Admin"); // Это можно заменить на текущего пользователя
        blogPostService.saveBlogPost(blogPost);
        return "redirect:/admin_blog";
    }

    // Переход на страницу редактирования блога
    @GetMapping("/edit/{id}")
    @Secured("ROLE_ADMIN")
    public String editBlog(
            @PathVariable("id") Long id,
            @RequestParam(value = "lang", required = false) String lang,
            Model model) {
        if (lang != null) {
            model.addAttribute("lang", lang);
        }
        BlogPost blogPost = blogPostService.getPostById(id);
        model.addAttribute("blogPost", blogPost);
        return "edit_blog"; // Шаблон для редактирования блога
    }

    // Обработка формы редактирования блога
    @PostMapping("/edit/{id}")
    @Secured("ROLE_ADMIN")
    public String updateBlog(
            @PathVariable("id") Long id,
            @ModelAttribute BlogPost blogPost) {
        blogPost.setId(id);
        blogPost.setCreatedDate(LocalDateTime.now()); // Обновление даты публикации
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

    // Главная страница
    @GetMapping("/")
    public String homePage(
            @RequestParam(value = "lang", required = false) String lang,
            Model model) {
        if (lang != null) {
            model.addAttribute("lang", lang);
        }
        return "index"; // Убедитесь, что шаблон index.html существует
    }

    // Поиск записей блога
    @GetMapping("/search")
    @Secured("ROLE_ADMIN")
    public String searchBlogPosts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String imageUrl,
            @RequestParam(value = "lang", required = false) String lang,
            Model model) {

        if (lang != null) {
            model.addAttribute("lang", lang);
        }
        List<BlogPost> searchResults = blogPostService.searchBlogPosts(title, content, date, imageUrl);
        model.addAttribute("blogPosts", searchResults);
        return "admin_blog"; // Возвращает страницу администратора с результатами поиска
    }

    // Просмотр полного текста блога
    @GetMapping("/{id}")
    public String viewBlogDetails(
            @PathVariable("id") Long id,
            @RequestParam(value = "lang", required = false) String lang,
            Model model) {
        if (lang != null) {
            model.addAttribute("lang", lang);
        }
        BlogPost blogPost = blogPostService.getPostById(id);
        model.addAttribute("blogPost", blogPost);
        return "blog_details";
    }
    @GetMapping("/admin_blog/new")
    public String changeLanguage(@RequestParam("lang") String lang, HttpServletRequest request) {
        Locale locale = new Locale(lang);
        LocaleContextHolder.setLocale(locale);
        return "redirect:/somePage"; // Перенаправление на нужную страницу
    }

}
