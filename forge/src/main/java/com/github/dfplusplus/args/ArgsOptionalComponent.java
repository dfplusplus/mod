package com.github.dfplusplus.args;

import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ArgsOptionalComponent extends ArgsComponent {
    private boolean enabled;
    private String optionalComponent;

    public boolean isEnabled() {
        return enabled;
    }

    public String getOptionalComponent() {
        return optionalComponent;
    }

    public String getMessage() {
        return enabled ? I18n.format("gui.yes") : I18n.format("gui.no");
    }

    public ArgsOptionalComponent(String title, String optionalComponent, boolean defaultValue) {
        this.title = title;
        this.optionalComponent = optionalComponent;
        this.enabled = defaultValue;
    }

    public void flip(Button button) {
        enabled = !enabled;
        button.setMessage(new StringTextComponent(getMessage()));
    }
}
