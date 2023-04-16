package pers.floret.huaengine.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pers.floret.huaengine.HuaEngine;
import pers.floret.huaengine.util.FileUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ActionGroup {

    private ActionGroup() {
    }

    public Map<String, List<String>> cmdGroupMap;

    private static ActionGroup actionGroup;

    public static ActionGroup get() {
        return actionGroup;
    }

    public static void load() {
        File file = new File(HuaEngine.ins().getDataFolder(), "ActionGroup.yml");
        FileUtil.saveFile(file, "ActionGroup.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        actionGroup = load(config);
    }
    private static ActionGroup load(FileConfiguration config) {
        ActionGroup actionGroup = new ActionGroup();
        actionGroup.cmdGroupMap = setMap(config);
        return actionGroup;
    }
    private static Map<String, List<String>> setMap(FileConfiguration config) {
        Map<String, List<String>> map = new HashMap<>();
        config.getKeys(false).forEach( key -> {
            List<String> list = config.getStringList(key + ".action");
            map.put(key, list);
        });
        return map;
    }
}
