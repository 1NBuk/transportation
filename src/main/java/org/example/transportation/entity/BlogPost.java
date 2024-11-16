package org.example.transportation.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Автоматическая генерация ID
    private Long id;

    @Column(nullable = false)  // Поле "Название записи", обязательное для заполнения
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")  // Поле "Текст записи", обязательное
    private String content;

    @Column(nullable = false)  // Поле "Дата опубликования", обязательное для заполнения
    private LocalDateTime createdDate;

    @Column(nullable = false)  // Поле "Кто добавил", обязательное
    private String createdBy;

    // Конструктор по умолчанию
    public BlogPost() {
    }

    // Конструктор с параметрами
    public BlogPost(String title, String content, LocalDateTime createdDate, String createdBy) {
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
