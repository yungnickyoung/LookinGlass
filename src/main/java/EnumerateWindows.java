import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.ptr.IntByReference;

import java.awt.*;
import java.util.Hashtable;

// TODO - Be able to control window while inactive. E.g. control window 1 while 4 is active
// TODO - hotkey to set/clear number of each window. If number is already used, either notify user or overwrite, or both

public class EnumerateWindows {

    private static final int MAX_TITLE_LENGTH = 1024;
    private static EnumerateWindows instance = null;

    // Holds pairs of <PID, state>
    // States are strings of 3 chars where the first char denotes [H]orizontal/[V]ertical,
    // the second char denotes the fraction of the screen ([H]alf, [T]hird),
    // and the third char is an int denoting the number ([1-3]).
    // For example, "VT2" translates to "Vertical Third 2", aka the middle vertical column
    private Hashtable<String, String> stateMap; //TODO - reset a window's state when it is manually moved or resized by the user. Perhaps store an object containing size/pos in the hashtable instead

    private EnumerateWindows() {
        //previousPos = "";
        stateMap = new Hashtable<>();
    }

    public static EnumerateWindows getInstance() {
        if (instance == null)
            instance = new EnumerateWindows();

        return instance;
    }

    public int[] getEffectiveResolutionSingleMonitor() {
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        int width = (int)screenSize.getWidth();
//        int height = (int)screenSize.getHeight();

        Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        return new int[]{bounds.width, bounds.height};
    }

    /*
     * Returns the coordinates of the currently active window as a RECT
     * Note - coordinates of each side can be accessed like so: rect.left, rect.top, etc
     */
    public RECT getActiveWindowCoordinates() {
        HWND hwnd = User32.INSTANCE.GetForegroundWindow();
        RECT rect = new RECT();

        User32.INSTANCE.GetWindowRect(hwnd, rect);
        return rect;
    }


    /*
     * Sets the currently active window's position to the specified top/left coords, and
     *  changes the window's size to the width/height provided
     */
    public void setActiveWindowPos(int left, int top, int width, int height) {
        HWND hwnd = User32.INSTANCE.GetForegroundWindow();
        User32.INSTANCE.SetWindowPos(hwnd, null, left, top, width, height,
                WinUser.SWP_NOZORDER);
    }

    /*
     * Moves the active window to the left half of the screen
     * TODO - fix height to account for start bar. Figure out proper calculation of width. Look into using Insets
     */
    public void setActiveWindowLeft() {
        int[] res = getEffectiveResolutionSingleMonitor();
        int resW = res[0];
        int resH = res[1];
        int width;
        int height = resH;

        String PID = this.getActiveWindowObject()[1];
        String previousPos = stateMap.getOrDefault(PID, "");

        // Determine new position based on previous state
        if (previousPos.equals("VT1")) {
            width = resW / 3;
            setActiveWindowPos(resW / 3, 0, width, height);
            stateMap.put(PID, "VT2");
        } else if (previousPos.equals("VH1")) {
            width = resW / 3;
            setActiveWindowPos(0, 0, width, height);
            stateMap.put(PID, "VT1");
        } else {
            width = resW / 2;
            setActiveWindowPos(0, 0, width, height);
            stateMap.put(PID, "VH1");
        }

//        setActiveWindowPos(-8, 0, width+14, height+8);
    }

    /*
     * Moves the active window to the right half of the screen
     */
    public void setActiveWindowRight() {
        int[] res = getEffectiveResolutionSingleMonitor();
        int resW = res[0];
        int resH = res[1];
        int width;
        int height = resH;

        String PID = this.getActiveWindowObject()[1];
        String previousPos = stateMap.getOrDefault(PID, "");

        // Determine new position based on previous state
        if (previousPos.equals("VT3")) {
            width = resW / 3;
            setActiveWindowPos(resW / 3, 0, width, height);
            stateMap.put(PID, "VT2");
        } else if (previousPos.equals("VH2")) {
            width = resW / 3;
            setActiveWindowPos(2 * resW / 3, 0, width, height);
            stateMap.put(PID, "VT3");
        } else {
            width = resW / 2;
            setActiveWindowPos(resW / 2, 0, width, height);
            stateMap.put(PID, "VH2");
        }

//        setActiveWindowPos(resW/2-8, 0, width+14, height+8);
    }

    /*
     * Moves the active window to the top half of the screen
     */
    public void setActiveWindowTop() {
        int[] res = getEffectiveResolutionSingleMonitor();
        int resW = res[0];
        int resH = res[1];
        int width = resW;
        int height;

        String PID = this.getActiveWindowObject()[1];
        String previousPos = stateMap.getOrDefault(PID, "");

        // Determine new position based on previous state
        if (previousPos.equals("HT1")) {
            height = resH / 3;
            setActiveWindowPos(0, resH / 3, width, height);
            stateMap.put(PID, "HT2");
        } else if (previousPos.equals("HH1")) {
            height = resH / 3;
            setActiveWindowPos(0, 0, width, height);
            stateMap.put(PID, "HT1");
        } else {
            height = resH / 2;
            setActiveWindowPos(0, 0, width, height);
            stateMap.put(PID, "HH1");
        }

//        setActiveWindowPos(0, 0, width, height);
    }

    /*
     * Moves the active window to the bottom half of the screen
     */
    public void setActiveWindowBottom() {
        int[] res = getEffectiveResolutionSingleMonitor();
        int resW = res[0];
        int resH = res[1];
        int width = resW;
        int height;

        String PID = this.getActiveWindowObject()[1];
        String previousPos = stateMap.getOrDefault(PID, "");

        // Determine new position based on previous state
        if (previousPos.equals("HT3")) {
            height = resH / 3;
            setActiveWindowPos(0, resH / 3, width, height);
            stateMap.put(PID, "HT2");
        } else if (previousPos.equals("HH2")) {
            height = resH / 3;
            setActiveWindowPos(0, 2 * resH / 3, width, height);
            stateMap.put(PID, "HT3");
        } else {
            height = resH / 2;
            setActiveWindowPos(0, resH / 2, width, height);
            stateMap.put(PID, "HH2");
        }

//        setActiveWindowPos(0, resH/2, width, height);
    }

    /*
     * Moves the active window to the center of the screen
     */
    public void setActiveWindowCenter() {
        int[] res = getEffectiveResolutionSingleMonitor();
        int resW = res[0];
        int resH = res[1];
        int width = resW / 2;
        int height = resH / 2;

        setActiveWindowPos(resW / 4, resH / 4, width, height);
//        previousPos.add("Center");
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

    // TODO - only return PID if that ends up being all I need
    public String[] getActiveWindowObject() {
        // Get window title
        HWND hwnd = User32.INSTANCE.GetForegroundWindow();
        char[] buffer = new char[MAX_TITLE_LENGTH * 2];
        User32.INSTANCE.GetWindowText(hwnd, buffer, MAX_TITLE_LENGTH);
        String userWindowTitle = Native.toString(buffer);

        // Get window process ID
        IntByReference rPID = new IntByReference();
        Integer thread = User32.INSTANCE.GetWindowThreadProcessId(hwnd, rPID);
        Integer PID = rPID.getValue();

        return new String[]{userWindowTitle, PID.toString(), thread.toString()};
    }
}