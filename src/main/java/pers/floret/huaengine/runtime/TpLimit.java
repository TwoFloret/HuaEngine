package pers.floret.huaengine.runtime;

public class TpLimit {
    private final int limitLevel;
    private final String limitWorldName;

    public TpLimit(int limitLevel, String limitWorldName) {
        this.limitLevel = limitLevel;
        this.limitWorldName = limitWorldName;
    }

    public int getLimitLevel() {
        return limitLevel;
    }

    public String getLimitWorldName() {
        return limitWorldName;
    }
}
