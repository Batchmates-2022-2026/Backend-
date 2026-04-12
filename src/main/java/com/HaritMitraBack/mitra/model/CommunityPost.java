package com.HaritMitraBack.mitra.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class CommunityPost {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String authorName;
    private String title;

    @Column(length = 2000)
    private String content;

    private String postType;
    private String cropRelated;
    private String location;

    private int likesCount = 0;
    private int commentsCount = 0;

    private boolean isApproved = true;

    private LocalDateTime createdAt = LocalDateTime.now();

    // getters setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getCropRelated() {
        return cropRelated;
    }

    public void setCropRelated(String cropRelated) {
        this.cropRelated = cropRelated;
    }

    public String getLocation() {
        return location;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}