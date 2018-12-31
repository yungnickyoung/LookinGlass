package com.github.yungnickyoung.lookingglass;

import java.awt.*;

public class DisplayScreen {
    // Actual monitor coordinates and resolution, e.g. (0, 0, 1920, 1080)
    private Rectangle nativeBounds;

    // Effective window space, adds JNA expansions (usually 8px in each direction) and subtracts insets (e.g. taskbar)
    // e.g. leftmost monitor with 40px tall taskbar would be (-8, -8, 1936, 1056)
    private Rectangle effectiveBounds;

    /**
     * Public constructor called with a java.awt.GraphicsConfiguration
     * @param gc GraphicsConfiguration for target screen
     */
    public DisplayScreen(GraphicsConfiguration gc) {
        this(gc.getBounds(), Toolkit.getDefaultToolkit().getScreenInsets(gc), gc.getDevice());
    }

    // Private constructor used by public constructor
    private DisplayScreen(Rectangle gcBounds, Insets insets, GraphicsDevice gd) {
        this.nativeBounds = gcBounds;

        System.out.print(gd + ", Native bounds: " + nativeBounds);
        System.out.print(", Insets: " + insets.toString());

        effectiveBounds = new Rectangle(
                gcBounds.x + insets.left,
                gcBounds.y + insets.top,
                gcBounds.width - (insets.left + insets.right),
                gcBounds.height - (insets.top + insets.bottom)
        );

        System.out.println(", Effective: " + effectiveBounds);
    }

    /**
     * Returns the amount of overlapping area of a window on this DisplayScreen.
     * @param windowRectangle A window represented as a java.awt.Rectangle
     * @return The overlapping area
     */
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

    /**
     * @return This display's native bounds as a java.awt.Rectangle
     */
    public Rectangle getNativeBounds() {
        return nativeBounds;
    }

    /** 
     * Returns the display's effective bounds. The effective bounds is typically equivalent to the native bounds, 
     * plus a buffer added by JNA on each side of the window (usually 8px), minus any insets that may change the
     * effective screen size (e.g. taskbar) 
     * @return This display's effective bounds as a java.awt.Rectangle. 
     */
    public Rectangle getEffectiveBounds() {
        return effectiveBounds;
    }
}
