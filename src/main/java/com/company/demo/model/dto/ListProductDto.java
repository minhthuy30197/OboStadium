package com.company.demo.model.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListProductDto {
    private List<ProductInfoDto> products;

    private int totalPages;

    private int currentPage;
}
