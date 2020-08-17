package com.github.dfplusplus.forge.chat.screens;

public class WidgetSpacer {
    static final int spacingY = 25;
    static final int defaultStartY = 35;

    private final int startY;

    private int currentY;

    public WidgetSpacer(int startY) {
        this.startY = startY;
        this.currentY = this.startY - spacingY; // spacingY is added on the first run through
    }

    public WidgetSpacer() {
        this(defaultStartY);
    }

    public void reset() {
        this.currentY = this.startY - spacingY;
    }

    public int getNextY() {
        return this.currentY += this.spacingY;
    }
}
