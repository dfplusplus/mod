package io.github.dfplusplus.common;

import com.google.common.collect.Lists;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class Test {
    public static String getHelloWorld() {
        return "Hello World!";
    }

    public static KeyBinding getKeyBinding() {
        return new KeyBinding("test", GLFW.GLFW_KEY_U,"testt");
    }
}
