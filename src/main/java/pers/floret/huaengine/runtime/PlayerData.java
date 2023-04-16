package pers.floret.huaengine.runtime;

import java.util.Map;

public class PlayerData {

    private String titleName;
    private boolean isModify;
    private boolean isFirstJoin;
    private Map<String, Var> varMap;

    public PlayerData(String titleName, boolean isModify, boolean isFirstJoin, Map<String, Var> varMap) {
        this.titleName = titleName;
        this.isModify = isModify;
        this.isFirstJoin = isFirstJoin;
        this.varMap = varMap;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public boolean isModify() {
        return isModify;
    }

    public void setModify(boolean modify) {
        this.isModify = modify;
    }

    public boolean isFirstJoin() {
        return isFirstJoin;
    }

    public void setFirstJoin(boolean firstJoin) {
        isFirstJoin = firstJoin;
    }

    public Map<String, Var> getVarMap() {
        return varMap;
    }
}
