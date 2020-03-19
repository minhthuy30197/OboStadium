package com.company.demo.service.impl;

import com.company.demo.entity.Brand;
import com.company.demo.repository.BrandRepository;
import com.company.demo.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Brand> getListBrand() {
        return brandRepository.findAll();
    }
}
