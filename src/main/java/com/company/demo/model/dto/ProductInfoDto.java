package com.company.demo.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductInfoDto {
    private String id;

    private String name;

    private String slug;

    private long price;

    private int totalSold;

    private String image;

    private long promotionPrice;

    public ProductInfoDto(String id, String name, String slug, long price, int totalSold, String image) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.price = price;
        this.totalSold = totalSold;
        this.image = image;
    }
}
