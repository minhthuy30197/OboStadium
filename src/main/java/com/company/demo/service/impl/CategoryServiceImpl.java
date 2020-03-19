package com.company.demo.service.impl;

import com.company.demo.entity.Category;
import com.company.demo.repository.CategoryRepository;
import com.company.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getListCategory() {
        return categoryRepository.findAll();
    }
}
