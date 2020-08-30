package com.github.dfplusplus.fabric.providers;

import com.github.dfplusplus.common.providers.IResourceProvider;

import static com.github.dfplusplus.common.CommonMain.MOD_ID;

public class FabricResourceProvider implements IResourceProvider {
    @Override
    public boolean containsResource(String name) {
        return getClass().getResource(String.format("/assets/%s/%s", MOD_ID, name)) != null;
    }
}
