package ru.itmo.blps.services.Impl;

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

            } else {
                authorities.add(new SimpleGrantedAuthority("ROLE_REGULAR"));
            }
            return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), true, true, true, true, authorities);
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));

        return new org.springframework.security.core.userdetails.User("anon", "anon", true, true, true, true, authorities);
    }
}
