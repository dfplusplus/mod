package com.github.dfplusplus.forge.providers;

import com.github.dfplusplus.common.providers.IResourceProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import static com.github.dfplusplus.common.CommonMain.MOD_ID;

public class ForgeResourceProvider implements IResourceProvider {
    @Override
    public boolean hasResource(String name) {
        return Minecraft.getInstance().getResourceManager().hasResource(
                new ResourceLocation(MOD_ID)
        );
    }
}
