package br.com.gateway.aksnes.security.persistence.repository;

import br.com.gateway.aksnes.security.persistence.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<RoleEntity, Integer> {
}
