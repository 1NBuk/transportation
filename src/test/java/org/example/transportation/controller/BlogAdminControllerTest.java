package org.example.transportation.controller;

import org.example.transportation.entity.BlogPost;
import org.example.transportation.service.BlogPostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BlogAdminControllerTest {

    @Mock
    private BlogPostService blogPostService;

    @Mock
    private Model model;

    @InjectMocks
    private BlogAdminController blogAdminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void viewAdminBlogPage_ShouldReturnAdminBlogTemplate() {

        when(blogPostService.getAllPosts()).thenReturn(Collections.emptyList());


        String viewName = blogAdminController.viewAdminBlogPage(null, model);


        assertEquals("admin_blog", viewName);
        verify(blogPostService, times(1)).getAllPosts();
        verify(model, times(1)).addAttribute("blogPosts", Collections.emptyList());
    }

    @Test
    void showNewBlogForm_ShouldReturnAddBlogTemplate() {

        String viewName = blogAdminController.showNewBlogForm(null, model);


        assertEquals("add_blog", viewName);
        verify(model, times(1)).addAttribute(eq("blogPost"), any(BlogPost.class));
    }

    @Test
    void addNewBlog_ShouldRedirectToAdminBlog() {

        BlogPost blogPost = new BlogPost();


        String redirectUrl = blogAdminController.addNewBlog(blogPost);


        assertEquals("redirect:/admin_blog", redirectUrl);
        verify(blogPostService, times(1)).saveBlogPost(blogPost);
        assertEquals("Admin", blogPost.getCreatedBy());
    }

    @Test
    void editBlog_ShouldReturnEditBlogTemplate() {

        Long blogId = 1L;
        BlogPost blogPost = new BlogPost();
        when(blogPostService.getPostById(blogId)).thenReturn(blogPost);


        String viewName = blogAdminController.editBlog(blogId, null, model);


        assertEquals("edit_blog", viewName);
        verify(blogPostService, times(1)).getPostById(blogId);
        verify(model, times(1)).addAttribute("blogPost", blogPost);
    }

    @Test
    void updateBlog_ShouldRedirectToAdminBlog() {

        Long blogId = 1L;
        BlogPost blogPost = new BlogPost();


        String redirectUrl = blogAdminController.updateBlog(blogId, blogPost);


        assertEquals("redirect:/admin_blog", redirectUrl);
        verify(blogPostService, times(1)).saveBlogPost(blogPost);
        assertEquals(blogId, blogPost.getId());
    }

    @Test
    void deleteBlog_ShouldRedirectToAdminBlog() {

        Long blogId = 1L;


        String redirectUrl = blogAdminController.deleteBlog(blogId);


        assertEquals("redirect:/admin_blog", redirectUrl);
        verify(blogPostService, times(1)).deleteBlogPost(blogId);
    }

    @Test
    void searchBlogPosts_ShouldReturnAdminBlogTemplate() {

        List<BlogPost> searchResults = Collections.emptyList();
        when(blogPostService.searchBlogPosts(null, null, null, null)).thenReturn(searchResults);


        String viewName = blogAdminController.searchBlogPosts(null, null, null, null, null, model);


        assertEquals("admin_blog", viewName);
        verify(blogPostService, times(1)).searchBlogPosts(null, null, null, null);
        verify(model, times(1)).addAttribute("blogPosts", searchResults);
    }

    @Test
    void viewBlogDetails_ShouldReturnBlogDetailsTemplate() {

        Long blogId = 1L;
        BlogPost blogPost = new BlogPost();
        when(blogPostService.getPostById(blogId)).thenReturn(blogPost);


        String viewName = blogAdminController.viewBlogDetails(blogId, null, model);


        assertEquals("blog_details", viewName);
        verify(blogPostService, times(1)).getPostById(blogId);
        verify(model, times(1)).addAttribute("blogPost", blogPost);
    }
}
