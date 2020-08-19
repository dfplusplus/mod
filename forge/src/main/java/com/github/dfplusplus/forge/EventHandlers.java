package com.github.dfplusplus.forge;

import com.github.dfplusplus.common.chat.ChatRoom;
import com.github.dfplusplus.common.chat.ChatScreenOverride;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.github.dfplusplus.forge.Main.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class EventHandlers {
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
}
