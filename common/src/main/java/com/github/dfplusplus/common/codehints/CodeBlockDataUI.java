// BY lukecashwell

package com.github.dfplusplus.common.codehints;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CodeBlockDataUI {
    private static CodeBlockDataUI codeblockUI = null;

    private HashMap<String, ArrayList<String>> codeActions = null;

    private static final Minecraft minecraft = Minecraft.getInstance();
    private boolean hitSign = false;
    private TileEntity sign;
    private Vector3d signLoc;
    private static boolean enabled;

    public static void setEnabled(boolean enabled) {
        CodeBlockDataUI.enabled = enabled;
    }

    private CodeBlockDataUI() {
        this.codeActions = CodeBlockDataLoader.loadCodeBlockInfo();
    }

    public void onTick() {
        if(minecraft.player != null && minecraft.player.isCreative()) {
            Vector3d step = minecraft.player.getLookVec();
            step = step.normalize();
            step = step.mul(0.2, 0.2, 0.2);

            Vector3d ray = minecraft.player.getPositionVec().add(0.0, 1.5, 0.0);
            this.hitSign = false;
            this.sign = null;
            for (int i = 0; i < 100; i++) {
                ray = ray.add(step);
                BlockPos pos = new BlockPos(ray.x, ray.y, ray.z);
                Block block = minecraft.player.getEntityWorld().getBlockState(pos).getBlock();

                if (block != Blocks.AIR) {
                    if (block == Blocks.STONE || block == Blocks.PISTON || block == Blocks.STICKY_PISTON) {
                        pos = new BlockPos(ray.x - 1, ray.y, ray.z - 1);
                    } else if (block == Blocks.CHEST) {
                        pos = new BlockPos(ray.x - 1, ray.y - 1, ray.z );
                    } else if (block != Blocks.OAK_WALL_SIGN) {
                        pos = new BlockPos(ray.x - 1, ray.y, ray.z);
                    }

                    block = minecraft.player.getEntityWorld().getBlockState(pos).getBlock();
                    this.sign = minecraft.player.getEntityWorld().getTileEntity(pos);
                    if (this.sign != null && block == Blocks.OAK_WALL_SIGN) {
                        if (this.sign instanceof SignTileEntity) {
                            this.hitSign = true;
                            this.signLoc = ray;
                        }
                    }
                    break;
                }
            }
        }
    }

    public void onRender() {
        if(this.hitSign && this.sign instanceof SignTileEntity && (minecraft.currentScreen == null || minecraft.currentScreen instanceof ChatScreen) && minecraft.player != null && enabled) {
            SignTileEntity s = (SignTileEntity)this.sign;
            RenderSystem.pushMatrix();
            float x = (minecraft.getFramebuffer().framebufferWidth / 2f / (float)minecraft.gameSettings.guiScale) + 5;
            float y = (minecraft.getFramebuffer().framebufferHeight / 2f  / (float)minecraft.gameSettings.guiScale) + 5;


            List<String> lines = CodeBlockDataUI.getInstance().codeActions.get(s.getText(0).getString() + ":" + s.getText(1).getString());

            MatrixStack stack = new MatrixStack();
            stack.push();
            stack.scale(1f, 1f, 1f);
            stack.translate(0.0, 0.0, 0.0);
            if(lines != null) {
                for(int i = 0; i < lines.size(); i++) {
                    minecraft.fontRenderer.drawStringWithShadow(stack, lines.get(i), x, y + i*10, 0xffffffff);
                }
            } else {
                minecraft.fontRenderer.drawStringWithShadow(stack, s.getText(0).getString() + ": " + s.getText(1).getString(), x, y, 0xffffffff);
            }
            stack.pop();
            RenderSystem.popMatrix();
        }
    }

    public static void initCodeBlockDataUI() {
        if(codeblockUI == null) {
            codeblockUI = new CodeBlockDataUI();
        }
    }

    public static CodeBlockDataUI getInstance() {
        return CodeBlockDataUI.codeblockUI;
    }

    public static void onClientTick() {
        CodeBlockDataUI ui = CodeBlockDataUI.getInstance();
        if(ui != null) {
            ui.onTick();
        }
    }

    public static void onRenderTick() {
         CodeBlockDataUI ui = CodeBlockDataUI.getInstance();
         if(ui != null) {
             ui.onRender();
         }
    }
}
