package com.task.minitweet.domains.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name ="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(50)")
    private String username;
    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(50)")
    private String email;
    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String password;
    private String biography;
    private String profilePicture;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Token>tokens;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Post> posts;

    @ManyToMany(mappedBy = "likedBy", fetch = FetchType.LAZY)
    private List<Post> likedPosts;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

}
