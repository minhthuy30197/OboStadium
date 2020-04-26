package com.company.demo.repository;

import com.company.demo.entity.Brand;
import com.company.demo.model.dto.BrandInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    @Query(nativeQuery = true, name = "getListBrandAndProductCount")
    public List<BrandInfo> getListBrandAndProductCount();
}
