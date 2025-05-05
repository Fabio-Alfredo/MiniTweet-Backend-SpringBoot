package com.task.minitweet.domains.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID code;
    private String content;
    @Column(name = "timestamp", updatable = false)
    private Date timestamp;

    private Boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;

}
