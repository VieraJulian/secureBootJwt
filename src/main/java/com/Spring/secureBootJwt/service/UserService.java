package com.Spring.secureBootJwt.service;

import com.Spring.secureBootJwt.dto.CreateUserDTO;
import com.Spring.secureBootJwt.model.ERole;
import com.Spring.secureBootJwt.model.RoleEntity;
import com.Spring.secureBootJwt.model.UserEntity;
import com.Spring.secureBootJwt.repository.IRoleRepository;
import com.Spring.secureBootJwt.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IUserRepository userRepo;

    @Autowired
    private IRoleRepository roleRepo;

    @Override
    public UserEntity createUser(CreateUserDTO createUserDTO) {

        Set<RoleEntity> roles = createUserDTO.getRoles().stream()
                .map(roleName -> {
                    ERole roleEnum = ERole.valueOf(roleName.toUpperCase());

                    return roleRepo.findByName(roleEnum)
                            .orElseGet(() -> {
                                RoleEntity newRole = RoleEntity.builder().name(roleEnum).build();
                                return roleRepo.save(newRole);
                            });

                })
                .collect(Collectors.toSet());


        UserEntity userEntity = UserEntity.builder()
                .username(createUserDTO.getUsername())
                .email(createUserDTO.getEmail())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .roles(roles)
                .build();

        return userRepo.save(userEntity);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
}
