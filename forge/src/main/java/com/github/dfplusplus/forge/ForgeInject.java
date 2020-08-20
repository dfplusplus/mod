package com.github.dfplusplus.forge;

import com.github.dfplusplus.common.chat.ChatGuiOverride;
import net.minecraft.client.Minecraft;

public class ForgeInject {
    public static void inject() {
        Minecraft mc = Minecraft.getInstance();
        mc.ingameGUI.persistantChatGUI = new ChatGuiOverride(mc);
    }
}
