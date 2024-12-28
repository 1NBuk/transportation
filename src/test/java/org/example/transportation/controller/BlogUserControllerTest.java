package org.example.transportation.controller;

import org.example.transportation.entity.BlogPost;
import org.example.transportation.service.BlogPostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BlogUserControllerTest {

    @Mock
    private BlogPostService blogPostService;

    @Mock
    private Model model;

    @InjectMocks
    private BlogUserController blogUserController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void viewUserBlogPage_shouldReturnUserBlogTemplate() {

        when(blogPostService.getAllPosts()).thenReturn(Collections.emptyList());

        String viewName = blogUserController.viewUserBlogPage(model);

        assertEquals("user_blog", viewName);
        verify(blogPostService, times(1)).getAllPosts();
        verify(model, times(1)).addAttribute("blogPosts", Collections.emptyList());
    }

    @Test
    void searchBlogPosts_shouldReturnUserBlogTemplate() {

        List<BlogPost> searchResults = Collections.emptyList();
        when(blogPostService.searchBlogPosts(null, null, null, null)).thenReturn(searchResults);

        String viewName = blogUserController.searchBlogPosts(null, null, null, null, model);

        assertEquals("user_blog", viewName);
        verify(blogPostService, times(1)).searchBlogPosts(null, null, null, null);
        verify(model, times(1)).addAttribute("blogPosts", searchResults);
    }

    @Test
    void viewBlogDetails_shouldReturnBlogDetailsTemplate() {

        Long blogId = 1L;
        BlogPost blogPost = new BlogPost();
        when(blogPostService.getPostById(blogId)).thenReturn(blogPost);

        String viewName = blogUserController.viewBlogDetails(blogId, model);

        assertEquals("blog_details", viewName);
        verify(blogPostService, times(1)).getPostById(blogId);
        verify(model, times(1)).addAttribute("blogPost", blogPost);
    }
}
