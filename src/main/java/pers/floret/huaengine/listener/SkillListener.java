package pers.floret.huaengine.listener;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.serverct.ersha.api.AttributeAPI;
import org.serverct.ersha.attribute.data.AttributeData;
import pers.floret.huaengine.config.Config;
import pers.floret.huaengine.damagedisplay.WorldTextureManager;
import pers.floret.huaengine.runtime.DataManager;
import pers.floret.huaengine.util.Scheduler;
import sharks.mythicskillview.api.event.SkillEvents;

import java.util.UUID;

public class SkillListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void skillAttack(SkillEvents.Damage e) {
        // 玩家释放mm技能时 创建伤害显示生僻字
        if(e.getDefender() instanceof LivingEntity) {
            boolean isSkillApDamage = Config.get().skillApDamage;
            DataManager.get().setApDamage(isSkillApDamage);
            // 转为生命实体
            LivingEntity livingEntity = (LivingEntity) e.getDefender();
            // 获取目标的属性数据
            AttributeData attrData = AttributeAPI.getAttrData(livingEntity);
            // 获取目标的物理防御
            double defense = (double) attrData.getAttributeValue("物理防御")[1];
            // 如果目标是盔甲架则跳过
            if (e.getDefender().getType().equals(EntityType.ARMOR_STAND)) return;
            // 如果伤害低于防御则跳过
            if (e.getDamage() <= defense) return;
            // 获取最终伤害
            int damage = (int) (e.getDamage() - defense);
            String line = "嵇:技能伤害:" + damage;
            // 生成一个随机id
            String uuid = UUID.randomUUID().toString();
            // 获取目标坐标
            Location location = e.getDefender().getLocation().add(0, Math.random() * 2.5 + 2.5, 0);
            // 生成伤害显示贴图
            WorldTextureManager.create(uuid, location, line, true, 0.6F, 0.6F);
            // 删除伤害显示贴图
            Scheduler.runTaskLater(() -> WorldTextureManager.delete(uuid), 26);
        }
    }
}
