package com.github.dfplusplus.chat;

import com.github.dfplusplus.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class ChatScreenOverride extends ChatScreen {
    private Button settingsButton;
    private final ChatGuiOverride chatGuiOverride;

    public ChatScreenOverride(String defaultText, ChatGuiOverride chatGuiOverride) {
        super(defaultText);
        this.chatGuiOverride = chatGuiOverride;
    }

    public static int i1 = 0;

    @Override
    protected void init() {
        super.init();
        this.settingsButton = new Button(2,height - 36,125,20 ,"Settings...",this::onSettingsButtonPress);
        this.addButton(settingsButton);
    }

    public void onSettingsButtonPress(Button button) {
        minecraft.displayGuiScreen(new ChatSettingsScreen((this)));
        this.defaultInputFieldText = this.inputField.getText();
    }

    @SubscribeEvent
    public static void onGuiInitPre(GuiScreenEvent.InitGuiEvent.Pre initGuiEvent) {
        if (initGuiEvent.getGui().getClass() == ChatScreen.class) {
            initGuiEvent.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onGuiInitPost(GuiScreenEvent.InitGuiEvent.Post initGuiEvent) {
        if (initGuiEvent.getGui().getClass() == ChatScreen.class) {
            Minecraft.getInstance().displayGuiScreen(new ChatScreenOverride(((ChatScreen) initGuiEvent.getGui()).defaultInputFieldText, ((ChatGuiOverride) Minecraft.getInstance().ingameGUI.persistantChatGUI)));
        }
    }
}
