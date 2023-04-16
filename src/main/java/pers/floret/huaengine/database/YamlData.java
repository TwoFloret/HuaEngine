package pers.floret.huaengine.database;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pers.floret.huaengine.HuaEngine;
import pers.floret.huaengine.runtime.DataManager;
import pers.floret.huaengine.runtime.PlayerData;
import pers.floret.huaengine.runtime.Var;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class YamlData {

    private final File dataFolder = HuaEngine.ins().getDataFolder();

    public void load(Player player) {
        UUID playerId = player.getUniqueId();
        PlayerData playerData = load(playerId);
        DataManager.get().addData(playerId, playerData);
    }
    private PlayerData load(UUID playerId) {
        File file = new File(dataFolder + "/data/", playerId + ".yml");
        FileConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            return new PlayerData("", true, true, new HashMap<>());
        }else {
            String titleName = yaml.getString("Title.Name", "");
            boolean isModify = yaml.getBoolean("Title.Modify", true);
            boolean isFirstJoin = yaml.getBoolean("Join.isFirstJoin", true);
            Map<String, Var> varMap = new HashMap<>();
            ConfigurationSection section = yaml.getConfigurationSection("Var");
            if (section != null) {
                section.getKeys(false).forEach( key -> {
                    int value = section.getInt(key + ".value");
                    boolean isCountdown = section.getBoolean(key + ".isCountdown");
                    boolean isTiming = section.getBoolean(key + ".isTiming");
                    Var var = new Var(value, isCountdown, isTiming);
                    varMap.put(key, var);
                });
            }
            return new  PlayerData(titleName, isModify, isFirstJoin, varMap);
        }
    }

    public void save(Player player) {
        UUID playerId = player.getUniqueId();
        File file = new File(dataFolder + "/data/", playerId + ".yml");
        FileConfiguration yaml = new YamlConfiguration();
        try {
            yaml.set("Title.Name", DataManager.get().getTitleName(playerId));
            yaml.set("Title.Modify", DataManager.get().isModify(playerId));
            yaml.set("Join.isFirstJoin", DataManager.get().isFirstJoin(playerId));
            DataManager.get().getVar(playerId).forEach((key, var) -> {
                yaml.set("Var." + key + ".value", var.getVarValue());
                yaml.set("Var." + key + ".isCountdown", var.isCountdown());
                yaml.set("Var." + key + ".isTiming", var.isTiming());
            });
            yaml.save(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
