package com.samstuff.dfadmintools;

import com.samstuff.dfadmintools.chat.ChatGuiOverride;
import com.samstuff.dfadmintools.chat.ChatPredicates;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("dfadmintools")
public class Main {
    public final static String MOD_ID = "dfadmintools";
    private final static Logger logger = LogManager.getLogger(MOD_ID);

    public Main() {
        log("Hello World from DF Admin Tools!");
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT,DFPlusPlusConfig.configSpec);
    }

    public static void log(Object message) {
        logger.log(Level.INFO,message);
    }

    public static String getModId() {
        return MOD_ID;
    }

    private void init(FMLClientSetupEvent event) {
        KeyBinds.registerKeyBindings();
        ChatGuiOverride.inject();
        ChatPredicates.setCustomWords(DFPlusPlusConfig.getCustomWords());
    }
}
