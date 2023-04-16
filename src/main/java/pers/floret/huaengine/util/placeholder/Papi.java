package pers.floret.huaengine.util.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pers.floret.huaengine.HuaEngine;
import pers.floret.huaengine.config.MythicSpawnerTime;
import pers.floret.huaengine.runtime.DataManager;
import pers.floret.huaengine.runtime.TaskRunnable;

import java.util.UUID;

public class Papi extends PlaceholderExpansion {

    @Override
    public @NotNull String getAuthor() {
        return "floret";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "hua";
    }

    @Override
    public @NotNull String getVersion() {
        return HuaEngine.ins().getDescription().getVersion();
    }

    @Override
    public String getRequiredPlugin() {
        return HuaEngine.ins().getDescription().getName();
    }

    @Override
    public boolean canRegister() {
        // This instance is assigned in canRegister()
        return Bukkit.getPluginManager().getPlugin(getRequiredPlugin()) != null;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        UUID playerId = player.getUniqueId();
        if ("title".equals(params)) {
            return DataManager.get().getTitleName(playerId);
        }
        for (String spawnName : MythicSpawnerTime.get().spawnerList) {
            String key = "spawner_" + spawnName;
            if (key.equals(params)) {
                int cooldown = TaskRunnable.getSpawnCooldown(spawnName);
                String temp = MythicSpawnerTime.get().defSpawnTime;
                if (cooldown != 0) {
                    temp = String.valueOf(cooldown);
                }
                return temp;
            }
        }
        for (String varName : DataManager.get().getVar(playerId).keySet()) {
            String key = "var_" + varName;
            if (key.equals(params)) {
                long var = DataManager.get().getVar(playerId).get(varName).getVarValue();
                return String.valueOf(var);
            }
        }
        return null;
    }
}
