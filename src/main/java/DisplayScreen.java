import java.awt.*;

public class DisplayScreen {
    // Actual monitor coordinates and resolution, e.g. (0, 0, 1920, 1080)
    private Rectangle nativeBounds;

    // effective window space, adds JNA expansions (usually 8px in each direction) and subtracts insets (e.g. taskbar)
    // e.g. leftmost monitor with 40px tall taskbar would be (-8, -8, 1936, 1056)
    private Rectangle effectiveBounds;

    // Public constructor - user calls with GraphicsConfiguration
    public DisplayScreen(GraphicsConfiguration gc) {
        this(gc.getBounds(), Toolkit.getDefaultToolkit().getScreenInsets(gc), gc.getDevice());
    }

    // Private constructor used by public constructor
    private DisplayScreen(Rectangle gcBounds, Insets insets, GraphicsDevice gd) {
        this.nativeBounds = gcBounds;

        System.out.print(gd + ", Native bounds: " + nativeBounds);
        System.out.print(", Insets: " + insets.toString());

        effectiveBounds = new Rectangle(
                gcBounds.x,
                gcBounds.y,
                gcBounds.width - (insets.left + insets.right),
                gcBounds.height - (insets.top + insets.bottom)
        );

        System.out.println(", Effective: " + effectiveBounds);
    }

    public float areaOverlap(Rectangle windowRectangle) {
        // Calculate bounds of window
        int windowLeft = windowRectangle.x;
        int windowTop = windowRectangle.y;
        int windowRight = windowRectangle.x + windowRectangle.width;
        int windowBottom = windowRectangle.y + windowRectangle.height;

        // Calculate bounds of display
        int screenLeft = effectiveBounds.x;
        int screenTop = effectiveBounds.y;
        int screenRight = effectiveBounds.x + effectiveBounds.width;
        int screenBottom = effectiveBounds.y + effectiveBounds.height;

        // Find region of intersection
        int intersectLeft = Math.max(windowLeft, screenLeft);
        int intersectTop = Math.max(windowTop, screenTop);
        int intersectRight = Math.min(windowRight, screenRight);
        int intersectBottom = Math.min(windowBottom, screenBottom);

        if (intersectLeft < intersectRight && intersectTop < intersectBottom) { // nonempty intersection
            return (intersectRight - intersectLeft) * (intersectBottom - intersectTop);
        }

        return 0; // empty intersection
    }

    public Rectangle getNativeBounds() {
        return nativeBounds;
    }

    public Rectangle getEffectiveBounds() {
        return effectiveBounds;
    }
}
