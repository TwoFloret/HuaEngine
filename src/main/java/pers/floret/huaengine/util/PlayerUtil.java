package pers.floret.huaengine.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class PlayerUtil {

    /**
     * 给全服玩家发送一个标题消息
     * @param title 主标题
     * @param subtitle 副标题
     * @param fadeIn 标题从透明到不透明的持续时间
     * @param stay 标题不透明的持续时间
     * @param fadeOut 标题从不透明到透明的持续时间
     */
    public static void broadcastTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        Bukkit.getOnlinePlayers().forEach(player -> sendTitle(player, title, subtitle, fadeIn, stay, fadeOut));
    }

    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        String finalTitle = StringUtil.colors(title);
        String finalSubTitle = StringUtil.colors(subtitle);
        player.sendTitle(finalTitle, finalSubTitle, fadeIn, stay, fadeOut);
    }
}
