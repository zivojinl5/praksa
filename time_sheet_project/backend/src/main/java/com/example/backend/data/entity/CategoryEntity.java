package com.example.backend.data.entity;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.example.backend.enums.DeletedStatus;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "categories")
@SQLDelete(sql = "UPDATE categories SET deletedStatus = 'DELETED' WHERE id=?")
@SQLRestriction("deletedStatus <> 'DELETED'")
public class CategoryEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Column(columnDefinition = "varchar(255) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private DeletedStatus deletedStatus = DeletedStatus.ACTIVE;

    @OneToMany(mappedBy = "category")
    private Set<ActivityEntity> entries = new HashSet<>();

}