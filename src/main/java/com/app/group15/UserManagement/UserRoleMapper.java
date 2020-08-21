package com.app.group15.UserManagement;

import com.app.group15.Persistence.Persistence;

public class UserRoleMapper extends Persistence {

    private int userId;
    private int roleId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

}
