package com.github.dfplusplus.fabric;

import com.github.dfplusplus.common.KeyBinds;
import com.github.dfplusplus.common.Util;
import com.github.dfplusplus.common.chat.ChatGuiOverride;
import com.github.dfplusplus.common.chat.ChatRoom;
import com.github.dfplusplus.common.chat.ChatScreenOverride;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;

public class FabricEventHandlers {
    public static void loadEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            if (Util.isValidClient()) {
                KeyBinds.onValidKeyPress();

                if (
                    minecraftClient.currentScreen != null &&
                    minecraftClient.currentScreen.getClass() == ChatScreen.class
                ) {
                    minecraftClient.openScreen(new ChatScreenOverride("", ((ChatGuiOverride) minecraftClient.inGameHud.getChatHud()), ChatRoom.DEFAULT_CHAT));
                }
            }
        });
    }
}
