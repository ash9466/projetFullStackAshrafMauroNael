package com.fullstack.projet.controllers;

import com.fullstack.projet.models.Tool;
import com.fullstack.projet.services.tool.IToolService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tools")
public class ToolController {

    private final IToolService toolService;

    public ToolController(IToolService toolService){
        this.toolService = toolService;
    }

    @GetMapping("/search")
    public List<Tool> searchTools(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String domain,
            @RequestParam(required = false) String description) {
        return toolService.searchTools(name, domain, description);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Tool createTool(@RequestBody Tool tool) {
        return toolService.save(tool);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteTool(@PathVariable Long id) {
        toolService.deleteById(id);
    }
}
