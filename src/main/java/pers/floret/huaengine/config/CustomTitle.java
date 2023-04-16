package pers.floret.huaengine.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pers.floret.huaengine.HuaEngine;
import pers.floret.huaengine.runtime.TitleTag;
import pers.floret.huaengine.util.FileUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class CustomTitle {

    private CustomTitle() {
    }

    public Map<String, TitleTag> titleTagMap;

    private static CustomTitle customTitle;

    public static CustomTitle get() {
        return customTitle;
    }

    public static void load() {
        File file = new File(HuaEngine.ins().getDataFolder(), "CustomTitle.yml");
        FileUtil.saveFile(file, "CustomTitle.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        customTitle = load(config);

    }

    private static CustomTitle load(FileConfiguration config) {
        CustomTitle customTitle = new CustomTitle();
        ConfigurationSection section = config.getConfigurationSection("tag-list");
        customTitle.titleTagMap = setMap(section);
        return customTitle;
    }

    private static Map<String, TitleTag> setMap(ConfigurationSection section) {
        Map<String, TitleTag> map = new HashMap<>();
        section.getKeys(false).forEach( tag -> {
            ConfigurationSection sectionSection = section.getConfigurationSection(tag);
            String tagName = sectionSection.getString("tag-name");
            String permission = sectionSection.getString("permission");
            TitleTag titleTag = new TitleTag(tagName, permission);
            map.put(tag, titleTag);
        });
        return map;
    }
}
