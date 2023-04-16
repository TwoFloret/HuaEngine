package pers.floret.huaengine.util;

import org.bukkit.Bukkit;
import pers.floret.huaengine.HuaEngine;

public class Scheduler {

    public static void runAsyncTimer(Runnable runnable, long delay, long period) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(HuaEngine.ins(), runnable, delay, period);
    }
    public static void runTimer(Runnable runnable, long delay, long period) {
        Bukkit.getScheduler().runTaskTimer(HuaEngine.ins(), runnable, delay, period);
    }

    public static void runAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(HuaEngine.ins(), runnable, 0);
    }
    public static void runAsync(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(HuaEngine.ins(), runnable, delay);
    }

    public static void runTaskLater(Runnable runnable) {
        runTaskLater(runnable, 0);
    }

    public static void runTaskLater(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLater(HuaEngine.ins(), runnable, delay);
    }
}
