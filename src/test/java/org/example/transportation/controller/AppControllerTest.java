package org.example.transportation.controller;

import org.example.transportation.entity.Good;
import org.example.transportation.service.GoodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AppControllerTest {

    @InjectMocks
    private AppController appController;

    @Mock
    private GoodService goodService;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testViewAdminHomePage() {
        // Arrange
        String keyword = "test";
        List<Good> mockGoods = new ArrayList<>();
        mockGoods.add(new Good());
        when(goodService.listAll(keyword)).thenReturn(mockGoods);

        // Act
        String viewName = appController.viewAdminHomePage(model, keyword);

        // Assert
        verify(goodService).listAll(keyword);
        verify(model).addAttribute("listGoods", mockGoods);
        verify(model).addAttribute("keyword", keyword);
        assertEquals("index", viewName);
    }

    @Test
    public void testViewUserHomePage() {
        // Arrange
        String keyword = "userTest";
        List<Good> mockGoods = new ArrayList<>();
        mockGoods.add(new Good());
        when(goodService.listAll(keyword)).thenReturn(mockGoods);

        // Act
        String viewName = appController.viewUserHomePage(model, keyword);

        // Assert
        verify(goodService).listAll(keyword);
        verify(model).addAttribute("listGoods", mockGoods);
        verify(model).addAttribute("keyword", keyword);
        assertEquals("index_user", viewName);
    }

    @Test
    public void testSaveGood() {
        // Arrange
        Good good = new Good();

        // Act
        String viewName = appController.saveGood(good);

        // Assert
        verify(goodService).save(good);
        assertEquals("redirect:/index", viewName);
    }

    @Test
    public void testDeleteGood() {
        // Arrange
        Long id = 1L;

        // Act
        String viewName = appController.deleteGood(id);

        // Assert
        verify(goodService).delete(id);
        assertEquals("redirect:/index", viewName);
    }

    @Test
    public void testAboutPage() {
        // Act
        String viewName = appController.aboutPage();

        // Assert
        assertEquals("about", viewName);
    }
}
