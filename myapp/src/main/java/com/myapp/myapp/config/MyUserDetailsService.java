package com.myapp.myapp.config;

import com.myapp.myapp.model.User;
import com.myapp.myapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist!"));

        // zamiana listy ról na tablicę ich nazw
        List<String> rolesNames = user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toList());
        String[] rolesNamesArray = new String[rolesNames.size()];
        rolesNames.toArray(rolesNamesArray);

        return org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password("{noop}" +user.getPassword())
                .roles(rolesNamesArray)
                .build();
    }
}
