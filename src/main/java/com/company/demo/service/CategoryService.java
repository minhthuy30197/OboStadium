package com.company.demo.service;

import com.company.demo.entity.Category;
import com.company.demo.model.dto.CategoryInfo;
import com.company.demo.model.request.CreateCategoryReq;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface CategoryService {
    public List<Category> getListCategory();

    public List<CategoryInfo> getListCategoryAndProductCount();

    public Category createCategory(CreateCategoryReq req);

    public void updateCategory(int id, CreateCategoryReq req);

    public void deleteCategory(int id);
}
