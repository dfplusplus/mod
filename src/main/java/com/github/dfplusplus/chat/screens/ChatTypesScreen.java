package com.github.dfplusplus.chat.screens;

import com.github.dfplusplus.PermissionLevel;
import com.github.dfplusplus.chat.ChatRule;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ChatTypesScreen extends Screen {
    private final Screen priorScreen;

    public ChatTypesScreen(Screen priorScreen) {
        super(new TranslationTextComponent("Settings"));
        this.priorScreen = priorScreen;
    }

    @Override
    protected void init() {
        super.init();
        addButtons();
        if (priorScreen != null) addButton(new Button(10,10,50,20,new TranslationTextComponent("gui.back"),this::onBackButtonPress));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack,mouseX,mouseY,partialTicks);
    }

    private void addButtons() {
        int BUTTON_WIDTH = 200;
        int x = (this.width / 2) - (BUTTON_WIDTH / 2);
        int y = 10;
        for (ChatRule.ChatRuleType chatRuleType : ChatRule.ChatRuleType.values()) {
            boolean validButton = true;
            switch (chatRuleType) {
                case SUPPORT: if (!PermissionLevel.hasPerms(PermissionLevel.SUPPORT)) validButton = false; break; // dont show button if no support perms
                case SESSION: if (!PermissionLevel.hasPerms(PermissionLevel.EXPERT)) validButton = false; break;
                case MOD: if (!PermissionLevel.hasPerms(PermissionLevel.MOD)) validButton = false; break;
                case ADMIN: if (!PermissionLevel.hasPerms(PermissionLevel.ADMIN)) validButton = false; break;
            }
            if (!validButton) continue;
            ChatRule chatRule = ChatRule.getChatRule(chatRuleType);

            int BUTTON_HEIGHT = 20;
            Button button = new Button(x, y, BUTTON_WIDTH, BUTTON_HEIGHT, new StringTextComponent(""), new ChatTypeSettingsHandler(chatRuleType));
            button.setMessage(getButtonMessage(chatRuleType));
            addButton(button);
            int BUTTON_GAP = 5;
            y+=(BUTTON_HEIGHT + BUTTON_GAP);
        }
    }

    private void onBackButtonPress(Button button) {
        minecraft.displayGuiScreen(priorScreen);
    }

    private static ITextComponent getButtonMessage(ChatRule.ChatRuleType chatRuleType) {
        return new TranslationTextComponent(String.format("gui.dfplusplus.%s.settings", chatRuleType.name().toLowerCase()));
    }

    private static class ChatTypeSettingsHandler implements Button.IPressable {
        private final ChatRule.ChatRuleType chatRuleType;

        public ChatTypeSettingsHandler(ChatRule.ChatRuleType chatRuleType) {
            this.chatRuleType = chatRuleType;
        }

        @Override
        public void onPress(Button button) {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.displayGuiScreen(new ChatTypeScreen(minecraft.currentScreen,chatRuleType));
        }
    }
}
