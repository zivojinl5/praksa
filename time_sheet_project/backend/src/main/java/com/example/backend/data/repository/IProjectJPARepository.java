package com.example.backend.data.repository;

import java.util.HashMap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.core.search_model.ProjectSearchModel;
import com.example.backend.data.entity.ProjectEntity;

public interface IProjectJPARepository extends JpaRepository<ProjectEntity, Long> {

        @Query("SELECT p FROM ProjectEntity p WHERE p.name LIKE CONCAT(:#{#projectSearchModel.startingLetter}, '%')" +
                        " AND (:#{#projectSearchModel.searchString} IS NULL OR p.name LIKE CONCAT('%', :#{#projectSearchModel.searchString}, '%'))")
        Page<ProjectEntity> searchClients(ProjectSearchModel projectSearchModel, Pageable pageable);

        @Query("SELECT DISTINCT p.id, p.name FROM ProjectEntity p " +
                        "JOIN p.customer c " +
                        "JOIN p.workers w " +
                        "WHERE c.id = :clientId AND w.id = :teamMemberId")
        HashMap<Long, String> findProjectNamesAndIdsByClientIdAndTeamMemberId(
                        @Param("clientId") Long clientId,
                        @Param("teamMemberId") Long teamMemberId);

}