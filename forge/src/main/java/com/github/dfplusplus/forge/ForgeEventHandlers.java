package com.github.dfplusplus.forge;

import com.github.dfplusplus.common.KeyBinds;
import com.github.dfplusplus.common.Util;
import com.github.dfplusplus.common.chat.ChatRoom;
import com.github.dfplusplus.common.chat.ChatScreenOverride;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.github.dfplusplus.forge.ForgeMain.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class ForgeEventHandlers {
    @SubscribeEvent
    public static void onGuiInitPre(GuiScreenEvent.InitGuiEvent.Pre initGuiEvent) {
        if (initGuiEvent.getGui().getClass() == ChatScreen.class) { // doesnt use instanceof since it must exactly match that class
            initGuiEvent.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onGuiInitPost(GuiScreenEvent.InitGuiEvent.Post initGuiEvent) {
        if (initGuiEvent.getGui().getClass() == ChatScreen.class) {
            ChatScreenOverride.showChat(((ChatScreen) initGuiEvent.getGui()).defaultInputFieldText, ChatRoom.DEFAULT_CHAT);
        }
    }

    @SubscribeEvent
    public static void onKeyPress(InputEvent.KeyInputEvent keyInputEvent) {
        if (Util.isValidClient()) {
            if (keyInputEvent.getAction() == 1)
                KeyBinds.onBeginKeyPress();

            if (keyInputEvent.getAction() == 0)
                KeyBinds.onEndKeyPress();
        }
    }
}
