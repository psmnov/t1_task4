package ru.t1.tasks;

import org.springframework.stereotype.Service;

@Service
public class MyService {

    private final RoleService roleService;

    public MyService(RoleService roleService) {
        this.roleService = roleService;
    }

    public void useRoles() {
        roleService.getRolesAsString()
                .subscribe(roles -> {
                    System.out.println("Roles: " + roles);
                });
    }
}
