package com.github.dfplusplus.fabric.mixin;

import com.github.dfplusplus.common.chat.ChatGuiOverride;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
	@Shadow private ChatHud chatHud;

	@Inject(at = @At("TAIL"), method = "<init>")
	private void init(CallbackInfo info) {
		chatHud = new ChatGuiOverride(MinecraftClient.getInstance());
	}
}
