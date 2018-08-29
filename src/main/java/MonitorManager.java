import com.google.common.graph.Graph;

import java.awt.*;
import java.util.ArrayList;

public class MonitorManager {
    private ArrayList<Monitor> monitors;

    public MonitorManager() {
        GraphicsDevice[] graphicsDevices = getGraphicsDevices();

        // Initialize monitor objects: one per detected graphics device
        for (GraphicsDevice gd : graphicsDevices) {
            monitors.add(new Monitor(gd));
        }
    }

    /*
     * TODO - will return the resolution of the GraphicsDevice which contains the active window
     * TODO - currently do not know how to figure out which GD contains the active window
     */
    private int[] getActiveMonitorResolution() {
//        GraphicsDevice activeWindow = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//        return getGraphicsDeviceResolution(activeWindow);
        return null;
    }

    /*
     * Returns an array of all GraphicsDevice's on the system
     */
    private GraphicsDevice[] getGraphicsDevices() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        return ge.getScreenDevices();
    }

    /*
     * An alternative method for getting the resolution of a monitor.
     * Only guaranteed to work on single-monitor setups.
     * Returns the resolution as an int[] ( {width, height} )
     */


}
