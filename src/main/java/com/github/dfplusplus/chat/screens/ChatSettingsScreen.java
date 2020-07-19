package com.github.dfplusplus.chat.screens;

import com.github.dfplusplus.Config;
import com.github.dfplusplus.chat.ChatPredicates;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;

public class ChatSettingsScreen extends Screen {

    private TextFieldWidget customWordsField;
//    private TextFieldWidget highlightWordsField;
//    private Button pingHighlightsButton;
    private final Screen priorScreen;

    public ChatSettingsScreen(Screen priorScreen) {
        super(new TranslationTextComponent("Settings"));
        this.priorScreen = priorScreen;
    }

    public void onCustomWordsFieldUpdate(String newText) {
        ChatPredicates.setCustomWords(newText);
    }

    @Override
    protected void init() {
        super.init();
        minecraft.keyboardListener.enableRepeatEvents(true);
        customWordsField = new TextFieldWidget(minecraft.fontRenderer,width/2 - 100,30,200,20,"Test");
        customWordsField.setMaxStringLength(1000);
        customWordsField.setText(ChatPredicates.getCustomWords());
        customWordsField.setResponder(this::onCustomWordsFieldUpdate);
//        highlightWordsField = new TextFieldWidget(minecraft.fontRenderer,width/2 - 100,90,200,20,"Test");
        if (priorScreen != null) addButton(new Button(10,10,50,20,"Back",this::onBackButtonPress));
        addButton(new Button((this.width / 2) - 100,90,200,20,"Chat Types Settings...",this::onChatTypesButtonPress));
        addButton(new Button((this.width / 2) - 100,115,200,20,"Side Chat Sizing...",this::onChatSizingButtonPress));

        children.add(customWordsField);
    }

    private void onBackButtonPress(Button button) {
        minecraft.displayGuiScreen(priorScreen);
    }

    private void onChatTypesButtonPress(Button button) {
        minecraft.displayGuiScreen(new ChatTypesScreen(this));
    }

    private void onChatSizingButtonPress(Button button) {
        minecraft.displayGuiScreen(new ChatSizingScreen(this));
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
        this.drawString(minecraft.fontRenderer,"Messages containing these phrases are",width/2 - 100,52,16777215);
        this.drawString(minecraft.fontRenderer,"put into the 'Custom' second chat.",width/2 - 100,62,16777215);
        this.drawString(minecraft.fontRenderer,"Separate phrases with commas.",width/2 - 100,72,16777215);

//        this.drawString(minecraft.fontRenderer,"Highlight Keywords",width/2 - 100,80,16777215);
//        highlightWordsField.render(p_render_1_,p_render_2_,p_render_3_);
//        this.drawString(minecraft.fontRenderer,"These words are highlighted",width/2 - 100,122,16777215);
    }

    @Override
    public void removed() {
        Config.setCustomWords(customWordsField.getText());
        minecraft.keyboardListener.enableRepeatEvents(false);
    }
}
