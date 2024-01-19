package com.group12.lawfirm.common;

public enum LogType {
    FORGET_PASSWORD("Reset password"),UPDATE_PASSWORD("Update Password"),BATCH_DELETE("Batch delete"),ADD_OR_UPDATE("Add or edit"),DELETE("Delete"),LOGIN("Login"),REGISTER("Register");

    private String value;

    public String getValue() {
        return value;
    }

    LogType(String value) {
        this.value = value;
    }
}
