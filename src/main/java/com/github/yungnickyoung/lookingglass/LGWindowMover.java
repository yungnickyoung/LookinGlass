package com.github.yungnickyoung.lookingglass;

import java.awt.Rectangle;

/**
 * Static class for moving windows on a system. This class is used by the
 * LGWindowManager.
 */
final class LGWindowMover {
    private LGWindowMover() {}

    /**
     * Moves a window to the left side of a display. The exact position of the window
     * depends on its {@code LGWindow} object's state history.
     * @param window LGWindow object of the window in question
     * @param effectiveDisplayBounds The effective bounds (as a {@code java.awt.Rectangle} of the display device
     * the window should be moved to. This is typically the primary display.
     * <br /><br />
     * The primary display can be found by using 
     * <pre>{@codeDisplayScreen DisplayManager.findPrimaryDisplayForWindow(LGWindow window); }</pre>
     * The effective bounds of any display can then be retrieved using the display's {@code getEffectiveBounds() }
     * method.
     */
    static void moveWindowLeft(LGWindow window, Rectangle effectiveDisplayBounds) {
        LGState mostRecentState = window.getMostRecentState();
        Rectangle newWindowBounds;

        if (mostRecentState == LGState.VERTICAL_THIRD_LEFT) {
            window.setNewState(LGState.VERTICAL_THIRD_MIDDLE);
            newWindowBounds = new Rectangle (
                (effectiveDisplayBounds.width / 3) + effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                (effectiveDisplayBounds.width / 3) + 14, effectiveDisplayBounds.height + 7
            );
        } else if (mostRecentState == LGState.VERTICAL_HALF_LEFT) {
            window.setNewState(LGState.VERTICAL_THIRD_LEFT);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                (effectiveDisplayBounds.width / 3) + 14, effectiveDisplayBounds.height + 7
            );
        } else {
            window.setNewState(LGState.VERTICAL_HALF_LEFT);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                (effectiveDisplayBounds.width / 2) + 14, effectiveDisplayBounds.height + 7
            );
        }

        window.moveWindow(newWindowBounds);
    }


    /**
     * Moves a window to the right side of a display. The exact position of the window
     * depends on its {@code LGWindow} object's state history.
     * @param window LGWindow object of the window in question
     * @param effectiveDisplayBounds The effective bounds (as a {@code java.awt.Rectangle} of the display device
     * the window should be moved to. This is typically the primary display.
     * <br /><br />
     * The primary display can be found by using 
     * <pre>{@codeDisplayScreen DisplayManager.findPrimaryDisplayForWindow(LGWindow window); }</pre>
     * The effective bounds of any display can then be retrieved using the display's {@code getEffectiveBounds() }
     * method.
     */
    static void moveWindowRight(LGWindow window, Rectangle effectiveDisplayBounds) {
        LGState mostRecentState = window.getMostRecentState();
        Rectangle newWindowBounds;

        if (mostRecentState == LGState.VERTICAL_THIRD_RIGHT) {
            window.setNewState(LGState.VERTICAL_THIRD_MIDDLE);
            newWindowBounds = new Rectangle (
                (effectiveDisplayBounds.width / 3) + effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                (effectiveDisplayBounds.width / 3) + 14, effectiveDisplayBounds.height + 7
            );
        } else if (mostRecentState == LGState.VERTICAL_HALF_RIGHT) {
            window.setNewState(LGState.VERTICAL_THIRD_RIGHT);
            newWindowBounds = new Rectangle (
                (2 * effectiveDisplayBounds.width / 3) + effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                (effectiveDisplayBounds.width / 3) + 15, effectiveDisplayBounds.height + 7
            );
        } else {
            window.setNewState(LGState.VERTICAL_HALF_RIGHT);
            newWindowBounds = new Rectangle (
                (effectiveDisplayBounds.width / 2) + effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                (effectiveDisplayBounds.width / 2) + 14, effectiveDisplayBounds.height + 7
            );
        }

        window.moveWindow(newWindowBounds);
    }


    /**
     * Moves a window to the top side of a display. The exact position of the window
     * depends on its {@code LGWindow} object's state history.
     * @param window LGWindow object of the window in question
     * @param effectiveDisplayBounds The effective bounds (as a {@code java.awt.Rectangle} of the display device
     * the window should be moved to. This is typically the primary display.
     * <br /><br />
     * The primary display can be found by using 
     * <pre>{@codeDisplayScreen DisplayManager.findPrimaryDisplayForWindow(LGWindow window); }</pre>
     * The effective bounds of any display can then be retrieved using the display's {@code getEffectiveBounds() }
     * method.
     */
    static void moveWindowUp(LGWindow window, Rectangle effectiveDisplayBounds) {
        LGState mostRecentState = window.getMostRecentState();
        Rectangle newWindowBounds;

        int height = effectiveDisplayBounds.height + 21;
        if (mostRecentState == LGState.HORIZONTAL_THIRD_TOP) {
            window.setNewState(LGState.HORIZONTAL_THIRD_MIDDLE);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, (height / 3) + effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, height / 3 + 8
            );
        } else if (mostRecentState == LGState.HORIZONTAL_HALF_TOP) {
            window.setNewState(LGState.HORIZONTAL_THIRD_TOP);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, height / 3 + 8
            );
        } else {
            window.setNewState(LGState.HORIZONTAL_HALF_TOP);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, height / 2 + 8
            );
        }

        window.moveWindow(newWindowBounds);
    }


    /**
     * Moves a window to the bottom side of a display. The exact position of the window
     * depends on its {@code LGWindow} object's state history.
     * @param window LGWindow object of the window in question
     * @param effectiveDisplayBounds The effective bounds (as a {@code java.awt.Rectangle} of the display device
     * the window should be moved to. This is typically the primary display.
     * <br /><br />
     * The primary display can be found by using 
     * <pre>{@codeDisplayScreen DisplayManager.findPrimaryDisplayForWindow(LGWindow window); }</pre>
     * The effective bounds of any display can then be retrieved using the display's {@code getEffectiveBounds() }
     * method.
     */
    static void moveWindowDown(LGWindow window, Rectangle effectiveDisplayBounds) {
        LGState mostRecentState = window.getMostRecentState();
        Rectangle newWindowBounds;

        int height = effectiveDisplayBounds.height + 21;
        if (mostRecentState == LGState.HORIZONTAL_THIRD_BOTTOM) {
            window.setNewState(LGState.HORIZONTAL_THIRD_MIDDLE);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, (height / 3) + effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, height / 3 + 8
            );
        } else if (mostRecentState == LGState.HORIZONTAL_HALF_BOTTOM) {
            window.setNewState(LGState.HORIZONTAL_THIRD_BOTTOM);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, (2 * height / 3) + effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, height / 3
            );
        } else {
            window.setNewState(LGState.HORIZONTAL_HALF_BOTTOM);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, (height / 2) + effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, height / 2
            );
        }

        window.moveWindow(newWindowBounds);
    }


    /**
     * Moves a window to the center of a display.
     * @param window LGWindow object of the window in question
     * @param effectiveDisplayBounds The effective bounds (as a {@code java.awt.Rectangle} of the display device
     * the window should be moved to. This is typically the primary display.
     * <br /><br />
     * The primary display can be found by using 
     * <pre>{@codeDisplayScreen DisplayManager.findPrimaryDisplayForWindow(LGWindow window); }</pre>
     * The effective bounds of any display can then be retrieved using the display's {@code getEffectiveBounds() }
     * method.
     */
    static void moveWindowCenter(LGWindow window, Rectangle effectiveDisplayBounds) {
        Rectangle newWindowBounds;

        newWindowBounds = new Rectangle(
            (effectiveDisplayBounds.width / 5) + effectiveDisplayBounds.x, (effectiveDisplayBounds.height / 4) + effectiveDisplayBounds.y,
            3 * effectiveDisplayBounds.width / 5, effectiveDisplayBounds.height / 2
        );

        window.setNewState(LGState.CENTER);
        window.moveWindow(newWindowBounds);
    }

}