package tech.naven;

import com.heypixel.heypixelmod.obsoverlay.Naven;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

public class NavenClientEntrypoint implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> Naven.getInstance().initialize());
    }
}
