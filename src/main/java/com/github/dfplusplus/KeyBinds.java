package com.github.dfplusplus;

import com.github.dfplusplus.actions.Action;
import com.github.dfplusplus.actions.ArgCommandAction;
import com.github.dfplusplus.actions.CommandAction;
import com.github.dfplusplus.chat.ChatGuiOverride;
import com.github.dfplusplus.chat.ChatScreenOverride;
import com.github.dfplusplus.screens.MainScreen;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.github.dfplusplus.Main.MOD_ID;
import static org.lwjgl.glfw.GLFW.*;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class KeyBinds {
    private static final Map<ChatScreenOverride.ChatRoom, KeyBinding> chatRoomKeybinds = Maps.newHashMap();

    private static final KeyBinding displayMainScreen = new KeyBinding(
            MOD_ID + ".key.mainscreen",
            GLFW_KEY_U,
            "key.categories.dfadmintools");
    private static final Minecraft minecraft = Minecraft.getInstance();
    private static final List<ActionKeyBidning> ACTION_KEY_BINDINGS = new LinkedList<>();
    private static MainScreen mainScreen = null;

    static {
        for (ChatScreenOverride.ChatRoom chatRoom : ChatScreenOverride.ChatRoom.values()) {
            chatRoomKeybinds.put(chatRoom, new KeyBinding(
                    String.format("%s.key.chatroom.%s", MOD_ID, chatRoom.name().toLowerCase()),
                    GLFW_KEY_UNKNOWN,
                    "key.categories.dfplusplus.chatrooms"
            ));
        }
    }

    @SubscribeEvent
    public static void onKeyPress(InputEvent.KeyInputEvent keyInputEvent) {
        if (Util.isValidClient() && keyInputEvent.getAction() == 1) {
            processMainScreenKeybind();
            processChatRoomKeyBinds();
            processActionKeyBinds();
        }
    }

    private static void processMainScreenKeybind() {
        if (displayMainScreen.isPressed()) onDisplayMainScreen();
    }

    private static void processChatRoomKeyBinds() {
        for (ChatScreenOverride.ChatRoom chatRoom : ChatScreenOverride.ChatRoom.values()) {
            if (chatRoomKeybinds.get(chatRoom).isPressed()) {
                ChatScreenOverride.showChat(chatRoom);
            }
        }
    }

    private static void processActionKeyBinds() {
        for (ActionKeyBidning actionKeyBidning : ACTION_KEY_BINDINGS) {
            if (actionKeyBidning.getKeyBinding().isKeyDown()) {
                actionKeyBidning.action.run();
            }
        }
    }

    public static void registerKeyBindings() {
        mainScreen = new MainScreen(null);
//        registerMainScreenKeyBind();
//        registerActionKeyBindings();
        registerChatRoomKeyBindings();
    }

    private static void registerMainScreenKeyBind() {
        ClientRegistry.registerKeyBinding(KeyBinds.displayMainScreen);
    }

    private static void registerActionKeyBindings() {
        ACTION_KEY_BINDINGS.clear();
        if (PermissionLevel.hasPerms(PermissionLevel.MOD)) {
            addCommandBinding("/v");
        }
        addCommandBinding("/fly");
        addCommandBinding("/server node1");
        addCommandBinding("/server node2");
        addCommandBinding("/server node3");
        addCommandBinding("/server node4");
        addCommandBinding("/server beta");
        addActionBinding("/ban", ArgCommandAction.getBanAction(null));

        for (ActionKeyBidning actionKeyBidning : ACTION_KEY_BINDINGS) {
            ClientRegistry.registerKeyBinding(actionKeyBidning.getKeyBinding());
        }
    }

    private static void registerChatRoomKeyBindings() {
        for (KeyBinding keyBinding : chatRoomKeybinds.values()) {
            ClientRegistry.registerKeyBinding(keyBinding);
        }
    }

    private static void onDisplayMainScreen() {
//        minecraft.displayGuiScreen(mainScreen);
    }

    private static void addCommandBinding(String command) {
        ACTION_KEY_BINDINGS.add(new ActionKeyBidning(GLFW_KEY_UNKNOWN,command,new CommandAction(command)));
    }

    private static void addActionBinding(String name, Action action) {
        ACTION_KEY_BINDINGS.add(new ActionKeyBidning(GLFW_KEY_UNKNOWN,name,action));
    }

    private static class ActionKeyBidning {
        private final KeyBinding keyBinding;
        private final Action action;

        public ActionKeyBidning(int keyCode, String name, Action action) {
            this.keyBinding = new KeyBinding(
                    name,
                    keyCode,
                    "key.categories.dfadmintools");
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
