package com.company.demo.service;

import com.company.demo.entity.Brand;
import com.company.demo.model.dto.BrandInfo;
import com.company.demo.model.request.CreateBrandReq;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface BrandService {
    public List<Brand> getListBrand();

    public List<BrandInfo> getListBrandAndProductCount();

    public Brand createBrand(CreateBrandReq req);

    public void updateBrand(int id, CreateBrandReq req);

    public void deleteBrand(int id);
}
