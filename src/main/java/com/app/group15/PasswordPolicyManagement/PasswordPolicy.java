package com.app.group15.PasswordPolicyManagement;

import com.app.group15.Persistence.Persistence;

import java.io.Serializable;

public class PasswordPolicy extends Persistence implements Serializable {


    private static final long serialVersionUID = 1L;
    private int policyId;
    private String policyName;
    private String policyDescription;
    private boolean is_active;
    private String policyValue;

    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getPolicyDescription() {
        return policyDescription;
    }

    public void setPolicyDescription(String policyDescription) {
        this.policyDescription = policyDescription;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    @Override
    public String toString() {
        return "PasswordPolicy{" +
                "policyId=" + policyId +
                ", policyName='" + policyName + '\'' +
                ", policyDescription='" + policyDescription + '\'' +
                ", is_active=" + is_active +
                ", policyValue='" + policyValue + '\'' +
                '}';
    }

    public String getPolicyValue() {
        return policyValue;
    }

    public void setPolicyValue(String policyValue) {
        this.policyValue = policyValue;
    }
}
