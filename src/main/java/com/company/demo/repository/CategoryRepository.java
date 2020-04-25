package com.company.demo.repository;

import com.company.demo.entity.Category;
import com.company.demo.model.dto.CategoryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(nativeQuery = true, name = "getListCategoryAndProductCount")
    public List<CategoryInfo> getListCategoryAndProductCount();

    @Query(nativeQuery = true, value = "Select 1 from product_category where category_id = ?1")
    public int checkProductInCategory(int id);
}
