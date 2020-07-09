package com.github.dfplusplus;

import com.github.dfplusplus.chat.ChatGuiOverride;
import com.github.dfplusplus.chat.ChatPredicates;
import com.github.dfplusplus.chat.ChatRule;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("dfplusplus")
public class Main {
    public final static String MOD_ID = "dfplusplus";
    private final static Logger logger = LogManager.getLogger(MOD_ID);

    public Main() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT,DFPlusPlusConfig.getConfigSpec());
    }

    public static void log(Object message) {
        logger.log(Level.INFO,message);
    }

    private void init(FMLClientSetupEvent event) {
        KeyBinds.registerKeyBindings();
        ChatGuiOverride.inject();
        ChatPredicates.setCustomWords(DFPlusPlusConfig.getCustomWords());
        for (ChatRule.ChatRuleType chatRuleType : ChatRule.ChatRuleType.values()) {
            ChatRule.getChatRule(chatRuleType).setChatSide(DFPlusPlusConfig.getChatSide(chatRuleType));
            ChatRule.getChatRule(chatRuleType).setChatSound(DFPlusPlusConfig.getChatSound(chatRuleType));
        }
    }
}
