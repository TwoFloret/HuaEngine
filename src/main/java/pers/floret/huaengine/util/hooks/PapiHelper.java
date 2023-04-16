package pers.floret.huaengine.util.hooks;

import pers.floret.huaengine.util.hooks.hook.HookHelper;
import pers.floret.huaengine.util.placeholder.Papi;

public class PapiHelper {

    public static void setup() {
        if (HookHelper.papiHook.isEnable()) {
            new Papi().register();
        }
    }
}
