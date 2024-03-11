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
@Table(name = "clients")
@SQLDelete(sql = "UPDATE clients SET deletedStatus = 'DELETED' WHERE id=?")
@SQLRestriction("deletedStatus <> 'DELETED'")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String city;
    @Column(name = "postal_code")
    private String postalCode;

    @Column(columnDefinition = "varchar(255) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private DeletedStatus deletedStatus = DeletedStatus.ACTIVE;

    @ManyToOne(optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    private CountryEntity country;

    @OneToMany(mappedBy = "customer")
    private Set<ProjectEntity> projects = new HashSet<>();

    @OneToMany(mappedBy = "client")
    private Set<ActivityEntity> entries = new HashSet<>();

}
