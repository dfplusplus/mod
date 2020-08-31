package com.github.dfplusplus.common;

import com.github.dfplusplus.common.providers.IUtilProvider;

import java.util.Arrays;
import java.util.List;

public enum PermissionLevel {
    ADMIN,
    MOD,
    EXPERT,
    SUPPORT,
    DEFAULT;

    private static PermissionLevel permissionLevel;

    private static void loadPerms() {
        if (Util.isDeveloperEnv()) {
            permissionLevel = PermissionLevel.ADMIN;
        } else {
            permissionLevel = PermissionLevel.DEFAULT;
            if (Util.hasResource("admin_permissions")) permissionLevel = PermissionLevel.ADMIN;
            if (Util.hasResource("mod_permissions")) permissionLevel = PermissionLevel.MOD;
            if (Util.hasResource("expert_permissions")) permissionLevel = PermissionLevel.EXPERT;
            if (Util.hasResource("support_permissions")) permissionLevel = PermissionLevel.SUPPORT;
        }
    }

    public static boolean hasPerms(PermissionLevel checkPermissionLevel) {
        if (permissionLevel == null) loadPerms();
        List<PermissionLevel> permissionLevelList = Arrays.asList(PermissionLevel.values());
        return permissionLevelList.indexOf(permissionLevel) <= permissionLevelList.indexOf(checkPermissionLevel);
    }
}
