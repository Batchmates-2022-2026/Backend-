package com.HaritMitraBack.mitra.controller;

import com.HaritMitraBack.mitra.model.CommunityPost;
import com.HaritMitraBack.mitra.model.CommunityComment;
import com.HaritMitraBack.mitra.service.ActivityService;
import com.HaritMitraBack.mitra.service.CommunityService;
import com.HaritMitraBack.mitra.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/community")
@CrossOrigin(origins = "*")
public class CommunityController {

    private final CommunityService service;
    private final JwtUtil jwtUtil;
    private final ActivityService activityService;

    public CommunityController(CommunityService service,
                               JwtUtil jwtUtil,
                               ActivityService activityService) {
        this.service = service;
        this.jwtUtil = jwtUtil;
        this.activityService = activityService;
    }

    // ✅ Get Posts (NO activity needed)
    @GetMapping("/posts")
    public List<CommunityPost> getPosts(
            @RequestParam(defaultValue = "all") String filter) {
        return service.getPosts(filter);
    }

    // ✅ Create Post
    @PostMapping("/post")
    public Object createPost(
            @RequestHeader("Authorization") String token,
            @RequestBody CommunityPost post) {

        String email = jwtUtil.extractEmail(token.replace("Bearer ", "").trim());

        // 🔥 activity update
        activityService.updateUserActivity(email);

        // 🔥 log
        activityService.log(email, "POST_CREATED", "Created a community post");

        return service.createPost(post);
    }

    // ✅ Like Post
    @PostMapping("/like/{postId}")
    public Object like(
            @RequestHeader("Authorization") String token,
            @PathVariable String postId) {

        String email = jwtUtil.extractEmail(token.replace("Bearer ", "").trim());

        activityService.updateUserActivity(email);
        activityService.log(email, "POST_LIKED", "Liked a post");

        service.likePost(postId);

        System.out.println("full token: " + token);

        return Map.of("message", "Liked");
    }

    // ✅ Get Comments (NO activity needed)
    @GetMapping("/comments/{postId}")
    public List<CommunityComment> getComments(@PathVariable String postId) {
        return service.getComments(postId);
    }

    // ✅ Add Comment
    @PostMapping("/comment")
    public Object addComment(
            @RequestHeader("Authorization") String token,
            @RequestBody CommunityComment comment) {

        String email = jwtUtil.extractEmail(token.replace("Bearer ", "").trim());

        activityService.updateUserActivity(email);
        activityService.log(email, "COMMENT_ADDED", "Commented on post");

        service.addComment(comment);

        return Map.of("message", "Comment added");
    }
}