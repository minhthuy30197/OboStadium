package com.company.demo.service.impl;

import com.company.demo.entity.*;
import com.company.demo.exception.BadRequestException;
import com.company.demo.model.request.CreateOrderReq;
import com.company.demo.repository.OrderRepository;
import com.company.demo.repository.ProductRepository;
import com.company.demo.repository.ProductSizeRepository;
import static com.company.demo.config.Constant.*;
import com.company.demo.service.OrderService;
import com.company.demo.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;
import java.util.Optional;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private PromotionService promotionService;

    @Override
    public Order createOrder(CreateOrderReq req, Long userId) {
        Order order = new Order();

        // Check product and size
        Optional<Product> product = productRepository.findById(req.getProductId());
        if (product.isEmpty()) {
            throw new BadRequestException("Sản phẩm không tồn tại");
        }
        if (!product.get().isAvailable()) {
            throw new BadRequestException("Shop không còn kinh doanh sản phẩm này. Vui lòng chọn sản phẩm khác");
        }
        ProductSize productSize = productSizeRepository.checkProductSizeAvailable(req.getProductId(), req.getSize());
        if (productSize == null) {
            throw new BadRequestException("Size giày cho sản phẩm này tạm hết. Vui lòng chọn sản phẩm khác");
        }
        if (product.get().getPrice() != req.getProductPrice()) {
            throw new BadRequestException("Giá sản phẩm đã thay đổi. Vui lòng kiểm tra và đặt lại đơn hàng");
        }

        // Check promotion
        if (req.getCouponCode() != "") {
            Promotion promotion = promotionService.checkPromotion(req.getCouponCode());
            if (promotion == null) {
                throw new BadRequestException("Mã khuyến mãi không tồn tại hoặc đã hết hạn");
            }
            long promotionPrice = promotionService.calculatePromotionPrice(req.getProductPrice(), promotion);
            if (promotionPrice != req.getTotalPrice()) {
                throw new BadRequestException("Tổng giá trị đơn hàng thay đổi. Vui lòng kiểm tra và đặt lại đơn hàng");
            }
            Order.UsedPromotion usedPromotion = new Order.UsedPromotion(req.getCouponCode(), promotion.getDiscountType(), promotion.getDiscountValue());
            order.setPromotion(usedPromotion);
        }

        // Create order
        order.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        User createdBy = new User();
        createdBy.setId(userId);
        order.setCreatedBy(createdBy);

        order.setReceiverAddress(req.getReceiverAddress());
        order.setReceiverName(req.getReceiverName());
        order.setReceiverPhone(req.getReceiverPhone());

        order.setProduct(product.get());
        order.setSize(req.getSize());
        order.setProductPrice(req.getProductPrice());
        order.setTotalPrice(req.getTotalPrice());
        order.setStatus(ORDER_STATUS);

        orderRepository.save(order);

        return order;
    }
}
