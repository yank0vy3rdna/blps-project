package ru.itmo.blps.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.stereotype.Service;
import ru.itmo.blps.DAO.entities.User;
import ru.itmo.blps.DAO.mappers.UserMapper;

@Service
public class AuthCheck {

    private final UserMapper userMapper;

    public AuthCheck(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User authCheck(Authentication authentication, int role){
        Object principal = authentication.getPrincipal();
        if (principal == null) {
            throw new PreAuthenticatedCredentialsNotFoundException("auth error");
        }
        if (!(principal instanceof UserDetails)) {
            throw new PreAuthenticatedCredentialsNotFoundException("principal is not user");
        }
        UserDetails userDetails = (UserDetails) principal;
        User user = userMapper.findUserByLogin(userDetails.getUsername());
        if (user.getRole() != role){
            throw new PreAuthenticatedCredentialsNotFoundException("wrong role");
        }
        return user;
    }
}
