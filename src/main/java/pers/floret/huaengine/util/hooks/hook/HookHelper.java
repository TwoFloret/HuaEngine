package pers.floret.huaengine.util.hooks.hook;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public final class HookHelper {

    @Hook(value = "MythicMobs", version = "4.11", isDepend = true)
    public static PluginHook mythicMobsHook;

    @Hook(value = "AttributePlus")
    public static PluginHook attributePlusHook;

    @Hook(value = "Vault")
    public static PluginHook vaultHook;

    @Hook(value = "PlaceholderAPI")
    public static PluginHook papiHook;

    @Hook(value = "MythicSkillView")
    public static PluginHook skillHook;

    @Hook(value = "ProtocolLib")
    public static PluginHook protocolHook;

    public static boolean initPluginHooks() {
        boolean enable = true;
        for (Field field : HookHelper.class.getFields()) {
            if (Modifier.isStatic(field.getModifiers()) && field.getType().equals(PluginHook.class) && field.isAnnotationPresent(Hook.class)) {
                Hook hook = field.getAnnotation(Hook.class);
                try {
                    PluginHook PluginHook = new PluginHook(hook.value(), hook.version()).load();
                    field.set(null, PluginHook);
                    if (enable && !PluginHook.isEnable() && hook.isDepend()) {
                        enable = false;
                    }
                } catch (IllegalAccessException ignored) {
                }
            }
        }
        return enable;
    }
}
