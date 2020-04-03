package com.company.demo.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostInfoDto {
    private long id;

    private String slug;

    private String title;

    private String createdAt;

    private String publishedAt;

    private String status;
}
