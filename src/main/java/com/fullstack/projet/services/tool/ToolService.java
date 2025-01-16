package com.fullstack.projet.services.tool;

import com.fullstack.projet.models.tool.Tool;
import com.fullstack.projet.models.user.User;
import com.fullstack.projet.repositories.ToolRepository;
import com.fullstack.projet.repositories.UserRepository;
import com.fullstack.projet.services.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ToolService implements IToolService {

    private final ToolRepository toolRepository;

    private final UserRepository userRepository;

    public ToolService(ToolRepository toolRepository, UserRepository userRepository) {
        this.toolRepository = toolRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<Tool> getAllTools() {
        return toolRepository.findAll();
    }

    @Override
    @Transactional
    public Tool save(Tool tool) {
        tool.validate();

        Optional<User> currentUser = userRepository.findByEmail(UserUtils.getCurrentUserEmail());
        tool.setCreator(currentUser.get());

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
