package com.github.dfplusplus.fabric.providers;

import com.github.dfplusplus.common.chat.ChatRule;
import com.github.dfplusplus.common.providers.IConfigProvider;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.ConfigHolder;
import me.sargunvohra.mcmods.autoconfig1u.ConfigManager;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.serializer.ConfigSerializer;
import me.sargunvohra.mcmods.autoconfig1u.serializer.Toml4jConfigSerializer;

import static com.github.dfplusplus.common.CommonMain.MOD_ID;

@Config(name = MOD_ID)
public class FabricConfigProvider implements IConfigProvider, ConfigData {
    static final ConfigManager<FabricConfigProvider> configManager;

    static {
        ConfigHolder<FabricConfigProvider> configHolder = AutoConfig.register(FabricConfigProvider.class, Toml4jConfigSerializer::new);
        if (configHolder instanceof ConfigManager) configManager = ((ConfigManager<FabricConfigProvider>) configHolder);
        else configManager = null;
    }

    String customWords = "";
    boolean hasGeneratedChatSizingSettings = false;
    int chatOffsetX = 0;
    int chatOffsetY = 0;
    boolean syncWithMinecraftClient = true;
    double chatScale;
    double chatWidth;

    private FabricConfigProvider getReference() {
        return AutoConfig.getConfigHolder(FabricConfigProvider.class).getConfig();
    }

    private void serialize() {
        try {
            configManager.getSerializer().serialize(getReference());
        } catch (ConfigSerializer.SerializationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCustomWords() {
        return getReference().customWords;
    }

    @Override
    public void setCustomWords(String customWords) {
        getReference().customWords = customWords;
        serialize();
    }

    @Override
    public boolean hasGeneratedChatSizingSettings() {
        return getReference().hasGeneratedChatSizingSettings;
    }

    @Override
    public void setGeneratedChatSizingSettings(boolean hasGeneratedChatSizingSettings) {
        getReference().hasGeneratedChatSizingSettings = hasGeneratedChatSizingSettings;
        serialize();
    }

    @Override
    public int getChatOffsetX() {
        return getReference().chatOffsetX;
    }

    @Override
    public void setChatOffsetX(int newChatOffsetX) {
        getReference().chatOffsetX = chatOffsetX;
        serialize();
    }

    @Override
    public int getChatOffsetY() {
        return getReference().chatOffsetY;
    }

    @Override
    public void setChatOffsetY(int newChatOffsetY) {
        getReference().chatOffsetY = newChatOffsetY;
        serialize();
    }

    @Override
    public boolean getSyncWithMinecraftClient() {
        return getReference().syncWithMinecraftClient;
    }

    @Override
    public void setSyncWithMinecraftClient(boolean newSyncWithMinecraftClient) {
        getReference().syncWithMinecraftClient = newSyncWithMinecraftClient;
        serialize();
    }

    @Override
    public double getChatScale() {
        return getReference().chatScale;
    }

    @Override
    public void setChatScale(double newChatScale) {
        getReference().chatScale = newChatScale;
        serialize();
    }

    @Override
    public double getWidth() {
        return getReference().chatWidth;
    }

    @Override
    public void setChatWidth(double newChatWidth) {
        getReference().chatWidth = newChatWidth;
        serialize();
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
