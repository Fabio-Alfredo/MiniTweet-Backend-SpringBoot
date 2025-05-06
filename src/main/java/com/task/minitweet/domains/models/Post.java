package com.task.minitweet.domains.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String content;
    private String image;

    @Column(name = "created_at", updatable = false)
    private Date createdAt = Date.from(Instant.now());

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User author;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "post_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> likedBy;
}
