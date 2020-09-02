package com.github.dfplusplus.forge.providers;

import com.github.dfplusplus.common.providers.IUtilProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.loading.FMLLoader;

import static com.github.dfplusplus.common.CommonMain.MOD_ID;

public class ForgeUtilProvider implements IUtilProvider {
    @Override
    public boolean hasResource(String name) {
        return Minecraft.getInstance().getResourceManager().hasResource(
                new ResourceLocation(MOD_ID,name)
        );
    }

    @Override
    public boolean isDeveloperEnv() {
        return !FMLLoader.isProduction();
    }
}
