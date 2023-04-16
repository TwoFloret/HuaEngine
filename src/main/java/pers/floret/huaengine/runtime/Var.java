package pers.floret.huaengine.runtime;

public class Var {
    private long varValue;
    private boolean isCountdown;
    private boolean isTiming;

    public Var(long varValue, boolean isCountdown, boolean isTiming) {
        this.varValue = varValue;
        this.isCountdown = isCountdown;
        this.isTiming = isTiming;
    }

    public long getVarValue() {
        return varValue;
    }

    public void setVarValue(long varValue) {
        this.varValue = varValue;
    }

    public boolean isCountdown() {
        return isCountdown;
    }

    public void setCountdown(boolean isCountdown) {
        this.isCountdown = isCountdown;
    }

    public boolean isTiming() {
        return isTiming;
    }

    public void setTiming(boolean isTiming) {
        this.isTiming = isTiming;
    }
}
