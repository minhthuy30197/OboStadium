package com.company.demo.service;

import com.company.demo.entity.Promotion;
import com.company.demo.model.dto.ProductInfoDto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface PromotionService {
    public List<ProductInfoDto> checkPublicPromotion(List<ProductInfoDto> products);

    public Promotion checkPublicPromotion();

    public Promotion checkPromotion(String code);

    public long calculatePromotionPrice(Long price, Promotion promotion);
}
