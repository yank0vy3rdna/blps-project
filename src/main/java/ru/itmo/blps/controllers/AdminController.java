package ru.itmo.blps.controllers;

import lombok.AllArgsConstructor;
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
import ru.itmo.blps.controllers.inputModel.SetRoleModel;
import ru.itmo.blps.controllers.inputModel.SetStatusModel;
import ru.itmo.blps.services.BackService;

import java.security.AccessControlException;

/**
 * @author TheHs
 */
@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;

    @GetMapping("/setRole")
    @Transactional(rollbackFor = PreAuthenticatedCredentialsNotFoundException.class)
    public void setRole(@RequestBody SetRoleModel req) throws IllegalAccessException {
        var user = getUserFromContext();
        var role = user.getRole();
        if (role != 0 && role != 1){
            throw new IllegalArgumentException("Illegal role value.");
        }

        var userTargetRole = userMapper.findUserById(req.getUserId()).getRole();


        if (role != 1 || userTargetRole == 1){
            throw new IllegalAccessException("You have no right to set role.");
        }

        userMapper.setRole(req.getUserId(), req.getRole());

    }

    @GetMapping("/setProjectStatus")
    @Transactional(rollbackFor = IllegalAccessException.class)
    public void setProjectMapper(@RequestBody SetStatusModel req) throws IllegalAccessException {
        var user = getUserFromContext();
        if (user.getRole() != 1){
            throw new IllegalAccessException("You have no right to set project status.");
        }

        projectMapper.updateStatus(req.getProjectId(), req.getStatus());
    }

    private User getUserFromContext() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal == null) {
            throw new PreAuthenticatedCredentialsNotFoundException("auth error");
        }
        if (!(principal instanceof UserDetails)) {
            throw new PreAuthenticatedCredentialsNotFoundException("principal is not user");
        }
        UserDetails userDetails = (UserDetails) principal;
        return userMapper.findUserByLogin(userDetails.getUsername());
    }
}
