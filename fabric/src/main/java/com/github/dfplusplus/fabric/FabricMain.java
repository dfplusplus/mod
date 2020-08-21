package com.github.dfplusplus.fabric;

import com.github.dfplusplus.common.chat.ChatPredicates;
import com.github.dfplusplus.common.chat.ChatRule;
import com.github.dfplusplus.common.chat.screens.ChatSizingScreen;
import net.fabricmc.api.ModInitializer;

public class FabricMain implements ModInitializer {
	@Override
	public void onInitialize() {
		// This code runs as soon as MinecraftClient is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		new FabricConfigProvider(); // loads fabric config temp
	}
}
