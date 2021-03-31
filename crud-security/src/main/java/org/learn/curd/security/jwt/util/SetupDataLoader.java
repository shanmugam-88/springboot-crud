package org.learn.curd.security.jwt.util;

import org.learn.curd.model.Privilege;
import org.learn.curd.model.Role;
import org.learn.curd.model.User;
import org.learn.curd.repository.PrivilegeRepository;
import org.learn.curd.repository.RoleRepository;
import org.learn.curd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (privilegeRepository.count()>1) {
            return;
        }
        // == create initial privileges
        Privilege createPrivilege = createPrivilegeIfNotFound("CREATE_ARTICLE");
        Privilege updatePrivilege = createPrivilegeIfNotFound("UPDATE_ARTICLE");
        Privilege deletePrivilege = createPrivilegeIfNotFound("DELETE_ARTICLE");
        Privilege createUser = createPrivilegeIfNotFound("CREATE_USER");

        // == create initial roles
        List<Privilege> adminPrivileges = Arrays.asList(createPrivilege, updatePrivilege,deletePrivilege,createUser);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        List<Privilege> rolePrivileges = Arrays.asList(createPrivilege);
        createRoleIfNotFound("ROLE_AUTHOR", rolePrivileges);
        List<Privilege> rootPrivileges = Arrays.asList(createPrivilege, updatePrivilege);
        createRoleIfNotFound("ROLE_SUPER_AUTHOR", rootPrivileges);


        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        User user = new User();
        user.setFirstName("Admin");
        user.setLastName("User");
        user.setEmail("admin@mail.com");
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode("pass"));
        user.setRoles(Arrays.asList(adminRole));
        user.setEnabled(true);
        userRepository.save(user);

        /*Role basicRole = roleRepository.findByName("ROLE_AUTHOR");
        User basicUser = new User();
        basicUser.setFirstName("User");
        basicUser.setLastName("User");
        basicUser.setEmail("user@test.com");
        basicUser.setPassword(passwordEncoder.encode("pass"));
        basicUser.setRoles(Arrays.asList(basicRole));
        basicUser.setEnabled(true);
        userRepository.save(basicUser);*/
    }

    @Transactional
    private Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    private Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

}