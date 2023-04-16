package pers.floret.huaengine.util.hooks;


import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import pers.floret.huaengine.util.hooks.hook.HookHelper;

import java.util.Objects;

public final class VaultHelper {

    private static Economy economy;

    public static void setup() {
        if (HookHelper.vaultHook.isEnable()) {
            ServicesManager servicesManager = Bukkit.getServer().getServicesManager();
            RegisteredServiceProvider<Economy> eco = servicesManager.getRegistration(Economy.class);
            if(eco != null) {
                Economy p = eco.getProvider();
                if(p.isEnabled()) {
                    economy = p;
                }
            }
        }
    }

    /**
     * 获取玩家金币
     *
     * @param player OfflinePlayer
     * @return double
     */
    public static double get(OfflinePlayer player) {
        return economy.getBalance(player);
    }

    /**
     * 检查玩家是否拥有相应金币
     *
     * @param player OfflinePlayer
     * @param value  double
     * @return boolean
     */
    public static boolean has(OfflinePlayer player, double value) {
        return value <= get(player);
    }

    /**
     * 给予玩家金币
     *
     * @param player OfflinePlayer
     * @param value  double
     */
    public static void give(OfflinePlayer player, double value) {
        if (Objects.nonNull(economy)) {
            economy.depositPlayer(player, value);
        }
    }

    /**
     * 扣取玩家金币
     *
     * @param player OfflinePlayer
     * @param value  double
     */
    public static void take(OfflinePlayer player, double value) {
        if (Objects.nonNull(economy)) {
            economy.withdrawPlayer(player, value);
        }
    }

}

