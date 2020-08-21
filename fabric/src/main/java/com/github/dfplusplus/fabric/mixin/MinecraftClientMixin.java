package com.github.dfplusplus.fabric.mixin;

import com.github.dfplusplus.common.chat.ChatGuiOverride;
import com.github.dfplusplus.common.chat.ChatPredicates;
import com.github.dfplusplus.common.chat.ChatRule;
import com.github.dfplusplus.common.chat.screens.ChatSizingScreen;
import com.github.dfplusplus.fabric.FabricRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	@Inject(at = @At("TAIL"), method = "<init>")
	private void init(CallbackInfo info) {
		FabricRegistry.registerKeyBindings();
		ChatPredicates.loadFromConfig();
		ChatRule.loadFromConfig();
		ChatSizingScreen.loadFromConfig();
	}
}
