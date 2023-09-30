package com.Spring.secureBootJwt.service;

import com.Spring.secureBootJwt.dto.CreateUserDTO;
import com.Spring.secureBootJwt.model.UserEntity;

public interface IUserService {
    public UserEntity createUser(CreateUserDTO createUserDTO);

    public void deleteUser(Long id);
}
