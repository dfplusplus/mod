package com.github.dfplusplus.common;

import com.github.dfplusplus.common.providers.IResourceProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.List;

import static com.github.dfplusplus.common.CommonMain.MOD_ID;

public enum PermissionLevel {
    ADMIN,
    MOD,
    EXPERT,
    SUPPORT,
    DEFAULT;

    private static PermissionLevel permissionLevel;
    private static IResourceProvider resourceProvider;

    public static void setResourceProvider(IResourceProvider resourceProvider) {
        PermissionLevel.resourceProvider = resourceProvider;
    }

    private static void loadPerms() {
        if (resourceProvider == null) throw new IllegalStateException("Resource Provider not yet set!");
        if (Util.isDeveloperEnv()) {
            permissionLevel = PermissionLevel.ADMIN;
        } else {
            permissionLevel = PermissionLevel.DEFAULT;
            if (resourceProvider.hasResource("admin_permissions")) permissionLevel = PermissionLevel.ADMIN;
            if (resourceProvider.hasResource("mod_permissions")) permissionLevel = PermissionLevel.MOD;
            if (resourceProvider.hasResource("expert_permissions")) permissionLevel = PermissionLevel.EXPERT;
            if (resourceProvider.hasResource("support_permissions")) permissionLevel = PermissionLevel.SUPPORT;
        }
    }

    public static boolean hasPerms(PermissionLevel checkPermissionLevel) {
        if (permissionLevel == null) loadPerms();
        List<PermissionLevel> permissionLevelList = Arrays.asList(PermissionLevel.values());
        return permissionLevelList.indexOf(permissionLevel) <= permissionLevelList.indexOf(checkPermissionLevel);
    }
}
