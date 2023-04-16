package pers.floret.huaengine.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;


/**
 * 字符串工具类
 */

public final class StringUtil {

    private StringUtil() {
    }
    /*
        更改颜色符号、%player%变量
     */
    public static String colors(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static boolean isEmpty(@Nullable String str) {
        return str == null || "".equals(str.trim()) || "[]".equals(str.trim());
    }
    public static String replace(String str, Object... params) {
        if (params.length == 0 || str.length() == 0) {
            return str;
        }
        char[] arr = str.toCharArray();
        StringBuilder stringBuilder = new StringBuilder(str.length());
        for (int i = 0; i < arr.length; i++) {
            int mark = i;
            if (arr[i] == '{') {
                int num = 0;
                while (i + 1 < arr.length && Character.isDigit(arr[i + 1])) {
                    i++;
                    num *= 10;
                    num += arr[i] - '0';
                }
                if (i != mark && i + 1 < arr.length && arr[i + 1] == '}') {
                    i++;
                    if (params.length > num) stringBuilder.append(params[num]);
                } else {
                    i = mark;
                }
            }
            if (mark == i) {
                stringBuilder.append(arr[i]);
            }
        }
        return stringBuilder.toString();
    }
}
