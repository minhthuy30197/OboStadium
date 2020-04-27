package com.company.demo.controller.admin;

import com.company.demo.entity.Order;
import com.company.demo.entity.Promotion;
import com.company.demo.entity.User;
import com.company.demo.model.dto.ShortProductInfoDto;
import com.company.demo.model.request.UpdateDetailOrderReq;
import com.company.demo.model.request.UpdateStatusOrderReq;
import com.company.demo.security.CustomUserDetails;
import com.company.demo.service.OrderService;
import com.company.demo.service.ProductService;
import com.company.demo.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.company.demo.config.Constant.ORDER_STATUS;
import static com.company.demo.config.Constant.SIZE_VN;

@Controller
public class ManageOrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PromotionService promotionService;

    @GetMapping("/admin/orders")
    public String getOrderManagePage(Model model,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "") String id,
                                     @RequestParam(defaultValue = "") String name,
                                     @RequestParam(defaultValue = "") String phone,
                                     @RequestParam(defaultValue = "") String status,
                                     @RequestParam(defaultValue = "") String product) {
        // Get list product to select
        List<ShortProductInfoDto> products = productService.getAllProduct();
        model.addAttribute("products", products);

        // Get list order
        Page<Order> result = orderService.adminGetListOrder(id, name, phone, status, product, page);
        model.addAttribute("orders", result.getContent());
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("currentPage", result.getPageable().getPageNumber() - 1);

        return "admin/order/list";
    }

    @GetMapping("/admin/orders/create")
    public String getOrderCreatePage(Model model) {
        // Get list product to select
        List<ShortProductInfoDto> products = productService.getAvailableProducts();
        model.addAttribute("products", products);

        // Get list size
        model.addAttribute("sizeVn", SIZE_VN);

        // Get list valid promotion
        List<Promotion> promotions = promotionService.getAllValidPromotion();
        model.addAttribute("promotions", promotions);

        return "admin/order/create";
    }

    @GetMapping("/admin/orders/{id}")
    public String getOrderDetailPage(Model model, @PathVariable long id) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);

        if (order.getStatus() == ORDER_STATUS) {
            // Get list product to select
            List<ShortProductInfoDto> products = productService.getAvailableProducts();
            model.addAttribute("products", products);

            // Get list valid promotion
            List<Promotion> promotions = promotionService.getAllValidPromotion();
            model.addAttribute("promotions", promotions);
            if (order.getPromotion() != null) {
                boolean validPromotion = false;
                for (Promotion promotion : promotions) {
                    if (promotion.getCouponCode().equals(order.getPromotion().getCouponCode())) {
                        validPromotion = true;
                        break;
                    }
                }
                if (!validPromotion) {
                    promotions.add(new Promotion(order.getPromotion()));
                }
            }

            // Check size available
            boolean sizeIsAvailable = productService.checkProductSizeAvailable(order.getProduct().getId(), order.getSize());
            model.addAttribute("sizeIsAvailable", sizeIsAvailable);
        }

        return "admin/order/detail";
    }

    @PutMapping("/api/admin/orders/{id}/update-detail")
    public ResponseEntity<?> updateDetailOrder(@Valid @RequestBody UpdateDetailOrderReq req, @PathVariable long id) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        orderService.updateDetailOrder(req, id, user.getId());

        return ResponseEntity.ok("Cập nhật thành công");
    }

    @PutMapping("/api/admin/orders/{id}/update-status")
    public ResponseEntity<?> updateStatusOrder(@Valid @RequestBody UpdateStatusOrderReq req, @PathVariable long id) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        orderService.updateStatusOrder(req, id, user.getId());

        return ResponseEntity.ok("Cập nhật thành công");
    }
}
