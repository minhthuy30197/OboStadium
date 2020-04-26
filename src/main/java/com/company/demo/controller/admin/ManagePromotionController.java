package com.company.demo.controller.admin;

import com.company.demo.entity.Promotion;
import com.company.demo.model.request.CreatePromotionReq;
import com.company.demo.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
public class ManagePromotionController {
    @Autowired
    private PromotionService promotionService;

    @GetMapping("/admin/promotions")
    public String getPromotionManagePage(Model model,
                                         @RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "") String active,
                                         @RequestParam(defaultValue = "") String ispublic,
                                         @RequestParam(defaultValue = "") String code,
                                         @RequestParam(defaultValue = "") String name) {
        if (!active.equals("") && !active.equals("true") && !active.equals("false")) {
            return "error/admin";
        }
        if (!ispublic.equals("") && !ispublic.equals("true") && !ispublic.equals("false")) {
            return "error/admin";
        }

        Page<Promotion> result = promotionService.adminGetListPromotion(code, name, ispublic, active, page);
        model.addAttribute("promotions", result.getContent());
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("currentPage", result.getPageable().getPageNumber()+1);

        return "admin/promotion/list";
    }

    @GetMapping("/admin/promotions/create")
    public String getPromotionCreatePage(Model model) {
        return "admin/promotion/create";
    }

    @GetMapping("/admin/promotions/{id}")
    public String getPromotionDetailPage(Model model, @PathVariable long id) {
        Promotion promotion = promotionService.getPromotionById(id);
        model.addAttribute("promotion", promotion);

        return "admin/promotion/detail";
    }

    @PostMapping("/api/admin/promotions")
    public ResponseEntity<?> createPromotion(@Valid @RequestBody CreatePromotionReq req) {
        Promotion promotion = promotionService.createPromotion(req);

        return ResponseEntity.ok(promotion.getId());
    }

    @PutMapping("/api/admin/promotions/{id}")
    public ResponseEntity<?> updatePromotion(@Valid @RequestBody CreatePromotionReq req, @PathVariable long id) {
        promotionService.updatePromotion(id, req);

        return ResponseEntity.ok("Cập nhật thành công");
    }

    @DeleteMapping("/api/admin/promotions/{id}")
    public ResponseEntity<?> deletePromotion(@PathVariable long id) {
        promotionService.deletePromotion(id);

        return ResponseEntity.ok("Xóa khuyến mãi thành công");
    }
}
