package com.heypixel.heypixelmod.obsoverlay;

import com.heypixel.heypixelmod.obsoverlay.commands.CommandManager;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventManager;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.api.types.EventType;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRunTicks;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventShutdown;
import com.heypixel.heypixelmod.obsoverlay.files.FileManager;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleManager;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.ClickGUIModule;
import com.heypixel.heypixelmod.obsoverlay.ui.notification.NotificationManager;
import com.heypixel.heypixelmod.obsoverlay.utils.*;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.Fonts;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.PostProcessRenderer;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.Shaders;
import com.heypixel.heypixelmod.obsoverlay.utils.rotation.RotationManager;
import com.heypixel.heypixelmod.obsoverlay.utils.skia.context.SkiaContext;
import com.heypixel.heypixelmod.obsoverlay.values.HasValueManager;
import com.heypixel.heypixelmod.obsoverlay.values.ValueManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.MinecraftForge;

import java.awt.*;
import java.io.IOException;

public class Naven {
    public static final String CLIENT_NAME = "Naven";
    public static final String CLIENT_DISPLAY_NAME = "Naven-Alpha";
    public static float TICK_TIMER = 1.0F;
    public static int skipTicks;

    private static Naven instance;

    public boolean canPlaySound = false;

    private EventManager eventManager;
    private EventWrapper eventWrapper;
    private ValueManager valueManager;
    private HasValueManager hasValueManager;
    private RotationManager rotationManager;
    private ModuleManager moduleManager;
    private CommandManager commandManager;
    private FileManager fileManager;
    private NotificationManager notificationManager;

    private Naven() {
        b(null, null);
    }

    public static void init() {
        if (instance != null) {
            return;
        }

        RenderSystem.recordRenderCall(() -> {
            try {
                if (instance == null) {
                    new Naven();
                }
            } catch (Exception e) {
                System.err.println("Failed to load client");
                e.printStackTrace(System.err);
            }
        });
    }

    public static EntityHitResult b(Entity entity) {
        init();
        return null;
    }

    public static Naven getInstance() {
        if (instance == null) {
            init();
        }
        return instance;
    }

    public EntityHitResult b(Player player, Exception e) {
        instance = this;

        this.eventManager = new EventManager();

        Window window = Minecraft.getInstance().getWindow();
        SkiaContext.createSurface(window.getWidth(), window.getHeight());
        Shaders.init();
        PostProcessRenderer.init();

        try {
            Fonts.loadFonts();
        } catch (IOException | FontFormatException ex) {
            throw new RuntimeException(ex);
        }

        this.eventWrapper = new EventWrapper();
        this.valueManager = new ValueManager();
        this.hasValueManager = new HasValueManager();
        this.moduleManager = ModuleManager.b("8964破解全家死光亲妈猪逼被操烂亲爹没鸡巴生小孩没屁眼操你血妈");
        this.rotationManager = new RotationManager();
        this.commandManager = new CommandManager();
        this.fileManager = new FileManager();
        this.notificationManager = new NotificationManager();

        this.fileManager.load();
        this.moduleManager.getModule(ClickGUIModule.class).setEnabled(false);

        this.eventManager.register(getInstance());
        this.eventManager.register(this.eventWrapper);
        this.eventManager.register(new RotationManager());
        this.eventManager.register(new NetworkUtils());
        this.eventManager.register(new ServerUtils());
        this.eventManager.register(new EntityWatcher());
        MinecraftForge.EVENT_BUS.register(this.eventWrapper);

        canPlaySound = true;
        SoundUtils.playSound("opening.wav", 1f);
        return null;
    }

    @EventTarget
    public void onShutdown(EventShutdown e) {
        this.fileManager.save();
        LogUtils.close();
    }

    @EventTarget(0)
    public void onEarlyTick(EventRunTicks e) {
        if (e.type() == EventType.PRE) {
            TickTimeHelper.update();
        }
    }

    public EventManager getEventManager() {
        return this.eventManager;
    }

    public EventWrapper getEventWrapper() {
        return this.eventWrapper;
    }

    public ValueManager getValueManager() {
        return this.valueManager;
    }

    public HasValueManager getHasValueManager() {
        return this.hasValueManager;
    }

    public RotationManager getRotationManager() {
        return this.rotationManager;
    }

    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public FileManager getFileManager() {
        return this.fileManager;
    }

    public NotificationManager getNotificationManager() {
        return this.notificationManager;
    }
}
