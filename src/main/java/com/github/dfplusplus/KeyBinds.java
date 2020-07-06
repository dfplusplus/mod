package com.github.dfplusplus;

import com.github.dfplusplus.actions.Action;
import com.github.dfplusplus.actions.ArgCommandAction;
import com.github.dfplusplus.actions.CommandAction;
import com.github.dfplusplus.chat.ChatGuiOverride;
import com.github.dfplusplus.screens.MainScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;

import java.util.LinkedList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class KeyBinds {
    private static final KeyBinding displayMainScreen = new KeyBinding(
            Main.MOD_ID + ".key.mainscreen",
            GLFW_KEY_U,
            "key.categories.dfadmintools");
    private static final Minecraft minecraft = Minecraft.getInstance();
    private static final List<ActionKeyBidning> ACTION_KEY_BINDINGS = new LinkedList<>();
    private static MainScreen mainScreen = null;

    @SubscribeEvent
    public static void onKeyPress(InputEvent.KeyInputEvent keyInputEvent) {
        if (Util.isValidClient() && keyInputEvent.getAction() == 1) {
            if (displayMainScreen.isKeyDown()) onDisplayMainScreen();
            for (ActionKeyBidning actionKeyBidning : ACTION_KEY_BINDINGS) {
                if (actionKeyBidning.getKeyBinding().isKeyDown()) {
                    actionKeyBidning.action.run();
                }
            }
        }
    }

    public static void registerKeyBindings() {
        mainScreen = new MainScreen(null);
//        addKeyBindings();

        ClientRegistry.registerKeyBinding(KeyBinds.displayMainScreen);
//        for (ActionKeyBidning actionKeyBidning : ACTION_KEY_BINDINGS) {
//            ClientRegistry.registerKeyBinding(actionKeyBidning.getKeyBinding());
//        }
    }

    private static void addKeyBindings() {
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
    }

    private static void onDisplayMainScreen() {
//        minecraft.displayGuiScreen(mainScreen);
        minecraft.ingameGUI.persistantChatGUI = new ChatGuiOverride(minecraft);
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
