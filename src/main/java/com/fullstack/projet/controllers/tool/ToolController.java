package com.fullstack.projet.controllers.tool;

import com.fullstack.projet.controllers.ErrorHandlingUtils;
import com.fullstack.projet.exceptions.ValidationException;
import com.fullstack.projet.models.tool.Tool;
import com.fullstack.projet.models.tool.ToolDto;
import com.fullstack.projet.services.tool.IToolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tools")
public class ToolController {

    private final Logger LOG = LoggerFactory.getLogger(ToolController.class);

    private final IToolService toolService;

    public ToolController(IToolService toolService){
        this.toolService = toolService;
    }

    @GetMapping("/search")
    public List<ToolDto> searchTools(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String domain,
            @RequestParam(required = false) String description) {
        LOG.info("Searching tools");
        return toolService.searchTools(name, domain, description).stream().map(ToolDto::fromTool).toList();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Object> createTool(@RequestBody Tool tool) {
        LOG.info("Creating tool");
        try{
            tool = toolService.save(tool);
            LOG.info("Successfully created tool");
            return ResponseEntity.ok(ToolDto.fromTool(tool));
        } catch (ValidationException e) {
            return ErrorHandlingUtils.handleBadRequest(LOG,e);
        } catch (Exception e) {
            return ErrorHandlingUtils.handleInternalServerError(LOG, "L'ajout de l'outil a echoué", e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteTool(@PathVariable Long id) {
        LOG.info("Deleting tool {}", id);
        toolService.deleteById(id);
        LOG.info("Successfully deleted tool {}", id);
    }
}
