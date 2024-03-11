package com.example.backend.data.entity;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.example.backend.enums.DeletedStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@SQLDelete(sql = "UPDATE countries SET deletedStatus = 'DELETED' WHERE id=?")
@SQLRestriction("deletedStatus <> 'DELETED'")
@Entity
@Table(name = "countries")
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "country name can't be null")
    @Size(min = 5, max = 20, message = "country name must be between 5 and 20 characters")
    @Column(unique = true) // Add unique constraint at the column level
    private String name;

    @Column(columnDefinition = "varchar(255) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private DeletedStatus deletedStatus = DeletedStatus.ACTIVE;

    @OneToMany(mappedBy = "country")
    private Set<ClientEntity> clients = new HashSet<>();
}
