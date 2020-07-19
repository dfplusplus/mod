package com.github.dfplusplus.chat.screens;

import com.github.dfplusplus.Config;
import com.github.dfplusplus.chat.ChatGuiOverride;
import com.github.dfplusplus.chat.ChatRule;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.NewChatGui;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.OptionSlider;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.AbstractOption;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ChatSizingScreen extends Screen {
    private final Screen priorScreen;
    private static double chatOffsetXValue = 0;
    private static double chatOffsetYValue = 0;
    private static boolean syncWithMinecraft = true;
    private static double chatScale;
    private static double chatWidth;

    private Button toggleSyncButton;
    private OptionSlider chatScaleSlider;
    private OptionSlider chatWidthSlider;

    public static int getChatOffsetX() {
        return ((int) chatOffsetXValue);
    }

    public static int getChatOffsetY() {
        return (int) chatOffsetYValue;
    }

    public static double getChatScale() {
        return syncWithMinecraft ? Minecraft.getInstance().gameSettings.chatScale : chatScale;
    }

    public static double getChatWidth() {
        return syncWithMinecraft ? Minecraft.getInstance().gameSettings.chatWidth : chatWidth;
    }

    public static void loadFromConfig() {
        syncWithMinecraft = Config.getSyncWithMinecraft();
        chatScale = Config.getChatScale();
        chatWidth = Config.getChatWidth();
    }

    public ChatSizingScreen(Screen priorScreen) {
        super(new TranslationTextComponent("Sizing"));
        this.minecraft = Minecraft.getInstance();
        this.priorScreen = priorScreen;
    }

    @Override
    public void init(Minecraft p_init_1_, int p_init_2_, int p_init_3_) {
        super.init(p_init_1_, p_init_2_, p_init_3_);
        // back button
        if (priorScreen != null) addButton(new Button(10,10,50,20,"Back",this::onBackButtonPress));

        // chat offset x slider
        int windowWidth = minecraft.getMainWindow().getScaledWidth();
        this.addButton(new OptionSlider(
                minecraft.gameSettings, (width / 2) - 100, 20, 200, 20,
                new SliderPercentageOption("gui.dfplusplus.chatoffsetx", -windowWidth/2f, windowWidth/2f, 1f,
                        (gameSettings -> chatOffsetXValue),
                        ((gameSettings, aDouble) -> chatOffsetXValue = aDouble),
                        (gameSettings, sliderPercentageOption) -> String.format("Chat Offset X: %spx", ((int) chatOffsetXValue))
                ))
        );

        // chat offset y slider
        this.addButton(new OptionSlider(
                minecraft.gameSettings, (width / 2) - 100, 45, 200, 20,
                new SliderPercentageOption("gui.dfplusplus.chatoffsetx", 0f, minecraft.getMainWindow().getScaledHeight(), 1f,
                        (gameSettings -> chatOffsetYValue),
                        ((gameSettings, aDouble) -> chatOffsetYValue = aDouble),
                        (gameSettings, sliderPercentageOption) -> String.format("Chat Offset Y: %spx", ((int) chatOffsetYValue))
                ))
        );

        // sync toggle button
        this.toggleSyncButton = this.addButton(new Button(
                (width/2) - 100, 70, 200, 20, "", (button) -> {
            this.genConfigIfNeeded(); // if switching for the first time, copy the current chat sizing over for easy editing
            syncWithMinecraft = !syncWithMinecraft;
            this.init(minecraft,width,height);
        }
        ));
        this.toggleSyncButton.setMessage(I18n.format(syncWithMinecraft ? "gui.dfplusplus.settings.sync.true" : "gui.dfplusplus.settings.sync.false"));

        // chat scale slider
        this.chatScaleSlider = this.addButton(new OptionSlider(
                minecraft.gameSettings, (width / 2) - 100, 95, 200, 20,
                new SliderPercentageOption("gui.dfplusplus.chatscale", 0, 1, 0,
                        (gameSettings -> getActualChatScale()), // on get
                        ((gameSettings, aDouble) -> {
                            if (!syncWithMinecraft) chatScale = aDouble;
                            minecraft.ingameGUI.persistantChatGUI.clearChatMessages(false);
                        }), // on set, only works if syncing
                        (gameSettings, sliderPercentageOption) -> String.format("Chat Scale: %s%%", (int)(getActualChatScale() * 100)) // on display
                )
        ));
        this.chatScaleSlider.active = !syncWithMinecraft;

        // chat width slider
        this.chatWidthSlider = this.addButton(new OptionSlider(
                minecraft.gameSettings, (width / 2) - 100, 120, 200, 20,
                new SliderPercentageOption("gui.dfplusplus.chatwidth", 0, 1, 0,
                        (gameSettings -> getActualChatWidth()), // on get
                        ((gameSettings, aDouble) -> {
                            if (!syncWithMinecraft) chatWidth = aDouble;
                            minecraft.ingameGUI.persistantChatGUI.clearChatMessages(false);
                        }), // on set, only works if syncing
                        (gameSettings, sliderPercentageOption) -> String.format("Chat Width: %spx", NewChatGui.calculateChatboxWidth(getActualChatWidth())) // on display
                )
        ));

        // dummy message button
        addButton(new Button((width / 2) ,145,200,20,"Send Dummy Messages",this::onDummyMessagesButtonPress));
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 20, 16777215);
        super.render(p_render_1_, p_render_2_, p_render_3_);
    }

    @Override
    public void removed() {
        Config.setSyncWithMinecraft(syncWithMinecraft);
        Config.setChatScale(chatScale);
        Config.setChatWidth(chatWidth);
    }

    private void onBackButtonPress(Button button) {
        minecraft.displayGuiScreen(priorScreen);
    }

    private void genConfigIfNeeded() {
        if (!Config.hasGeneratedChatSizingSettings()) {
            Config.genChatSizingSettings(minecraft.gameSettings);
            loadFromConfig();
        }
    }

    private void onDummyMessagesButtonPress(Button button) {
        ChatGuiOverride chatGuiOverride = ((ChatGuiOverride) minecraft.ingameGUI.persistantChatGUI);
        chatGuiOverride.addToChat(ChatRule.ChatSide.MAIN, "TEST");
        chatGuiOverride.addToChat(ChatRule.ChatSide.SIDE, "TEST2");
    }

    private double getActualChatScale() {
        return syncWithMinecraft ? minecraft.gameSettings.chatScale : chatScale;
    }

    private double getActualChatWidth() {
        return syncWithMinecraft ? minecraft.gameSettings.chatWidth : chatWidth;
    }
}
