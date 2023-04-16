package pers.floret.huaengine.config;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pers.floret.huaengine.HuaEngine;
import pers.floret.huaengine.util.FileUtil;

import java.io.File;

public final class Config {

    private Config() {
    }

    public String msgPrefix = "&fHuaEngine"; // 获取私信消息前缀值
    public boolean debug;
    public boolean varEnable;
    public boolean spawnerEnable;
    public boolean skillApDamage;
    private static Config config;

    public static Config get() {
        return config;
    }

    public static void load() {
        File file = new File(HuaEngine.ins().getDataFolder(), "Config.yml");
        FileUtil.saveFile(file, "Config.yml");
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        config = load(configuration); // 载入 Config.yml 配置文件
        Rule.load();
        CustomTitle.load();
        DamageDisplay.load();
        ActionGroup.load();
        JoinAction.load();
        MythicSpawnerTime.load();
        DamageDisplay.loadAnimation();
        Message.load();
    }
    private static Config load(Configuration configuration) {
        Config config = new Config();
        if (configuration.contains("message-prefix")) {
            config.msgPrefix = configuration.getString("message-prefix");
        }
        config.debug = configuration.getBoolean("debug");
        config.varEnable = configuration.getBoolean("var-enable");
        config.spawnerEnable = configuration.getBoolean("spawner-enable");
        config.skillApDamage = configuration.getBoolean("skill-ap-damage");
        return config;
    }
}
