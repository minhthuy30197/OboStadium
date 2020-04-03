package com.company.demo.controller.admin;

import com.company.demo.model.dto.PageableDto;
import com.company.demo.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ManagePostController {
    @Autowired
    private BlogService blogService;

    @GetMapping("/admin/posts")
    public String getPostManagePage(Model model,
                                    @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "created_at") String order,
                                    @RequestParam(defaultValue = "desc") String direction,
                                    @RequestParam(defaultValue = "") String title,
                                    @RequestParam(defaultValue = "") String status) {
        if (!status.equals("") && !status.equals("0") && !status.equals("1")) {
            return "error/admin";
        }

        PageableDto result = blogService.adminGetListPost(title, status, page, order, direction);
        model.addAttribute("posts", result.getItems());
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("currentPage", result.getCurrentPage());

        return "admin/blog/list";
    }

    @GetMapping("/admin/posts/create")
    public String getPostCreatePage(Model model) {
        return "admin/blog/create";
    }
}
