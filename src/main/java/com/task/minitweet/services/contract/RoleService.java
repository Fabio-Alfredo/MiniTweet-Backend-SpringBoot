package com.task.minitweet.services.contract;


import com.task.minitweet.domains.models.Role;

import java.util.List;

public interface RoleService {
    Role findRoleById(String id);
    List<Role> findAllRoles();
}
