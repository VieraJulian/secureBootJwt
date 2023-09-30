package com.Spring.secureBootJwt.service;

import com.Spring.secureBootJwt.model.UserEntity;
import com.Spring.secureBootJwt.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("the user with the name " + username + "does not exist."));

        Collection<? extends GrantedAuthority> authorities = userEntity.getRoles().stream()
                .map(role ->
                        new SimpleGrantedAuthority("ROLE_".concat(role.getName().name()))).collect(Collectors.toSet());

        return new User(userEntity.getUsername(), userEntity.getPassword(), true, true, true, true, authorities);
    }
}
