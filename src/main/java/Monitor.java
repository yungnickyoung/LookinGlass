import java.awt.*;

public class Monitor {
    private final GraphicsDevice graphicsDevice; // one device associated per monitor
    private final int width; // in pixels
    private final int height; // in pixels

    // Constructor - must be initialized with a graphics device
    public Monitor(GraphicsDevice gd) {
        graphicsDevice = gd;
        width = getGraphicsDeviceResolution()[0];
        height = getGraphicsDeviceResolution()[1];
    }

    public int[] getResolutionAsArray() {
        return new int[] {width, height};
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    /*
     * Returns the resolution of a GraphicsDevice as an int[] {width, height}
     */
    private int[] getGraphicsDeviceResolution() {
        return new int[] {
                graphicsDevice.getDisplayMode().getWidth(),
                graphicsDevice.getDisplayMode().getHeight()
        };
    }
}
