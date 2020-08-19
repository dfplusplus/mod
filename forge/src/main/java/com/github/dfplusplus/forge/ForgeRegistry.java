package com.github.dfplusplus.forge;

import com.github.dfplusplus.common.KeyBinds;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ForgeRegistry {
    public static void registerKeyBindings() {
        for (KeyBinding keyBinding : KeyBinds.fetchKeyBindings())
            ClientRegistry.registerKeyBinding(keyBinding);
    }
}
