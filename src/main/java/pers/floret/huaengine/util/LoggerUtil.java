package pers.floret.huaengine.util;

import pers.floret.huaengine.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pers.floret.huaengine.HuaEngine;

import java.util.logging.Level;

public final class LoggerUtil {
    public static void log(String message, Object... params) {
        String msg = StringUtil.replace(message, params);
        Bukkit.getConsoleSender().sendMessage(StringUtil.colors(msg));
    }

    public static void log(CommandSender sender, String message, Object... params) {
        String msg = StringUtil.replace(message, params);
        sender.sendMessage(StringUtil.colors(StringUtil.replace("§r{0} §6{1}", Config.get().msgPrefix, msg)));
    }

    public static void log(Level level, String message, Object... params) {
        HuaEngine.ins().getLogger().log(level, StringUtil.replace(message, params) + "\u001b[0m");
    }

    public static void debug(String message, Object... params) {
        if (Config.get().debug) {
            log(Bukkit.getConsoleSender(), message, params);
        }
    }

    private LoggerUtil() {
        throw new UnsupportedOperationException("the class cannot be instantiated.");
    }
}
