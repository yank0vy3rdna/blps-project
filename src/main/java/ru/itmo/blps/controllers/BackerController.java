package ru.itmo.blps.controllers;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.entities.User;
import ru.itmo.blps.DAO.mappers.ProjectMapper;
import ru.itmo.blps.DAO.mappers.UserMapper;
import ru.itmo.blps.controllers.inputModel.BackModel;
import ru.itmo.blps.services.BackMessageProducerService;
import ru.itmo.blps.services.BackService;
import ru.itmo.blps.services.Impl.BackMessageProducerServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/backing")
@AllArgsConstructor
public class BackerController {

    private static final Logger logger = LoggerFactory.getLogger(BackerController.class);

    private final ProjectMapper projectMapper;
    private final BackMessageProducerService backMessageProducerService;
    private final UserMapper userMapper;

    @Secured({"ROLE_REGULAR"})
    @Transactional
    @GetMapping("/projects/")
    List<Project> backedProjects(Authentication authentication) {
        User user = userMapper.findUserByLogin(authentication.getName());
        return projectMapper.getBackedProjects(user.getId());
    }

    @Secured({"ROLE_REGULAR"})
    @Transactional(rollbackFor = PreAuthenticatedCredentialsNotFoundException.class)
    @PostMapping("/back/")
    void backProject(@RequestBody BackModel req) {
        // User user = userMapper.findUserByLogin(authentication.getName());

        // We should set backer id in BackModel too.
        logger.info("Well at the least I'm here. Backer {} backing project {}", req.getBackerUsername(), req.getProjectId());
        backMessageProducerService.sendBackMessage("back", req);
    }

}
