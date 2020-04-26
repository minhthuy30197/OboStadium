package com.company.demo.model.request;

import lombok.*;
import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateBrandReq {
    @NotNull(message = "Tên nhãn hiệu trống")
    @NotEmpty(message = "Tên nhãn hiệu trống")
    @Size(min = 1, max = 255, message = "Độ dài tên nhãn hiệu từ 1 - 255 ký tự")
    private String name;

    @NotNull(message = "Logo trống")
    @NotEmpty(message = "Logo trống")
    private String thumbnail;
}
