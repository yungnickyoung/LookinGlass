import com.sun.jna.Native;
import com.sun.jna.platform.unix.X11;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.ptr.IntByReference;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;

// TODO - Be able to control window while inactive. E.g. control window 1 while 4 is active
// TODO - hotkey to set/clear number of each window. If number is already used, either notify user or overwrite, or both

/**
 * Singleton class for repositioning the currently active window. This class was
 * designed to have its methods called upon certain keypress combinations,
 * e.g. {@code Alt + Shift + Left --> setActiveWindowLeft()}.
 * <br /><br />
 * Use {@code LGWindowManager.getInstance()} to retrieve the single instance.
 */
public class LGWindowManager {
    // Singleton instance
    private static LGWindowManager instance = null;

    // Maps Process IDs (PIDs) to LGWindow objects
    private Hashtable<Integer, LGWindow> windowTable;

    // Private constructor used by getInstance()
    private LGWindowManager() {
        windowTable = new Hashtable<>();
    }

    /**
     * @return New instance if not already instantiated; previously created
     * instance otherwise
     */
    public static LGWindowManager getInstance() {
        if (instance == null)
            instance = new LGWindowManager();

        return instance;
    }

    /**
     * Get the Process ID of a window from it's handle. This is useful for retrieving the PID
     * of a window without creating a corresponding LGWindow object.
     * @param hwnd Handle (com.sun.jna.platform.win32.WinDef.HWND) to a window
     * @return The window's Process ID (PID)
     */
    private Integer getWindowPID(HWND hwnd) {
        IntByReference rPID = new IntByReference();
        User32.INSTANCE.GetWindowThreadProcessId(hwnd, rPID);
        return rPID.getValue();
    }

    /**
     * Moves the currently active window to the next left position on its primary display. 
     * Does nothing if unable to locate the primary display for the active window 
     * (this shouldn't happen).
     */
    public void setActiveWindowLeft() {
        // Get active window's PID
        HWND hwnd = User32.INSTANCE.GetForegroundWindow();
        Integer PID = getWindowPID(hwnd);

        // Get active window's LGWindow object, or create it if it does not yet exist
        LGWindow activeWindow = windowTable.get(PID);
        if (activeWindow == null) {
            activeWindow = new LGWindow(hwnd);
            windowTable.put(PID, activeWindow);
        }

        activeWindow.updateWindowPosition();

///DEBUG
System.out.println(activeWindow);

        DisplayScreen primaryDisplay = DisplayManager.findPrimaryDisplayForWindow(activeWindow);
        if (primaryDisplay == null) {
            System.err.println("ERROR: Unable to locate primary display for window!");
            return; 
        }

        Rectangle displayBounds = primaryDisplay.getEffectiveBounds();
        Rectangle newWindowBounds;

        LGState state = activeWindow.getMostRecentState();

        if (state == LGState.VERTICAL_THIRD_LEFT) {
            newWindowBounds = new Rectangle(
                (displayBounds.width / 3) + displayBounds.x - 7,
                displayBounds.y, 
                (displayBounds.width / 3) + 14,
                displayBounds.height + 7);
            activeWindow.setNewState(LGState.VERTICAL_THIRD_MIDDLE);
        } else if (state == LGState.VERTICAL_HALF_LEFT) {
            newWindowBounds = new Rectangle(
                displayBounds.x - 7,
                displayBounds.y, 
                (displayBounds.width / 3) + 14, 
                displayBounds.height + 7);
            activeWindow.setNewState(LGState.VERTICAL_THIRD_LEFT);
        } else {
            newWindowBounds = new Rectangle(
                displayBounds.x - 7, 
                displayBounds.y, 
                (displayBounds.width / 2) + 14, 
                displayBounds.height + 7);
            activeWindow.setNewState(LGState.VERTICAL_HALF_LEFT);
        }

        activeWindow.moveWindow(newWindowBounds);
    }

