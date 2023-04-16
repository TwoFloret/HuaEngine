package pers.floret.huaengine.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataManager {

    private static final DataManager instance = new DataManager();

    private final Map<UUID, PlayerData> dataMap = new HashMap<>();

    private boolean apDamage = false;

    public boolean isApDamage() {
        return apDamage;
    }

    public void setApDamage(boolean apDamage) {
        this.apDamage = apDamage;
    }

    private DataManager() {
        // 私有构造方法，防止被外部类直接实例化
    }

    public static DataManager get() {
        return instance;
    }

    /**
     *
     * @param playerId 玩家uid
     * @return 获取玩家数据
     */
    public PlayerData getPlayerData(UUID playerId) {
        return dataMap.get(playerId);
    }

    /**
     * 增加玩家数据
     * @param playerId 玩家uid
     * @param playerData 玩家数据
     */
    public void addData(UUID playerId, PlayerData playerData) {
        dataMap.put(playerId, playerData);
    }

    /**
     * 删除玩家数据
     * @param playerId 玩家uid
     */
    public void removeData(UUID playerId) {
        dataMap.remove(playerId);
    }
    /**
     *
     * @param playerId 玩家uid
     * @return 获取称号名称
     */
    public String getTitleName(UUID playerId) {
        return getPlayerData(playerId).getTitleName();
    }
    /**
     * 设置玩家称号
     * @param playerId 玩家uid
     * @param titleName 称号名称
     */
    public void setTitleName(UUID playerId, String titleName) {
        getPlayerData(playerId).setTitleName(titleName);
    }
    /**
     *
     * @param playerId 玩家uid
     * @return 获取玩家是否可修改称号
     */
    public boolean isModify(UUID playerId) {
        return getPlayerData(playerId).isModify();
    }
    /**
     * 设置玩家是否可以修改称号
     * @param playerId 玩家uid
     * @param isModify 是否可修改
     */
    public void setModify(UUID playerId, boolean isModify) {
        getPlayerData(playerId).setModify(isModify);
    }

    /**
     *
     * @param playerId 玩家uid
     * @return 获取玩家是否是第一次进入服务器
     */
    public boolean isFirstJoin(UUID playerId) {
        return getPlayerData(playerId).isFirstJoin();
    }

    /**
     * 设置玩家是否第一次进入服务器
     * @param playerId 玩家uid
     * @param firstJoin 是否第一次进入服务器
     */
    public void setFirstJoin(UUID playerId, boolean firstJoin) {
        getPlayerData(playerId).setFirstJoin(firstJoin);
    }
    /**
     *
     * @param playerId 玩家uid
     * @return 获取玩家所有变量数据
     */
    public Map<String, Var> getVar(UUID playerId) {
        return dataMap.get(playerId).getVarMap();
    }

    /**
     * 增加一个变量
     * @param playerId 玩家uid
     * @param varName 变量名
     * @param varValue 变量值
     * @param isCountdown 是否启用倒计时
     * @param isTiming 是否启用计时
     */
    public void addVar(UUID playerId, String varName, long varValue, boolean isCountdown, boolean isTiming) {
        Var var = new Var(varValue, isCountdown, isTiming);
        getVar(playerId).put(varName, var);
    }

    /**
     * 删除一个变量
     * @param playerId 玩家uid
     * @param varName 变量名
     */
    public void removeVar(UUID playerId, String varName) {
        getVar(playerId).remove(varName);
    }
}
