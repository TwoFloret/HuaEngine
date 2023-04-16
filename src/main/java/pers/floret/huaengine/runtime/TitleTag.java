package pers.floret.huaengine.runtime;

public class TitleTag {

    private final String tag;
    private final String permission;

    public TitleTag(String tag, String permission) {
        this.tag = tag;
        this.permission = permission;
    }
    public String getTag() {
        return tag;
    }

    public String getPermission() {
        return permission;
    }
}
