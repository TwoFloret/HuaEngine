package pers.floret.huaengine.damagedisplay;

import eos.moe.dragoncore.api.CoreAPI;
import eos.moe.dragoncore.api.worldtexture.WorldTexture;
import eos.moe.dragoncore.api.worldtexture.animation.ScaleAnimation;
import eos.moe.dragoncore.api.worldtexture.animation.TranslateAnimation;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pers.floret.huaengine.config.DamageDisplay;

public final class CoreWorldTexture {

    private final WorldTexture worldTexture = new WorldTexture();

    public void create(Player player, Location loc, String key, String line, boolean follow, float width, float height) {
        worldTexture.translateX = loc.getX();
        worldTexture.translateY = loc.getY();
        worldTexture.translateZ = loc.getZ();
        worldTexture.path = "[text]" + line;
        worldTexture.followPlayerEyes = follow;
        worldTexture.width = width;
        worldTexture.height = height;
        addScaleAnimation();
        addTranslateAnimationStart();
        addTranslateAnimationEnd();
        CoreAPI.setPlayerWorldTextureItem(player, key, worldTexture);
    }
    public void delete(Player player, String key) {
        CoreAPI.removePlayerWorldTexture(player, key);
    }

    private void addTranslateAnimationStart() {
        TranslateAnimation t = new TranslateAnimation();
        t.direction = DamageDisplay.animation.getString("translate-animation.direction");
        t.delay = DamageDisplay.animation.getInt("translate-animation.delay");
        t.distance = DamageDisplay.animation.getInt("translate-animation.distance");
        t.duration = DamageDisplay.animation.getInt("translate-animation.duration");
        t.cycleCount = DamageDisplay.animation.getInt("translate-animation.cycleCount");
        t.fixed = DamageDisplay.animation.getBoolean("translate-animation.fixed");
        worldTexture.animationList.add(t);
    }

    private void addScaleAnimation() {
        ScaleAnimation s1 = new ScaleAnimation();
        s1.delay = DamageDisplay.animation.getInt("scale-animation-start.delay");
        s1.fromScale = (float) DamageDisplay.animation.getDouble("scale-animation-start.fromScale");
        s1.toScale = (float) DamageDisplay.animation.getDouble("scale-animation-start.toScale");
        s1.duration = DamageDisplay.animation.getInt("scale-animation-start.duration");
        s1.cycleCount = DamageDisplay.animation.getInt("scale-animation-start.cycleCount");
        s1.fixed = DamageDisplay.animation.getBoolean("scale-animation-start.fixed");
        s1.resetTime = DamageDisplay.animation.getInt("scale-animation-start.resetTime");
        worldTexture.animationList.add(s1);
    }
    private void addTranslateAnimationEnd() {
        ScaleAnimation s2 = new ScaleAnimation();
        s2.delay = DamageDisplay.animation.getInt("scale-animation-end.delay");
        s2.fromScale = (float) DamageDisplay.animation.getDouble("scale-animation-end.fromScale");
        s2.toScale = (float) DamageDisplay.animation.getDouble("scale-animation-end.toScale");
        s2.duration = DamageDisplay.animation.getInt("scale-animation-end.duration");
        s2.cycleCount = DamageDisplay.animation.getInt("scale-animation-end.cycleCount");
        s2.fixed = DamageDisplay.animation.getBoolean("scale-animation-end.fixed");
        s2.resetTime = DamageDisplay.animation.getInt("scale-animation-end.resetTime");
        worldTexture.animationList.add(s2);
    }
}
