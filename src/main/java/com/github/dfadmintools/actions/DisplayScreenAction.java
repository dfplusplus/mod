package com.github.dfadmintools.actions;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;

public class DisplayScreenAction extends Action {
    protected Screen screen;

    public DisplayScreenAction(Screen screen) {
        this.screen = screen;
    }

    @Override
    public void run() {
        Minecraft.getInstance().displayGuiScreen(screen);
    }
}
