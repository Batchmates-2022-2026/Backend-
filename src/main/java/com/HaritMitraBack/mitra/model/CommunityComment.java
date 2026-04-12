package com.HaritMitraBack.mitra.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CommunityComment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String postId;
    private String authorName;

    @Column(length = 1000)
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();

    // getters & setters
    public String getId() { return id; }

    public String getPostId() { return postId; }
    public void setPostId(String postId) { this.postId = postId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}