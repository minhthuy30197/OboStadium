package com.company.demo.model.dto;

import com.company.demo.entity.Brand;
import lombok.*;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DetailProductInfoDto {
    private String id;

    private String name;

    private String slug;

    private long price;

    private int totalSold;

    private ArrayList<String> productImages;

    private ArrayList<String> onfeetImages;

    private long promotionPrice;

    private String couponCode;

    private String description;

    private Brand brand;
}
