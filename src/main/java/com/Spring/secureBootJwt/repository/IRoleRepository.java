package com.Spring.secureBootJwt.repository;

import com.Spring.secureBootJwt.model.ERole;
import com.Spring.secureBootJwt.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(ERole eRole);
}
