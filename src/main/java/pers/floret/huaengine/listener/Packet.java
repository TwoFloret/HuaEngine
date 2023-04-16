package pers.floret.huaengine.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.plugin.Plugin;
import pers.floret.huaengine.config.Rule;

import java.util.List;
import java.util.Random;

public class Packet extends PacketAdapter {
    public Packet(Plugin plugin, ListenerPriority listenerPriority, PacketType... types) {
        super(plugin, listenerPriority, types);
    }
    public void onPacketSending(PacketEvent event) {
        Random r = new Random();
        PacketContainer pc = event.getPacket();
        List<EnumWrappers.Particle> particles = pc.getParticles().getValues();
        if (particles.contains(EnumWrappers.Particle.DAMAGE_INDICATOR)) {
            boolean isOn = Rule.get().reduceDamageEffect;
            int max = Rule.get().damageEffectMax;
            int min = Rule.get().damageEffectMin;
            int index = particles.indexOf(EnumWrappers.Particle.DAMAGE_INDICATOR);
            if (isOn) {
                int rand = r.nextInt(max - min + 1) + min;
                event.getPacket().getIntegers().write(index, rand);
            } else {
                event.setCancelled(true);
            }
        }
    }
}
