package com.github.dfplusplus.common.screens;

import com.github.dfplusplus.common.actions.ArgCommandAction;
import net.minecraft.client.gui.screen.Screen;

public class ModScreen extends ButtonScreen {
    public static final boolean IS_ENABLED = true;

    @Override
    public String getName() {
        return "gui.dfplusplus.modscreen";
    }

    public ModScreen(Screen priorScreen) {
        super(priorScreen);
        addButton("/ban", ArgCommandAction.getBanAction(this));
        addButton("/warn", ArgCommandAction.getWarnAction(this));
        addButton("/unwarn", ArgCommandAction.getUnwarnAction(this));
        addButton("/tempban", ArgCommandAction.getTempbanAction(this));
        addButton("/hist",ArgCommandAction.getHistAction(this));
        addButton("/alts",ArgCommandAction.getAltsAction(this));
        addButton("/mod log",ArgCommandAction.getModLogAction(this));
    }
}
