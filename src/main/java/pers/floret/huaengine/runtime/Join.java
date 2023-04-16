package pers.floret.huaengine.runtime;

import java.util.List;

public class Join {
    private String permission;
    private List<String> firstActions;
    private List<String> everyActions;

    public Join(String permission, List<String> firstActions, List<String> everyActions) {
        this.permission = permission;
        this.firstActions = firstActions;
        this.everyActions = everyActions;
    }

    public String getPermission() {
        return permission;
    }

    public List<String> getFirstActions() {
        return firstActions;
    }

    public List<String> getEveryActions() {
        return everyActions;
    }
}
