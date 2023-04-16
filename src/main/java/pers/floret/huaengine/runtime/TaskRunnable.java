package pers.floret.huaengine.runtime;

import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.spawning.spawners.MythicSpawner;
import org.bukkit.Bukkit;
import pers.floret.huaengine.config.Config;
import pers.floret.huaengine.config.MythicSpawnerTime;
import pers.floret.huaengine.util.LoggerUtil;
import pers.floret.huaengine.util.hooks.MythicMobsHook;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 执行循环任务
 */
public class TaskRunnable {

    private static final Map<String, Integer> spawns = new HashMap<>();

    public static void countdown() {
        if (!Config.get().varEnable) return;
        long nowVal = 1;
        Bukkit.getOnlinePlayers().forEach(player -> {
            UUID playerId = player.getUniqueId();
            if (DataManager.get().getVar(playerId).isEmpty()) return;
            DataManager.get().getVar(playerId).forEach((key, var) -> {
                if (var.isTiming()) {
                    long newVal = var.getVarValue() + nowVal;
                    var.setVarValue(newVal);
                }
                if (var.getVarValue() != 0) {
                    if (var.isCountdown()) {
                        long newVal = var.getVarValue() - nowVal;
                        var.setVarValue(newVal);
                    }
                }
                LoggerUtil.debug(player.getName() + "的变量->" + key + "值为: " + var.getVarValue());
            });
        });
    }

    public static void readSpawnTime() {
        if (!Config.get().spawnerEnable) return;
        if (!MythicMobsHook.isRun()) return;
        for (String spawnName : MythicSpawnerTime.get().spawnerList) {
            if (MythicMobs.inst().getSpawnerManager().getSpawnerByName(spawnName) != null) {
                MythicSpawner m = MythicMobs.inst().getSpawnerManager().getSpawnerByName(spawnName);
                spawns.put(spawnName, m.getRemainingWarmupSeconds());
                LoggerUtil.debug(spawnName + " 倒计时: " + spawns.get(spawnName));
            }
        }
    }
    public static int getSpawnCooldown(String spawnName) {
        return spawns.get(spawnName);
    }
}
