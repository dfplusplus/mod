package com.samstuff.dfadmintools;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static com.samstuff.dfadmintools.Main.MOD_ID;

public enum PermissionLevel {
    ADMIN,
    MOD,
    SUPPORT,
    DEFAULT;

    private static PermissionLevel permissionLevel = PermissionLevel.ADMIN;

    static {
        ResourceLocation adminsResourceLocation = new ResourceLocation(MOD_ID,"admin_permissions");
        ResourceLocation modPermissionLocation = new ResourceLocation(MOD_ID,"mod_permissions");
        ResourceLocation supportPermissionLocation = new ResourceLocation(MOD_ID,"support_permissions");

        IResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        permissionLevel = PermissionLevel.DEFAULT;
        if (resourceManager.hasResource(adminsResourceLocation)) permissionLevel = PermissionLevel.ADMIN;
        if (resourceManager.hasResource(modPermissionLocation)) permissionLevel = PermissionLevel.MOD;
        if (resourceManager.hasResource(supportPermissionLocation)) permissionLevel = PermissionLevel.SUPPORT;
    }

    public static boolean hasPerms(PermissionLevel checkPermissionLevel) {
        List<PermissionLevel> permissionLevelList = Arrays.asList(PermissionLevel.values());
        return permissionLevelList.indexOf(permissionLevel) <= permissionLevelList.indexOf(checkPermissionLevel);
    }
}
