package com.company.demo.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateOnfeetImagesReq {
    @JsonProperty("onfeet_images")
    private ArrayList<String> onfeetImages;
}
