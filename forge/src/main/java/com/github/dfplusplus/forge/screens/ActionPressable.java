package com.github.dfplusplus.forge.screens;

import com.github.dfplusplus.forge.actions.Action;
import net.minecraft.client.gui.widget.button.Button;

public class ActionPressable implements Button.IPressable {
    private final Action action;

    public ActionPressable(Action action) {
        this.action = action;
    }

    @Override
    public void onPress(Button p_onPress_1_) {
        action.run();
    }
}
