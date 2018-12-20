package com.github.yungnickyoung.lookingglass;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class for managing all display devices on a system.
 */
public class DisplayManager {
    // private static DisplayManager instance = null; // static singleton instance
    private static ArrayList<DisplayScreen> displayDevices; // list of display devices being managed

    /**
     * Private constructor to prevent instantiation
     */
    private DisplayManager() {}

    /**
     * Finds the primary display device for a window based on area of overlap.
     * Whichever display device contains the greatest area of overlap with the window
     * is designated as the primary display.
     * @param window The window in question, as a java.awt.Rectangle
     * @return The primary display for the given window, or {@code null} if no raster type
     * display device overlaps with this window
     */
    public static DisplayScreen findPrimaryDisplayForWindow(Rectangle window) {
        updateSystemDisplayDevices();

        float maxOverlapArea = 0;
        DisplayScreen primaryDisplay = null;

        for (DisplayScreen ds : displayDevices) {
            float overlapArea = ds.areaOverlap(window);
            if (overlapArea > maxOverlapArea) {
                maxOverlapArea = overlapArea;
                primaryDisplay = ds;
            }
        }

        return primaryDisplay;
    }

    /**
     * Finds the primary display device for a window based on area of overlap.
     * Whichever display device contains the greatest area of overlap with the window
     * is designated as the primary display.
     * @param window The window in question, as an LGWindow
     * @return The primary display for the given window, or {@code null} if no raster type
     * display device overlaps with this window
     */
    public static DisplayScreen findPrimaryDisplayForWindow(LGWindow window) {
        return findPrimaryDisplayForWindow(window.getRectangle());
    }

    /**
     * Method for updating the DisplayManager's list of the system's display devices.
     */
    private static void updateSystemDisplayDevices() {
        displayDevices = findSystemDisplays();
    }

    /**
     * Private method used to create a list of the system's display devices.
     * These devices are determined using {@code java.awt.GraphicsEnvironment}.
     * Only devices which are a {@code java.awt.GraphicsDevice.TYPE_RASTER_SCREEN} are used.
     * @return ArrayList of the system's DisplayScreen's
     */
    private static ArrayList<DisplayScreen> findSystemDisplays () {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();

        ArrayList<DisplayScreen> displayDevices = new ArrayList<>();

        for (GraphicsDevice gd : gs) {
            System.out.println("Graphics device: " + gd.getIDstring() + " Type:" + ((gd.getType() == 0) ? "Display" : "Non-display; ignoring..."));

            if (gd.getType() != GraphicsDevice.TYPE_RASTER_SCREEN) {
                continue; // Ignore non-monitor graphical devices
            }

            GraphicsConfiguration[] gc = gd.getConfigurations();

            for (int i=0; i < gc.length; i++) {
                System.out.print("  Configuration " + i + ": ");
                displayDevices.add(new DisplayScreen(gc[i]));
            }
        }
        return displayDevices;
    }
}

