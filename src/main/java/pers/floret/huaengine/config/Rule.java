package pers.floret.huaengine.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import pers.floret.huaengine.HuaEngine;
import pers.floret.huaengine.runtime.TpLimit;
import pers.floret.huaengine.util.FileUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Rule {

    private Rule() {
    }

    public boolean banEntityCombust;
    public boolean reduceDamageEffect;
    public int damageEffectMin;
    public int damageEffectMax;
    public boolean fallingDamage;
    public boolean fireDamage;
    public boolean drowningDamage;
    public boolean foodLevelConsume;
    public String banPutOff;
    public boolean banPutOffReverse;
    public boolean banLeftAttack;
    public boolean mainEmptyDamage;
    public String mainHasLore;
    public boolean offEmptyDamage;
    public String offHasLore;
    public boolean banLeftAttackMsg;
    public boolean banBlockBuild;
    public boolean banBlockBuildReverse;
    public boolean banDropItem;
    public boolean banDropItemReverse;
    public boolean banWeatherChange;
    public boolean banInventoryDrop;
    public boolean tpLevelLimit;
    public boolean banHangingBreak;
    public List<String> banEntityCombusts;
    public List<String> banBlockBuildWorlds;
    public List<String> banDropItemWorlds;
    public List<String> banWeatherChangeWorlds;
    public List<String> banInventoryDropWorlds;
    public List<String> banOpenContainer;
    public List<String> foodLevelConsumeList;

    public Map<String, TpLimit> tpLevelLimitMap;
    private static Rule rule;

    public static Rule get() {
        return rule;
    }

    public static void load() {
        File worldLawFile = new File(HuaEngine.ins().getDataFolder(), "WorldLaw.yml");
        FileUtil.saveFile(worldLawFile, "WorldLaw.yml");
        FileConfiguration worldLaw = YamlConfiguration.loadConfiguration(worldLawFile);
        File entityLawFIle = new File(HuaEngine.ins().getDataFolder(), "EntityLaw.yml");
        FileUtil.saveFile(entityLawFIle, "EntityLaw.yml");
        FileConfiguration entityLaw = YamlConfiguration.loadConfiguration(entityLawFIle);
        rule = load(worldLaw, entityLaw);
    }
    private static Rule load(FileConfiguration worldLaw, FileConfiguration entityLaw) {
        Rule rule = new Rule();
        rule.banBlockBuildWorlds = worldLaw.getStringList("ban-block-build.list");
        rule.banBlockBuild = worldLaw.getBoolean("ban-block-build.enable");
        rule.banHangingBreak = worldLaw.getBoolean("ban-block-build.hanging");
        rule.banBlockBuildReverse = worldLaw.getBoolean("ban-block-build.reverse");
        rule.banDropItemWorlds = worldLaw.getStringList("ban-drop-item.list");
        rule.banDropItem = worldLaw.getBoolean("ban-drop-item.enable");
        rule.banDropItemReverse = worldLaw.getBoolean("ban-drop-item.reverse");
        rule.banWeatherChangeWorlds = worldLaw.getStringList("ban-weather-change.list");
        rule.banWeatherChange = worldLaw.getBoolean("ban-weather-change.enable");
        rule.banInventoryDropWorlds = worldLaw.getStringList("ban-inventory-drop.list");
        rule.banInventoryDrop = worldLaw.getBoolean("ban-inventory-drop.enable");

        rule.tpLevelLimit = worldLaw.getBoolean("teleport-level-limit.enable");
        rule.tpLevelLimitMap = setMap(worldLaw.getStringList("teleport-level-limit.list"));

        rule.banEntityCombusts = entityLaw.getStringList("ban-entity-combust.list");
        rule.banEntityCombust = entityLaw.getBoolean("ban-entity-combust.enable");
        rule.banLeftAttack = entityLaw.getBoolean("ban-left-attack.enable");
        rule.mainEmptyDamage = entityLaw.getBoolean("ban-left-attack.main-empty-damage");
        rule.mainHasLore = entityLaw.getString("ban-left-attack.main-has-lore");
        rule.offEmptyDamage = entityLaw.getBoolean("ban-left-attack.off-empty-damage");
        rule.offHasLore = entityLaw.getString("ban-left-attack.off-has-lore");
        rule.banLeftAttackMsg = entityLaw.getBoolean("ban-left-attack.message-enable");
        rule.banOpenContainer = entityLaw.getStringList("ban-open-container.list");
        rule.banPutOff = entityLaw.getString("ban-put-off.identifier");
        rule.banPutOffReverse = entityLaw.getBoolean("ban-put-off.reverse");
        rule.fallingDamage = entityLaw.getBoolean("falling-damage");
        rule.fireDamage = entityLaw.getBoolean("fire-damage");
        rule.drowningDamage = entityLaw.getBoolean("drowning-damage");
        rule.foodLevelConsume = entityLaw.getBoolean("food-level-consume.enable");
        rule.foodLevelConsumeList = entityLaw.getStringList("food-level-consume.player");
        rule.reduceDamageEffect = entityLaw.getBoolean("reduce-damage-effect.enable");
        rule.damageEffectMin = entityLaw.getInt("reduce-damage-effect.min");
        rule.damageEffectMax = entityLaw.getInt("reduce-damage-effect.max");

        return rule;
    }

    @NotNull
    private static Map<String, TpLimit> setMap(List<String> list) {
        Map<String, TpLimit> map = new HashMap<>();
        list.forEach( key -> {
            String[] strings = key.split("\\|");
            TpLimit tpLimit = new TpLimit(Integer.parseInt(strings[1]), strings[2]);
            map.put(strings[0], tpLimit);
        });
        return map;
    }
}
