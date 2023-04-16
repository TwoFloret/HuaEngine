package pers.floret.huaengine.damagedisplay;

import java.util.Map;

/**
 *  游戏内文本替换数字
 */

public final class NumberReplace {

    private NumberReplace() {
    }

    private String match;

    private Map<Character, Character> replace;

    public NumberReplace(String match, Map<Character, Character> replace) {
        this.match = match;
        this.replace = replace;
    }

    public Map<Character, Character> getReplace() {
        return replace;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public void setReplace(Map<Character, Character> replace) {
        this.replace = replace;
    }

    public static String replaceNumber(String source, Map<Character, Character> data) {
        StringBuilder s = new StringBuilder();
        char[] chars = source.toCharArray();
        for (char c: chars) {
            data.forEach( (key, value) -> {
                if (c == key) s.append(value);
            });
        }
        return s.toString();
    }
}
