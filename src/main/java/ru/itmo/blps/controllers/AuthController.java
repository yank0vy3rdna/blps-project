package ru.itmo.blps.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.itmo.blps.DAO.entities.User;
import ru.itmo.blps.DAO.mappers.UserMapper;
import ru.itmo.blps.auth.JwtResponse;
import ru.itmo.blps.auth.JwtTokenUtil;

import javax.annotation.security.RolesAllowed;
import javax.security.auth.message.AuthException;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final UserMapper mapper;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
    
    @Transactional
    @PostMapping(path = "/login/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    @Secured({"ROLE_ANONYMOUS"})
    ResponseEntity<?> getAuthUser(@RequestBody User user) throws Exception {

        authenticate(user.getUsername(), user.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(user.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @Transactional(rollbackFor = AuthException.class)
    @PostMapping("/signup/")
    @Secured({"ROLE_ANONYMOUS"})
    User signup(@RequestBody User user) throws AuthException {

        if (mapper.findUserByLogin(user.getUsername()) != null) {
            throw new AuthException("user already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        mapper.insertUser(user);
        return user;
    }
}
