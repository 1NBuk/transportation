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
    @Column(nullable = true)  // Поле "URL изображения", необязательно
    private String imageUrl;

    @Column(nullable = true) // Новый столбец для ссылки ВКонтакте
    private String vkLink;

    // Конструктор по умолчанию
    public BlogPost() {
    }

    // Конструктор с параметрами
    public BlogPost(String title, String content, LocalDateTime createdDate, String createdBy, String imageUrl, String vkLink) {
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.imageUrl = (imageUrl == null || imageUrl.isBlank())
                ? "https://cdn1.ozone.ru/s3/multimedia-1-z/6980409107.jpg"
                : imageUrl;
        this.vkLink = vkLink;

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
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    // Геттеры и сеттеры
    public String getVkLink() {
        return vkLink;
    }

    public void setVkLink(String vkLink) {
        this.vkLink = vkLink;
    }
}
