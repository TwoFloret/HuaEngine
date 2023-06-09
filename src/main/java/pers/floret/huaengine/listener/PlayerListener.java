package pers.floret.huaengine.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pers.floret.huaengine.config.Rule;
import pers.floret.huaengine.database.YamlData;
import pers.floret.huaengine.config.JoinAction;
import pers.floret.huaengine.runtime.DataManager;
import pers.floret.huaengine.util.ActionUtil;

import java.util.List;
import java.util.Set;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        new YamlData().load(player);
        joinAction(player);
        if (Rule.get().foodLevelConsume) return;
        if (!Rule.get().foodLevelConsumeList.contains(player.getName())) return;
        // 创建一个饱和度效果（potion effect）
        PotionEffect saturation = new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 1, false, false);
        // 给玩家应用该效果
        player.addPotionEffect(saturation);
        player.setFoodLevel(20);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        new YamlData().save(player);
        DataManager.get().removeData(player.getUniqueId());
    }

    private void joinAction(Player player) {
        if (!JoinAction.get().joinActionEnable) return;
        Set<String> keySet = JoinAction.get().joinActionMap.keySet();
        for (String key : keySet) {
            String permission = JoinAction.get().joinActionMap.get(key).getPermission();
            if (player.hasPermission(permission)) {
                if (DataManager.get().isFirstJoin(player.getUniqueId())) {
                    List<String> firstList = JoinAction.get().joinActionMap.get(key).getFirstActions();
                    for (String action : firstList) {
                        ActionUtil.delayActions(player, action);
                    }
                    DataManager.get().setFirstJoin(player.getUniqueId(), false);
                    new YamlData().save(player);
                }
                List<String> everyList = JoinAction.get().joinActionMap.get(key).getEveryActions();
                for (String action : everyList) {
                    ActionUtil.delayActions(player, action);
                }
            }
        }
    }
}
