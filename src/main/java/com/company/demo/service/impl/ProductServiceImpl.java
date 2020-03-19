package com.company.demo.service.impl;

import com.company.demo.entity.*;
import com.company.demo.exception.*;
import com.company.demo.model.dto.*;
import com.company.demo.model.mapper.*;
import com.company.demo.repository.*;
import com.company.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import java.util.*;
import static com.company.demo.config.Constant.*;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Override
    public List<ProductInfoDto> getListBestSellerProduct() {
        List<ProductInfoDto> products = productRepository.getListBestSellerProduct(5);

        return checkPromotion(products);
    }

    @Override
    public List<ProductInfoDto> getListNewProduct() {
        List<ProductInfoDto> products = productRepository.getListNewProduct(  5);

        return checkPromotion(products);
    }

    @Override
    public List<ProductInfoDto> getListSuggestProduct() {
        // Get Obo choices
        List<Configuration> configs = configurationRepository.findAll();
        if (configs.size() > 0) {
            Configuration config = configs.get(0);
            List<ProductInfoDto> products = productRepository.getListSuggestProduct(config.getOboChoices(), 5);

            return checkPromotion(products);
        }

        return null;
    }

    @Override
    public DetailProductInfoDto getDetailProductById(long id) {
        // Get product info
        Optional<Product> result = productRepository.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }

        Product product = result.get();

        if (!product.isAvailable()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }

        DetailProductInfoDto dto = ProductMapper.toDetailProductInfoDto(product);

        // Check promotion
        dto.setPromotionPrice(calculatePromotionPrice(dto.getPrice()));

        return dto;
    }

    @Override
    public List<ProductInfoDto> getRelatedProducts(long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }

        List<ProductInfoDto> products = productRepository.getRelatedProducts(id, product.get().getCategoryIds(), product.get().getBrand().getId(), 5);

        return checkPromotion(products);
    }

    @Override
    public List<Integer> getListAvailableSize(long id) {
        List<ProductSize> sizes = productSizeRepository.findAllByProductId(id);

        List<Integer> result = new ArrayList<Integer>();
        for (ProductSize productSize : sizes) {
            result.add(productSize.getSize());
        }

        return result;
    }

    @Override
    public ListProductDto searchProduct(int page) {
        Page<ProductInfoDto> products = productRepository.searchProduct(PageRequest.of(page, 16));

        ListProductDto result = new ListProductDto(checkPromotion(products.getContent()), products.getTotalPages(), ++page);

        return result;
    }

    public List<ProductInfoDto> checkPromotion(List<ProductInfoDto> products) {
        List<ProductInfoDto> rs = products;

        // Check has promotion
        Promotion promotion = promotionRepository.checkHasPromotion();
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

    public Long calculatePromotionPrice(Long price) {
        Promotion promotion = promotionRepository.checkHasPromotion();
        if (promotion != null) {
            long discountValue = promotion.getMaximumDiscountValue();
            if (promotion.getDiscountType() == DISCOUNT_PERCENT) {
                long tmp = price * promotion.getDiscountValue() / 100;
                if (tmp < discountValue) {
                    discountValue = tmp;
                }
            }

            long promotionPrice = price - discountValue;
            if (promotionPrice < 0) {
                promotionPrice = 0;
            }

            return promotionPrice;
        }

        return null;
    }
}
