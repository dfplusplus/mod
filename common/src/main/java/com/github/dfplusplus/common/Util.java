package com.github.dfplusplus.common;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class Util {
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
        String target = System.getenv().get("target");
        return target != null && target.contains("dev");
    }
}
