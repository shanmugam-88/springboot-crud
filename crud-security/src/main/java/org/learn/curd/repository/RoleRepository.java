package org.learn.curd.repository;


import org.learn.curd.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    void delete(Role role);

}
