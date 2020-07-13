package com.github.dfplusplus.chat.screens;

import com.github.dfplusplus.chat.ChatGuiOverride;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.NewChatGui;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.OptionSlider;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.settings.AbstractOption;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ChatSizingScreen extends Screen {
    private final Screen priorScreen;
    private static double chatOffsetXValue = 0;
    private static double chatOffsetYValue = 0;

    public static int getChatOffsetX() {
        return ((int) chatOffsetXValue);
    }

    public static int getChatOffsetY() {
        return (int) chatOffsetYValue;
    }

    public ChatSizingScreen(Screen priorScreen) {
//        super("");
        super(new TranslationTextComponent("Sizing"));
        this.minecraft = Minecraft.getInstance();
        this.priorScreen = priorScreen;
    }

    @Override
    protected void init() {
        super.init();
//        inputField.setEnabled(false);
        if (priorScreen != null) addButton(new Button(10,10,50,20,"Back",this::onBackButtonPress));
        int windowWidth = minecraft.getMainWindow().getScaledWidth();
        this.addButton(new OptionSlider(
                minecraft.gameSettings, (width / 2) - 100, 20, 200, 20,
                new SliderPercentageOption("gui.dfplusplus.chatoffsetx", -windowWidth/2f, windowWidth/2f, 1f,
                        (gameSettings -> chatOffsetXValue),
                        ((gameSettings, aDouble) -> chatOffsetXValue = aDouble), // %% is escaped to %
                        (gameSettings, sliderPercentageOption) -> String.format("Chat Offset X: %spx", ((int) chatOffsetXValue))
                ))
        );

        this.addButton(new OptionSlider(
                minecraft.gameSettings, (width / 2) - 100, 45, 200, 20,
                new SliderPercentageOption("gui.dfplusplus.chatoffsetx", 0f, minecraft.getMainWindow().getScaledHeight(), 1f,
                        (gameSettings -> chatOffsetYValue),
                        ((gameSettings, aDouble) -> chatOffsetYValue = aDouble), // %% is escaped to %
                        (gameSettings, sliderPercentageOption) -> String.format("Chat Offset Y: %spx", ((int) chatOffsetYValue))
                ))
        );
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 20, 16777215);
        super.render(p_render_1_, p_render_2_, p_render_3_);
    }

    private void onBackButtonPress(Button button) {
        minecraft.displayGuiScreen(priorScreen);
    }
}
