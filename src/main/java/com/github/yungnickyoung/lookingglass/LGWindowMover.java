package com.github.yungnickyoung.lookingglass;

import java.awt.Rectangle;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinDef.HWND;

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
     * on which the window should be positioned. This is typically the primary display.
     * <br /><br />
     * The primary display can be found by using 
     * <pre>{@codeDisplayScreen DisplayManager.findPrimaryDisplayForWindow(LGWindow window); }</pre>
     * The effective bounds of any display can then be retrieved using the display's {@code getEffectiveBounds() }
     * method.
     */
    static void moveWindowLeft(LGWindow window, Rectangle effectiveDisplayBounds) {
        LGState mostRecentState = window.getMostRecentState();
        Rectangle newWindowBounds;
        int height = effectiveDisplayBounds.height + 21;

        int modulo3 = effectiveDisplayBounds.width % 3;

        int addonLeft = (modulo3 == 0) ? 0 : 1;
        int addonMiddle = (modulo3 == 2) ? 2 : 0;

        int offsetMiddle = (modulo3 == 0) ? 0 : 1;
        int offsetRight = modulo3;

        if (mostRecentState == LGState.VERTICAL_THIRD_LEFT || 
            mostRecentState == LGState.VERTICAL_TWO_THIRDS_RIGHT) 
        {
            window.setNewState(LGState.VERTICAL_THIRD_MIDDLE);
            newWindowBounds = new Rectangle (
                (effectiveDisplayBounds.width / 3) + effectiveDisplayBounds.x - 7 + offsetMiddle, effectiveDisplayBounds.y, 
                (effectiveDisplayBounds.width / 3) + 15 + addonMiddle, effectiveDisplayBounds.height + 7);
        } 
        else if (mostRecentState == LGState.VERTICAL_THIRD_MIDDLE) {
            window.setNewState(LGState.VERTICAL_TWO_THIRDS_LEFT);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                (2 * effectiveDisplayBounds.width / 3) + 15 + addonLeft, effectiveDisplayBounds.height + 7);
        }
        else if(mostRecentState == LGState.VERTICAL_THIRD_RIGHT) 
        {
            window.setNewState(LGState.VERTICAL_TWO_THIRDS_RIGHT);
            newWindowBounds = new Rectangle (
                (effectiveDisplayBounds.width / 3) + effectiveDisplayBounds.x - 7 + offsetMiddle, effectiveDisplayBounds.y, 
                (2 * effectiveDisplayBounds.width / 3) + 14, effectiveDisplayBounds.height + 7);
        }
        else if (mostRecentState == LGState.VERTICAL_HALF_LEFT) 
        {
            window.setNewState(LGState.VERTICAL_THIRD_LEFT);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                (effectiveDisplayBounds.width / 3) + 15 + addonLeft, effectiveDisplayBounds.height + 7);
        } 
        else if (mostRecentState == LGState.FOURTH_BOTTOM_RIGHT)
        {
            window.setNewState(LGState.HORIZONTAL_HALF_BOTTOM);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, (height / 2) + effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, height / 2);
        }
        else if (mostRecentState == LGState.FOURTH_TOP_RIGHT)
        {
            window.setNewState(LGState.HORIZONTAL_HALF_TOP);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, height / 2 + 8);
        }
        else if (mostRecentState == LGState.HORIZONTAL_HALF_TOP ||
                 mostRecentState == LGState.TWO_SIXTHS_TOP_LEFT)
        {
            window.setNewState(LGState.FOURTH_TOP_LEFT);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y,
                (effectiveDisplayBounds.width / 2) + 15, height / 2 + 8);
        }
        else if (mostRecentState == LGState.HORIZONTAL_HALF_BOTTOM)
        {
            window.setNewState(LGState.FOURTH_BOTTOM_LEFT);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, (height / 2) + effectiveDisplayBounds.y,
                (effectiveDisplayBounds.width / 2) + 15, height / 2 + 8);
        }
        else if (mostRecentState == LGState.FOURTH_TOP_LEFT)
        {
            window.setNewState(LGState.SIXTH_TOP_LEFT);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                (effectiveDisplayBounds.width / 3) + 15 + addonLeft, height / 2 + 8);
        }
        else if (mostRecentState == LGState.SIXTH_TOP_LEFT ||
                 mostRecentState == LGState.TWO_SIXTHS_TOP_RIGHT)
        {
            window.setNewState(LGState.SIXTH_TOP_MIDDLE);
            newWindowBounds = new Rectangle (
                (effectiveDisplayBounds.width / 3) + effectiveDisplayBounds.x - 7 + offsetMiddle, effectiveDisplayBounds.y, 
                (effectiveDisplayBounds.width / 3) + 15 + addonMiddle, height / 2 + 8);
        }
        else if (mostRecentState == LGState.SIXTH_TOP_MIDDLE)
        {
            window.setNewState(LGState.TWO_SIXTHS_TOP_LEFT);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                (2 * effectiveDisplayBounds.width / 3) + 15 + addonLeft, height / 2 + 8);
        }
        else if (mostRecentState == LGState.SIXTH_TOP_RIGHT)
        {
            window.setNewState(LGState.TWO_SIXTHS_TOP_RIGHT);
            newWindowBounds = new Rectangle (
                (effectiveDisplayBounds.width / 3) + effectiveDisplayBounds.x - 7 + offsetMiddle, effectiveDisplayBounds.y, 
                (2 * effectiveDisplayBounds.width / 3) + 14, height / 2 + 8);
        }
        else 
        {
            window.setNewState(LGState.VERTICAL_HALF_LEFT);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                (effectiveDisplayBounds.width / 2) + 15, effectiveDisplayBounds.height + 7);
        }

        window.moveWindow(newWindowBounds);
    }


    /**
     * Moves a window to the right side of a display. The exact position of the window
     * depends on its {@code LGWindow} object's state history.
     * @param window LGWindow object of the window in question
     * @param effectiveDisplayBounds The effective bounds (as a {@code java.awt.Rectangle} of the display device
     * on which the window should be positioned. This is typically the primary display.
     * <br /><br />
     * The primary display can be found by using 
     * <pre>{@codeDisplayScreen DisplayManager.findPrimaryDisplayForWindow(LGWindow window); }</pre>
     * The effective bounds of any display can then be retrieved using the display's {@code getEffectiveBounds() }
     * method.
     */
    static void moveWindowRight(LGWindow window, Rectangle effectiveDisplayBounds) {
        LGState mostRecentState = window.getMostRecentState();
        Rectangle newWindowBounds;
        int height = effectiveDisplayBounds.height + 21;

        int modulo3 = effectiveDisplayBounds.width % 3;

        int addonLeft = (modulo3 == 0) ? 0 : 1;
        int addonMiddle = (modulo3 == 2) ? 2 : 0;

        int offsetMiddle = (modulo3 == 0) ? 0 : 1;
        int offsetRight = modulo3;

        if (mostRecentState == LGState.VERTICAL_THIRD_RIGHT ||
            mostRecentState == LGState.VERTICAL_TWO_THIRDS_LEFT) 
        {
            window.setNewState(LGState.VERTICAL_THIRD_MIDDLE);
            newWindowBounds = new Rectangle (
                (effectiveDisplayBounds.width / 3) + effectiveDisplayBounds.x - 7 + offsetMiddle, effectiveDisplayBounds.y, 
                (effectiveDisplayBounds.width / 3) + 15 + addonMiddle, effectiveDisplayBounds.height + 7);
        } 
        else if (mostRecentState == LGState.VERTICAL_THIRD_MIDDLE) {
            window.setNewState(LGState.VERTICAL_TWO_THIRDS_RIGHT);
            newWindowBounds = new Rectangle (
                (effectiveDisplayBounds.width / 3) + effectiveDisplayBounds.x - 7 + offsetMiddle, effectiveDisplayBounds.y, 
                (2 * effectiveDisplayBounds.width / 3) + 14, effectiveDisplayBounds.height + 7);
        }
        else if (mostRecentState == LGState.VERTICAL_THIRD_LEFT)
        {
            window.setNewState(LGState.VERTICAL_TWO_THIRDS_LEFT);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                (2 * effectiveDisplayBounds.width / 3) + 15 + addonLeft, effectiveDisplayBounds.height + 7);
        }
        else if (mostRecentState == LGState.VERTICAL_HALF_RIGHT) 
        {
            window.setNewState(LGState.VERTICAL_THIRD_RIGHT);
            newWindowBounds = new Rectangle (
                (2 * effectiveDisplayBounds.width / 3) - 7 + effectiveDisplayBounds.x + offsetRight, effectiveDisplayBounds.y, 
                (effectiveDisplayBounds.width / 3) + 14, effectiveDisplayBounds.height + 7);
        } 
        else if (mostRecentState == LGState.FOURTH_TOP_LEFT)
        {
            window.setNewState(LGState.HORIZONTAL_HALF_TOP);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, height / 2 + 8);
        }
        else if (mostRecentState == LGState.FOURTH_BOTTOM_LEFT)
        {
            window.setNewState(LGState.HORIZONTAL_HALF_BOTTOM);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, (height / 2) + effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, height / 2);
        }
        else if (mostRecentState == LGState.HORIZONTAL_HALF_TOP)
        {
            window.setNewState(LGState.FOURTH_TOP_RIGHT);
            newWindowBounds = new Rectangle (
                (effectiveDisplayBounds.width / 2) + effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y,
                (effectiveDisplayBounds.width / 2) + 14, height / 2 + 8);
        }
        else if (mostRecentState == LGState.HORIZONTAL_HALF_BOTTOM) 
        {
            window.setNewState(LGState.FOURTH_BOTTOM_RIGHT);
            newWindowBounds = new Rectangle (
                (effectiveDisplayBounds.width / 2) + effectiveDisplayBounds.x - 7, (height / 2) + effectiveDisplayBounds.y,
                (effectiveDisplayBounds.width / 2) + 14, height / 2 + 8);
        }
        else 
        {
            window.setNewState(LGState.VERTICAL_HALF_RIGHT);
            newWindowBounds = new Rectangle (
                (effectiveDisplayBounds.width / 2) + effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                (effectiveDisplayBounds.width / 2) + 14, effectiveDisplayBounds.height + 7);
        }

        window.moveWindow(newWindowBounds);
    }


    /**
     * Moves a window to the top side of a display. The exact position of the window
     * depends on its {@code LGWindow} object's state history.
     * @param window LGWindow object of the window in question
     * @param effectiveDisplayBounds The effective bounds (as a {@code java.awt.Rectangle} of the display device
     * on which the window should be positioned. This is typically the primary display.
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

        if (mostRecentState == LGState.HORIZONTAL_THIRD_TOP ||
            mostRecentState == LGState.HORIZONAL_TWO_THIRDS_BOTTOM)
        {
            window.setNewState(LGState.HORIZONTAL_THIRD_MIDDLE);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, (height / 3) + effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, height / 3 + 8);
        } 
        else if (mostRecentState == LGState.HORIZONTAL_THIRD_MIDDLE)
        {
            window.setNewState(LGState.HORIZONTAL_TWO_THIRDS_TOP);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, 2 * height / 3 + 8);
        }
        else if (mostRecentState == LGState.HORIZONTAL_THIRD_BOTTOM)
        {
            window.setNewState(LGState.HORIZONAL_TWO_THIRDS_BOTTOM);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, (height / 3) + effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, 2 * height / 3 + 8);
        }
        else if (mostRecentState == LGState.HORIZONTAL_HALF_TOP) 
        {
            window.setNewState(LGState.HORIZONTAL_THIRD_TOP);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, height / 3 + 8);
        } 
        else if (mostRecentState == LGState.VERTICAL_HALF_RIGHT)
        {
            window.setNewState(LGState.FOURTH_TOP_RIGHT);
            newWindowBounds = new Rectangle (
                (effectiveDisplayBounds.width / 2) + effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y,
                (effectiveDisplayBounds.width / 2) + 14, height / 2 + 8);
        }
        else if (mostRecentState == LGState.VERTICAL_HALF_LEFT)
        {
            window.setNewState(LGState.FOURTH_TOP_LEFT);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y,
                (effectiveDisplayBounds.width / 2) + 14, height / 2 + 8);
        }
        else if (mostRecentState == LGState.FOURTH_BOTTOM_RIGHT) {
            window.setNewState(LGState.VERTICAL_HALF_RIGHT);
            newWindowBounds = new Rectangle (
                (effectiveDisplayBounds.width / 2) + effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                (effectiveDisplayBounds.width / 2) + 14, effectiveDisplayBounds.height + 7);
        }
        else if (mostRecentState == LGState.FOURTH_BOTTOM_LEFT)
        {
            window.setNewState(LGState.VERTICAL_HALF_LEFT);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                (effectiveDisplayBounds.width / 2) + 14, effectiveDisplayBounds.height + 7);
        }
        else 
        {
            window.setNewState(LGState.HORIZONTAL_HALF_TOP);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, height / 2 + 8);
        }

        window.moveWindow(newWindowBounds);
    }


    /**
     * Moves a window to the bottom side of a display. The exact position of the window
     * depends on its {@code LGWindow} object's state history.
     * @param window LGWindow object of the window in question
     * @param effectiveDisplayBounds The effective bounds (as a {@code java.awt.Rectangle} of the display device
     * on which the window should be positioned. This is typically the primary display.
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
        if (mostRecentState == LGState.HORIZONTAL_THIRD_BOTTOM ||
            mostRecentState == LGState.HORIZONTAL_THIRD_TOP ||
            mostRecentState == LGState.HORIZONTAL_TWO_THIRDS_TOP) 
        {
            window.setNewState(LGState.HORIZONTAL_THIRD_MIDDLE);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, (height / 3) + effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, height / 3 + 8);
        } 
        else if (mostRecentState == LGState.HORIZONTAL_THIRD_MIDDLE)
        {
            window.setNewState(LGState.HORIZONAL_TWO_THIRDS_BOTTOM);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, (height / 3) + effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, 2 * height / 3 + 8);
        }
        else if (mostRecentState == LGState.HORIZONTAL_THIRD_TOP)
        {
            window.setNewState(LGState.HORIZONTAL_TWO_THIRDS_TOP);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, 2 * height / 3 + 8);
        }
        else if (mostRecentState == LGState.HORIZONTAL_HALF_BOTTOM) 
        {
            window.setNewState(LGState.HORIZONTAL_THIRD_BOTTOM);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, (2 * height / 3) + effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, height / 3);
        } 
        else if (mostRecentState == LGState.VERTICAL_HALF_RIGHT)
        {
            window.setNewState(LGState.FOURTH_BOTTOM_RIGHT);
            newWindowBounds = new Rectangle (
                (effectiveDisplayBounds.width / 2) + effectiveDisplayBounds.x - 7, (height / 2) + effectiveDisplayBounds.y,
                (effectiveDisplayBounds.width / 2) + 14, height / 2 + 8);
        }
        else if (mostRecentState == LGState.VERTICAL_HALF_LEFT)
        {
            window.setNewState(LGState.FOURTH_BOTTOM_LEFT);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, (height / 2) + effectiveDisplayBounds.y,
                (effectiveDisplayBounds.width / 2) + 14, height / 2 + 8);
        }
        else if (mostRecentState == LGState.FOURTH_TOP_RIGHT) {
            window.setNewState(LGState.VERTICAL_HALF_RIGHT);
            newWindowBounds = new Rectangle (
                (effectiveDisplayBounds.width / 2) + effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                (effectiveDisplayBounds.width / 2) + 14, effectiveDisplayBounds.height + 7);
        }
        else if (mostRecentState == LGState.FOURTH_TOP_LEFT)
        {
            window.setNewState(LGState.VERTICAL_HALF_LEFT);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                (effectiveDisplayBounds.width / 2) + 14, effectiveDisplayBounds.height + 7);
        }
        else 
        {
            window.setNewState(LGState.HORIZONTAL_HALF_BOTTOM);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, (height / 2) + effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, height / 2);
        }

        window.moveWindow(newWindowBounds);
    }


    /**
     * Moves a window to the center of a display.
     * @param window LGWindow object of the window in question
     * @param effectiveDisplayBounds The effective bounds (as a {@code java.awt.Rectangle} of the display device
     * on which the window should be positioned. This is typically the primary display.
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

    /**
     * Maximizes a window to its last active display.
     * @param window LGWindow object of the window in question
     */
    static void maximizeWindow(LGWindow window) {
        HWND hwnd = window.getHWND();
        User32.INSTANCE.ShowWindow(hwnd, WinUser.SW_MAXIMIZE);
        window.setNewState(LGState.MAXIMIZE);
    }

    /**
     * Minimizes a window.
     * @param window LGWindow object of the window in question
     */
    static void minimizeWindow(LGWindow window) {
        HWND hwnd = window.getHWND();
        User32.INSTANCE.ShowWindow(hwnd, WinUser.SW_MINIMIZE);
        window.setNewState(LGState.MINIMIZE);
    }

}