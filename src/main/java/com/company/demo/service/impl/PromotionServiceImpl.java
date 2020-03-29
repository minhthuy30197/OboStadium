package com.company.demo.service.impl;

import com.company.demo.entity.Promotion;
import com.company.demo.model.dto.CheckPromotion;
import com.company.demo.model.dto.ProductInfoDto;
import com.company.demo.repository.PromotionRepository;
import com.company.demo.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import static com.company.demo.config.Constant.DISCOUNT_PERCENT;

@Component
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public List<ProductInfoDto> checkPublicPromotion(List<ProductInfoDto> products) {
        List<ProductInfoDto> rs = products;

        // Check has promotion
        Promotion promotion = promotionRepository.checkHasPublicPromotion();
        if (promotion != null) {
            // Calculate promotion price
            for (ProductInfoDto product : products) {
                long discountValue = promotion.getMaximumDiscountValue();
                if (promotion.getDiscountType() == DISCOUNT_PERCENT) {
                    long tmp = product.getPrice() * promotion.getDiscountValue() / 100;
                    if (tmp < discountValue) {
                        discountValue = tmp;
                    }
                }

                long promotionPrice = product.getPrice() - discountValue;
                if (promotionPrice > 0) {
                    product.setPromotionPrice(promotionPrice);
                } else {
                    product.setPromotionPrice(0);
                }
            }
        }

        return rs;
    }

    @Override
    public Promotion checkPublicPromotion() {
        Promotion promotion = promotionRepository.checkHasPublicPromotion();
        return promotion;
    }

    @Override
    public Promotion checkPromotion(String code) {
        Promotion promotion = promotionRepository.checkPromotion(code);
        return promotion;
    }

    @Override
    public long calculatePromotionPrice(Long price, Promotion promotion) {
        long discountValue = promotion.getMaximumDiscountValue();
        long tmp = promotion.getDiscountValue();
        if (promotion.getDiscountType() == DISCOUNT_PERCENT) {
            tmp = price * promotion.getDiscountValue() / 100;
        }
        if (tmp < discountValue) {
            discountValue = tmp;
        }

        long promotionPrice = price - discountValue;
        if (promotionPrice < 0) {
            promotionPrice = 0;
        }

        return promotionPrice;
    }
}
