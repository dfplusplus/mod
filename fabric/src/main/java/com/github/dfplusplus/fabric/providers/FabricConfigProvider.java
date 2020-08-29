package com.github.dfplusplus.fabric.providers;

import com.github.dfplusplus.common.chat.ChatRule;
import com.github.dfplusplus.common.providers.IConfigProvider;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

import static com.github.dfplusplus.common.CommonMain.MOD_ID;

@Config(name = MOD_ID)
public class FabricConfigProvider implements IConfigProvider, ConfigData {
    boolean coolToggle = true;

    @Override
    public String getCustomWords() {
        return "beans";
    }

    @Override
    public void setCustomWords(String customWords) {

    }

    @Override
    public boolean hasGeneratedChatSizingSettings() {
        return true;
    }

    @Override
    public void setGeneratedChatSizingSettings(boolean hasGeneratedChatSizingSettings) {

    }

    @Override
    public int getChatOffsetX() {
        return 0;
    }

    @Override
    public void setChatOffsetX(int newChatOffsetX) {

    }

    @Override
    public int getChatOffsetY() {
        return 0;
    }

    @Override
    public void setChatOffsetY(int newChatOffsetY) {

    }

    @Override
    public boolean getSyncWithMinecraftClient() {
        return true;
    }

    @Override
    public void setSyncWithMinecraftClient(boolean newSyncWithMinecraftClient) {

    }

    @Override
    public double getChatScale() {
        return 1;
    }

    @Override
    public void setChatScale(double newChatScale) {

    }

    @Override
    public double getWidth() {
        return 1;
    }

    @Override
    public void setChatWidth(double newChatWidth) {

    }

    @Override
    public ChatRule.ChatSide getChatSide(ChatRule.ChatRuleType chatRuleType) {
        return ChatRule.ChatSide.EITHER;
    }

    @Override
    public void setChatSide(ChatRule.ChatRuleType chatRuleType, ChatRule.ChatSide chatSide) {

    }

    @Override
    public ChatRule.ChatSound getChatSound(ChatRule.ChatRuleType chatRuleType) {
        return ChatRule.ChatSound.NONE;
    }

    @Override
    public void setChatSound(ChatRule.ChatRuleType chatRuleType, ChatRule.ChatSound chatSound) {

    }
}
