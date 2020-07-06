package com.github.dfplusplus.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;

public class AdminScreen extends ButtonScreen {
    public static final boolean IS_ENABLED = true;

    public AdminScreen(Screen priorScreen) {
        super(priorScreen);
        addCommandButton("/adminv on");
        addCommandButton("/adminv off");
        addCommandButton("/adminv join");
        addCommandButton("/adminv quit");
    }

    @Override
    public String getName() {
        return "gui.dfadmintools.adminscreen";
    }

    public void onTestButtonPress(Button button) {
        minecraft.displayGuiScreen(null);
    }
}
