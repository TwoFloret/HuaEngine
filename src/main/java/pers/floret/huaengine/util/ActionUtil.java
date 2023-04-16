package pers.floret.huaengine.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pers.floret.huaengine.HuaEngine;

/**
 * 执行动作工具类
 */

public final class ActionUtil {


    private ActionUtil() {
    }

    /**
     * 执行一条带有延迟的动作 消息或者OP、CONSOLE指令
     * @param player  触发者
     * @param str 内容 可以是消息或者指令
     */
    public static void delayActions(Player player, String str) {
        String[] s = str.split("<->");
        long delay = 0;
        if (s.length == 2) {
            delay = Long.parseLong(s[1]);
        }
        Scheduler.runTaskLater(() -> executeActions(player, s[0]), delay);
    }
    /**
     * 执行一个动作 消息或者OP、CONSOLE指令
     * @param player 执行的玩家
     * @param str 内容 可以是消息或者指令
     */
    public static void executeActions(Player player, String str) {
        str = str.replace("%p%", player.getName());
        str = str.replace("%player%", player.getName());
        str = str.replace("<player>", player.getName());
        str = PlaceholderAPI.setPlaceholders(player, str);
        String type = str.toLowerCase();
        if (type.startsWith("[op]")) {
            boolean isOp = player.isOp();
            try {
                player.setOp(true);
                player.chat("/" + str.substring(4).trim());
            } catch (Throwable throwable) {
                HuaEngine.ins().getLogger().info("执行OP命令出现了异常: " + str);
                throwable.printStackTrace();
            } finally {
                player.setOp(isOp);
            }
        } else if (type.startsWith("[console]")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), str.substring(9).trim());
        } else if (type.startsWith("[message]")) {
            player.sendMessage(StringUtil.colors(str.substring(9).trim()));
        } else if (type.startsWith("[title]")) {
            String[] s = StringUtil.colors(str.substring(7).trim()).split(",");
            PlayerUtil.sendTitle(player, s[0], s[1], Integer.parseInt(s[2]), Integer.parseInt(s[3]), Integer.parseInt(s[4]));
        } else if (type.startsWith("[broadcast-title]")) {
            String[] s = StringUtil.colors(str.substring(17).trim()).split(",");
            PlayerUtil.broadcastTitle(s[0], s[1], Integer.parseInt(s[2]), Integer.parseInt(s[3]), Integer.parseInt(s[4]));
        } else {
            player.chat("/" + str.trim());
        }
    }
}
