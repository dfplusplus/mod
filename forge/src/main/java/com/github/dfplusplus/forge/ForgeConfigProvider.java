package com.github.dfplusplus.forge;

import com.github.dfplusplus.common.chat.ChatRule;
import com.github.dfplusplus.common.config.Config;
import com.github.dfplusplus.common.config.IConfigProvider;
import com.google.common.collect.Maps;
import net.minecraft.client.GameSettings;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import java.util.Map;

import static com.github.dfplusplus.common.CommonMain.MOD_ID;

public class ForgeConfigProvider implements IConfigProvider {
    private static final ForgeConfigSpec configSpec;
    private static final ForgeConfigSpec.ConfigValue<String> customWordsSpec;
    private static final ForgeConfigSpec.ConfigValue<Integer> chatOffsetXSpec, chatOffsetYSpec;
    private static final ForgeConfigSpec.BooleanValue hasGeneratedChatSizingSettingsSpec;
    private static final ForgeConfigSpec.BooleanValue syncWithMinecraftSpec;
    private static final ForgeConfigSpec.DoubleValue chatScaleSpec, chatWidthSpec;

    private static final Map<ChatRule.ChatRuleType, ForgeConfigSpec.EnumValue<ChatRule.ChatSide>> chatSideSpecs = Maps.newHashMap();
    private static final Map<ChatRule.ChatRuleType, ForgeConfigSpec.EnumValue<ChatRule.ChatSound>> chatSoundSpecs = Maps.newHashMap();

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("chat");
        customWordsSpec = builder
                .comment("The words for custom 2nd chat")
                .translation(MOD_ID + ".config.customwords")
                .define("customWords","");

        hasGeneratedChatSizingSettingsSpec = builder
                .comment("Whether the user has select 'Do not sync' on the chat sizing settings before")
                .translation(MOD_ID + ".config.hasgeneratedchatsizingsettings")
                .define("hasGeneratedChatSizingSettings",false);

        chatOffsetXSpec = builder
                .comment("The offset X, in pixels")
                .translation(MOD_ID + ".config.chatoffsetx")
                .define("chatOffsetX",0);

        chatOffsetYSpec = builder
                .comment("The offset Y, in pixels")
                .translation(MOD_ID + ".config.chatoffsety")
                .define("chatOffsetY",0);

        syncWithMinecraftSpec = builder
                .comment("Whether chat sizing settings are synced with minecraft")
                .translation(MOD_ID + ".config.syncwithminecraft")
                .define("syncWithMinecraft",true);

        chatScaleSpec = builder
                .comment("The scale of the chat")
                .translation(MOD_ID + ".config.chatscale")
                .defineInRange("chatScale",0,0f,1f);

        chatWidthSpec = builder
                .comment("The width of the chat")
                .translation(MOD_ID + ".config.chatwidth")
                .defineInRange("chatWidth",0,0f,1f);

        for (ChatRule.ChatRuleType chatRuleType : ChatRule.ChatRuleType.values()) {
            chatSideSpecs.put(chatRuleType, builder
                    .comment(String.format("Side of %s", chatRuleType.name()))
                    .translation(String.format("%s.config.%sside", MOD_ID, chatRuleType.name()))
                    .defineEnum(String.format("%s_isSide", chatRuleType.name()), ChatRule.ChatSide.MAIN));
            chatSoundSpecs.put(chatRuleType, builder
                    .comment(String.format("Sound of %s", chatRuleType.name()))
                    .translation(String.format("%s.config.%sside", MOD_ID, chatRuleType.name()))
                    .defineEnum(String.format("%s_sound", chatRuleType.name()), ChatRule.ChatSound.NONE));
        }
        configSpec = builder.build();

        Config.setConfigProvider(new ForgeConfigProvider());
    }

    public static ForgeConfigSpec getConfigSpec() {
        return configSpec;
    }

    @Override
    public String getCustomWords() {
        return customWordsSpec.get();
    }

    @Override
    public void setCustomWords(String customWords) {
        customWordsSpec.set(customWords);
        customWordsSpec.save();
    }

    @Override
    public boolean hasGeneratedChatSizingSettings() {
        return hasGeneratedChatSizingSettingsSpec.get();
    }

    @Override
    public void setGeneratedChatSizingSettings(boolean hasGeneratedChatSizingSettings) {
        hasGeneratedChatSizingSettingsSpec.set(hasGeneratedChatSizingSettings);
        hasGeneratedChatSizingSettingsSpec.save();
    }

    @Override
    public int getChatOffsetX() {
        return chatOffsetXSpec.get();
    }

    @Override
    public void setChatOffsetX(int newChatOffsetX) {
        if (newChatOffsetX == chatOffsetXSpec.get()) return;
        chatOffsetXSpec.set(newChatOffsetX);
        chatOffsetXSpec.save();
    }

    @Override
    public int getChatOffsetY() {
        return chatOffsetYSpec.get();
    }

    @Override
    public void setChatOffsetY(int newChatOffsetY) {
        if (newChatOffsetY == chatOffsetYSpec.get()) return;
        chatOffsetYSpec.set(newChatOffsetY);
        chatOffsetYSpec.save();
    }

    @Override
    public boolean getSyncWithMinecraft() {
        return syncWithMinecraftSpec.get();
    }

    @Override
    public void setSyncWithMinecraft(boolean newSyncWithMinecraft) {
        if (newSyncWithMinecraft == syncWithMinecraftSpec.get()) return;
        syncWithMinecraftSpec.set(newSyncWithMinecraft);
        syncWithMinecraftSpec.save();
    }

    @Override
    public double getChatScale() {
        return chatScaleSpec.get();
    }

    @Override
    public void setChatScale(double newChatScale) {
        if (newChatScale == chatScaleSpec.get()) return;
        chatScaleSpec.set(newChatScale);
        chatScaleSpec.save();
    }

    @Override
    public double getChatWidth() {
        return chatWidthSpec.get();
    }

    @Override
    public void setChatWidth(double newChatWidth) {
        if (newChatWidth == chatWidthSpec.get()) return;
        chatWidthSpec.set(newChatWidth);
        chatWidthSpec.save();
    }

    @Override
    public ChatRule.ChatSide getChatSide(ChatRule.ChatRuleType chatRuleType) {
        return chatSideSpecs.get(chatRuleType).get();
    }

    @Override
    public void setChatSide(ChatRule.ChatRuleType chatRuleType, ChatRule.ChatSide chatSide) {
        chatSideSpecs.get(chatRuleType).set(chatSide); // true if inputted 'SIDE'
        chatSideSpecs.get(chatRuleType).save();
    }

    @Override
    public ChatRule.ChatSound getChatSound(ChatRule.ChatRuleType chatRuleType) {
        return chatSoundSpecs.get(chatRuleType).get();
    }

    @Override
    public void setChatSound(ChatRule.ChatRuleType chatRuleType, ChatRule.ChatSound chatSound) {
        chatSoundSpecs.get(chatRuleType).set(chatSound);
        chatSoundSpecs.get(chatRuleType).save();
    }
}
