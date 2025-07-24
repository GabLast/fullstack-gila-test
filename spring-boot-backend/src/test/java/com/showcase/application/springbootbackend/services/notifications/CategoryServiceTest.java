package com.showcase.application.springbootbackend.services.notifications;

import com.showcase.application.springbootbackend.models.notifications.Category;
import com.showcase.application.springbootbackend.repository.notifications.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;
    @InjectMocks
    private CategoryService service;

    @Test
    void testFindByNameAndEnabled() {

        String name = "channel";

        Category category = Category.builder()
                .name(name)
                .build();
        category.setId(1L);

        when(repository.findByNameAndEnabled(name, true)).thenReturn(category);

        Category test = service.findByNameAndEnabled(name, true);

        Assertions.assertNotNull(test);
    }

    @Test
    void testSave() {
        String name = "channel";

        Category category = Category.builder()
                .name(name)
                .build();
        category.setId(1L);

        when(repository.saveAndFlush(Mockito.any(Category.class))).thenReturn(category);

        Category test = service.saveCategory(name);

        Assertions.assertNotNull(test);
    }
}
