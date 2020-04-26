package com.company.demo.controller.admin;

import com.company.demo.entity.Post;
import com.company.demo.entity.User;
import com.company.demo.model.dto.PageableDto;
import com.company.demo.model.request.CreatePostReq;
import com.company.demo.security.CustomUserDetails;
import com.company.demo.service.BlogService;
import com.company.demo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Controller
public class ManagePostController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/admin/posts")
    public String getPostManagePage(Model model,
                                    @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "post.created_at") String order,
                                    @RequestParam(defaultValue = "desc") String direction,
                                    @RequestParam(defaultValue = "") String title,
                                    @RequestParam(defaultValue = "") String status) {
        if (!status.equals("") && !status.equals("0") && !status.equals("1")) {
            return "error/admin";
        }
        if (!direction.toLowerCase().equals("desc")) {
            direction = "asc";
        }

        PageableDto result = blogService.adminGetListPost(title, status, page, order, direction);
        model.addAttribute("posts", result.getItems());
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("currentPage", result.getCurrentPage());

        return "admin/blog/list";
    }

    @GetMapping("/admin/posts/create")
    public String getPostCreatePage(Model model) {
        // Get list image of user
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<String> images = imageService.getListImageOfUser(user.getId());
        model.addAttribute("images", images);

        return "admin/blog/create";
    }

    @PostMapping("/api/admin/posts")
    public ResponseEntity<?> createPost(@Valid @RequestBody CreatePostReq req) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Post post = blogService.createPost(req, user);

        return ResponseEntity.ok(post.getId());
    }

    @GetMapping("/admin/posts/{id}")
    public String getPostDetailPage(Model model, @PathVariable long id) {
        // Get list image of user
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<String> images = imageService.getListImageOfUser(user.getId());
        model.addAttribute("images", images);

        Post post = blogService.getPostById(id);
        model.addAttribute("post", post);

        return "admin/blog/detail";
    }

    @PutMapping("/api/admin/posts/{id}")
    public ResponseEntity<?> updatePost(@Valid @RequestBody CreatePostReq req, @PathVariable long id) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        blogService.updatePost(req, user, id);

        return ResponseEntity.ok("Cập nhật thành công");
    }

    @DeleteMapping("/api/admin/posts/{id}")
    public ResponseEntity<?> updatePost( @PathVariable long id) {
        blogService.deletePost(id);

        return ResponseEntity.ok("Xóa thành công");
    }
}
