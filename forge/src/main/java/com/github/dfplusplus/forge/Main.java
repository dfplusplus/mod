package com.github.dfplusplus.forge;

import com.github.dfplusplus.common.config.Config;
import com.github.dfplusplus.common.chat.ChatGuiOverride;
import com.github.dfplusplus.common.chat.ChatPredicates;
import com.github.dfplusplus.common.chat.ChatRule;
import com.github.dfplusplus.common.chat.screens.ChatSizingScreen;
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
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ForgeConfigProvider.getConfigSpec());
    }

    public static void log(Object message) {
        logger.log(Level.INFO,message);
    }

    private void init(FMLClientSetupEvent event) {
        ForgeRegistry.registerKeyBindings();
        ChatGuiOverride.inject();
        ChatPredicates.loadFromConfig();
        ChatRule.loadFromConfig();
        ChatSizingScreen.loadFromConfig();
    }
}
