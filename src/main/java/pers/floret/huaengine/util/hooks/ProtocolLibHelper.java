package pers.floret.huaengine.util.hooks;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import pers.floret.huaengine.HuaEngine;
import pers.floret.huaengine.listener.Packet;
import pers.floret.huaengine.util.hooks.hook.HookHelper;

public class ProtocolLibHelper {
    public static void setup() {
        if (HookHelper.protocolHook.isEnable()) {
            ProtocolManager pm = ProtocolLibrary.getProtocolManager();
            pm.addPacketListener(new Packet(HuaEngine.ins(), ListenerPriority.NORMAL, PacketType.Play.Server.WORLD_PARTICLES));
        }
    }
}
