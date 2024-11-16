package org.example.transportation.service;

import org.example.transportation.entity.BlogPost;
import org.example.transportation.repository.BlogPostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;

    public BlogPostService(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    public List<BlogPost> getAllPosts() {
        return blogPostRepository.findAll();  // Получаем все записи
    }

    public BlogPost saveBlogPost(BlogPost blogPost) {
        return blogPostRepository.save(blogPost);  // Сохраняем или обновляем запись
    }

    public BlogPost getPostById(Long id) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        return blogPost.orElseThrow(() -> new IllegalArgumentException("Invalid blog ID: " + id));  // Получаем запись по ID
    }

    public void deleteBlogPost(Long id) {
        blogPostRepository.deleteById(id);  // Удаляем запись по ID
    }
}
