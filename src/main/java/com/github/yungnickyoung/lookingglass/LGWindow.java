package com.github.yungnickyoung.lookingglass;

import java.awt.Rectangle;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.ptr.IntByReference;

/**
 * Representation of a Windows OS process window used by Looking Glass. Allows
 * easy storage/retrieval of commonly used information for a window including 
 * title, PID, thread ID, Rectangle (size/position).
 */
public class LGWindow {
    private Integer PID;
    private Integer thread;
    private String title;
    private Rectangle rectangle;
    private HWND hwnd; // This window's handle
    private KickOutQueue<LGState> stateHistory; // Store last five states of window

    /**
     * Creates a new LGWindow object for a given window handle
     * @param hwnd existing handle to a window 
     */
    public LGWindow(HWND hwnd) {
        this.hwnd = hwnd;

        // Store PID and thread
        IntByReference rPID = new IntByReference();
        thread = User32.INSTANCE.GetWindowThreadProcessId(hwnd, rPID);
        PID = rPID.getValue();

        // Store window title
        char[] buffer = new char[2048];
        User32.INSTANCE.GetWindowText(hwnd, buffer, 1024);
        title = Native.toString(buffer);

        stateHistory = new KickOutQueue<>(5); // Records last five states for window
        rectangle = calculateWindowRectangle(hwnd);
    }

    /**
     * Returns this window's information as a string in the following form:
     * <pre>{@code "<title>", PID: <PID>, TID: <threadID>, POS: <position as java.awt.Rectangle>}</pre>
     */
    public String toString() {
        return "\"" + title + "\", PID: " + PID + ", TID: " + thread + ", POS:" + rectangle;
    }

    /**
     * Designate the state of this window. Stores this state in the window's state history
     * @param newState the window's new state
     */
    public void setNewState(LGState newState) {
        stateHistory.offer(newState);
    }

    /**
     * Get the state of this window at a certain position in the window's state history.
     * Index 0 indicates the oldest state in the list.
     * @param i the index of the state to retrieve
     * @return the state at position {@code i}, or {@code null} if the position is not valid
     */
    public LGState getStateAt(int i) {
        try {
            return stateHistory.peekAt(i);
        } catch(IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Get the most recent state of this window.
     * @return the most recent state of this window, or {@code null} if no states recorded
     */
    public LGState getMostRecentState() {
        return getStateAt(stateHistory.size() - 1);
    }

    /**
     * Moves and resizes this window to the values indicated by a rectangle. Specifically, both the position
     * (x/y coordinates) and the dimensions (width/height) will be set to those of the provided 
     * {@code java.awt.Rectangle}.
     * @param rect The {@code java.awt.Rectangle} containing the values for the window's new position and size
     */
    public void moveWindow(Rectangle rect) {
        rectangle = rect; // Update the window's stored position/size information
        User32.INSTANCE.ShowWindow(hwnd, WinUser.SW_SHOWNORMAL); // Get window out of minimized or maximized state
        User32.INSTANCE.SetWindowPos(hwnd, null, rect.x, rect.y, rect.width, rect.height, WinUser.SWP_NOZORDER); // actuall moves window
    }

    /**
     * Updates the window's stored position in case the user has moved it without using the built-in
     * mechanisms (e.g. by dragging it with the mouse).
     */
    public void updateWindowPosition() {
        rectangle = calculateWindowRectangle(hwnd);
    }
    
    //------------------------- GETTERS -----------------------//
    public Integer getPID() { return PID; }
    public Integer getThread() { return thread; }
    public String getTitle() { return title; }
    public Rectangle getRectangle() { return rectangle; }
    //---------------------------------------------------------//

    private Rectangle calculateWindowRectangle(HWND hwnd) {
        RECT rect = new RECT();
        User32.INSTANCE.GetWindowRect(hwnd, rect);

        return new Rectangle(rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top);
    }
}