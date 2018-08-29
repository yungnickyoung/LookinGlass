import com.google.common.collect.EvictingQueue;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.platform.win32.WinUser;

import java.awt.*;



public class EnumerateWindows {

    private static EnumerateWindows instance = null;
    private String previousPos; //TODO - maintain a previousPos for each window!! or, keep a hashtable of (window name, previous pos)
    private EnumerateWindows() {
        previousPos = "";
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
        return new int[] {bounds.width, bounds.height};
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
     * TODO - fix height to account for start bar. Figure out proper calculation of width
     */
    public void setActiveWindowLeft() {
        int[] res = getEffectiveResolutionSingleMonitor();
        int resW = res[0];
        int resH = res[1];
        int width;
        int height = resH;

        // Determine new position based on previous state
        if (previousPos.equals("VT1")) {
            width = resW/3;
            setActiveWindowPos(resW/3, 0, width, height);
            previousPos = "VT2";
        } else if (previousPos.equals("VH1")) {
            width = resW/3;
            setActiveWindowPos(0, 0, width, height);
            previousPos = "VT1";
        } else {
            width = resW/2;
            setActiveWindowPos(0, 0, width, height);
            previousPos = "VH1";
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

        // Determine new position based on previous state
        if (previousPos.equals("VT3")) {
            width = resW/3;
            setActiveWindowPos(resW/3, 0, width, height);
            previousPos = "VT2";
        } else if (previousPos.equals("VH2")) {
            width = resW/3;
            setActiveWindowPos(2*resW/3, 0, width, height);
            previousPos = "VT3";
        } else {
            width = resW/2;
            setActiveWindowPos(resW/2, 0, width, height);
            previousPos = "VH2";
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

        // Determine new position based on previous state
        if (previousPos.equals("HT1")) {
            height = resH/3;
            setActiveWindowPos(0, resH/3, width, height);
            previousPos = "HT2";
        } else if (previousPos.equals("HH1")) {
            height = resH/3;
            setActiveWindowPos(0, 0, width, height);
            previousPos = "HT1";
        } else {
            height = resH/2;
            setActiveWindowPos(0, 0, width, height);
            previousPos = "HH1";
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

        // Determine new position based on previous state
        if (previousPos.equals("HT3")) {
            height = resH/3;
            setActiveWindowPos(0, resH/3, width, height);
            previousPos = "HT2";
        } else if (previousPos.equals("HH2")) {
            height = resH/3;
            setActiveWindowPos(0, 2*resH/3, width, height);
            previousPos = "HT3";
        } else {
            height = resH/2;
            setActiveWindowPos(0, resH/2, width, height);
            previousPos = "HH2";
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

        setActiveWindowPos(resW/4, resH/4, width, height);
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

}