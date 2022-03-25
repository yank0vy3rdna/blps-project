package ru.itmo.blps.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.blps.DAO.entities.User;
import ru.itmo.blps.DAO.mappers.ProjectMapper;
import ru.itmo.blps.DAO.mappers.UserMapper;
import ru.itmo.blps.auth.AuthCheck;
import ru.itmo.blps.controllers.inputModel.SetRoleModel;
import ru.itmo.blps.controllers.inputModel.SetStatusModel;
import ru.itmo.blps.services.BackService;

import java.security.AccessControlException;

/**
 * @author TheHs
 */
@RestController
@RequestMapping("/admin")
@Secured({"ROLE_ADMIN"})
@AllArgsConstructor
public class AdminController {
    private final ProjectMapper projectMapper;
    private final AuthCheck authCheck;
    private final UserMapper userMapper;

    @GetMapping("/setRole")
    @Transactional(rollbackFor = PreAuthenticatedCredentialsNotFoundException.class)
    public void setRole(@RequestBody SetRoleModel req, Authentication authentication) throws IllegalAccessException {
        authCheck.authCheck(authentication, 1);
        if (req.getRole() != 0) {
            throw new IllegalArgumentException("Illegal role value.");
        }
        userMapper.setRole(req.getUserId(), req.getRole());
    }

    @GetMapping("/setProjectStatus")
    @Transactional(rollbackFor = IllegalAccessException.class)
    public void setProjectMapper(@RequestBody SetStatusModel req, Authentication authentication) throws IllegalAccessException {
        User user = authCheck.authCheck(authentication, 1);
        if (user.getRole() != 1) {
            throw new IllegalAccessException("You have no right to set project status.");
        }

        projectMapper.updateStatus(req.getProjectId(), req.getStatus());
    }
}
