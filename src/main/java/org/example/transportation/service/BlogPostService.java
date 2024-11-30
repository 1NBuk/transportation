package org.example.transportation.service;

import org.example.transportation.entity.BlogPost;
import org.example.transportation.repository.BlogPostRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;

    public BlogPostService(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    // Получение всех записей блога
    public List<BlogPost> getAllPosts() {
        return blogPostRepository.findAll();
    }

    // Сохранение или обновление записи блога
    public BlogPost saveBlogPost(BlogPost blogPost) {
        return blogPostRepository.save(blogPost);
    }

    // Получение записи по ID
    public BlogPost getPostById(Long id) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        return blogPost.orElseThrow(() -> new IllegalArgumentException("Invalid blog ID: " + id));
    }

    // Удаление записи по ID
    public void deleteBlogPost(Long id) {
        blogPostRepository.deleteById(id);
    }

    // Поиск записей блога по заголовку, содержанию, дате и URL изображения
    public List<BlogPost> searchBlogPosts(String title, String content, LocalDate date, String imageUrl) {
        Specification<BlogPost> spec = Specification.where((root, query, criteriaBuilder) -> null);

        // Поиск по заголовку
        if (title != null && !title.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("title"), "%" + title + "%"));
        }

        // Поиск по содержимому
        if (content != null && !content.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("content"), "%" + content + "%"));
        }

        // Поиск по дате
        if (date != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("createdDate"), date));
        }

        // Поиск по URL изображения
        if (imageUrl != null && !imageUrl.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("imageUrl"), "%" + imageUrl + "%"));
        }

        return blogPostRepository.findAll(spec);
    }
}
