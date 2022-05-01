package ru.itmo.blps.services.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itmo.blps.DAO.entities.User;
import ru.itmo.blps.DAO.mappers.UserMapper;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserMapper userMapper;

    public UserDetailsServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (username != null) {
            User u = userMapper.findUserByLogin(username);
            if (Objects.isNull(u)) {
                throw new UsernameNotFoundException(String.format("User %s is not found", username));
            }
            if (u.getRole() == 1) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                logger.info("User {} is admin", username);
            } else {
                authorities.add(new SimpleGrantedAuthority("ROLE_REGULAR"));
                logger.info("User {} is regular", username);
            }
            return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), true, true, true, true, authorities);
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
        logger.info("It's an anonymous user");
        return new org.springframework.security.core.userdetails.User("anon", "anon", true, true, true, true, authorities);
    }
}
