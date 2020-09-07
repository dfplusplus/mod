package com.github.dfplusplus.common;

import com.github.dfplusplus.common.actions.Action;
import com.github.dfplusplus.common.actions.ArgCommandAction;
import com.github.dfplusplus.common.actions.CommandAction;
import com.github.dfplusplus.common.chat.ChatRoom;
import com.github.dfplusplus.common.chat.ChatScreenOverride;
import com.github.dfplusplus.common.screens.MainScreen;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.dfplusplus.common.CommonMain.MOD_ID;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_U;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UNKNOWN;

public class KeyBinds {
    private static final Map<ChatRoom, KeyBinding> chatRoomKeybinds = Maps.newHashMap();

    private static final KeyBinding displayMainScreen = new KeyBinding(
            MOD_ID + ".key.mainscreen",
            GLFW_KEY_U,
            "key.categories.dfplusplus");
    private static final Minecraft minecraft = Minecraft.getInstance();
    private static final List<ActionKeyBidning> ACTION_KEY_BINDINGS = new LinkedList<>();
    private static MainScreen mainScreen = null;

    public static void onBeginKeyPress() {
        processMainScreenKeybind();
        processActionKeyBinds();
    }

    public static void onEndKeyPress() {
        processChatRoomKeyBinds();
    }

    private static void processMainScreenKeybind() {
        if (displayMainScreen.isPressed()) onDisplayMainScreen();
    }

    private static void processChatRoomKeyBinds() {
        for (ChatRoom chatRoom : ChatRoom.getCustomChatrooms()) {
            if (chatRoomKeybinds.get(chatRoom).isPressed()) {
                ChatScreenOverride.showChat(chatRoom);
            }
        }
    }

    private static void processActionKeyBinds() {
        for (ActionKeyBidning actionKeyBidning : ACTION_KEY_BINDINGS) {
            if (actionKeyBidning.getKeyBinding().isPressed()) {
                actionKeyBidning.getAction().run();
            }
        }
    }

    public static List<KeyBinding> fetchKeyBindings() {
        mainScreen = new MainScreen(null);
        List<KeyBinding> keyBindings = Lists.newLinkedList();
        keyBindings.add   (fetchMainScreenKeyBind());
        keyBindings.addAll(fetchActionKeyBindings());
        keyBindings.addAll(fetchChatRoomKeyBindings());
        return keyBindings;
    }

    private static KeyBinding fetchMainScreenKeyBind() {
        return KeyBinds.displayMainScreen;
    }

    private static List<KeyBinding> fetchActionKeyBindings() {
        ACTION_KEY_BINDINGS.clear();

        // mod keybinds
        addCommandBinding("/mod v", PermissionLevel.MOD);
        addActionBinding("/ban", ArgCommandAction.getBanAction(null), PermissionLevel.MOD);
        addActionBinding("/warn", ArgCommandAction.getWarnAction(null), PermissionLevel.MOD);
        addActionBinding("/unwarn", ArgCommandAction.getUnwarnAction(null), PermissionLevel.MOD);
        addActionBinding("/tempban", ArgCommandAction.getTempbanAction(null), PermissionLevel.MOD);
        addActionBinding("/hist", ArgCommandAction.getHistAction(null), PermissionLevel.MOD);
        addActionBinding("/alts", ArgCommandAction.getAltsAction(null), PermissionLevel.MOD);

        // support keybinds
        addCommandBinding("/support accept", PermissionLevel.SUPPORT);
        addCommandBinding("/support queue", PermissionLevel.SUPPORT);

        // default keybinds
        addCommandBinding("/fly", PermissionLevel.DEFAULT);
        addCommandBinding("/server node1", PermissionLevel.DEFAULT);
        addCommandBinding("/server node2", PermissionLevel.DEFAULT);
        addCommandBinding("/server node3", PermissionLevel.DEFAULT);
        addCommandBinding("/server node4", PermissionLevel.DEFAULT);
        addCommandBinding("/server beta", PermissionLevel.DEFAULT);

        return ACTION_KEY_BINDINGS.stream() // gathers all the key bindings I just added
                .map(actionKeyBidning -> actionKeyBidning.keyBinding)
                .collect(Collectors.toList());
    }

    private static List<KeyBinding> fetchChatRoomKeyBindings() {
        for (ChatRoom chatRoom : ChatRoom.getCustomChatrooms()) {
            chatRoomKeybinds.put(chatRoom, new KeyBinding(
                    String.format("%s.key.chatroom.%s", MOD_ID, chatRoom.name().toLowerCase()),
                    GLFW_KEY_UNKNOWN,
                    "key.categories.dfplusplus.chatrooms"
            ));
        }
        return Lists.newLinkedList(chatRoomKeybinds.values());
    }

    private static void onDisplayMainScreen() {
        minecraft.displayGuiScreen(mainScreen);
    }

    private static void addCommandBinding(String command, PermissionLevel permissionLevel) {
        addActionBinding(command,new CommandAction(command),permissionLevel);
    }

    private static void addActionBinding(String name, Action action, PermissionLevel permissionLevel) {
        if (PermissionLevel.hasPerms(permissionLevel))
            ACTION_KEY_BINDINGS.add(new ActionKeyBidning(GLFW_KEY_UNKNOWN,name,action,permissionLevel));
    }

    private static class ActionKeyBidning {
        private final KeyBinding keyBinding;
        private final Action action;

        public ActionKeyBidning(int keyCode, String name, Action action, PermissionLevel permissionLevel) {
            this.keyBinding = new KeyBinding(
                    name,
                    keyCode,
                    String.format("key.categories.dfplusplus.%s", permissionLevel.name().toLowerCase()));
            this.action = action;
        }

        public KeyBinding getKeyBinding() {
            return keyBinding;
        }

        public Action getAction() {
            return action;
        }
    }
}
