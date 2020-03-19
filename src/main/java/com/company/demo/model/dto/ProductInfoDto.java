package com.company.demo.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductInfoDto {
    private long id;

    private String name;

    private long price;

    private int totalSold;

    private String image;

    private long promotionPrice;

    public ProductInfoDto(long id, String name, long price, int totalSold, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.totalSold = totalSold;
        this.image = image;
    }
}
