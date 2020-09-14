package com.github.dfplusplus.fabric.mixin;

import com.github.dfplusplus.common.codehints.CodeBlockDataUI;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class RenderMixin {

    @Inject(at = @At("TAIL"), method = "render")
    public void render(CallbackInfo info) {
        CodeBlockDataUI.onRenderTick();
    }
}
