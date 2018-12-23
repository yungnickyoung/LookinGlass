package com.github.yungnickyoung.lookingglass;

import java.awt.Rectangle;

/**
 * Class for moving windows on a system. This class is used by the
 * LGWindowManager.
 */
final class LGWindowMover {
    private LGWindowMover() {}

    static void moveWindowLeft(LGWindow activeWindow, Rectangle effectiveDisplayBounds) {
        LGState mostRecentState = activeWindow.getMostRecentState();
        Rectangle newWindowBounds;

        if (mostRecentState == LGState.VERTICAL_THIRD_LEFT) {
            activeWindow.setNewState(LGState.VERTICAL_THIRD_MIDDLE);
            newWindowBounds = new Rectangle (
                (effectiveDisplayBounds.width / 3) + effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                (effectiveDisplayBounds.width / 3) + 14, effectiveDisplayBounds.height + 7
            );
        } else if (mostRecentState == LGState.VERTICAL_HALF_LEFT) {
            activeWindow.setNewState(LGState.VERTICAL_THIRD_LEFT);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                (effectiveDisplayBounds.width / 3) + 14, effectiveDisplayBounds.height + 7
            );
        } else {
            activeWindow.setNewState(LGState.VERTICAL_HALF_LEFT);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                (effectiveDisplayBounds.width / 2) + 14, effectiveDisplayBounds.height + 7
            );
        }

        activeWindow.moveWindow(newWindowBounds);
    }

    static void moveWindowRight(LGWindow activeWindow, Rectangle effectiveDisplayBounds) {
        LGState mostRecentState = activeWindow.getMostRecentState();
        Rectangle newWindowBounds;

        if (mostRecentState == LGState.VERTICAL_THIRD_RIGHT) {
            activeWindow.setNewState(LGState.VERTICAL_THIRD_MIDDLE);
            newWindowBounds = new Rectangle (
                (effectiveDisplayBounds.width / 3) + effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                (effectiveDisplayBounds.width / 3) + 14, effectiveDisplayBounds.height + 7
            );
        } else if (mostRecentState == LGState.VERTICAL_HALF_RIGHT) {
            activeWindow.setNewState(LGState.VERTICAL_THIRD_RIGHT);
            newWindowBounds = new Rectangle (
                (2 * effectiveDisplayBounds.width / 3) + effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                (effectiveDisplayBounds.width / 3) + 15, effectiveDisplayBounds.height + 7
            );
        } else {
            activeWindow.setNewState(LGState.VERTICAL_HALF_RIGHT);
            newWindowBounds = new Rectangle (
                (effectiveDisplayBounds.width / 2) + effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                (effectiveDisplayBounds.width / 2) + 14, effectiveDisplayBounds.height + 7
            );
        }

        activeWindow.moveWindow(newWindowBounds);
    }

    // WEFWEFWEF
    static void moveWindowUp(LGWindow activeWindow, Rectangle effectiveDisplayBounds) {
        LGState mostRecentState = activeWindow.getMostRecentState();
        Rectangle newWindowBounds;

        int height = effectiveDisplayBounds.height + 21;
        if (mostRecentState == LGState.HORIZONTAL_THIRD_TOP) {
            activeWindow.setNewState(LGState.HORIZONTAL_THIRD_MIDDLE);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, (height / 3) + effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, height / 3 + 8
            );
        } else if (mostRecentState == LGState.HORIZONTAL_HALF_TOP) {
            activeWindow.setNewState(LGState.HORIZONTAL_THIRD_TOP);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, height / 3 + 8
            );
        } else {
            activeWindow.setNewState(LGState.HORIZONTAL_HALF_TOP);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, height / 2 + 8
            );
        }

        activeWindow.moveWindow(newWindowBounds);
    }

    static void moveWindowDown(LGWindow activeWindow, Rectangle effectiveDisplayBounds) {
        LGState mostRecentState = activeWindow.getMostRecentState();
        Rectangle newWindowBounds;

        int height = effectiveDisplayBounds.height + 21;
        if (mostRecentState == LGState.HORIZONTAL_THIRD_BOTTOM) {
            activeWindow.setNewState(LGState.HORIZONTAL_THIRD_MIDDLE);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, (height / 3) + effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, height / 3 + 8
            );
        } else if (mostRecentState == LGState.HORIZONTAL_HALF_BOTTOM) {
            activeWindow.setNewState(LGState.HORIZONTAL_THIRD_BOTTOM);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, (2 * height / 3) + effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, height / 3
            );
        } else {
            activeWindow.setNewState(LGState.HORIZONTAL_HALF_BOTTOM);
            newWindowBounds = new Rectangle (
                effectiveDisplayBounds.x - 7, (height / 2) + effectiveDisplayBounds.y, 
                effectiveDisplayBounds.width + 14, height / 2
            );
        }

        activeWindow.moveWindow(newWindowBounds);
    }

    static void moveWindowCenter(LGWindow activeWindow, Rectangle effectiveDisplayBounds) {
        Rectangle newWindowBounds;

        newWindowBounds = new Rectangle(
            (effectiveDisplayBounds.width / 5) + effectiveDisplayBounds.x, (effectiveDisplayBounds.height / 4) + effectiveDisplayBounds.y,
            3 * effectiveDisplayBounds.width / 5, effectiveDisplayBounds.height / 2
        );

        activeWindow.setNewState(LGState.CENTER);
        activeWindow.moveWindow(newWindowBounds);
    }

}