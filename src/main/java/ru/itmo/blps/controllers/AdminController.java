package ru.itmo.blps.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.blps.DAO.mappers.ProjectMapper;
import ru.itmo.blps.DAO.mappers.UserMapper;
import ru.itmo.blps.controllers.inputModel.SetRoleModel;
import ru.itmo.blps.controllers.inputModel.SetStatusModel;

/**
 * @author TheHs
 */
@RestController
@RequestMapping("/admin")
@Secured({"ROLE_ADMIN"})
@AllArgsConstructor
public class AdminController {
    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;

    @PostMapping("/setRole")
    @Transactional(rollbackFor = PreAuthenticatedCredentialsNotFoundException.class)
    public void setRole(@RequestBody SetRoleModel req) {
        userMapper.setRole(req.getUserId(), req.getRole());
    }

    @PostMapping("/setProjectStatus")
    @Transactional(rollbackFor = IllegalAccessException.class)
    public void setProjectMapper(@RequestBody SetStatusModel req) {
        projectMapper.updateStatus(req.getProjectId(), req.getStatus());
    }
}
