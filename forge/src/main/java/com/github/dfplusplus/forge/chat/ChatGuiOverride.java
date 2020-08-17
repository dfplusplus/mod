package com.github.dfplusplus.forge.chat;

import com.github.dfplusplus.forge.Util;
import com.github.dfplusplus.forge.chat.screens.ChatSizingScreen;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.*;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ChatGuiOverride extends NewChatGui {
    private final List<ChatLine> mainDrawnChatLines = Lists.newArrayList();
    private final List<ChatLine> sideDrawnChatLines = Lists.newArrayList();

    public ChatGuiOverride(Minecraft mcIn) {
        super(mcIn);
    }

    @Override
    public void func_238492_a_(MatrixStack p_238492_1_, int updateCounter) {
        if (!this.func_238496_i_()) {
            int i = this.getLineCount();
            int mainDrawnChatLinesSize = this.mainDrawnChatLines.size();
            boolean flag = false;
            if (this.getChatOpen()) {
                flag = true;
            }
            if (mainDrawnChatLinesSize > 0) { // if theres messages in main chat

                double d0 = this.getScale();
                int k = MathHelper.ceil((double) this.getChatWidth() / d0);
                // marked as depracated but minecraft still use em so.. so do I
                RenderSystem.pushMatrix();
                RenderSystem.translatef(2.0F, 8.0F, 0.0F);
                RenderSystem.scaled(d0, d0, 1.0D);
                double d1 = this.mc.gameSettings.chatOpacity * (double) 0.9F + (double) 0.1F;
                double d2 = this.mc.gameSettings.accessibilityTextBackgroundOpacity;
                double d3 = 9.0D * (this.mc.gameSettings.field_238331_l_ + 1.0D);
                double d4 = -8.0D * (this.mc.gameSettings.field_238331_l_ + 1.0D) + 4.0D * this.mc.gameSettings.field_238331_l_;
                int l = 0;
                renderChat(p_238492_1_, 0, 0, this.getMainDrawnChatLines(), updateCounter, i, flag, k, d1, d2, d3, d4, l);

                if (flag) { // scroll bar (ignoring one for 2nd chat)
                    int l2 = 9;
                    RenderSystem.translatef(-3.0F, 0.0F, 0.0F);
                    int i3 = mainDrawnChatLinesSize * l2 + mainDrawnChatLinesSize;
                    int j3 = l * l2 + l;
                    int k3 = this.scrollPos * j3 / mainDrawnChatLinesSize;
                    int k1 = j3 * j3 / i3;
                    int l3 = 96;
                    int i4 = this.isScrolled ? 13382451 : 3355562;
                    fill(p_238492_1_, 0, -k3, 2, -k3 - k1, i4 + (l3 << 24));
                    fill(p_238492_1_, 2, -k3, 1, -k3 - k1, 13421772 + (l3 << 24));
                }
                RenderSystem.popMatrix();
            }
            // then, a seperate check for the side drawn chat
            if (this.sideDrawnChatLines.size() > 0) {
                // resets the matrix or smth
                double d0 = getSideChatScale();
                int k = MathHelper.ceil((double)getSideChatWidth() / d0);
                RenderSystem.pushMatrix();
                RenderSystem.translatef(2.0F, 8.0F, 0.0F);
                RenderSystem.scaled(d0, d0, 1.0D);
                double d1 = this.mc.gameSettings.chatOpacity * (double)0.9F + (double)0.1F;
                double d2 = this.mc.gameSettings.accessibilityTextBackgroundOpacity;
                double d3 = 9.0D * (this.mc.gameSettings.field_238331_l_ + 1.0D);
                double d4 = -8.0D * (this.mc.gameSettings.field_238331_l_ + 1.0D) + 4.0D * this.mc.gameSettings.field_238331_l_;
                int l = 0;
                renderChat(p_238492_1_, getSideChatStartX() + ChatSizingScreen.getChatOffsetX(), -ChatSizingScreen.getChatOffsetY(), this.getSideDrawnChatLines(),updateCounter,i,flag,k,d1,d2,d3,d4,l);
                RenderSystem.popMatrix();
            }
        }
    }

    private void renderChat(MatrixStack matrixStack, int x, int y, List<ChatLine> chatLines, int updateCounter, int i, boolean flag, int k, double d1, double d2, double d3, double d4, int l) {
        for(int i1 = 0; i1 + this.scrollPos < chatLines.size() && i1 < i; ++i1) {
            ChatLine chatline = chatLines.get(i1 + this.scrollPos);
            if (chatline != null) {
                int j1 = updateCounter - chatline.getUpdatedCounter();
                if (j1 < 200 || flag) {
                    double d5 = flag ? 1.0D : getLineBrightness(j1);
                    int l1 = (int)(255.0D * d5 * d1);
                    int i2 = (int)(255.0D * d5 * d2);
                    ++l;
                    if (l1 > 3) {
                        double d6 = (double)(-i1) * d3;
                        matrixStack.push();
                        matrixStack.translate(x, y, 50.0D);
                        fill(matrixStack, -2, (int)(d6 - d3), k + 4, (int)d6, i2 << 24);
                        RenderSystem.enableBlend();
                        matrixStack.translate(0.0D, 0.0D, 50.0D);
                        this.mc.fontRenderer.func_238407_a_(matrixStack, chatline.func_238169_a_(), 0.0F, (float)((int)(d6 + d4)), 16777215 + (l1 << 24));
                        RenderSystem.disableAlphaTest();
                        RenderSystem.disableBlend();
                        matrixStack.pop();
                    }
                }
            }
        }
    }

    /**
     * Clears the chat.
     */
    @Override
    public void clearChatMessages(boolean p_146231_1_) {
        super.clearChatMessages(p_146231_1_);
        this.getMainDrawnChatLines().clear();
        this.getSideDrawnChatLines().clear();
    }

    @Override
    protected void func_238493_a_(ITextProperties textProperties, int chatLineId, int updateCounter, boolean displayOnly) {
        super.func_238493_a_(textProperties,chatLineId,updateCounter,displayOnly);
        ITextComponent chatComponent = ((ITextComponent) textProperties);

        boolean matchedARule = false;
        for (ChatRule chatRule : ChatRule.getChatRules()) {
            if (chatRule.matches(chatComponent)) {
                // also don't add to chat if the chat side is either
                if (!matchedARule && chatRule.getChatSide() != ChatRule.ChatSide.EITHER) {
                    addToChat(chatRule.getChatSide(), chatComponent, chatLineId, updateCounter);
                    matchedARule = true;
                }

                if (chatRule.getChatSound() != ChatRule.ChatSound.NONE) {
                    Util.playSound(chatRule.getChatSound().getSoundEvent());
                }
            }
        }
        if (!matchedARule) {
            int i = MathHelper.floor((double) this.getChatWidth() / this.getScale());
            List<ChatLine> outputChatLines = getOutputChatLines(chatComponent, chatLineId, updateCounter, i);
            this.getMainDrawnChatLines().addAll(0, outputChatLines);
        }
    }

    private List<ChatLine> getOutputChatLines(ITextComponent chatComponent, int chatLineId, int updateCounter, int i) {
        List<ChatLine> outputChatLines =
                RenderComponentsUtil.func_238505_a_(chatComponent, i, this.mc.fontRenderer)
                        .stream()
                        .map(iTextComponent -> new ChatLine(updateCounter, iTextComponent, chatLineId))
                        .collect(Collectors.toList());
        Collections.reverse(outputChatLines);
        return outputChatLines;
    }

    @Override
    public void refreshChat() {
        super.refreshChat();
        this.getMainDrawnChatLines().clear();
        this.getSideDrawnChatLines().clear();
    }

    @Override
    public void deleteChatLine(int id) {
        super.deleteChatLine(id);

        this.getMainDrawnChatLines().removeIf(chatline -> chatline.getChatLineID() == id);
        this.getSideDrawnChatLines().removeIf(chatline -> chatline.getChatLineID() == id);
    }

    @Nullable
    @Override
    public Style func_238494_b_(double mouseX, double mouseY) {
        if (this.getChatOpen() && !this.mc.gameSettings.hideGUI && !this.func_238496_i_()) {
            double scale = this.getScale();
            double d0 = mouseX - 2.0D;
            double d1 = (double)this.mc.getMainWindow().getScaledHeight() - mouseY - 40.0D;
            d0 = (double)MathHelper.floor(d0 / scale);
            d1 = (double)MathHelper.floor(d1 / (scale * (this.mc.gameSettings.field_238331_l_ + 1.0D)));
            if (!(d0 < 0.0D) && !(d1 < 0.0D)) {
                int i = Math.min(this.getLineCount(), this.getMainDrawnChatLines().size());
                if (d0 <= (double) MathHelper.floor((double) this.getChatWidth() / scale) && d1 < (double) (9 * i + i)) {
                    int j = (int) (d1 / 9.0D + (double) this.scrollPos);
                    if (j >= 0 && j < this.getMainDrawnChatLines().size()) {
                        ChatLine chatline = this.getMainDrawnChatLines().get(j);
                        Style returnedStyle = this.mc.fontRenderer.func_238420_b_().func_238357_a_(chatline.func_238169_a_(), (int) d0);
                        if (returnedStyle != null) return returnedStyle; // only return if there's actually some style found
                    }
                }
            }

            // then recalculate the consts as 2nd chat may be diff size
            scale = getSideChatScale();
            d0 = mouseX - 2.0D;
            d1 = (double)this.mc.getMainWindow().getScaledHeight() - mouseY - 40.0D;
            d0 = MathHelper.floor(d0 / scale);
            d1 = MathHelper.floor(d1 / (scale * (this.mc.gameSettings.field_238331_l_ + 1.0D)));
            if (!(d0 < 0.0D) && !(d1 < 0.0D)) {
                int i = Math.min(this.getLineCount(), this.getSideDrawnChatLines().size());
                if (d0 <= (double) MathHelper.floor((double) this.getSideChatWidth() / scale) && d1 < (double) (9 * i + i)) {
                    int j = (int)(d1 / 9.0D + (double)this.scrollPos);
                    if (j >= 0 && j < this.getSideDrawnChatLines().size()) {
                        ChatLine chatline = this.getSideDrawnChatLines().get(j);
                        int k = getSideChatStartX(); // subtract to d0 so that it thinks cursor is at x = 0 when x = sideChatStartX
                        return this.mc.fontRenderer.func_238420_b_().func_238357_a_(chatline.func_238169_a_(), (int)d0 - k);
                    }
                }

            }
        }
        return null;
    }

    private int getSideChatStartX() {
        return (int) ((mc.getMainWindow().getScaledWidth() - getSideChatWidth()) / getSideChatScale()) - 4;
    }

    @Override
    public void printChatMessageWithOptionalDeletion(ITextComponent chatComponent, int chatLineId) {
        this.func_238493_a_(chatComponent, chatLineId, this.mc.ingameGUI.getTicks(), false);
        LOGGER.info("[CHAT] {}", chatComponent.getString().replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n"));
    }

    @Override
    public boolean getChatOpen() {
        return super.getChatOpen() || this.mc.currentScreen instanceof ChatSizingScreen;
    }

    public static void inject() {
        Minecraft mc = Minecraft.getInstance();
        mc.ingameGUI.persistantChatGUI = new ChatGuiOverride(mc);
    }

    public void addToChat(ChatRule.ChatSide side, String message) {
        this.addToChat(side, new TranslationTextComponent(message),0,mc.ingameGUI.getTicks());
    }

    private List<ChatLine> getChatLines(ChatRule.ChatSide chatSide) {
        switch (chatSide) {
            case MAIN: default:
                return mainDrawnChatLines;
            case SIDE:
                return sideDrawnChatLines;
        }
    }

    private List<ChatLine> getMainDrawnChatLines() {
        return mainDrawnChatLines;
    }

    private List<ChatLine> getSideDrawnChatLines() {
        return sideDrawnChatLines;
    }

    private void addToChat(ChatRule.ChatSide side, ITextComponent chatComponent, int chatLineId, int updateCounter) {
        int i;
        switch (side) {
            case MAIN: default:
                i = MathHelper.floor((double) this.getChatWidth() / this.getScale());
                break;
            case SIDE:
                i = MathHelper.floor((double) getSideChatWidth() / getSideChatScale());
                break;
        }
        List<ChatLine> outputChatLines = getOutputChatLines(chatComponent, chatLineId, updateCounter, i);
        this.getChatLines(side).addAll(0, outputChatLines);
    }

    private int getSideChatWidth() {
        return calculateChatboxWidth(ChatSizingScreen.getChatWidth());
    }

    private double getSideChatScale() {
        return ChatSizingScreen.getChatScale();
    }
}
