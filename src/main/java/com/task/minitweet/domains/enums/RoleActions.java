package com.task.minitweet.domains.enums;


public enum RoleActions {
    ADD,
    REMOVE;

    public static RoleActions fromString(String action) {
        for (RoleActions roleAction : RoleActions.values()) {
            if (roleAction.name().equalsIgnoreCase(action)) {
                return roleAction;
            }
        }
        throw new IllegalArgumentException("No constant with action " + action + " found");
    }
}
