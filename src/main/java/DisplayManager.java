import java.awt.*;
import java.util.ArrayList;

/**
 * Singleton class for managing all display devices on a system.
 * Use <pre> {@code DisplayManager.getInstance()} </pre> for instantiation.
 */
public class DisplayManager {
    private static DisplayManager instance = null; // static singleton instance
    private ArrayList<DisplayScreen> displayDevices; // list of display devices being managed

    // Private constructor used by getInstance()
    private DisplayManager() {
        displayDevices = getSystemDisplays();
    }

    /**
     * @return Singleton instance of DisplayManager
     */
    public static DisplayManager getInstance() {
        if (instance == null)
            instance = new DisplayManager();

        return instance;
    }

    /**
     * Finds the primary display device for a window based on area of overlap.
     * Whichever display device contains the greatest area of overlap with the window
     * is designated as the primary display.
     * @param window The window in question, as a java.awt.Rectangle
     * @return The primary display for the given window, as a DisplayScreen
     */
    public DisplayScreen findPrimaryDisplayForWindow(Rectangle window) {
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
     * Method for updating the DisplayManager's list of the system's display devices. Useful if any 
     * devices have been added or removed.
     */
    public void updateSystemDisplayDevices() {
        displayDevices = getSystemDisplays();
    }

    /**
     * Private method used to create a list of the system's display devices.
     * These devices are determined using java.awt.GraphicsEnvironment.
     * Only devices which are a {@code java.awt.GraphicsDevice.TYPE_RASTER_SCREEN} are used.
     * @return List of the system's DisplayScreen's as an ArrayList.
     */
    private ArrayList<DisplayScreen> getSystemDisplays () {
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

