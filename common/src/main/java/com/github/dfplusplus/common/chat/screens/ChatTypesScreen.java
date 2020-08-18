package com.github.dfplusplus.common.chat.screens;

import com.github.dfplusplus.common.PermissionLevel;
import com.github.dfplusplus.common.chat.ChatRule;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ChatTypesScreen extends Screen {
    private final Screen priorScreen;
    private final WidgetSpacer widgetSpacer;

    public ChatTypesScreen(Screen priorScreen) {
        super(new TranslationTextComponent("gui.dfplusplus.filtersettingstitle"));
        this.priorScreen = priorScreen;
        this.widgetSpacer = new WidgetSpacer();
    }

    @Override
    protected void init() {
        super.init();
        widgetSpacer.reset();
        addButtons();
        if (priorScreen != null) addButton(new Button(10,10,50,20,new TranslationTextComponent("gui.back"),this::onBackButtonPress));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        this.drawCenteredString(matrixStack, this.font, this.title.getString(), this.width / 2, 20, 16777215);
        super.render(matrixStack,mouseX,mouseY,partialTicks);
    }

    private void addButtons() {
        int BUTTON_WIDTH = 200;
        int x = (this.width / 2) - (BUTTON_WIDTH / 2);
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

            Button button = new Button(x, widgetSpacer.getNextY(), BUTTON_WIDTH, 20, new StringTextComponent(""), new ChatTypeSettingsHandler(chatRuleType));
            button.setMessage(getButtonMessage(chatRuleType));
            addButton(button);
        }
    }

    private void onBackButtonPress(Button button) {
        minecraft.displayGuiScreen(priorScreen);
    }

    private static ITextComponent getButtonMessage(ChatRule.ChatRuleType chatRuleType) {
        return new TranslationTextComponent(
                "gui.dfplusplus.filtersettingslabel",
                new TranslationTextComponent(String.format("gui.dfplusplus.%s", chatRuleType.name().toLowerCase())));
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
