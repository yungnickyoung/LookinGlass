import java.awt.*;
import java.util.ArrayList;

public class DisplayManager {
    private static DisplayManager instance = null;
    private ArrayList<DisplayScreen> displayDevices;

    private DisplayManager() {
        displayDevices = getSystemDisplays();
    }

    public static DisplayManager getInstance() {
        if (instance == null)
            instance = new DisplayManager();

        return instance;
    }

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

