package com.company.demo.service;

import com.company.demo.entity.Promotion;
import com.company.demo.model.dto.ProductInfoDto;
import com.company.demo.model.request.CreatePromotionReq;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface PromotionService {
    public List<ProductInfoDto> checkPublicPromotion(List<ProductInfoDto> products);

    public Promotion checkPublicPromotion();

    public Promotion checkPromotion(String code);

    public long calculatePromotionPrice(Long price, Promotion promotion);

    public Page<Promotion> adminGetListPromotion(String code, String name, String ispublic, String active, int page);

    public Promotion createPromotion(CreatePromotionReq req);

    public void updatePromotion(long id, CreatePromotionReq req);

    public void deletePromotion(long id);

    public Promotion getPromotionById(long id);

    public List<Promotion> getAllValidPromotion();
}
