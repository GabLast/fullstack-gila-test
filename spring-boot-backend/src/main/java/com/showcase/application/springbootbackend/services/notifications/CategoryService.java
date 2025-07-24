package com.showcase.application.springbootbackend.services.notifications;

import com.showcase.application.springbootbackend.exceptions.ResourceExistsException;
import com.showcase.application.springbootbackend.exceptions.ResourceNotFoundException;
import com.showcase.application.springbootbackend.models.notifications.Category;
import com.showcase.application.springbootbackend.repository.notifications.CategoryRepository;
import com.showcase.application.springbootbackend.services.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService extends BaseService<Category, Long> {

    private final CategoryRepository repository;

    @Override
    protected JpaRepository<Category, Long> getRepository() {
        return repository;
    }

    public void boostrap() {
        try {
            for (Category.CategoryType value : Category.CategoryType.values()) {
                Category category = repository.findByNameAndEnabled(value.getName(), true);
                if (category == null) {
                    save(Category.builder().name(value.getName()).build());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Category findByNameAndEnabled(String name, boolean enabled) {
        Category category = repository.findByNameAndEnabled(name, enabled);
        if(category == null) {
            throw new ResourceNotFoundException("The category name " + name + " was not found.");
        }
        return category;
    }

    @Transactional
    public Category saveCategory(String name) {
        Category category = repository.findByNameAndEnabled(name, true);
        if(category != null) {
            throw new ResourceExistsException("The category name " + name + " already exists. Please select a new name.");
        }
        return saveAndFlush(Category.builder().name(name).build());
    }
}
