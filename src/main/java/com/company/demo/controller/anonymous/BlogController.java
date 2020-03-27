package com.company.demo.controller.anonymous;

import com.company.demo.entity.Post;
import com.company.demo.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping("/tin-tuc")
    public String getBlogPage(Model model, @RequestParam(required = false) Integer page) {
        // Validate page number
        if (page == null) {
            page = 0;
        } else {
            page--;
            if (page < 0) {
                page = 0;
            }
        }

        Page<Post> posts = blogService.getListPost(page);

        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("currentPage", ++page);
        model.addAttribute("listPost", posts.getContent());

        return "blog/blog";
    }

    @GetMapping("/tin-tuc/{slug}/{id}")
    public String getPostDetailPage(Model model, @PathVariable long id) {
        Post post = blogService.getPostById(id);
        List<Post> latestPosts = blogService.getLatestPostsNotId(id);

        model.addAttribute("post", post);
        model.addAttribute("latestPosts", latestPosts);

        return "blog/detail";
    }

    @GetMapping("/chinh-sach")
    public String getPolicyPage(Model model) {
        return "blog/policy";
    }

    @GetMapping("/dieu-khoan")
    public String getRulesPage(Model model) {
         return "blog/rules";
    }

    @GetMapping("/hoi-dap")
    public String getQAPage(Model model) {
        return "blog/faqs";
    }
}
