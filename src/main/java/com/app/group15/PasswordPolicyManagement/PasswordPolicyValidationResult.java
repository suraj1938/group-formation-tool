package com.app.group15.PasswordPolicyManagement;

import java.util.List;

public class PasswordPolicyValidationResult {
    private boolean result;
    private String message;
    private List<String> failedPolicyList;

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<String> getFailedPolicyList() {
        return failedPolicyList;
    }

    public void setFailedPolicyList(List<String> failedPolicyList) {
        this.failedPolicyList = failedPolicyList;
    }

    public String isMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
