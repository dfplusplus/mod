package com.github.dfplusplus.chat.screens;

import com.github.dfplusplus.Config;
import com.github.dfplusplus.chat.ChatGuiOverride;
import com.github.dfplusplus.chat.ChatRule;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.NewChatGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.OptionSlider;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.text.TranslationTextComponent;

public class ChatSizingScreen extends Screen {
    private final Screen priorScreen;
    private static double chatOffsetX = 0;
    private static double chatOffsetY = 0;
    private static boolean syncWithMinecraft = true;
    private static double chatScale;
    private static double chatWidth;

    private Button toggleSyncButton;
    private FineTuneSlider chatScaleSlider;
    private OptionSlider chatWidthSlider;

    public static int getChatOffsetX() {
        return (int) chatOffsetX;
    }

    public static int getChatOffsetY() {
        return (int) chatOffsetY;
    }

    public static double getChatScale() {
        return syncWithMinecraft ? Minecraft.getInstance().gameSettings.chatScale : chatScale;
    }

    public static double getChatWidth() {
        return syncWithMinecraft ? Minecraft.getInstance().gameSettings.chatWidth : chatWidth;
    }

    public static void loadFromConfig() {
        chatOffsetX = Config.getChatOffsetX();
        chatOffsetY = Config.getChatOffsetY();
        syncWithMinecraft = Config.getSyncWithMinecraft();
        chatScale = Config.getChatScale();
        chatWidth = Config.getChatWidth();
    }

    private static void saveToConfig() {
        Config.setChatOffsetX(getChatOffsetX());
        Config.setChatOffsetY(getChatOffsetY());
        Config.setSyncWithMinecraft(syncWithMinecraft);
        Config.setChatScale(getChatScale());
        Config.setChatWidth(getChatWidth());
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
        new FineTuneSlider(
                (width / 2) - 100, 20, 200, 20, true, 1,
                new SliderPercentageOption("gui.dfplusplus.chatoffsetx", -windowWidth/2f, windowWidth/2f, 1f,
                        (gameSettings -> chatOffsetX),
                        ((gameSettings, aDouble) -> chatOffsetX = aDouble),
                        (gameSettings, sliderPercentageOption) -> String.format("Chat Offset X: %spx", ((int) chatOffsetX))
                ));

        // chat offset y slider
        new FineTuneSlider(
                (width / 2) - 100, 45, 200, 20, true, 1,
                new SliderPercentageOption("gui.dfplusplus.chatoffsetx", 0f, minecraft.getMainWindow().getScaledHeight(), 1f,
                        (gameSettings -> chatOffsetY),
                        ((gameSettings, aDouble) -> chatOffsetY = aDouble),
                        (gameSettings, sliderPercentageOption) -> String.format("Chat Offset Y: %spx", ((int) chatOffsetY))
                ));

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
        this.chatScaleSlider = new FineTuneSlider((width / 2) - 100, 95, 200, 20, !syncWithMinecraft, 0.01, new SliderPercentageOption("gui.dfplusplus.chatscale", 0, 1, 0,
            (gameSettings -> getActualChatScale()), // on get
            ((gameSettings, aDouble) -> {
                if (!syncWithMinecraft) chatScale = aDouble;
                minecraft.ingameGUI.persistantChatGUI.clearChatMessages(false);
            }), // on set, only works if syncing
            (gameSettings, sliderPercentageOption) -> String.format("Chat Scale: %s%%", (int)(getActualChatScale() * 100)) // on display
        ));

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
        this.chatWidthSlider.active = !syncWithMinecraft;

        // dummy message button
        addButton(new Button((width / 2) -100,145,200,20,"Send Dummy Messages",this::onDummyMessagesButtonPress));
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 20, 16777215);
        super.render(p_render_1_, p_render_2_, p_render_3_);
    }

    @Override
    public void removed() {
        saveToConfig();
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

    private void refresh() {
        this.init(minecraft, minecraft.getMainWindow().getScaledWidth(),minecraft.getMainWindow().getScaledHeight());
    }

    private class FineTuneSlider {
        private final double stepSize;
        private final SliderPercentageOption sliderPercentageOption;
        private final GameSettings gameSettings = Minecraft.getInstance().gameSettings;

        public FineTuneSlider(int x, int y, int width, int height, boolean active, double stepSize, SliderPercentageOption sliderPercentageOption) {
            this.stepSize = stepSize;
            this.sliderPercentageOption = sliderPercentageOption;

            ChatSizingScreen.this.addButton(new OptionSlider(
                    minecraft.gameSettings,
                    x + height,
                    y,
                    width - (height * 2),
                    height,
                    sliderPercentageOption
            ))
            .active = active;
            //otherwise it complains about 'height'
            //noinspection SuspiciousNameCombination
            ChatSizingScreen.this.addButton(new Button(
                    x, y, height, height,"<",Button -> {
                double newValue = sliderPercentageOption.get(gameSettings) - this.stepSize;
                if (newValue < sliderPercentageOption.getMinValue()) newValue = sliderPercentageOption.getMinValue();
                sliderPercentageOption.set(gameSettings, newValue);
                ChatSizingScreen.this.refresh();
            }
            ))
            .active = active;

            //noinspection SuspiciousNameCombination
            ChatSizingScreen.this.addButton(new Button(
                    (x + width) - height, y, height, height,">",Button -> {
                double newValue = sliderPercentageOption.get(gameSettings) + this.stepSize;
                if (newValue > sliderPercentageOption.getMaxValue()) newValue = sliderPercentageOption.getMaxValue();
                sliderPercentageOption.set(gameSettings, newValue);
                ChatSizingScreen.this.refresh();
            }
            ))
            .active = active;
        }
    }
}
