package com.github.dfplusplus.chat;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.List;

public enum ChatRoom {
    DEFAULT_CHAT(Minecraft.getInstance().gameSettings.getChatBackgroundColor(Integer.MIN_VALUE)),
    SUPPORT_CHAT((new Color(0x55,0xff,0xff,100)).getRGB()),
    MOD_CHAT((new Color(0x55,0xff,0x55,100).getRGB())),
    ADMIN_CHAT((new Color(0xff,0x55,0x55,100).getRGB()));

    private final int color;

    public int getColor() {
        return color;
    }

    ChatRoom(int color) {
        this.color = color;
    }

    /**
     * Gets every chat room except ChatRoom.DEFAULT_CHAT
     * @return An array of all chatrooms
     */
    public static List<ChatRoom> getCustomChatrooms() {
        List<ChatRoom> customChatrooms = Lists.newArrayList(ChatRoom.values());
        customChatrooms.removeIf(chatRoom -> chatRoom == ChatRoom.DEFAULT_CHAT);
        return customChatrooms;
    }
}
