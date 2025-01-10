package com.fullstack.projet.controllers;

import com.fullstack.projet.models.Tool;
import com.fullstack.projet.services.tool.IToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tools")
public class ToolController {

    @Autowired
    private IToolService toolService;

    @GetMapping
    public List<Tool> getAllTools() {
        return toolService.getAllTools();
    }

    @PostMapping
    public Tool createTool(@RequestBody Tool tool) {
        return toolService.save(tool);
    }

    @DeleteMapping("/{id}")
    public void deleteTool(@PathVariable Long id) {
        toolService.deleteById(id);
    }
}
