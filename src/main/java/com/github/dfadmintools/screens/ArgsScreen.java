package com.github.dfadmintools.screens;

import com.github.dfadmintools.args.ArgsComponent;
import com.github.dfadmintools.args.ArgsOptionalComponent;
import com.github.dfadmintools.args.ArgsStaticComponent;
import com.github.dfadmintools.args.ArgsStringComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.StringTextComponent;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ArgsScreen extends Screen {
    private static final int Y_SPACING = 35;
    public static final int Y_START = 45;
    private final List<ArgsWidget> argsWidgets;
    private final List<ArgsComponent> argsComponents;
    private Screen priorScreen;
    private Button submitButton;

    public ArgsScreen(Screen priorScreen, List<Object> args) {
        super(new StringTextComponent(""));
        this.priorScreen = priorScreen;
        this.argsWidgets = new LinkedList<>();
        this.argsComponents = new LinkedList<>();

        for (Object arg: args) {
            ArgsComponent argsComponent = null;
            if (arg instanceof ArgsComponent) argsComponents.add((ArgsComponent) arg);
            else if (arg instanceof String) argsComponents.add(new ArgsStaticComponent((String) arg));
            else throw (new IllegalArgumentException(String.format("Argument %s was not an ArgsComponent or String",arg)));
        }
    }

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        minecraft.keyboardListener.enableRepeatEvents(true);
        super.init(minecraft, width, height);
        if (priorScreen != null) addButton(new Button(10,10,50,20,"Back",this::onBackButtonPress));

        argsWidgets.clear();
        int x = width / 2;
        int y = Y_START;
        for (ArgsComponent argsComponent : argsComponents) {
            Widget widget = null;
            if (argsComponent instanceof ArgsStringComponent) {
                ArgsStringComponent argsStringComponent = ((ArgsStringComponent) argsComponent);
                TextFieldWidget newTextField = new TextFieldWidget(font,0,0,200,200, "");
                newTextField.setText(I18n.format(argsStringComponent.getText()));
                newTextField.func_212954_a(argsStringComponent::setText);
                argsStringComponent.setText(I18n.format(argsStringComponent.getText()));
                widget = newTextField;
            } else if (argsComponent instanceof ArgsOptionalComponent) {
                ArgsOptionalComponent argsOptionalComponent = ((ArgsOptionalComponent) argsComponent);
                Button newButton = new Button(0,0,200,200,"",argsOptionalComponent::flip);
                newButton.setMessage(I18n.format(argsOptionalComponent.getMessage()));
                widget = newButton;
            }

            if (Objects.isNull(widget)) continue; // stuff like static component has no widget
            setupWidgetDimensions(x,y,widget);
            if (widget instanceof Button) addButton(((Button) widget));
            else children.add(widget);
            argsWidgets.add(new ArgsWidget(widget,argsComponent));
            y+=Y_SPACING;
        }

        submitButton = addButton(new Button(0,0,200,200,"Run Command",this::onSubmitButtonPress));
        setupWidgetDimensions(x,y,submitButton);
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        renderBackground();
        super.render(p_render_1_, p_render_2_, p_render_3_);
        drawCenteredString(font, getCommand(),width/2, 20 ,16777215);

        int x = width / 2;
        int y = Y_START;
        for (ArgsWidget argsWidget : argsWidgets) {
            drawCenteredString(font, I18n.format(argsWidget.component.getTitle()),x,y-10,16777215);
            if (argsWidget.component instanceof ArgsStringComponent) argsWidget.widget.render(p_render_1_,p_render_2_,p_render_3_);
            y+=Y_SPACING;
        }
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        super.resize(minecraft, width, height);
        init(minecraft,width,height);
    }

    @Override
    public void tick() {
        for (ArgsWidget argsWidget : argsWidgets) {
            if (argsWidget.component instanceof ArgsStringComponent) ((TextFieldWidget) argsWidget.widget).tick();
        }
    }

    @Override
    public void removed() {
        minecraft.keyboardListener.enableRepeatEvents(false);
    }

    private void setupWidgetDimensions(int x, int y, Widget widget) {
        widget.setWidth(100);
        widget.setHeight(20);
        widget.x = x - widget.getWidth()/2;
        widget.y = y;
    }

    private String getCommand() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ArgsComponent argsComponent : argsComponents) {
            if (argsComponent instanceof ArgsStaticComponent)
                stringBuilder.append(((ArgsStaticComponent) argsComponent).getTitle());

            if (argsComponent instanceof ArgsStringComponent)
                stringBuilder.append(((ArgsStringComponent) argsComponent).getText());

            if (argsComponent instanceof ArgsOptionalComponent && ((ArgsOptionalComponent) argsComponent).isEnabled())
                stringBuilder.append(((ArgsOptionalComponent) argsComponent).getOptionalComponent());
        }
        return stringBuilder.toString();
    }

    private void onBackButtonPress(Button button) {
        minecraft.displayGuiScreen(priorScreen);
    }

    private void onSubmitButtonPress(Button button) {
        minecraft.displayGuiScreen(null);
        minecraft.player.sendChatMessage(getCommand());
    }

    private static class ArgsWidget {
        private final Widget widget;
        private final ArgsComponent component;

        public ArgsWidget(Widget widget, ArgsComponent component) {
            this.widget = widget;
            this.component = component;
        }
    }
}
