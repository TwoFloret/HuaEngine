package pers.floret.huaengine.util.hooks;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface AttributeHelper {

    void setPlayerAttribute(@NotNull Player player, @NotNull String key, @NotNull List<String> lore);

    void clearAllAttribute(@NotNull Player player);

    void setEntityAttribute(@NotNull LivingEntity entity, @NotNull List<String> lore);

    String getFilterCode();
}
