package com.example.backend.data.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import com.example.backend.enums.Role;
import com.example.backend.enums.DeletedStatus;
import com.example.backend.enums.UserStatus;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deletedStatus = 'DELETED' WHERE id=?")
@SQLRestriction("deletedStatus <> 'DELETED'")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(unique = true)
    private String userName;
    @Column(unique = true)
    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus teamMemberStatus;
    @Column(columnDefinition = "varchar(255) default 'ACTIVE'")

    @Enumerated(EnumType.STRING)
    private DeletedStatus deletedStatus = DeletedStatus.ACTIVE;

    @ManyToMany
    @JoinTable(name = "worker_project", joinColumns = @JoinColumn(name = "worker_id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Set<ProjectEntity> projects;

    @OneToMany(mappedBy = "lead")
    private Set<ProjectEntity> leadingProjects = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<ActivityEntity> entries = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<TokenEntity> tokens;
}
