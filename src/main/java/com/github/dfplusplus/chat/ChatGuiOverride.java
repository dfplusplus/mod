package com.github.dfplusplus.chat;

import com.github.dfplusplus.Main;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

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
    public void render(int updateCounter) {
        if (this.isChatVisible()) {
            int i = this.getLineCount();
            int j = this.drawnChatLines.size();
            if (j > 0) {
                boolean flag = false;
                if (this.getChatOpen()) {
                    flag = true;
                }

                double d0 = this.getScale();
                int k = MathHelper.ceil((double)this.getChatWidth() / d0);
                RenderSystem.pushMatrix();
                RenderSystem.translatef(2.0F, 8.0F, 0.0F);
                RenderSystem.scaled(d0, d0, 1.0D);
                double d1 = this.mc.gameSettings.chatOpacity * (double)0.9F + (double)0.1F;
                double d2 = this.mc.gameSettings.accessibilityTextBackgroundOpacity;
                int l = 0;

                renderChat(0, this.getMainDrawnChatLines(), updateCounter, i, flag, k, d1, d2, l);
                renderChat(mc.getMainWindow().getScaledWidth() - getChatWidth(), this.getSideDrawnChatLines(),updateCounter,i,flag,k,d1,d2,l);

                if (flag) {
                    int l2 = 9;
                    RenderSystem.translatef(-3.0F, 0.0F, 0.0F);
                    int i3 = j * l2 + j;
                    int j3 = l * l2 + l;
                    int k3 = this.scrollPos * j3 / j;
                    int k1 = j3 * j3 / i3;
                    int l3 = 96;
                    int i4 = this.isScrolled ? 13382451 : 3355562;
                    fill(0, -k3, 2, -k3 - k1, i4 + (l3 << 24));
                    fill(2, -k3, 1, -k3 - k1, 13421772 + (l3 << 24));
                }

                RenderSystem.popMatrix();
            }
        }
    }

    private void renderChat(int x, List<ChatLine> chatLines, int updateCounter, int i, boolean flag, int k, double d1, double d2, int l) {
        Matrix4f mainMatrix4f = Matrix4f.makeTranslate(x, 0, -100);

        for(int i1 = 0; i1 + this.scrollPos < chatLines.size() && i1 < i; ++i1) {
            ChatLine chatline = chatLines.get(i1 + this.scrollPos);
            if (chatline != null) {
                int j1 = updateCounter - chatline.getUpdatedCounter();
                if (j1 < 200 || flag) {
                    double d3 = flag ? 1.0D : getLineBrightness(j1);
                    int l1 = (int)(255.0D * d3 * d1);
                    int i2 = (int)(255.0D * d3 * d2);
                    ++l;
                    if (l1 > 3) {
                        int k2 = -i1 * 9;
                        fill(mainMatrix4f, -2, k2 - 9, k + 4, k2, i2 << 24);
                        String s = chatline.getChatComponent().getFormattedText();
                        RenderSystem.enableBlend();
                        this.mc.fontRenderer.drawStringWithShadow(s, x, (float)(k2 - 8), 16777215 + (l1 << 24));
                        RenderSystem.disableAlphaTest();
                        RenderSystem.disableBlend();
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
    protected void setChatLine(ITextComponent chatComponent, int chatLineId, int updateCounter, boolean displayOnly) {
        super.setChatLine(chatComponent,chatLineId,updateCounter,displayOnly);

        int i = MathHelper.floor((double)this.getChatWidth() / this.getScale());
        List<ChatLine> outputChatLines =
                RenderComponentsUtil.splitText(chatComponent, i, this.mc.fontRenderer, false, false)
                .stream()
                .map(iTextComponent -> new ChatLine(updateCounter,iTextComponent,chatLineId))
                .collect(Collectors.toList());
        Collections.reverse(outputChatLines);

        boolean matchedARule = false;
        for (ChatRule chatRule : ChatRule.getChatRules()) {
            if (!matchedARule && chatRule.matches(chatComponent)) {
                if (chatRule.getChatSide() == ChatRule.ChatSide.MAIN)
                    this.getMainDrawnChatLines().addAll(0,outputChatLines);
                else if (chatRule.getChatSide() == ChatRule.ChatSide.SIDE)
                    this.getSideDrawnChatLines().addAll(0, outputChatLines);
                matchedARule = true;
            }
        }
        if (!matchedARule) this.getMainDrawnChatLines().addAll(0,outputChatLines);
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
    public ITextComponent getTextComponent(double p_194817_1_, double p_194817_3_) {
        if (this.getChatOpen() && !this.mc.gameSettings.hideGUI && this.isChatVisible()) {
            double d0 = this.getScale();
            double d1 = p_194817_1_ - 2.0D;
            double d2 = (double)this.mc.getMainWindow().getScaledHeight() - p_194817_3_ - 40.0D;
            d1 = MathHelper.floor(d1 / d0);
            d2 = MathHelper.floor(d2 / d0);
            if (!(d1 < 0.0D) && !(d2 < 0.0D)) {
                int i = Math.min(this.getLineCount(), this.getMainDrawnChatLines().size());
                if (d1 <= (double)MathHelper.floor((double)this.getChatWidth() / this.getScale()) && d2 < (double)(9 * i + i)) {
                    int j = (int)(d2 / 9.0D + (double)this.scrollPos);
                    if (j >= 0 && j < this.getMainDrawnChatLines().size()) {
                        ChatLine chatline = this.getMainDrawnChatLines().get(j);
                        int k = 0;

                        for(ITextComponent itextcomponent : chatline.getChatComponent()) {
                            if (itextcomponent instanceof StringTextComponent) {
                                k += this.mc.fontRenderer.getStringWidth(RenderComponentsUtil.removeTextColorsIfConfigured(((StringTextComponent)itextcomponent).getText(), false));
                                if ((double)k > d1) {
                                    return itextcomponent;
                                }
                            }
                        }
                    }
                }

                i = Math.min(this.getLineCount(), this.getSideDrawnChatLines().size());
                if (
                        d1 >= mc.getMainWindow().getScaledWidth() - (double)MathHelper.floor((double)this.getChatWidth() / this.getScale())
                        && d2 < (double)(9 * i + i)) {
                    int j = (int)(d2 / 9.0D + (double)this.scrollPos);
                    if (j >= 0 && j < this.getSideDrawnChatLines().size()) {
                        ChatLine chatline = this.getSideDrawnChatLines().get(j);
                        int k = mc.getMainWindow().getScaledWidth() - getChatWidth();

                        for(ITextComponent itextcomponent : chatline.getChatComponent()) {
                            if (itextcomponent instanceof StringTextComponent) {
                                k += this.mc.fontRenderer.getStringWidth(RenderComponentsUtil.removeTextColorsIfConfigured(((StringTextComponent)itextcomponent).getText(), false));
                                if ((double)k > d1) {
                                    return itextcomponent;
                                }
                            }
                        }
                    }
                }

            }
        }
        return null;
    }

    @Override
    public void printChatMessageWithOptionalDeletion(ITextComponent chatComponent, int chatLineId) {
        this.setChatLine(chatComponent, chatLineId, this.mc.ingameGUI.getTicks(), false);
    }

    public static void inject() {
        Minecraft mc = Minecraft.getInstance();
        mc.ingameGUI.persistantChatGUI = new ChatGuiOverride(mc);
    }

    private List<ChatLine> getMainDrawnChatLines() {
        return mainDrawnChatLines;
    }

    private List<ChatLine> getSideDrawnChatLines() {
        return sideDrawnChatLines;
    }
}
