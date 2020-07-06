package com.github.dfplusplus.args;

public class ArgsStringComponent extends ArgsComponent {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArgsStringComponent(String title, String defaultText) {
        this.title = title;
        this.text = defaultText;
    }
}
