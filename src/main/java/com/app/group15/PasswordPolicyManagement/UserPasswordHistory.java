package com.app.group15.PasswordPolicyManagement;

import com.app.group15.Persistence.Persistence;

import java.io.Serializable;

public class UserPasswordHistory extends Persistence implements Serializable {


    private static final long serialVersionUID = 1L;
    private String historyPassword;
    private int userId;

    public String getHistoryPassword() {
        return historyPassword;
    }

    public void setHistoryPassword(String historyPassword) {
        this.historyPassword = historyPassword;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
