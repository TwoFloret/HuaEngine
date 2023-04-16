package pers.floret.huaengine.damagedisplay;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import pers.floret.huaengine.config.DamageDisplay;

public final class WorldTextureManager {

    private WorldTextureManager() {
    }

    public static void create(String key, Location loc, @NotNull String line, boolean follow, float width, float height) {
        CoreWorldTexture coreWorldTexture = new CoreWorldTexture();
        String[] strings = line.split(":");
        NumberReplace numberReplace;
        String replaceContext;
        if (strings.length > 2) {
            if (DamageDisplay.getNumReplaceFromMatch(strings[1]) != null) {
                numberReplace = DamageDisplay.getNumReplaceFromMatch(strings[1]);
                replaceContext = strings[0] + NumberReplace.replaceNumber(line, numberReplace.getReplace());
            }else {
                replaceContext = line;
            }
        }else {
            if (DamageDisplay.getNumReplaceFromMatch(strings[0]) != null) {
                numberReplace = DamageDisplay.getNumReplaceFromMatch(strings[0]);
                replaceContext = NumberReplace.replaceNumber(line, numberReplace.getReplace());
            }else {
                replaceContext = line;
            }
        }
        Bukkit.getOnlinePlayers().forEach(player -> coreWorldTexture.create(player, loc, key, replaceContext, follow, width, height));
    }
    public static void delete(String key) {
        CoreWorldTexture coreWorldTexture = new CoreWorldTexture();
        Bukkit.getOnlinePlayers().forEach(player ->  coreWorldTexture.delete(player, key));
    }
}
