package pers.floret.huaengine.util;

import pers.floret.huaengine.HuaEngine;

import java.io.File;

public final class FileUtil {

    private FileUtil() {
    }
    public static void saveFile(File file, String path) {
        if (!file.exists()) {
            HuaEngine.ins().saveResource(path, false);
        }
    }

}
