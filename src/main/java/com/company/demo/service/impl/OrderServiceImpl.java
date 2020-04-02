package com.company.demo.service.impl;

import com.company.demo.entity.*;
import com.company.demo.exception.BadRequestException;
import com.company.demo.exception.NotFoundException;
import com.company.demo.model.dto.OrderDetailDto;
import com.company.demo.model.dto.OrderInfoDto;
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
import java.util.List;
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
    public Order createOrder(CreateOrderReq req, long userId) {
        Order order = new Order();

        // Check product and size
        Optional<Product> product = productRepository.findById(req.getProductId());
        if (product.isEmpty()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
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
                throw new NotFoundException("Mã khuyến mãi không tồn tại hoặc đã hết hạn");
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

    @Override
    public List<OrderInfoDto> getListOrderOfPersonByStatus(int status, long userId) {
        List<OrderInfoDto> list = orderRepository.getListOrderOfPersonByStatus(status, userId);

        for (OrderInfoDto order : list) {
            for (int i=0; i<SIZE_VN.size(); i++) {
                if (SIZE_VN.get(i) == order.getSizeVn()) {
                    order.setSizeUs(SIZE_US[i]);
                    order.setSizeCm(SIZE_CM[i]);
                }
            }
        }

        return list;
    }

    @Override
    public OrderDetailDto userGetDetailById(long id, long userId) {
        OrderDetailDto order = orderRepository.userGetDetailById(id, userId);
        if (order == null) {
            return null;
        }

        if (order.getStatus() == ORDER_STATUS) {
            order.setStatusText("Chờ lấy hàng");
        } else if (order.getStatus() == DELIVERY_STATUS) {
            order.setStatusText("Đang giao hàng");
        } else if (order.getStatus() == COMPLETE_STATUS) {
            order.setStatusText("Đã giao hàng");
        } else if (order.getStatus() == CANCELED_STATUS) {
            order.setStatusText("Đã hủy");
        } else if (order.getStatus() == RETURNED_STATUS) {
            order.setStatusText("Đã trả hàng");
        }

        for (int i=0; i<SIZE_VN.size(); i++) {
            if (SIZE_VN.get(i) == order.getSizeVn()) {
                order.setSizeUs(SIZE_US[i]);
                order.setSizeCm(SIZE_CM[i]);
            }
        }

        return order;
    }

    @Override
    public void userCancelOrder(long id, long userId) {
        Optional<Order> rs = orderRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("Đơn hàng không tồn tại");
        }
        Order order = rs.get();
        if (order.getBuyer().getId() != userId) {
            throw new BadRequestException("Bạn không phải chủ nhân đơn hàng");
        }
        if (order.getStatus() != ORDER_STATUS) {
            throw new BadRequestException("Trạng thái đơn hàng không phù hợp để hủy. Vui lòng liên hệ với shop để được hỗ trợ");
        }

        order.setStatus(CANCELED_STATUS);
        orderRepository.save(order);
    }
}