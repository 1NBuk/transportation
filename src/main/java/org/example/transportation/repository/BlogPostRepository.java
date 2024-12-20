package org.example.transportation.repository;

import org.example.transportation.entity.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    // Здесь можно добавить дополнительные методы для поиска по каким-то критериям
}
