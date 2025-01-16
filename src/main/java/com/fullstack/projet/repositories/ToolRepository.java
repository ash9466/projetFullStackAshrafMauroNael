package com.fullstack.projet.repositories;

import com.fullstack.projet.models.tool.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToolRepository extends JpaRepository<Tool, Long> {

    @Query(value = "SELECT * FROM tool t WHERE " +
             "(:name IS NULL OR LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
             "(:domain IS NULL OR LOWER(t.domain) LIKE LOWER(CONCAT('%', :domain, '%'))) AND " +
             "(:description IS NULL OR LOWER(t.description) LIKE LOWER(CONCAT('%', :description, '%')))", nativeQuery = true)
    List<Tool> searchTools(@Param("name") String name,
                            @Param("domain") String domain,
                            @Param("description") String description);
}


