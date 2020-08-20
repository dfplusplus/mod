package com.github.dfplusplus.fabric;

import com.github.dfplusplus.common.Test;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;

public class FabricMain implements ModInitializer {
	@Override
	public void onInitialize() {
		// This code runs as soon as MinecraftClient is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		System.out.println("Hello Fabric world!");
	}
}
