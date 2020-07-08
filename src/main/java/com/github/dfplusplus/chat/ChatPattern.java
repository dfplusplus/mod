package com.github.dfplusplus.chat;

import com.google.common.collect.Lists;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class ChatPattern {
    private final List<ChatComponent> chatComponents;

    public ChatPattern(ChatComponent... chatComponents) {
        this.chatComponents = Lists.newArrayList(chatComponents);
    }

    public ChatPattern(ITextComponent textComponent) {
        this.chatComponents = Lists.newArrayList();
        populateChatComponents(textComponent);
    }

    public boolean contains(ChatPattern subChatPattern) {
        int correctInARow = 0; // also used to determine which component in the sub pattern to check
        int currentPos = 0;
        for (ChatComponent chatComponent : chatComponents) {
            ChatComponent subChatComponent = subChatPattern.chatComponents.get(correctInARow);
            if (chatComponent.equals(subChatComponent) && chatComponent.posEquals(currentPos)) {
                correctInARow++; // will check next sub component next iteration
                if (correctInARow == subChatPattern.chatComponents.size()) return true;
            }
            else correctInARow = 0;
            currentPos++;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ChatComponent chatComponent : chatComponents) {
            stringBuilder.append(chatComponent.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    private void populateChatComponents(ITextComponent textComponent) {
        if (textComponent.getSiblings().isEmpty()) { // only display the bottom most nodes
            chatComponents.add(new ChatComponent(textComponent.getString(),textComponent.getStyle().getColor()));
        }
        else for (ITextComponent childComponent : textComponent.getSiblings()) populateChatComponents(childComponent);
    }

    public static class ChatComponent {
        private String string;
        private TextFormatting color;
        private int pos = -1;

        public ChatComponent(String string, TextFormatting color) {
            this.string = string;
            this.color = color;
        }

        public ChatComponent(String string, TextFormatting color, int pos) {
            this.string = string;
            this.color = color;
            this.pos = pos;
        }

        public boolean equals(ChatComponent chatComponent) {
            boolean stringEquals = false;
            // if either strings are null then its assumed 'match any'
            if (this.string == null || chatComponent.string == null) stringEquals = true;
            else if (this.string.equals(chatComponent.string)) stringEquals = true;

            boolean colorEquals = false;
            // same for colour
            if (this.color == null || chatComponent.color == null) colorEquals = true;
            else if (this.color == chatComponent.color) colorEquals = true;

            return stringEquals && colorEquals;
        }

        private boolean posEquals(int pos) {
            return this.pos == -1 || this.pos == pos;
        }

        @Override
        public String toString() {
            if (color != null)
                return "ChatComponent{" +
                        "string='" + string + '\'' +
                        ", color=" + color.name() +
                        '}';
            else
                return "ChatComponent{" +
                        "string='" + string + '\'' +
                        ", color=" + "null" +
                        '}';

        }
    }
}
