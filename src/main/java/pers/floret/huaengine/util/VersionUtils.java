package pers.floret.huaengine.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class VersionUtils {
    private static final List<String> rangeChat = Arrays.asList("~", "-", "|");

    public static boolean meet(String version, String max) {
        double v = toVersionInto10(version);
        double m = toVersionInto10(max);
        return v >= m;
    }

    public static boolean isCompatible(String version, String range) {
        String v = range.replaceAll(" ", "");
        Iterator<String> var3 = rangeChat.iterator();

        String c;
        do {
            if (!var3.hasNext()) {
                return isCompatible(version, v, null);
            }

            c = var3.next();
        } while(!v.contains(c));

        String[] split = v.split(c);
        return isCompatible(version, split[0], split[1]);
    }

    private static boolean isCompatible(String version, String min, String max) {
        double v = toVersionInto10(version);
        double i = toVersionInto10(min);
        double a = toVersionInto10(max);
        return a > -1.0D ? (a > i ? v >= i && v < a : v >= i) : v >= i;
    }

    public static double toVersionInto10(String str) {
        if (StringUtil.isEmpty(str)) {
            return -1.0D;
        } else {
            String v;
            if (str.contains("-")) {
                v = str.split("-")[0];
            } else {
                v = str;
            }

            v = v.replaceAll("[a-zA-Z]*", "");
            StringBuilder builder = new StringBuilder();
            if (v.contains(".")) {
                String[] splits = v.split("\\.");
                builder.append(splits[0]).append(splits[1]);
                if (splits.length > 2) {
                    builder.append(".");

                    for(int i = 2; i < splits.length; ++i) {
                        builder.append(splits[i]);
                    }
                }
            } else {
                builder.append(v).append("0");
            }

            try {
                return Double.parseDouble(builder.toString());
            } catch (NumberFormatException var5) {
                return 0.0D;
            }
        }
    }
}
