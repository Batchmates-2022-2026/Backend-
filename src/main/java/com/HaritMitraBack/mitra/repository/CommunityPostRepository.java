package com.HaritMitraBack.mitra.repository;

import com.HaritMitraBack.mitra.model.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, String> {

    List<CommunityPost> findByIsApprovedTrueOrderByCreatedAtDesc();

    List<CommunityPost> findByPostTypeAndIsApprovedTrueOrderByCreatedAtDesc(String postType);
}