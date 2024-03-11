package com.example.backend.data.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.data.entity.ActivityEntity;

public interface IActivityJPARepository extends JpaRepository<ActivityEntity, Long> {

        @Query("SELECT a FROM ActivityEntity a " +
                        "WHERE a.date = :date AND a.user.id = :userId")
        List<ActivityEntity> findAllByDateAndUserId(
                        @Param("date") LocalDate date,
                        @Param("userId") Long userId);

        @Query("SELECT a FROM ActivityEntity a WHERE a.date >= :startDate AND a.date <= :endDate AND a.user.id = :userId")
        Stream<ActivityEntity> search(
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("userId") Long userId);
}
