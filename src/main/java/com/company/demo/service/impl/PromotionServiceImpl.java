package com.company.demo.service.impl;

import com.company.demo.entity.Promotion;
import com.company.demo.exception.BadRequestException;
import com.company.demo.exception.InternalServerException;
import com.company.demo.exception.NotFoundException;
import com.company.demo.model.dto.ProductInfoDto;
import com.company.demo.model.mapper.PromotionMapper;
import com.company.demo.model.request.CreatePromotionReq;
import com.company.demo.repository.PromotionRepository;
import com.company.demo.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Page<Promotion> adminGetListPromotion(String code, String name, String ispublic, String active, int page) {
        page--;
        if (page < 0) {
            page = 0;
        }

        Page<Promotion> rs = promotionRepository.adminGetListPromotion(code, name, ispublic, active, PageRequest.of(page, 15));

        return rs;
    }

    @Override
    public Promotion createPromotion(CreatePromotionReq req) {
        // Validate
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (req.getExpiredDate().before(now)) {
            throw new BadRequestException("Hạn khuyến mãi không hợp lệ");
        }
        if (req.getDiscountType() == DISCOUNT_PERCENT) {
            if (req.getDiscountValue() < 1 || req.getDiscountValue() > 100) {
                throw new BadRequestException("Mức giảm giá từ 1% - 100%");
            }
            if (req.getMaxValue() < 1000) {
                throw new BadRequestException("Mức giảm tối đa phải lớn hơn 1000");
            }
        } else {
            if (req.getDiscountValue() < 1000) {
                throw new BadRequestException("Mức giảm giá phải lớn hơn 1000 ");
            }
        }
        Optional<Promotion> rs = promotionRepository.findByCouponCode(req.getCode());
        if (!rs.isEmpty()) {
            throw new BadRequestException("Mã code đã tồn tại trong hệ thống");
        }

        // Check has valid promotion
        if (req.isPublic() && req.isActive()) {
            Promotion alreadyPromotion = promotionRepository.checkHasPublicPromotion();
            if (alreadyPromotion != null) {
                throw new BadRequestException("Chương trình khuyến mãi công khai \""+alreadyPromotion.getCouponCode()+"\" đang chạy. Không thể tạo mới");
            }
        }

        Promotion promotion = PromotionMapper.toProduct(req);
        promotion.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        promotionRepository.save(promotion);

        return promotion;
    }

    @Override
    public void updatePromotion(long id, CreatePromotionReq req) {
        // Check exist promotion
        Optional<Promotion> rs = promotionRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("Khuyến mãi không tồn tại");
        }

        // Validate
        if (req.getDiscountType() == DISCOUNT_PERCENT) {
            if (req.getDiscountValue() < 1 || req.getDiscountValue() > 100) {
                throw new BadRequestException("Mức giảm giá từ 1% - 100%");
            }
            if (req.getMaxValue() < 1000) {
                throw new BadRequestException("Mức giảm tối đa phải lớn hơn 1000");
            }
        } else {
            if (req.getDiscountValue() < 1000) {
                throw new BadRequestException("Mức giảm giá phải lớn hơn 1000 ");
            }
        }
        rs = promotionRepository.findByCouponCode(req.getCode());
        if (!rs.isEmpty() && rs.get().getId() != id) {
            throw new BadRequestException("Mã code đã tồn tại trong hệ thống");
        }

        // Check has valid promotion
        if (req.isPublic() && req.isActive()) {
            Promotion alreadyPromotion = promotionRepository.checkHasPublicPromotion();
            if (alreadyPromotion != null && alreadyPromotion.getId() != id) {
                throw new BadRequestException("Chương trình khuyến mãi công khai \""+alreadyPromotion.getCouponCode()+"\" đang chạy. Không thể tạo mới");
            }
        }

        Promotion promotion = PromotionMapper.toProduct(req);
        promotion.setCreatedAt(rs.get().getCreatedAt());
        promotion.setId(id);

        try {
            promotionRepository.save(promotion);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi cập nhật khuyến mãi");
        }
    }

    @Override
    public void deletePromotion(long id) {
        // Check exist promotion
        Optional<Promotion> rs = promotionRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("Khuyến mãi không tồn tại");
        }

        // Check promotion in use
        int countUse = promotionRepository.checkPromotionInUse(rs.get().getCouponCode());
        if (countUse > 0) {
            throw new BadRequestException("Khuyến mãi đã được sử dụng. Không thể xóa");
        }

        try {
            promotionRepository.deleteById(id);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi xóa khuyến mãi");
        }
    }

    @Override
    public Promotion getPromotionById(long id) {
        Optional<Promotion> rs = promotionRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("Khuyến mãi không tồn tại");
        }

        return rs.get();
    }

    @Override
    public List<Promotion> getAllValidPromotion() {
        return promotionRepository.getAllValidPromotion();
    }
}
