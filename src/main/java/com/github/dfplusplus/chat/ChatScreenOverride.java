package com.github.dfplusplus.chat;

import com.github.dfplusplus.Main;
import com.github.dfplusplus.Util;
import com.github.dfplusplus.chat.screens.ChatSettingsScreen;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class ChatScreenOverride extends ChatScreen {
    private Button settingsButton;
    private final ChatGuiOverride chatGuiOverride;
    private final ChatRoom chatRoom;

    public ChatScreenOverride(String defaultText, ChatGuiOverride chatGuiOverride, ChatRoom chatRoom) {
        super(defaultText);
        this.chatGuiOverride = chatGuiOverride;
        this.chatRoom = chatRoom;
    }

    public static int i1 = 0;

    @Override
    protected void init() {
        super.init();
        this.settingsButton = new Button(2,2,125,20 ,"Settings...",this::onSettingsButtonPress);
        this.addButton(settingsButton);
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.setFocused(this.inputField);
        this.inputField.setFocused2(true);
        fill(2, this.height - 14, this.width - 2, this.height - 2, chatRoom.getColor());
        this.inputField.render(p_render_1_, p_render_2_, p_render_3_);
        this.commandSuggestionHelper.render(p_render_1_, p_render_2_);
        ITextComponent itextcomponent = this.minecraft.ingameGUI.getChatGUI().getTextComponent((double)p_render_1_, (double)p_render_2_);
        if (itextcomponent != null && itextcomponent.getStyle().getHoverEvent() != null) {
            this.renderComponentHoverEffect(itextcomponent, p_render_1_, p_render_2_);
        }

        super.render(p_render_1_, p_render_2_, p_render_3_);
    }

    // COPIED FROM ChatScreen#keyPressed
    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (p_keyPressed_1_ == 257) { // if enter was pressed
            sendToChat(); // do my custom chat stuff
            return true;
        } else { // else do what you normally do
            return super.keyPressed(p_keyPressed_1_,p_keyPressed_2_,p_keyPressed_3_);
        }
    }

    public void onSettingsButtonPress(Button button) {
        minecraft.displayGuiScreen(new ChatSettingsScreen((this)));
        this.defaultInputFieldText = this.inputField.getText();
    }

    private void sendToChat() {
        String s = this.inputField.getText().trim();
        if (!s.isEmpty()) {
            switch (this.chatRoom) {
                case DEFAULT_CHAT:
                    this.sendMessage(s);
                    break;
                case SUPPORT_CHAT:
                    this.sendMessage(String.format("/sb %s", s));
                    break;
                case MOD_CHAT:
                    this.sendMessage(String.format("/mb %s", s));
                    break;
                case ADMIN_CHAT:
                    this.sendMessage(String.format("/ab %s", s));
                    break;
            }
        }

        this.minecraft.displayGuiScreen((Screen)null);
    }

    @SubscribeEvent
    public static void onGuiInitPre(GuiScreenEvent.InitGuiEvent.Pre initGuiEvent) {
        if (initGuiEvent.getGui().getClass() == ChatScreen.class) { // doesnt use instanceof since it must exactly match that class
            initGuiEvent.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onGuiInitPost(GuiScreenEvent.InitGuiEvent.Post initGuiEvent) {
        if (initGuiEvent.getGui().getClass() == ChatScreen.class) {
            showChat(((ChatScreen) initGuiEvent.getGui()).defaultInputFieldText, ChatRoom.DEFAULT_CHAT);
        }
    }

    public static void showChat(String defaultInputFieldText, ChatRoom chatRoom) {
        Minecraft.getInstance().displayGuiScreen(new ChatScreenOverride(
                defaultInputFieldText,
                ((ChatGuiOverride) Minecraft.getInstance().ingameGUI.persistantChatGUI),
                chatRoom
        ));
    }

    public static void showChat(ChatRoom chatRoom) {
        ChatScreenOverride.showChat("",chatRoom);
    }
}
