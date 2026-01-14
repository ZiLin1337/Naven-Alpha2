package com.heypixel.heypixelmod.obsoverlay.modules.impl.misc;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventPacket;
import com.heypixel.heypixelmod.obsoverlay.modules.Category;
import com.heypixel.heypixelmod.obsoverlay.modules.Module;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import net.minecraft.world.entity.Entity;

@ModuleInfo(
        name = "ClientFriend",
        cnName = "客户端朋友",
        description = "Treat other users as friend!",
        category = Category.MISC
)
public class ClientFriend extends Module {
    public static boolean isUser(Entity entity) {
        return false;
    }

    @EventTarget
    public void onPacket(EventPacket event) {
    }
}