    public void setActiveWindowRight() {
        // Get active window's PID
        HWND hwnd = User32.INSTANCE.GetForegroundWindow();
        Integer PID = getWindowPID(hwnd);

        // Get active window's LGWindow object, or create it if it does not yet exist
        LGWindow activeWindow = windowTable.get(PID);
        if (activeWindow == null) {
            activeWindow = new LGWindow(hwnd);
            windowTable.put(PID, activeWindow);
        }
        
        activeWindow.updateWindowPosition();

///DEBUG
System.out.println(activeWindow);

        DisplayScreen primaryDisplay = DisplayManager.findPrimaryDisplayForWindow(activeWindow);
        if (primaryDisplay == null) {
            System.err.println("ERROR: Unable to locate primary display for window!");
            return; 
        }

        Rectangle displayBounds = primaryDisplay.getEffectiveBounds();
        Rectangle newWindowBounds;

        LGState state = activeWindow.getMostRecentState();

        if (state == LGState.VERTICAL_THIRD_RIGHT) {
            newWindowBounds = new Rectangle(
                (displayBounds.width / 3) + displayBounds.x - 7, 
                displayBounds.y, 
                (displayBounds.width / 3) + 14, 
                displayBounds.height + 7);
            activeWindow.setNewState(LGState.VERTICAL_THIRD_MIDDLE);
        } else if (state == LGState.VERTICAL_HALF_RIGHT) {
            newWindowBounds = new Rectangle(
                (2 * displayBounds.width / 3) + displayBounds.x - 7, 
                displayBounds.y, 
                (displayBounds.width / 3) + 15, 
                displayBounds.height + 7);
            activeWindow.setNewState(LGState.VERTICAL_THIRD_RIGHT);
        } else {
            newWindowBounds = new Rectangle(
                (displayBounds.width / 2) + displayBounds.x - 7, 
                displayBounds.y, 
                (displayBounds.width / 2) + 14, 
                displayBounds.height + 7);
            activeWindow.setNewState(LGState.VERTICAL_HALF_RIGHT);
        }

        activeWindow.moveWindow(newWindowBounds);
    }

    /*
     * Moves the active window to the top half of the screen
     */
    public void setActiveWindowTop() {
////        int[] res = getEffectiveResolutionSingleMonitor();
//        int[] res = new int[]{3, 4};
//
//        int resW = res[0];
//        int resH = res[1];
//        int width = resW;
//        int height;
//
//        String PID = this.getActiveWindowObject()[1];
//        String previousPos = stateMap.getOrDefault(PID, "");
//
//        // Determine new position based on previous state
//        if (previousPos.equals("HT1")) {
//            height = resH / 3;
//            setActiveWindowPos(0, resH / 3, width, height);
//            stateMap.put(PID, "HT2");
//        } else if (previousPos.equals("HH1")) {
//            height = resH / 3;
//            setActiveWindowPos(0, 0, width, height);
//            stateMap.put(PID, "HT1");
//        } else {
//            height = resH / 2;
//            setActiveWindowPos(0, 0, width, height);
//            stateMap.put(PID, "HH1");
//        }
//
////        setActiveWindowPos(0, 0, width, height);
    }

    /*
     * Moves the active window to the bottom half of the screen
     */
    public void setActiveWindowBottom() {
////        int[] res = getEffectiveResolutionSingleMonitor();
//        int[] res = new int[]{3, 4};
//
//        int resW = res[0];
//        int resH = res[1];
//        int width = resW;
//        int height;
//
//        String PID = this.getActiveWindowObject()[1];
//        String previousPos = stateMap.getOrDefault(PID, "");
//
//        // Determine new position based on previous state
//        if (previousPos.equals("HT3")) {
//            height = resH / 3;
//            setActiveWindowPos(0, resH / 3, width, height);
//            stateMap.put(PID, "HT2");
//        } else if (previousPos.equals("HH2")) {
//            height = resH / 3;
//            setActiveWindowPos(0, 2 * resH / 3, width, height);
//            stateMap.put(PID, "HT3");
//        } else {
//            height = resH / 2;
//            setActiveWindowPos(0, resH / 2, width, height);
//            stateMap.put(PID, "HH2");
//        }
//
////        setActiveWindowPos(0, resH/2, width, height);
    }

    /*
     * Moves the active window to the center of the screen
     */
    public void setActiveWindowCenter() {
////        int[] res = getEffectiveResolutionSingleMonitor();
//        int[] res = new int[]{3, 4};
//
//        int resW = res[0];
//        int resH = res[1];
//        int width = resW / 2;
//        int height = resH / 2;
//
//        setActiveWindowPos(resW / 4, resH / 4, width, height);
////        previousPos.add("Center");
    }

    public void setActiveWindowMaximize() {
        HWND hwnd = User32.INSTANCE.GetForegroundWindow();
        User32.INSTANCE.ShowWindow(hwnd, WinUser.SW_MAXIMIZE);
//        previousPos.add("Maximize");
    }

    public void setActiveWindowminimize() {
        HWND hwnd = User32.INSTANCE.GetForegroundWindow();
        User32.INSTANCE.ShowWindow(hwnd, WinUser.SW_MINIMIZE);
//        previousPos.add("Minimize");
    }

    /*
     * TODO - Moves the active window to the next screen
     */
    public void setActiveWindowNextMonitor() {
//        previousPos.add("Monitor");
    }
}