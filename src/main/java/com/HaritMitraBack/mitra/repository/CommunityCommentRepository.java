package com.HaritMitraBack.mitra.repository;

import com.HaritMitraBack.mitra.model.CommunityComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityCommentRepository extends JpaRepository<CommunityComment, String> {

    List<CommunityComment> findByPostIdOrderByCreatedAtAsc(String postId);
}