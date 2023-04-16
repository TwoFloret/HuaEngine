package pers.floret.huaengine.util.hooks;

import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import pers.floret.huaengine.util.hooks.hook.HookHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MythicMobsHook {

    private static Method xGetHealth;
    private static Method xGet;
    private static boolean isPlaceholder;

    public static void setup() {
        try {
            Class<?> clazz = Class.forName("io.lumine.xikage.mythicmobs.mobs.MythicMob");
            xGetHealth = clazz.getMethod("getHealth");
            double versionInto10 = HookHelper.mythicMobsHook.getVersionInto10();
            if (versionInto10 >= 49) {
                Class<?> c = Class.forName("io.lumine.xikage.mythicmobs.skills.placeholders.parsers.PlaceholderDouble");
                xGet = c.getMethod("get");
                isPlaceholder = true;
            }else {
                isPlaceholder = false;
            }
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static double getHealth(MythicMob mob) {
        try {
            Object instance = xGetHealth.invoke(mob);
            if (isPlaceholder) {
                return (Double) xGet.invoke(instance);
            }else {
                return (Double) instance;
            }
        } catch (IllegalAccessException | InvocationTargetException ignored) {
        }
        return 0;
    }

    public static boolean isRun() {
        return isPlaceholder;
    }

}
