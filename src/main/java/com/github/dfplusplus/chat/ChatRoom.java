package com.github.dfplusplus.chat;

import com.github.dfplusplus.PermissionLevel;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.List;

public enum ChatRoom {
    DEFAULT_CHAT(Minecraft.getInstance().gameSettings.getChatBackgroundColor(Integer.MIN_VALUE), PermissionLevel.DEFAULT),
    SUPPORT_CHAT((new Color(0x55,0xff,0xff,100)).getRGB(), PermissionLevel.SUPPORT),
    MOD_CHAT((new Color(0x55,0xff,0x55,100).getRGB()), PermissionLevel.MOD),
    ADMIN_CHAT((new Color(0xff,0x55,0x55,100).getRGB()), PermissionLevel.ADMIN);

    private final int color;

    private final PermissionLevel permissionLevel;

    public int getColor() {
        return color;
    }

    public PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }

    ChatRoom(int color, PermissionLevel permissionLevel) {
        this.color = color;
        this.permissionLevel = permissionLevel;
    }

    /**
     * Gets every chat room except ChatRoom.DEFAULT_CHAT
     * @return An array of all chatrooms
     */
    public static List<ChatRoom> getCustomChatrooms() {
        List<ChatRoom> customChatrooms = Lists.newArrayList(ChatRoom.values());
        customChatrooms.removeIf(chatRoom -> chatRoom == ChatRoom.DEFAULT_CHAT); // remove default chat
        customChatrooms.removeIf(chatRoom -> !PermissionLevel.hasPerms(chatRoom.getPermissionLevel())); // remove chats you dont have perms for
        return customChatrooms;
    }
}
