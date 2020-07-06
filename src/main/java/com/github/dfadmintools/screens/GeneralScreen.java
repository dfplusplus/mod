package com.github.dfadmintools.screens;

import net.minecraft.client.gui.screen.Screen;

public class GeneralScreen extends ButtonScreen {
    public GeneralScreen(Screen priorScreen) {
        super(priorScreen);
        addCommandButton("/fly");
    }

    @Override
    public String getName() {
        return "gui.dfadmintools.generalscreen";
    }
}
