package com.github.dfplusplus.fabric;

import com.github.dfplusplus.common.Config;
import com.github.dfplusplus.common.PermissionLevel;
import com.github.dfplusplus.common.chat.ChatPredicates;
import com.github.dfplusplus.common.chat.ChatRule;
import com.github.dfplusplus.common.chat.screens.ChatSizingScreen;
import com.github.dfplusplus.fabric.providers.FabricConfigProvider;
import com.github.dfplusplus.fabric.providers.FabricResourceProvider;
import net.fabricmc.api.ModInitializer;

public class FabricMain implements ModInitializer {
	@Override
	public void onInitialize() {
		// This code runs as soon as MinecraftClient is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		PermissionLevel.setResourceProvider(new FabricResourceProvider());
		Config.setConfigProvider(new FabricConfigProvider());

		FabricRegistry.registerKeyBindings();
		ChatPredicates.loadFromConfig();
		ChatRule.loadFromConfig();
		ChatSizingScreen.loadFromConfig();
	}
}
