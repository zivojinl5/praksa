package com.example.backend.data.entity;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.example.backend.enums.DeletedStatus;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "projects")
@SQLDelete(sql = "UPDATE projects SET deletedStatus = 'DELETED' WHERE id=?")
@SQLRestriction("deletedStatus <> 'DELETED'")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    @Column(columnDefinition = "varchar(255) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private DeletedStatus deletedStatus = DeletedStatus.ACTIVE;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity customer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity lead;

    @ManyToMany(mappedBy = "projects")
    private Set<UserEntity> workers = new HashSet<>();

    @OneToMany(mappedBy = "project")
    private Set<ActivityEntity> entries = new HashSet<>();

}
