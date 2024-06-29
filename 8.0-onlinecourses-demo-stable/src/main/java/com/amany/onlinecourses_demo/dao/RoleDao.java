package com.amany.onlinecourses_demo.dao;

import com.amany.onlinecourses_demo.entity.Role;

public interface RoleDao {
    public Role findRoleByName (String theRoleName);
}
