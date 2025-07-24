package com.showcase.application.springbootbackend.controllers.notifications;

import com.showcase.application.springbootbackend.dto.request.notifications.CategoryRequest;
import com.showcase.application.springbootbackend.services.notifications.CategoryService;
import com.showcase.application.springbootbackend.services.notifications.NotificationModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final NotificationModuleService notificationModuleService;

    @GetMapping
    public ResponseEntity<?> get(@RequestParam("name") String name) {
        return new ResponseEntity<>(categoryService.findByNameAndEnabled(name, true), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody CategoryRequest categoryRequest) {
        return new ResponseEntity<>(categoryService.saveCategory(categoryRequest.name()), HttpStatus.OK);
    }

    @GetMapping("/findall")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserCategories(@RequestParam("id") Long userId) {
        return new ResponseEntity<>(notificationModuleService.getCategoriesForUser(userId), HttpStatus.OK);
    }
}
