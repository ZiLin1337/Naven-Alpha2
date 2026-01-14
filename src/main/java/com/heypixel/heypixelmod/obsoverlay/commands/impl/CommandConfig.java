package com.heypixel.heypixelmod.obsoverlay.commands.impl;

import com.heypixel.heypixelmod.obsoverlay.commands.Command;
import com.heypixel.heypixelmod.obsoverlay.commands.CommandInfo;
import com.heypixel.heypixelmod.obsoverlay.files.FileManager;
import com.heypixel.heypixelmod.obsoverlay.utils.ChatUtils;

import java.awt.*;

@CommandInfo(
        name = "config",
        description = "Open client config folder.",
        aliases = {"conf"}
)
public class CommandConfig extends Command {
    @Override
    public void onCommand(String[] args) {
        if (args.length > 0) {
            ChatUtils.addChatMessage("Cloud config is not available in this build.");
            return;
        }

        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(FileManager.clientFolder);
                ChatUtils.addChatMessage("Opened config folder: " + FileManager.clientFolder.getAbsolutePath());
            } else {
                ChatUtils.addChatMessage("Config folder: " + FileManager.clientFolder.getAbsolutePath());
            }
        } catch (Exception e) {
            ChatUtils.addChatMessage("Failed to open config folder: " + e.getMessage());
        }
    }

    @Override
    public String[] onTab(String[] args) {
        return new String[0];
    }
}
