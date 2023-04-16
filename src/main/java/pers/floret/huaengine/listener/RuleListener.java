package pers.floret.huaengine.listener;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.serverct.ersha.api.event.AttrEntityAttackEvent;
import org.serverct.ersha.api.event.attribute.AttrAttributeTriggerEvent;
import pers.floret.huaengine.config.Message;
import pers.floret.huaengine.config.MsgNode;
import pers.floret.huaengine.config.Rule;
import pers.floret.huaengine.runtime.DataManager;
import pers.floret.huaengine.util.LoggerUtil;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

public class RuleListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent e) {
        // 取消玩家燃烧、摔落、溺水、熔岩伤害
        Entity entity = e.getEntity();
        if (entity.getType().equals(EntityType.PLAYER)) {
            EntityDamageEvent.DamageCause cause = e.getCause();
            switch (cause) {
                case FALL:
                    if (Rule.get().fallingDamage) return;
                    e.setCancelled(true);
                    break;
                case LAVA:
                case FIRE:
                case FIRE_TICK:
                    if (Rule.get().fireDamage) return;
                    e.setCancelled(true);
                    break;
                case DROWNING:
                    if (Rule.get().drowningDamage) return;
                    e.setCancelled(true);
                    break;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityCombust(EntityCombustEvent e) {
        // 实体禁止燃烧
        if (!Rule.get().banEntityCombust) return;
        Rule.get().banEntityCombusts.forEach(r -> {
            String[] args = r.split("<->");
            if (args[0].equalsIgnoreCase("name")) {
                String entityName = e.getEntity().getName();
                if (entityName.contains(args[1])) {
                    e.setCancelled(true);
                }
            } else if (args[0].equalsIgnoreCase("type")) {
                EntityType entityType = e.getEntityType();
                if (entityType.equals(EntityType.valueOf(args[1].toUpperCase()))) {
                    e.setCancelled(true);
                }
            }
        });
    }
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void InventoryClick(InventoryClickEvent e) {
        // 禁止带有指定lore的物品放入副手
        // 判断是否是数字键放入
        if (e.getClick().equals(ClickType.NUMBER_KEY)) {
            ItemStack item = e.getView().getItem(e.getHotbarButton() + 36);
            if (item != null && !item.getType().equals(Material.AIR) && hasBanPutLore(item) && e.getRawSlot() == 45) {
                e.setCancelled(true);
                LoggerUtil.log(e.getWhoClicked(), Message.get(MsgNode.RULE_PUT_OFFHAND_CANCELLED));
            }

        }
        // 判断是否点击放入
        ItemStack item = e.getCursor();
        if (item != null && !item.getType().equals(Material.AIR) && hasBanPutLore(item)) {
            if (e.getRawSlot() == 45 && hasBanPutLore(item)) {
                e.setCancelled(true);
                LoggerUtil.log(e.getWhoClicked(), Message.get(MsgNode.RULE_PUT_OFFHAND_CANCELLED));
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerSwapHandItems(PlayerSwapHandItemsEvent e) {
        // 禁止带有指定lore的物品切换至副手
        ItemStack item = e.getOffHandItem();
        if (item != null && !item.getType().equals(Material.AIR) && hasBanPutLore(item)) {
            e.setCancelled(true);
            LoggerUtil.log(e.getPlayer(), Message.get(MsgNode.RULE_PUT_OFFHAND_CANCELLED));
        }
    }
    // 判断物品是否带有指定 lore
    private static boolean hasBanPutLore(ItemStack item) {
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasLore()) {
                List<String> lore = meta.getLore();
                for (String s : lore) {
                    if (s.contains(Rule.get().banPutOff)) {
                        return !Rule.get().banPutOffReverse;
                    }else {
                        return Rule.get().banPutOffReverse;
                    }
                }
            }
        }
        LoggerUtil.debug("执行到这");
        return false;
    }
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void boxOpen(PlayerInteractEvent e) {
        // 禁止打开容器
        if (Rule.get().banOpenContainer == null) return;
        Player player = e.getPlayer();
        if (player.isOp()) return;
        Block block = e.getClickedBlock();
        if (block != null) {
            LoggerUtil.debug(block.getType().name());
            for (String m : Rule.get().banOpenContainer) {
                if (block.getType().name().equals(m)) {
                    // 判断是否为原版的箱子
                    e.setCancelled(true);
                    LoggerUtil.log(player, Message.get(MsgNode.RULE_OPEN_CANCELLED));
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void AttrAttributeTrigger(AttrAttributeTriggerEvent.Before e) {
        // 取消玩家ap3属性js脚本触发
        e.setCancelled(DataManager.get().isApDamage());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void playerAttributeAttack(AttrEntityAttackEvent e) {
        // 取消玩家ap3属性伤害
        if (!e.getAttackerOrKiller().getType().equals(EntityType.PLAYER)) return;
        if (!Rule.get().banLeftAttack) return;
        Player player = (Player) e.getAttackerOrKiller();
        String mainHasLore = Rule.get().mainHasLore;
        String offHasLore = Rule.get().offHasLore;
        if (checkMain(player, mainHasLore) || checkOff(player, offHasLore)) {
            e.setCancelled(true);
            DataManager.get().setApDamage(true);
            if (!Rule.get().banLeftAttackMsg) return;
            LoggerUtil.log(player, Message.get(MsgNode.RULE_ATTACK_CANCELLED));
        }
    }
    // 判断主手条件取消攻击
    private boolean checkMain(Player player, String mainHasLore) {
        ItemStack main = player.getInventory().getItemInMainHand();
        if (main == null || main.getType().equals(Material.AIR)) {
            return Rule.get().mainEmptyDamage;
        }
        List<String> lores = main.getItemMeta().getLore();
        if (main.hasItemMeta()) {
            if (lores != null) {
                for (String line : lores) {
                    if (line.contains(mainHasLore)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    // 判断副手条件取消攻击
    private boolean checkOff(Player player, String offHasLore) {
        ItemStack off = player.getInventory().getItemInOffHand();
        if (off == null || off.getType().equals(Material.AIR)) {
            return Rule.get().offEmptyDamage;
        }
        List<String> lores = off.getItemMeta().getLore();
        if (off.hasItemMeta()) {
            if (lores != null) {
                for (String line : lores) {
                    if (line.contains(offHasLore)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent e) {
        // 死亡不掉落
        if (!Rule.get().banInventoryDrop) return;
        String worldName = e.getEntity().getWorld().getName();
        Rule.get().banInventoryDropWorlds.forEach( w -> {
            if (!w.equals(worldName)) return;
            e.setKeepInventory(true);
            e.getDrops().clear();
            e.setKeepLevel(true);
            e.setDroppedExp(0);
        });
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onHangingBreakByEntity(HangingBreakByEntityEvent e) {
        if (!Rule.get().banBlockBuild) return;
        Player player = (Player) e.getRemover();
        String worldName = player.getWorld().getName();
        if (!Rule.get().banHangingBreak) return;
        if (Rule.get().banBlockBuildWorlds == null) return;
        if (e.getEntity().getType() == EntityType.PAINTING
                || e.getEntity().getType() == EntityType.ITEM_FRAME) {
            Rule.get().banBlockBuildWorlds
                    .stream()
                    .filter(w -> !player.hasPermission("HuaEngine.build"))
                    .forEach(w -> {
                        boolean bool = Rule.get().banBlockBuildReverse == w.equals(worldName);
                        if (bool) return;
                        e.setCancelled(true);
                        LoggerUtil.log(player, Message.get(MsgNode.RULE_HANGING_BREAK_CANCELLED));
                    });
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e) {
        // 取消方块放置
        if (!Rule.get().banBlockBuild) return;
        Player player = e.getPlayer();
        String worldName = player.getWorld().getName();
        if (Rule.get().banBlockBuildWorlds == null) return;
        Rule.get().banBlockBuildWorlds
                .stream()
                .filter(w -> !player.hasPermission("HuaEngine.build"))
                .forEach(w -> {
                    boolean bool = Rule.get().banBlockBuildReverse == w.equals(worldName);
                    if (bool) return;
                    e.setCancelled(true);
                    LoggerUtil.log(player, Message.get(MsgNode.RULE_BLOCK_PLACE_CANCELLED));
                });
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e) {
        // 取消方块破坏
        if (!Rule.get().banBlockBuild) return;
        Player player = e.getPlayer();
        String worldName = player.getWorld().getName();
        if (Rule.get().banBlockBuildWorlds == null) return;
        Rule.get().banBlockBuildWorlds
                .stream()
                .filter(w -> !player.hasPermission("HuaEngine.build"))
                .forEach(w -> {
                    boolean bool = Rule.get().banBlockBuildReverse == w.equals(worldName);
                    if (bool) return;
                    e.setCancelled(true);
                    LoggerUtil.log(player, Message.get(MsgNode.RULE_BLOCK_BREAK_CANCELLED));
                });
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void playerDropItem(PlayerDropItemEvent e) {
        // 禁止丢弃物品
        if (!Rule.get().banDropItem) return;
        Player player = e.getPlayer();
        String worldName = player.getWorld().getName();
        if (Rule.get().banDropItemWorlds == null) return;
        Rule.get().banDropItemWorlds
                .stream()
                .filter(w -> !player.hasPermission("HuaEngine.drop"))
                .forEach(w -> {
                    boolean bool = Rule.get().banDropItemReverse == w.equals(worldName);
                    if (bool) return;
                    e.setCancelled(true);
                    LoggerUtil.log(player, Message.get(MsgNode.RULE_DROP_ITEM_CANCELLED));
                });
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onWeatherChange(WeatherChangeEvent e) {
        // 禁止天气切换
        String worldName = e.getWorld().getName();
        if (!Rule.get().banWeatherChange) return;
        if (Rule.get().banWeatherChangeWorlds == null) return;
        Rule.get().banWeatherChangeWorlds.forEach( w -> {
            if (!w.equals(worldName) && !e.toWeatherState()) return;
            e.setCancelled(true);
        });
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onThunderChange(ThunderChangeEvent e) {
        // 禁止雷暴切换
        if (!Rule.get().banWeatherChange) return;
        String worldName = e.getWorld().getName();
        if (Rule.get().banWeatherChangeWorlds == null) return;
        Rule.get().banWeatherChangeWorlds.forEach( w -> {
            if (!w.equals(worldName) && !e.toThunderState()) return;
            e.setCancelled(true);
        });
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        // 世界传送玩家等级限制
        if (!Rule.get().tpLevelLimit) return;
        World tpWorld = e.getTo().getWorld(); // 传送至的世界
        String worldEnName = tpWorld.getName();
        // 如果传送去的世界不等于当前所在世界 并且传送至的世界存在世界限制列表中
        if (tpWorld == e.getFrom().getWorld()) return;
        if (!Rule.get().tpLevelLimitMap.containsKey(worldEnName)) return;
        // 获取传送世界限制的等级
        int limitLevel = Rule.get().tpLevelLimitMap.get(worldEnName).getLimitLevel();
        String worldZhName = Rule.get().tpLevelLimitMap.get(worldEnName).getLimitWorldName();
        Player player = e.getPlayer(); // 获取传送事件中的玩家
        if (player.getLevel() < limitLevel) { // 如果玩家等级小于 传送世界限制的等级
            e.setCancelled(true); // 设置取消此次传送事件
            LoggerUtil.log(player, MessageFormat.format(Message.get(MsgNode.RULE_TP_LEVEL_LIMIT),
                    limitLevel, worldZhName));
        }
    }
}
