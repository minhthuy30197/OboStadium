package com.company.demo.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UpdateSizeCountReq {
    private int size;

    @Min(0)
    private int count;

    @NotEmpty(message = "Mã sản phẩm trống")
    @NotNull(message = "Mã sản phẩm trống")
    @JsonProperty("product_id")
    private String productId;
}
