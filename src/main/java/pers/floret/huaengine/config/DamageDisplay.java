package pers.floret.huaengine.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pers.floret.huaengine.HuaEngine;
import pers.floret.huaengine.damagedisplay.NumberReplace;
import pers.floret.huaengine.util.FileUtil;

import java.io.File;
import java.util.*;

public final class DamageDisplay {

    private DamageDisplay() {
    }

    private final static List<NumberReplace> replaces = new ArrayList<>();

    public static NumberReplace getNumReplaceFromMatch(String match) {
        Optional<NumberReplace> any = replaces.stream().filter(it -> it.getMatch().equalsIgnoreCase(match)).findAny();
        return any.orElse(null);
    }

    public static FileConfiguration animation;

    public static void load() {
        replaces.clear();
        File file = new File(HuaEngine.ins().getDataFolder(), "DamageDisplay/replace.yml");
        FileUtil.saveFile(file, "DamageDisplay/replace.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.getKeys(false).forEach( node -> {
            ConfigurationSection session = config.getConfigurationSection(node);
            String match = session.getString("match");
            Map<Character, Character> data = new HashMap<>();
            session.getConfigurationSection("replace").getKeys(false).forEach(chars -> {
                char key = chars.charAt(0);
                char value = session.getString("replace." + chars).charAt(0);
                data.put(key, value);
                NumberReplace numReplaceUtil = new NumberReplace(match, data);
                replaces.add(numReplaceUtil);
            });
        });
    }

    public static void loadAnimation() {
        File file = new File(HuaEngine.ins().getDataFolder(), "DamageDisplay/animation.yml");
        FileUtil.saveFile(file, "DamageDisplay/animation.yml");
        animation = YamlConfiguration.loadConfiguration(file);
    }
}
