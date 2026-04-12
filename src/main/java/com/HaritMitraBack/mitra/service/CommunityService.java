package com.HaritMitraBack.mitra.service;

import com.HaritMitraBack.mitra.model.CommunityPost;
import com.HaritMitraBack.mitra.model.CommunityComment;
import com.HaritMitraBack.mitra.repository.CommunityPostRepository;
import com.HaritMitraBack.mitra.repository.CommunityCommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityService {

    private final CommunityPostRepository postRepo;
    private final CommunityCommentRepository commentRepo;

    public CommunityService(CommunityPostRepository postRepo,
                            CommunityCommentRepository commentRepo) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
    }

    public List<CommunityPost> getPosts(String filter) {
        if (filter.equals("all")) {
            return postRepo.findByIsApprovedTrueOrderByCreatedAtDesc();
        }
        return postRepo.findByPostTypeAndIsApprovedTrueOrderByCreatedAtDesc(filter);
    }

    public CommunityPost createPost(CommunityPost post) {
        return postRepo.save(post);
    }

    public void likePost(String postId) {
        CommunityPost post = postRepo.findById(postId).orElseThrow();
        post.setLikesCount(post.getLikesCount() + 1);
        postRepo.save(post);
    }

    public List<CommunityComment> getComments(String postId) {
        return commentRepo.findByPostIdOrderByCreatedAtAsc(postId);
    }

    public void addComment(CommunityComment comment) {
        commentRepo.save(comment);

        CommunityPost post = postRepo.findById(comment.getPostId()).orElseThrow();
        post.setCommentsCount(post.getCommentsCount() + 1);
        postRepo.save(post);
    }
}