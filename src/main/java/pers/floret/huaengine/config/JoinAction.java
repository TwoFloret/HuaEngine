package pers.floret.huaengine.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pers.floret.huaengine.HuaEngine;
import pers.floret.huaengine.runtime.Join;
import pers.floret.huaengine.util.FileUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class JoinAction {
    private JoinAction() {
    }

    public Map<String, Join> joinActionMap;
    public boolean joinActionEnable;

    private static JoinAction joinAction;

    public static JoinAction get() {
        return joinAction;
    }

    public static void load() {
        File file = new File(HuaEngine.ins().getDataFolder(), "JoinAction.yml");
        FileUtil.saveFile(file, "JoinAction.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        joinAction = load(config);
    }
    private static JoinAction load(FileConfiguration config) {
        JoinAction joinAction = new JoinAction();
        ConfigurationSection section = config.getConfigurationSection("actions");
        joinAction.joinActionMap = setMap(section);
        joinAction.joinActionEnable = config.getBoolean("enable");
        return joinAction;
    }
    private static Map<String, Join> setMap(ConfigurationSection section) {
        Map<String, Join> map = new HashMap<>();
        section.getKeys(false).forEach( key -> {
            String permission = section.getString(key + ".permission");
            List<String> firstList = section.getStringList(key + ".first-join-action");
            List<String> everyList = section.getStringList(key + ".every-join-action");
            Join join = new Join(permission, firstList, everyList);
            map.put(key, join);
        });
        return map;
    }
}
