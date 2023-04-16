package pers.floret.huaengine.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pers.floret.huaengine.HuaEngine;
import pers.floret.huaengine.util.FileUtil;

import java.io.File;
import java.util.List;

public class MythicSpawnerTime {

    private MythicSpawnerTime() {
    }

    public List<String> spawnerList;
    public String defSpawnTime;

    private static MythicSpawnerTime mythicSpawnerTime;

    public static MythicSpawnerTime get() {
        return mythicSpawnerTime;
    }

    public static void load() {
        File file = new File(HuaEngine.ins().getDataFolder(), "MythicSpawnerTime.yml");
        FileUtil.saveFile(file, "MythicSpawnerTime.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        mythicSpawnerTime = load(config);
    }
    private static MythicSpawnerTime load(FileConfiguration config) {
        MythicSpawnerTime mythicSpawnerTime = new MythicSpawnerTime();
        mythicSpawnerTime.spawnerList = config.getStringList("spawner-list");
        mythicSpawnerTime.defSpawnTime = config.getString("default");
        return mythicSpawnerTime;
    }
}
