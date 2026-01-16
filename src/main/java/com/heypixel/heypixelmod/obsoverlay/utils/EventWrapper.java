package com.heypixel.heypixelmod.obsoverlay.utils;

import com.heypixel.heypixelmod.obsoverlay.Naven;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.api.types.EventType;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventMotion;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRespawn;
import net.minecraft.client.Minecraft;

public class EventWrapper {
    @EventTarget
    public void onMotion(EventMotion e) {
        if (e.getType() == EventType.PRE && Minecraft.getInstance().player != null && Minecraft.getInstance().player.tickCount <= 1) {
            Naven.getInstance().getEventManager().call(new EventRespawn());
        }
    }
}
