package com.github.dfplusplus.chat;

import com.github.dfplusplus.DFPlusPlusConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
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
        if (priorScreen != null) addButton(new Button(10,10,50,20,"Back",this::onBackButtonPress));
        int x = (this.width / 2) - 100;
        addButton(new Button(x,10,200,20, getSideMessage(getChatRule().getChatSide()),this::onChangeSidePress));
        addButton(new Button(x,35,200,20, getSoundMessage(getChatRule().getChatSound()),this::onChangeSoundPress));
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        super.render(p_render_1_, p_render_2_, p_render_3_);
    }

    private ChatRule getChatRule() {
        return ChatRule.getChatRule(chatRuleType);
    }

    private void onChangeSidePress(Button button) {
        ChatRule.ChatSide newChatSide = ChatRule.toggleChatType(chatRuleType);
        button.setMessage(getSideMessage(newChatSide));

        DFPlusPlusConfig.setChatSide(chatRuleType,newChatSide);
    }

    private void onChangeSoundPress(Button button) {
        getChatRule().setChatSound(getChatRule().getChatSound().next());
        button.setMessage(getSoundMessage(getChatRule().getChatSound()));

        DFPlusPlusConfig.setChatSound(chatRuleType, getChatRule().getChatSound());
    }

    private String getSideMessage(ChatRule.ChatSide newChatSide) {
        return I18n.format(String.format("gui.dfplusplus.chatside.%s", newChatSide.name().toLowerCase()));
    }

    private String getSoundMessage(ChatRule.ChatSound chatSound) {
        return I18n.format(String.format("gui.dfplusplus.chatsound.%s", chatSound.name().toLowerCase()));
    }

    private void onBackButtonPress(Button button) {
        minecraft.displayGuiScreen(priorScreen);
    }
}
