package com.github.dfplusplus.chat.screens;

import com.github.dfplusplus.Config;
import com.github.dfplusplus.Util;
import com.github.dfplusplus.chat.ChatRule;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ChatTypeScreen extends Screen {
    private final Screen priorScreen;
    private final ChatRule.ChatRuleType chatRuleType;

    public ChatTypeScreen(Screen priorScreen, ChatRule.ChatRuleType chatRuleType) {
        super(new TranslationTextComponent(ChatRule.getChatRule(chatRuleType).getName()));
        this.priorScreen = priorScreen;
        this.chatRuleType = chatRuleType;
    }

    @Override
    protected void init() {
        super.init();
        if (priorScreen != null) addButton(new Button(10,10,50,20,new TranslationTextComponent("gui.back"),this::onBackButtonPress));
        int x = (this.width / 2) - 100;
        addButton(new Button(x,10,200,20, getSideMessage(getChatRule().getChatSide()),this::onChangeSidePress));
        addButton(new Button(x,35,200,20, getSoundMessage(getChatRule().getChatSound()),this::onChangeSoundPress));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack,mouseX,mouseY,partialTicks);
    }

    private ChatRule getChatRule() {
        return ChatRule.getChatRule(chatRuleType);
    }

    private void onChangeSidePress(Button button) {
        ChatRule.ChatSide newChatSide = ChatRule.toggleChatType(chatRuleType);
        button.setMessage(getSideMessage(newChatSide));

        Config.setChatSide(chatRuleType,newChatSide);
    }

    private void onChangeSoundPress(Button button) {
        getChatRule().setChatSound(getChatRule().getChatSound().next());
        button.setMessage(getSoundMessage(getChatRule().getChatSound()));

        if (getChatRule().getChatSound() != ChatRule.ChatSound.NONE) {
            Util.playSound(getChatRule().getChatSound().getSoundEvent());
        };

        Config.setChatSound(chatRuleType, getChatRule().getChatSound());
    }

    private ITextComponent getSideMessage(ChatRule.ChatSide newChatSide) {
        return new TranslationTextComponent("gui.dfplusplus.chatside.%s", newChatSide.name().toLowerCase());
    }

    private ITextComponent getSoundMessage(ChatRule.ChatSound chatSound) {
        return new TranslationTextComponent("gui.dfplusplus.chatsound.%s", chatSound.name().toLowerCase());
    }

    private void onBackButtonPress(Button button) {
        minecraft.displayGuiScreen(priorScreen);
    }
}
