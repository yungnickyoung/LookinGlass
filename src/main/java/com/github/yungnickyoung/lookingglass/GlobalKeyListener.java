package com.github.yungnickyoung.lookingglass;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GlobalKeyListener implements NativeKeyListener {
    Hashtable<String, Boolean> keysPressedMap = new Hashtable<>();

    public GlobalKeyListener() {
        // Set jnativehook logger to warning level
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);

    // Don't forget to disable the parent handlers.
        logger.setUseParentHandlers(false);
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        Integer keyCode = e.getKeyCode();
        String keyStr = NativeKeyEvent.getKeyText(keyCode);
        LGWindowManager windowManager = LGWindowManager.getInstance();

        keysPressedMap.put(keyStr, true);
        System.out.println("Key Pressed: " + keyStr + " " + keyCode);

        // Only continue to check keys if alt is being pressed. This prevents much unnecessary conditional
        // logic every time a key is pressed, since the alt key is relatively rarely pressed.
        if (!keysPressedMap.getOrDefault("Alt", false))
            return;

        // Then check shift key separately, after alt is confirmed to be currently pressed.
        // This is for further optimization.
        if (!keysPressedMap.getOrDefault("Shift", false))
            return;

        // Determine if any of the hot key combinations have been completed (and execute the corresponding action if so)
        if (keysPressedMap.getOrDefault("Left", false)) {
            System.out.println("SHIFTED LEFT");
            windowManager.repositionActiveWindow("left");
        } else if (keysPressedMap.getOrDefault("Up", false)) {
            System.out.println("SHIFTED UP");
            windowManager.repositionActiveWindow("up");
        } else if (keysPressedMap.getOrDefault("Right", false)) {
            System.out.println("SHIFTED RIGHT");
            windowManager.repositionActiveWindow("right");
        } else if (keysPressedMap.getOrDefault("Down", false)) {
            System.out.println("SHIFTED DOWN");
            windowManager.repositionActiveWindow("down");
        } else if (keysPressedMap.getOrDefault("C", false)) {
            System.out.println("RESET TO CENTER");
            windowManager.repositionActiveWindow("center");
        } else if (keysPressedMap.getOrDefault("F", false)) {
            System.out.println("MAXIMIZE");
            windowManager.repositionActiveWindow("maximize");
        } else if (keysPressedMap.getOrDefault("M", false)) {
            System.out.println("MINIMIZE");
            windowManager.repositionActiveWindow("minimize");
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        Integer keyCode = e.getKeyCode();
        String keyStr = NativeKeyEvent.getKeyText(keyCode);

        System.out.println("Key Released: " + keyStr + " " + keyCode);
        keysPressedMap.put(keyStr, false);
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }
}