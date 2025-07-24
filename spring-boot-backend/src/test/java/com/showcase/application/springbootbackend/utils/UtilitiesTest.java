package com.showcase.application.springbootbackend.utils;

import com.showcase.application.springbootbackend.models.notifications.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class UtilitiesTest {

    @Test
    void testCapitilizeWords() {
        String string = "this is a nice sentence to capitalize.";

        Assertions.assertEquals("This Is A Nice Sentence To Capitalize.", Utilities.capitalizeEachWord(string));
    }

    @Test
    void testGetIdField() throws Exception {
        Field field = Category.class.getSuperclass().getDeclaredField("id");
        Assertions.assertEquals(field, Utilities.getIdField(Category.class));
    }

    @Test
    void testGetIdFieldName() throws Exception {
        Field field = Category.class.getSuperclass().getDeclaredField("id");
        Assertions.assertEquals(field.getName(), Utilities.getIdName(Category.class));
    }

    @Test
    void testSetFieldValue() throws Exception {
        Category category = Category.builder().build();
        Utilities.setFieldValue(category, "name", "my name");
        Assertions.assertEquals("my name", category.getName());
    }
}
