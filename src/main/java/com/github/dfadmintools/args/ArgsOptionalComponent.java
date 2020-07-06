package com.github.dfadmintools.args;

import net.minecraft.client.gui.widget.button.Button;

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
        return enabled ? "Yes" : "No";
    }

    public ArgsOptionalComponent(String title, String optionalComponent, boolean defaultValue) {
        this.title = title;
        this.optionalComponent = optionalComponent;
        this.enabled = defaultValue;
    }

    public void flip(Button button) {
        enabled = !enabled;
        button.setMessage(getMessage());
    }
}
