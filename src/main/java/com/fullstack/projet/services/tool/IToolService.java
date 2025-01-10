package com.fullstack.projet.services.tool;

import com.fullstack.projet.models.Tool;

import java.util.List;
import java.util.Optional;

public interface IToolService {
    List<Tool> getAllTools();
    Tool save(Tool tool);
    void deleteById(Long id);
    Optional<Tool> findById(Long id);
    List<Tool> searchByDomain(String domain);
}
