package org.example.transportation.service;

import org.example.transportation.entity.BlogPost;
import org.example.transportation.repository.BlogPostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogPostServiceTest {

    @Mock
    private BlogPostRepository blogPostRepository;

    @InjectMocks
    private BlogPostService blogPostService;

    @Test
    void getAllPosts_shouldReturnAllPosts() {
        // Arrange
        BlogPost post1 = new BlogPost();
        BlogPost post2 = new BlogPost();
        when(blogPostRepository.findAll()).thenReturn(Arrays.asList(post1, post2));

        // Act
        List<BlogPost> posts = blogPostService.getAllPosts();

        // Assert
        assertThat(posts).hasSize(2);
        verify(blogPostRepository, times(1)).findAll();
    }

    @Test
    void saveBlogPost_shouldSaveAndReturnPost() {
        // Arrange
        BlogPost post = new BlogPost();
        when(blogPostRepository.save(post)).thenReturn(post);

        // Act
        BlogPost savedPost = blogPostService.saveBlogPost(post);

        // Assert
        assertThat(savedPost).isNotNull();
        verify(blogPostRepository, times(1)).save(post);
    }

    @Test
    void getPostById_shouldReturnPost_whenExists() {
        // Arrange
        BlogPost post = new BlogPost();
        when(blogPostRepository.findById(1L)).thenReturn(Optional.of(post));

        // Act
        BlogPost foundPost = blogPostService.getPostById(1L);

        // Assert
        assertThat(foundPost).isNotNull();
        verify(blogPostRepository, times(1)).findById(1L);
    }

    @Test
    void getPostById_shouldThrowException_whenNotExists() {
        // Arrange
        when(blogPostRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> blogPostService.getPostById(1L));
        verify(blogPostRepository, times(1)).findById(1L);
    }

    @Test
    void deleteBlogPost_shouldDeletePost() {
        // Act
        blogPostService.deleteBlogPost(1L);

        // Assert
        verify(blogPostRepository, times(1)).deleteById(1L);
    }

    @Test
    void searchBlogPosts_shouldCallRepositoryWithSpecification() {
        // Arrange
        Specification<BlogPost> spec = Specification.where(null);

        // Act
        blogPostService.searchBlogPosts("title", "content", LocalDate.now(), "imageUrl");

        // Assert
        verify(blogPostRepository, times(1)).findAll(any(Specification.class));
    }
}
