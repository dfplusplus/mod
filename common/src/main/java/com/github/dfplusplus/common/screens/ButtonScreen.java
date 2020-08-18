package com.github.dfplusplus.common.screens;

import com.github.dfplusplus.common.actions.Action;
import com.github.dfplusplus.common.actions.CommandAction;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public abstract class ButtonScreen extends Screen {
    private final int BUTTON_WIDTH = 200;
    private final int BUTTON_HEIGHT = 20;
    private final int BUTTON_GAP = 5;

    public abstract String getName();

    private List<Button> actionButtons;
    private Screen priorScreen;
    private int pageNo = 0;

    public ButtonScreen(Screen priorScreen) {
        super(new StringTextComponent(""));
        this.actionButtons = new ArrayList<>();
        this.priorScreen = priorScreen;
    }

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        super.init(minecraft, width, height);
        if (priorScreen != null) addButton(new Button(10,10,50,20,new TranslationTextComponent("gui.back"),this::onBackButtonPress));
        updateButtons();
    }

    public void onBackButtonPress(Button button) {
        minecraft.displayGuiScreen(priorScreen);
    }

    public void onScrollForwardButtonPress(Button button) {
        pageNo++;
        init(minecraft,width,height);
    }

    public void onScrollBackButtonPress(Button button) {
        pageNo--;
        init(minecraft,width,height);
    }

    public void updateButtons() {
        int x = width/2;
        int y = 30;
        int possibleDisplayAmount = getPossibleDisplayAmount();
        if (possibleDisplayAmount == 0) return; // dont even try
        boolean displayPageButtons = false;
        if (possibleDisplayAmount < actionButtons.size()) {
            displayPageButtons = true;
            possibleDisplayAmount--;
        }
        if (pageNo > getMaxPageNo()) pageNo = getMaxPageNo(); // if there are now less pages, fix pageNo so its not too big
        int displayFromIndex = pageNo * possibleDisplayAmount;
        int displayToIndex = Math.min(displayFromIndex + possibleDisplayAmount,actionButtons.size());
        for (Button button : actionButtons.subList(displayFromIndex,displayToIndex)) {
            button.x = x - (button.getWidth()/2);
            button.y = y;
            button.setMessage(new TranslationTextComponent(button.getMessage().getString()));
            addButton(button);
            y+=(BUTTON_HEIGHT + BUTTON_GAP);
        }
        // as it happens y is now what is needed for the scroll buttons
        if (displayPageButtons) {
            Button scrollBackButton = addButton(new Button(
                    x - BUTTON_WIDTH / 2,
                    y,
                    (BUTTON_WIDTH - BUTTON_GAP) / 2,
                    BUTTON_HEIGHT,
                    new StringTextComponent("<--"), // will never need to be translated
                    this::onScrollBackButtonPress
            ));
            scrollBackButton.active = (pageNo > 0);

            Button scrollForwardButton = addButton(new Button(
                    x + BUTTON_GAP / 2,
                    y,
                    (BUTTON_WIDTH - BUTTON_GAP) / 2,
                    BUTTON_HEIGHT,
                    new StringTextComponent("-->"),
                    this::onScrollForwardButtonPress
            ));
            scrollForwardButton.active = (pageNo < getMaxPageNo());
        }
    }

    private int getPossibleDisplayAmount() {
        return (this.height - (30 + 5)) / 25;
    }

    // takes into account the <-- and --> buttons
    private int getAdjustedPossibleDisplayAmount() {
        int possibleDisplayAmount = getPossibleDisplayAmount();
        if (possibleDisplayAmount < actionButtons.size()) possibleDisplayAmount--;
        return possibleDisplayAmount;
    }

    private int getMaxPageNo() { // e.g you can display up to the 0th page
        int adjustedPossibleDisplayAmount = getAdjustedPossibleDisplayAmount();
        if (adjustedPossibleDisplayAmount == 0) return 0; // avoids divide by 0 error if you make screen mega small
        return (actionButtons.size()-1) / adjustedPossibleDisplayAmount;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack,mouseX,mouseY,partialTicks);
        drawCenteredString(matrixStack, font, I18n.format(getName()),width/2,20,16777215);
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        super.resize(minecraft, width, height);
        init(minecraft,width,height);
    }

    protected Button addButton(String name, Action action) {
        Button newButton = new Button(
                0,
                0,
                BUTTON_WIDTH,
                BUTTON_HEIGHT,
                new StringTextComponent(name),
                new ActionPressable(action)
        );
        actionButtons.add(newButton);
        return newButton;
    }

    protected Button addCommandButton(String command) {
        return addButton(command, new CommandAction(command));
    }
}
