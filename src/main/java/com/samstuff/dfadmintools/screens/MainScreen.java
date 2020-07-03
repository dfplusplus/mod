package com.samstuff.dfadmintools.screens;

import com.samstuff.dfadmintools.PermissionLevel;
import com.samstuff.dfadmintools.actions.DisplayScreenAction;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;

public class MainScreen extends ButtonScreen {
    public MainScreen(Screen priorScreen) {
        super(priorScreen);
        if (PermissionLevel.hasPerms(PermissionLevel.ADMIN)) addButton("gui.dfadmintools.mainscreen.adminscreen", new DisplayScreenAction(new AdminScreen(this)));
        if (PermissionLevel.hasPerms(PermissionLevel.MOD)) addButton("gui.dfadmintools.mainscreen.modscreen",new DisplayScreenAction(new ModScreen(this)));
        addButton("gui.dfadmintools.mainscreen.generalscreen",new DisplayScreenAction(new GeneralScreen(this)));
    }

    @Override
    public String getName() {
        return "gui.dfadmintools.mainscreen";
    }
}
