package com.company.demo.model.mapper;

import com.company.demo.entity.Product;
import com.company.demo.model.dto.DetailProductInfoDto;

public class ProductMapper {
    public static DetailProductInfoDto toDetailProductInfoDto(Product product) {
        DetailProductInfoDto rs = new DetailProductInfoDto();
        rs.setId(product.getId());
        rs.setBrand(product.getBrand());
        rs.setDescription(product.getDescription());
        rs.setName(product.getName());
        rs.setOnfeetImages(product.getOnfeetImages());
        rs.setProductImages(product.getProductImages());
        rs.setTotalSold(product.getTotalSold());
        rs.setPrice(product.getPrice());

        return rs;
    }
}
