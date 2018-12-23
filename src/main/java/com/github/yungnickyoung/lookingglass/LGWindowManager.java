package com.github.yungnickyoung.lookingglass;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.ptr.IntByReference;

import java.awt.*;
import java.util.Hashtable;

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
     * Moves the currently active window to a new position on its primary display,
     * as indicated by the provided command string. Does nothing if unable to locate the primary display 
     * for the active window (this shouldn't happen).
     * @param command Case-insensitive string indicating the position to set the window to. Acceptable commands are the following:
     * <br /><br />
     * {@code"LEFT"}, {@code"RIGHT"}, {@code"UP"}, {@code"DOWN"}, {@code"CENTER"}
     */
    public void repositionActiveWindow(String command) {
        // Get active window's PID
        HWND hwnd = User32.INSTANCE.GetForegroundWindow();
        Integer PID = getWindowPID(hwnd);

        // Get active window's LGWindow object, or create it if it does not yet exist
        LGWindow activeWindow = windowTable.get(PID);
        if (activeWindow == null) {
            activeWindow = new LGWindow(hwnd);
            windowTable.put(PID, activeWindow);
        }

        // Update window's data in case the window has been rearranged via methods outside of LG (e.g. mouse)
        activeWindow.updateWindowPosition();

        // Find the window's primary display (based on the window's and display's common area of overlap)
        DisplayScreen primaryDisplay = DisplayManager.findPrimaryDisplayForWindow(activeWindow);
        if (primaryDisplay == null) {
            System.err.println("ERROR: Unable to locate primary display for window!");
            return; 
        }

        Rectangle effectiveDisplayBounds = primaryDisplay.getEffectiveBounds();

        // Move window according to command provided
        command = command.toUpperCase();
        if (command.equals("LEFT"))
            LGWindowMover.moveWindowLeft(activeWindow, effectiveDisplayBounds);
        else if (command.equals("RIGHT"))
            LGWindowMover.moveWindowRight(activeWindow, effectiveDisplayBounds);
        else if (command.equals("UP"))
            LGWindowMover.moveWindowUp(activeWindow, effectiveDisplayBounds);
        else if (command.equals("DOWN"))
            LGWindowMover.moveWindowDown(activeWindow, effectiveDisplayBounds);
        else if (command.equals("CENTER"))
            LGWindowMover.moveWindowCenter(activeWindow, effectiveDisplayBounds);
        else if (command.equals("MAXIMIZE"))
            LGWindowMover.maximizeWindow(activeWindow);
        else if (command.equals("MINIMIZE"))
            LGWindowMover.minimizeWindow(activeWindow);

///DEBUG
System.out.println(activeWindow);
    }

    /*
     * TODO - Moves the active window to the next screen
     */
    public void setActiveWindowNextMonitor() {
//        previousPos.add("Monitor");
    }
}