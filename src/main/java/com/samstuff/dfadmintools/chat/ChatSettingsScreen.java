package com.samstuff.dfadmintools.chat;

import com.samstuff.dfadmintools.DFPlusPlusConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ChatSettingsScreen extends Screen {
    private final int BUTTON_WIDTH = 200;
    private final int BUTTON_HEIGHT = 20;
    private final int BUTTON_GAP = 5;

    private TextFieldWidget customWordsField;
    private TextFieldWidget highlightWordsField;
    private Button pingHighlightsButton;

    protected ChatSettingsScreen(ITextComponent titleIn) {
        super(titleIn);
    }

    public ChatSettingsScreen() {
        super(new TranslationTextComponent("Settings"));
    }

    public void onCustomWordsFieldUpdate(String newText) {
        ChatPredicates.setCustomWords(newText);
    }

    @Override
    protected void init() {
        super.init();
        customWordsField = new TextFieldWidget(minecraft.fontRenderer,width/2 - 100,30,200,20,"Test");
        customWordsField.setText(ChatPredicates.getCustomWords());
        customWordsField.setResponder(this::onCustomWordsFieldUpdate);
        customWordsField.setMaxStringLength(1000);
        highlightWordsField = new TextFieldWidget(minecraft.fontRenderer,width/2 - 100,90,200,20,"Test");
        addButtons();

        children.add(customWordsField);
    }

    private void addButtons() {
        int x = (this.width / 2) - (BUTTON_WIDTH / 2);
        int y = 80;
        for (ChatRule.ChatRuleType chatRuleType : ChatRule.ChatRuleType.values()) {
            ChatRule chatRule = ChatRule.getChatRule(chatRuleType);

            Button button = new Button(x, y, BUTTON_WIDTH, BUTTON_HEIGHT, "", new ToggleChatHandler(chatRuleType));
            button.setMessage(getButtonMessage(chatRule.getChatSide(),chatRuleType));
            addButton(button);
            y+=(BUTTON_HEIGHT + BUTTON_GAP);
        }
    }

    private static String getButtonMessage(ChatRule.ChatSide chatSide, ChatRule.ChatRuleType chatRuleType) {
        return String.format("%s side: %s", chatRuleType.name(), chatSide.name());
    }

    @Override
    public void tick() {
        super.tick();
        customWordsField.tick();
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        super.render(p_render_1_, p_render_2_, p_render_3_);

        this.drawString(minecraft.fontRenderer,"Custom Chat Keywords",width/2 - 100,20,16777215);
        customWordsField.render(p_render_1_,p_render_2_,p_render_3_);
        this.drawString(minecraft.fontRenderer,"Messages containing these words are",width/2 - 100,52,16777215);
        this.drawString(minecraft.fontRenderer,"put into the 'Custom' second chat",width/2 - 100,62,16777215);

//        this.drawString(minecraft.fontRenderer,"Highlight Keywords",width/2 - 100,80,16777215);
//        highlightWordsField.render(p_render_1_,p_render_2_,p_render_3_);
//        this.drawString(minecraft.fontRenderer,"These words are highlighted",width/2 - 100,122,16777215);
    }

    @Override
    public void removed() {
        DFPlusPlusConfig.setCustomWords(customWordsField.getText());
    }

    private static class ToggleChatHandler implements Button.IPressable {
        private ChatRule.ChatRuleType chatRuleType;

        public ToggleChatHandler(ChatRule.ChatRuleType chatRuleType) {
            this.chatRuleType = chatRuleType;
        }

        @Override
        public void onPress(Button button) {
            ChatRule.ChatSide newChatSide = ChatRule.toggleChatType(chatRuleType);
            button.setMessage(getButtonMessage(newChatSide,chatRuleType));
        }
    }
}
