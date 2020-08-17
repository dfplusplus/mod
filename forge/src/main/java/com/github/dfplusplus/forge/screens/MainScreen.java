package com.github.dfplusplus.forge.screens;

import com.github.dfplusplus.forge.PermissionLevel;
import com.github.dfplusplus.forge.actions.DisplayScreenAction;
import net.minecraft.client.gui.screen.Screen;

public class MainScreen extends ButtonScreen {
    public MainScreen(Screen priorScreen) {
        super(priorScreen);
        if (PermissionLevel.hasPerms(PermissionLevel.ADMIN)) addButton("gui.dfplusplus.mainscreen.adminscreen", new DisplayScreenAction(new AdminScreen(this)));
        if (PermissionLevel.hasPerms(PermissionLevel.MOD)) addButton("gui.dfplusplus.mainscreen.modscreen",new DisplayScreenAction(new ModScreen(this)));
        addButton("gui.dfplusplus.mainscreen.generalscreen",new DisplayScreenAction(new GeneralScreen(this)));
    }

    @Override
    public String getName() {
        return "gui.dfplusplus.mainscreen";
    }
}
