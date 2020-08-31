package com.github.dfplusplus.fabric.providers;

import com.github.dfplusplus.common.providers.IUtilProvider;
import net.fabricmc.loader.api.FabricLoader;

import static com.github.dfplusplus.common.CommonMain.MOD_ID;

public class FabricUtilProvider implements IUtilProvider {
    @Override
    public boolean containsResource(String name) {
        return getClass().getResource(String.format("/assets/%s/%s", MOD_ID, name)) != null;
    }

    @Override
    public boolean isDeveloperEnv() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
