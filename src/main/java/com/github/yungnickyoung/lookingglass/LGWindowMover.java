package com.github.yungnickyoung.lookingglass;

import java.awt.Rectangle;

/**
 * Class for moving windows on a system. This class is used by the
 * LGWindowManager.
 */
final class LGWindowMover {
    private LGWindowMover() {}

    // static void moveWindow(LGWindow activeWindow, String position, Rectangle displayBounds) {
    //     if (position.equals("left")) {
    //         moveWindowLeft(activeWindow, displayBounds);
    //     } else if (position.equals("right")) {
    //         moveWindowRight(activeWindow, displayBounds);
    //     }

    // }

    static void moveWindowLeft(LGWindow activeWindow, Rectangle displayBounds) {
        LGState mostRecentState = activeWindow.getMostRecentState();
        Rectangle newWindowBounds;

        if (mostRecentState == LGState.VERTICAL_THIRD_LEFT) {
            activeWindow.setNewState(LGState.VERTICAL_THIRD_MIDDLE);
            newWindowBounds = new Rectangle(
                (displayBounds.width / 3) + displayBounds.x - 7, displayBounds.y, 
                (displayBounds.width / 3) + 14, displayBounds.height + 7);
        } else if (mostRecentState == LGState.VERTICAL_HALF_LEFT) {
            activeWindow.setNewState(LGState.VERTICAL_THIRD_LEFT);
            newWindowBounds = new Rectangle(
                displayBounds.x - 7, displayBounds.y, 
                (displayBounds.width / 3) + 14, displayBounds.height + 7);
        } else {
            activeWindow.setNewState(LGState.VERTICAL_HALF_LEFT);
            newWindowBounds = new Rectangle(
                displayBounds.x - 7, displayBounds.y, 
                (displayBounds.width / 2) + 14, displayBounds.height + 7);
        }

        activeWindow.moveWindow(newWindowBounds);
    }

    static void moveWindowRight(LGWindow activeWindow, Rectangle displayBounds) {
        LGState mostRecentState = activeWindow.getMostRecentState();
        Rectangle newWindowBounds;

        if (mostRecentState == LGState.VERTICAL_THIRD_RIGHT) {
            activeWindow.setNewState(LGState.VERTICAL_THIRD_MIDDLE);
            newWindowBounds = new Rectangle(
                (displayBounds.width / 3) + displayBounds.x - 7, displayBounds.y, 
                (displayBounds.width / 3) + 14, displayBounds.height + 7);
        } else if (mostRecentState == LGState.VERTICAL_HALF_RIGHT) {
            activeWindow.setNewState(LGState.VERTICAL_THIRD_RIGHT);
            newWindowBounds = new Rectangle(
                (2 * displayBounds.width / 3) + displayBounds.x - 7, displayBounds.y, 
                (displayBounds.width / 3) + 15, displayBounds.height + 7);
        } else {
            activeWindow.setNewState(LGState.VERTICAL_HALF_RIGHT);
            newWindowBounds = new Rectangle(
                (displayBounds.width / 2) + displayBounds.x - 7, displayBounds.y, 
                (displayBounds.width / 2) + 14, displayBounds.height + 7);
        }

        activeWindow.moveWindow(newWindowBounds);
    }

}