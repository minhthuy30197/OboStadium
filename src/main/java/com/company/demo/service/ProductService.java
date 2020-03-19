package com.company.demo.service;

import com.company.demo.model.dto.DetailProductInfoDto;
import com.company.demo.model.dto.ProductInfoDto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ProductService {
    public List<ProductInfoDto> getListBestSellerProduct();

    public List<ProductInfoDto> getListNewProduct();

    public List<ProductInfoDto> getListSuggestProduct();

    public DetailProductInfoDto getDetailProductById(long id);

    public List<ProductInfoDto> getRelatedProducts(long id);

    public List<Integer> getListAvailableSize(long id);
}
