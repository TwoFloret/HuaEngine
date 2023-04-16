package pers.floret.huaengine;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pers.floret.huaengine.command.MainCommand;
import pers.floret.huaengine.config.Config;
import pers.floret.huaengine.database.YamlData;
import pers.floret.huaengine.listener.PlayerListener;
import pers.floret.huaengine.listener.RuleListener;
import pers.floret.huaengine.runtime.TaskRunnable;
import pers.floret.huaengine.util.LoggerUtil;
import pers.floret.huaengine.util.Scheduler;
import pers.floret.huaengine.util.hooks.*;
import pers.floret.huaengine.util.hooks.hook.HookHelper;
import pers.floret.huaengine.util.hooks.imp.AttributePlusHook_3;

public final class HuaEngine extends JavaPlugin {

    private static HuaEngine plugin;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        if (!HookHelper.initPluginHooks()) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        Scheduler.runTaskLater(() -> {
            serverLogo();
            LoggerUtil.log("§d╔═════════════════════════════ §6启动信息 §d═════════════════════════════");
        }, 48L);
        Scheduler.runTaskLater(() -> {
            if (HookHelper.attributePlusHook.isEnable()) {
                if (HookHelper.attributePlusHook.getVersionInto10() >= 30) {
                    new AttributePlusHook_3();
                }
            }
            ProtocolLibHelper.setup();
            VaultHelper.setup();
            MythicMobsHook.setup();
            PapiHelper.setup();
            SkillViewHelper.setup(this);
        });
        Config.load();
        this.registerListener(new PlayerListener());
        this.registerListener(new RuleListener());
        getCommand("huaengine").setExecutor(new MainCommand());
        Scheduler.runAsyncTimer(() -> {
            TaskRunnable.countdown();
            TaskRunnable.readSpawnTime();
        }, 20L, 20L);
        Scheduler.runTaskLater(() -> {
            LoggerUtil.log("§d║ §f> 插件状态: §6插件启动成功！");
            LoggerUtil.log("§d║ §f> 插件版本: §6" + HuaEngine.getProvidingPlugin(HuaEngine.class).getDescription().getVersion());
            LoggerUtil.log("§d║ §f> 插件作者: §6floret");
            LoggerUtil.log("§d║ §f> 作者QQ为: §62220063040");
            LoggerUtil.log("§d╚═════════════════════════════ §6启动信息 §d═════════════════════════════");

        }, 50L);
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach( player -> new YamlData().save(player));
        serverLogo();
        LoggerUtil.log("§c╔═════════════════════════════ §6卸载信息 §c═════════════════════════════");
        LoggerUtil.log("§c║ §f> 插件状态: §6插件卸载成功！");
        LoggerUtil.log("§c║ §f> 插件版本: §6" + HuaEngine.getProvidingPlugin(HuaEngine.class).getDescription().getVersion());
        LoggerUtil.log("§c║ §f> 插件作者: §6floret");
        LoggerUtil.log("§c║ §f> 作者QQ为: §62220063040");
        LoggerUtil.log("§c╚═════════════════════════════ §6卸载信息 §c═════════════════════════════");
    }
    public void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    public static HuaEngine ins() {
        return plugin;
    }
    private void serverLogo() {
        LoggerUtil.log("§5██╗   ██╗██╗   ██╗  ████╗           ████████╗████████╗████████╗  ████╗ ████████╗████████╗");
        LoggerUtil.log("§5██║   ██║██║   ██║██╔═══██╗         ██╔═════╝██╔═══██║██╔═════╝  ╚═══╝ ██╔═══██║██╔═════╝");
        LoggerUtil.log("§5████████║██║   ██║████████║ ██████╗ ████████╗██║   ██║██║ ████╗  ████╗ ██║   ██║████████╗");
        LoggerUtil.log("§5██╔═══██║██║   ██║██╔═══██║ ╚═════╝ ██╔═════╝██║   ██║██║ ╚═██║  ████║ ██║   ██║██╔═════╝");
        LoggerUtil.log("§5██║   ██║████████║██║   ██║         ████████╗██║   ██║████████║  ████║ ██║   ██║████████╗");
        LoggerUtil.log("§5╚═╝   ╚═╝╚═══════╝╚═╝   ╚═╝         ╚═══════╝╚═╝   ╚═╝╚═══════╝  ╚═══╝ ╚═╝   ╚═╝╚═══════╝");
    }
}
