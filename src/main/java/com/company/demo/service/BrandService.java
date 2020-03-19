package com.company.demo.service;

import com.company.demo.entity.Brand;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface BrandService {
    public List<Brand> getListBrand();
}
