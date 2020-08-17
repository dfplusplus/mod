package com.github.dfplusplus.forge.actions;

import net.minecraft.client.Minecraft;

public class CommandAction extends Action {
    private String command;

    public CommandAction(String command) {
        this.command = command;
    }

    public void run() {
        Minecraft.getInstance().player.sendChatMessage(command);
        Minecraft.getInstance().displayGuiScreen(null);
    }
}
