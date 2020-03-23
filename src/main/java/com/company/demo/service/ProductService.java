package com.company.demo.service;

import com.company.demo.model.dto.DetailProductInfoDto;
import com.company.demo.model.dto.ListProductDto;
import com.company.demo.model.dto.ProductInfoDto;
import com.company.demo.model.request.FilterProductReq;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ProductService {
    public List<ProductInfoDto> getListBestSellerProduct();

    public List<ProductInfoDto> getListNewProduct();

    public List<ProductInfoDto> getListSuggestProduct();

    public DetailProductInfoDto getDetailProductById(String id);

    public List<ProductInfoDto> getRelatedProducts(String id);

    public List<Integer> getListAvailableSize(String id);

    public ListProductDto filterProduct(FilterProductReq req);

    public ListProductDto searchProductByKeyword(String keyword, Integer page);
}