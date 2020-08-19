package com.github.dfplusplus.common.config;

import com.github.dfplusplus.common.chat.ChatRule;

public interface IConfigProvider {
    String getCustomWords();
    void setCustomWords(String customWords);

    boolean hasGeneratedChatSizingSettings();
    void setGeneratedChatSizingSettings(boolean hasGeneratedChatSizingSettings);

    int getChatOffsetX();
    void setChatOffsetX(int newChatOffsetX);

    int getChatOffsetY();
    void setChatOffsetY(int newChatOffsetY);

    boolean getSyncWithMinecraft();
    void setSyncWithMinecraft(boolean newSyncWithMinecraft);

    double getChatScale();
    void setChatScale(double newChatScale);

    double getChatWidth();
    void setChatWidth(double newChatWidth);

    ChatRule.ChatSide getChatSide(ChatRule.ChatRuleType chatRuleType);
    void setChatSide(ChatRule.ChatRuleType chatRuleType, ChatRule.ChatSide chatSide);

    ChatRule.ChatSound getChatSound(ChatRule.ChatRuleType chatRuleType);
    void setChatSound(ChatRule.ChatRuleType chatRuleType, ChatRule.ChatSound chatSound);
}
