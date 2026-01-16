package com.heypixel.heypixelmod.obsoverlay.utils;

import com.heypixel.heypixelmod.obsoverlay.Naven;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventManager;
import com.heypixel.heypixelmod.obsoverlay.events.api.types.EventType;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventClientChat;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRunTicks;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventShutdown;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;

@Environment(EnvType.CLIENT)
public final class FabricEventAdapter {
    private static boolean registered;

    private FabricEventAdapter() {
    }

    public static synchronized void registerAllEvents() {
        if (registered) {
            return;
        }
        registered = true;

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            Naven naven = Naven.getInstance();
            EventManager eventManager = naven.getEventManager();
            if (eventManager != null) {
                eventManager.call(new EventRunTicks(EventType.PRE));
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            Naven naven = Naven.getInstance();
            EventManager eventManager = naven.getEventManager();
            if (eventManager != null) {
                eventManager.call(new EventRunTicks(EventType.POST));
            }
        });

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            Naven naven = Naven.getInstance();
            EventManager eventManager = naven.getEventManager();
            if (eventManager != null) {
                eventManager.call(new EventShutdown());
            }
        });

        ClientSendMessageEvents.ALLOW_CHAT.register(message -> {
            Naven naven = Naven.getInstance();
            EventManager eventManager = naven.getEventManager();
            if (eventManager == null) {
                return true;
            }

            EventClientChat event = new EventClientChat(message);
            eventManager.call(event);
            return !event.isCancelled();
        });
    }
}
