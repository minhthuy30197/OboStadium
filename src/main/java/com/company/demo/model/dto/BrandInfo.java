package com.company.demo.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BrandInfo {
    private int id;

    private String name;

    private String thumbnail;

    private int productCount;
}

