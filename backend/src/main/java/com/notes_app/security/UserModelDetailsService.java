package com.notes_app.security;

import com.notes_app.dao.UserDao;
import com.notes_app.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Authenticate a user from the database.
 */
@Service
public class UserModelDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserModelDetailsService.class);
    private final UserDao userDao;

    public UserModelDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating user '{}'", login);

        // unwrap Optional<User>, or throw if not found
        User user = userDao.getUserByUsername(login)
                .orElseThrow(() -> new UsernameNotFoundException("User " + login + " not found"));

        return createSpringSecurityUser(login, user);
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String login, User user) {

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                grantedAuthorities);
    }
}
