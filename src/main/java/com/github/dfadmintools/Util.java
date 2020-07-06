package com.github.dfadmintools;

import net.minecraft.client.Minecraft;

public class Util {
    public static boolean isValidClient() {
        Minecraft minecraft = Minecraft.getInstance();
        return minecraft.world != null && minecraft.world.isRemote() && minecraft.player != null;
    }
}
