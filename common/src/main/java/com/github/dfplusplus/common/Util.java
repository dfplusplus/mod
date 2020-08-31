package com.github.dfplusplus.common;

import com.github.dfplusplus.common.providers.IUtilProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class Util {
    private static IUtilProvider utilProvider;

    public static void setUtilProvider(IUtilProvider utilProvider) {
        Util.utilProvider = utilProvider;
    }

    public static boolean isValidClient() {
        Minecraft minecraft = Minecraft.getInstance();
        return minecraft.world != null && minecraft.world.isRemote() && minecraft.player != null;
    }

    public static void playSound(SoundEvent soundEvent) {
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;
        mc.world.playSound(
                player.getPosX(),
                player.getPosY(),
                player.getPosZ(),
                soundEvent,
                SoundCategory.PLAYERS,
                1f,
                1f,
                false
        );
    }

    public static boolean isDeveloperEnv() {
        return utilProvider.isDeveloperEnv();
    }

    public static int getRGB(int r, int g, int b, int a) {
        return
                ((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8)  |
                ((b & 0xFF));
    }

    public static boolean hasResource(String name) {
        if (utilProvider == null) throw new IllegalStateException("Util Provider not yet set!");
        return utilProvider.hasResource(name);
    }
}
