package pers.floret.huaengine.util.hooks.imp;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.serverct.ersha.api.AttributeAPI;
import org.serverct.ersha.attribute.data.AttributeData;
import org.serverct.ersha.attribute.data.AttributeSource;
import pers.floret.huaengine.HuaEngine;
import pers.floret.huaengine.util.hooks.AttributeHelper;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class AttributePlusHook_3 implements AttributeHelper {

    private final Map<UUID, Set<String>> uuidBySources = new ConcurrentHashMap<>();

    @Override
    public void setPlayerAttribute(@NotNull Player player, @NotNull String key, @NotNull List<String> lore) {
        AttributeSource source = AttributeAPI.getAttributeSource(lore);
        AttributeData data = AttributeAPI.getAttrData(player);
        data.takeApiAttribute(key);
        data.operationApiAttribute(key, source, AttributeSource.OperationType.ADD, true);
        this.uuidBySources.computeIfAbsent(player.getUniqueId(), t -> new HashSet<>()).add(key);
    }

    @Override
    public void clearAllAttribute(@NotNull Player player) {
        AttributeData data = AttributeAPI.getAttrData(player);
        Set<String> sources = this.uuidBySources.getOrDefault(player.getUniqueId(), new HashSet<>());
        for (String source : sources) {
            data.takeApiAttribute(source);
        }
        data.updateAttribute(false);
    }

    @Override
    public void setEntityAttribute(@NotNull LivingEntity entity, @NotNull List<String> lore) {
        AttributeData attrData = AttributeAPI.getAttrData(entity);
        AttributeSource source = AttributeAPI.getAttributeSource(lore);
        attrData.operationApiAttribute(HuaEngine.ins().getName(), source, AttributeSource.OperationType.ADD, true);
    }

    @Override
    public String getFilterCode() {
        return "§-";
    }

}
