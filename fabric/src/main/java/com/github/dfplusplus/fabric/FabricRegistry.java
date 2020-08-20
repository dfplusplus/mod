package com.github.dfplusplus.fabric;

import com.github.dfplusplus.common.KeyBinds;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;

public class FabricRegistry {
    public static void registerKeyBindings() {
        for (KeyBinding keyBinding : KeyBinds.fetchKeyBindings())
            KeyBindingHelper.registerKeyBinding(keyBinding);
    }
}
