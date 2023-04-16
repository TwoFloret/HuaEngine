package pers.floret.huaengine.util.hooks;

import org.jetbrains.annotations.NotNull;
import pers.floret.huaengine.HuaEngine;
import pers.floret.huaengine.listener.SkillListener;
import pers.floret.huaengine.util.hooks.hook.HookHelper;

public class SkillViewHelper {
    public static void setup(@NotNull HuaEngine plugin) {
        if (HookHelper.skillHook.isEnable()) {
            plugin.registerListener(new SkillListener());
        }
    }
}
