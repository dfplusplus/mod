package com.github.dfplusplus.fabric.providers;

import com.github.dfplusplus.common.providers.IResourceProvider;

public class FabricResourceProvider implements IResourceProvider {
    @Override
    public boolean containsResource(String name) {
        return true;
    }
}
