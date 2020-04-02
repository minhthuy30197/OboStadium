package com.company.demo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagePostController {
    @GetMapping("/admin/posts")
    public String getPostManagePage(Model model) {
        return "admin/blog/list";
    }

    @GetMapping("/admin/posts/create")
    public String getPostCreatePage(Model model) {
        return "admin/blog/create";
    }
}
