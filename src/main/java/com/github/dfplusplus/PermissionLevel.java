package com.github.dfplusplus;

import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.TypesafeMap;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.List;

public enum PermissionLevel {
    ADMIN,
    MOD,
    EXPERT,
    SUPPORT,
    DEFAULT;

    private static PermissionLevel permissionLevel;

    static {
        if (Util.isDeveloperEnv()) {
            permissionLevel = PermissionLevel.ADMIN;
        } else {
            ResourceLocation adminsResourceLocation = new ResourceLocation(Main.MOD_ID,"admin_permissions");
            ResourceLocation modPermissionLocation = new ResourceLocation(Main.MOD_ID,"mod_permissions");
            ResourceLocation expertPermissionLocation = new ResourceLocation(Main.MOD_ID,"expert_permissions");
            ResourceLocation supportPermissionLocation = new ResourceLocation(Main.MOD_ID,"support_permissions");

            IResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
            permissionLevel = PermissionLevel.DEFAULT;
            if (resourceManager.hasResource(adminsResourceLocation)) permissionLevel = PermissionLevel.ADMIN;
            if (resourceManager.hasResource(modPermissionLocation)) permissionLevel = PermissionLevel.MOD;
            if (resourceManager.hasResource(expertPermissionLocation)) permissionLevel = PermissionLevel.EXPERT;
            if (resourceManager.hasResource(supportPermissionLocation)) permissionLevel = PermissionLevel.SUPPORT;
        }
    }

    public static boolean hasPerms(PermissionLevel checkPermissionLevel) {
        List<PermissionLevel> permissionLevelList = Arrays.asList(PermissionLevel.values());
        return permissionLevelList.indexOf(permissionLevel) <= permissionLevelList.indexOf(checkPermissionLevel);
    }
}
