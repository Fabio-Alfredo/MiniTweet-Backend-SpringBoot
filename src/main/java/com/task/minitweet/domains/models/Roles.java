package com.task.minitweet.domains.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Roles {

    @Id
    private String id;
    private String name;
}
