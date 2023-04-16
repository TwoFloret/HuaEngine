package pers.floret.huaengine.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pers.floret.huaengine.HuaEngine;
import pers.floret.huaengine.util.FileUtil;
import pers.floret.huaengine.util.LoggerUtil;
import pers.floret.huaengine.util.Scheduler;

import java.io.File;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public final class Message {

    private Message() {
    }

    private static final Map<String, String> MESSAGE_MAP = new HashMap<>();
    private static final String MESSAGE_NOW_VERSION = "2.4";

    public static void load() {
        File file = new File(HuaEngine.ins().getDataFolder(), "Message.yml"); // 读取配置文件路径
        FileUtil.saveFile(file, "Message.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file); // 读取配置文件
        // 将消息枚举与配置文件中的对应项绑定到 MESSAGE_MAP 上
        for (MsgNode msgNode : MsgNode.values()) {
            MESSAGE_MAP.put(msgNode.name(), config.getString(msgNode.name(), ""));
        }
        messageUpdate();
    }
    public static String get(MsgNode msg) {
        return MESSAGE_MAP.get(msg.name());
    }

    private static void messageUpdate() {
        if (get(MsgNode.MESSAGE_VERSION).equals(MESSAGE_NOW_VERSION)) return;
        LoggerUtil.log(MessageFormat.format(get(MsgNode.MESSAGE_NEW), MESSAGE_NOW_VERSION));
        HuaEngine.ins().saveResource("Message.yml", true);
        LoggerUtil.log(MessageFormat.format(get(MsgNode.MESSAGE_UPDATE), get(MsgNode.MESSAGE_NOW_VERSION)));
    }
}
