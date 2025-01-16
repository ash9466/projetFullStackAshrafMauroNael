package com.fullstack.projet.services.tool;

import com.fullstack.projet.models.Tool;
import com.fullstack.projet.repositories.ToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToolService implements IToolService {

    @Autowired
    private ToolRepository toolRepository;

    @Override
    public List<Tool> getAllTools() {
        return toolRepository.findAll();
    }

    @Override
    public Tool save(Tool tool) {
        return toolRepository.save(tool);
    }

    @Override
    public void deleteById(Long id) {
        toolRepository.deleteById(id);
    }

    @Override
    public Optional<Tool> findById(Long id) {
        return toolRepository.findById(id);
    }

    @Override
    public List<Tool> searchTools(String name, String domain, String description) {
        return toolRepository.searchTools(name, domain, description);
    }
}
