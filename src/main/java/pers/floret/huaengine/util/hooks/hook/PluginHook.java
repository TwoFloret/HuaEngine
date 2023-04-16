package pers.floret.huaengine.util.hooks.hook;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import pers.floret.huaengine.util.LoggerUtil;
import pers.floret.huaengine.util.Scheduler;
import pers.floret.huaengine.util.VersionUtils;

import java.text.MessageFormat;

public final class PluginHook {

    private final String name;

    private final String versions;

    private boolean enable = false;

    private double versionInto10;

    /**
     *
     * @param name 插件名
     * @param versions 版本
     */
    public PluginHook(String name, String versions) {
        this.name = name;
        this.versions = versions;
    }
    public PluginHook load() {
        PluginManager manager = Bukkit.getPluginManager();
        Plugin plugin = manager.getPlugin(this.name);
        boolean enable = false;
        if (plugin == null) {
            Scheduler.runAsync(() -> {
                LoggerUtil.log(MessageFormat.format("&d║ &f> {0} &f已跳过兼容.", this.name));
            }, 49);
        } else {
            String version = plugin.getDescription().getVersion();
            this.versionInto10 = VersionUtils.toVersionInto10(version);
            if (VersionUtils.isCompatible(version, this.versions)) {
                Scheduler.runAsync(() -> {
                    LoggerUtil.log(MessageFormat.format("&d║ &f> {0} &6版本{1} &a已成功兼容.", this.name, version));
                }, 49);
                enable = true;
            } else  {
                Scheduler.runAsync(() -> {
                    LoggerUtil.log(MessageFormat.format("&d║ &f> {0} &6版本{1} &6版本不兼容.", this.name, version));
                }, 49);
            }
        }

        this.enable = enable;
        return this;
    }

    public boolean isEnable() {
        return enable;
    }

    public double getVersionInto10() {
        return versionInto10;
    }

}
