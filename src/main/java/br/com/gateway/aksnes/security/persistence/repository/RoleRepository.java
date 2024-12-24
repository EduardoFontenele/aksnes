package br.com.gateway.aksnes.security.persistence.repository;

import br.com.gateway.aksnes.security.persistence.model.RoleEntity;
import br.com.gateway.aksnes.security.persistence.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByRole(Role role);
}
