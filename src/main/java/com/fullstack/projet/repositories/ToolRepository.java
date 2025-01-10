package com.fullstack.projet.repositories;

import com.fullstack.projet.models.Tool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ToolRepository extends JpaRepository<Tool, Long> {
     List<Tool> findByDomainContaining(String domain);
}


