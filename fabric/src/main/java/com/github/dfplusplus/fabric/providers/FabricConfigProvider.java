package com.github.dfplusplus.fabric.providers;

import com.github.dfplusplus.common.chat.ChatRule;
import com.github.dfplusplus.common.providers.IConfigProvider;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.ConfigHolder;
import me.sargunvohra.mcmods.autoconfig1u.ConfigManager;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import me.sargunvohra.mcmods.autoconfig1u.serializer.Toml4jConfigSerializer;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.dfplusplus.common.CommonMain.MOD_ID;

@Config(name = MOD_ID)
public class FabricConfigProvider implements IConfigProvider, ConfigData {
    static final ConfigManager<FabricConfigProvider> configManager;

    static {
        ConfigHolder<FabricConfigProvider> configHolder = AutoConfig.register(FabricConfigProvider.class, GsonConfigSerializer::new);
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
    Map<ChatRule.ChatRuleType, ChatRule.ChatSide> chatSides = Maps.asMap(
            new HashSet<>(Arrays.asList(ChatRule.ChatRuleType.values())), // uses a Set because Maps.toMap returns an ImmutableMap which I don't want
            input -> ChatRule.ChatSide.MAIN
    );
    Map<ChatRule.ChatRuleType, ChatRule.ChatSound> chatSounds = Maps.asMap(
            new HashSet<>(Arrays.asList(ChatRule.ChatRuleType.values())),
            input -> ChatRule.ChatSound.NONE
    );

    private FabricConfigProvider getReference() {
        return AutoConfig.getConfigHolder(FabricConfigProvider.class).getConfig();
    }

    private void save() {
        configManager.save();
    }

    @Override
    public String getCustomWords() {
        return getReference().customWords;
    }

    @Override
    public void setCustomWords(String customWords) {
        getReference().customWords = customWords;
        save();
    }

    @Override
    public boolean hasGeneratedChatSizingSettings() {
        return getReference().hasGeneratedChatSizingSettings;
    }

    @Override
    public void setGeneratedChatSizingSettings(boolean hasGeneratedChatSizingSettings) {
        getReference().hasGeneratedChatSizingSettings = hasGeneratedChatSizingSettings;
        save();
    }

    @Override
    public int getChatOffsetX() {
        return getReference().chatOffsetX;
    }

    @Override
    public void setChatOffsetX(int newChatOffsetX) {
        getReference().chatOffsetX = chatOffsetX;
        save();
    }

    @Override
    public int getChatOffsetY() {
        return getReference().chatOffsetY;
    }

    @Override
    public void setChatOffsetY(int newChatOffsetY) {
        getReference().chatOffsetY = newChatOffsetY;
        save();
    }

    @Override
    public boolean getSyncWithMinecraftClient() {
        return getReference().syncWithMinecraftClient;
    }

    @Override
    public void setSyncWithMinecraftClient(boolean newSyncWithMinecraftClient) {
        getReference().syncWithMinecraftClient = newSyncWithMinecraftClient;
        save();
    }

    @Override
    public double getChatScale() {
        return getReference().chatScale;
    }

    @Override
    public void setChatScale(double newChatScale) {
        getReference().chatScale = newChatScale;
        save();
    }

    @Override
    public double getWidth() {
        return getReference().chatWidth;
    }

    @Override
    public void setChatWidth(double newChatWidth) {
        getReference().chatWidth = newChatWidth;
        save();
    }

    @Override
    public ChatRule.ChatSide getChatSide(ChatRule.ChatRuleType chatRuleType) {
        return getReference().chatSides.get(chatRuleType);
    }

    @Override
    public void setChatSide(ChatRule.ChatRuleType chatRuleType, ChatRule.ChatSide chatSide) {
        getReference().chatSides.put(chatRuleType, chatSide);
        save();
    }

    @Override
    public ChatRule.ChatSound getChatSound(ChatRule.ChatRuleType chatRuleType) {
        return getReference().chatSounds.get(chatRuleType);
    }

    @Override
    public void setChatSound(ChatRule.ChatRuleType chatRuleType, ChatRule.ChatSound chatSound) {
        getReference().chatSounds.put(chatRuleType,chatSound);
        save();
    }
}
