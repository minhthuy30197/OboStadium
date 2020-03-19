package com.company.demo.service;

import com.company.demo.entity.Category;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface CategoryService {
    public List<Category> getListCategory();
}
