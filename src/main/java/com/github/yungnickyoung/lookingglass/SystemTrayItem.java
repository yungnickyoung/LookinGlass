package com.github.yungnickyoung.lookingglass;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.event.*;
import java.net.URL;

/**
 * Entry points for Looking Glass.
 * @author Nicholas Young
 */
public class SystemTrayItem {
    private static SystemTrayItem instance = null;

    // The images used as the system tray icon and the image in the About window, respectively.
    // PNGs are acceptable. Any other image type is untested.
    // Any size is acceptable as long as it is scaled with TrayIcon.setImageAutoSize(true).
    private static final String TRAY_ICON = "test3.png";
    private static final String ABOUT_ICON = "test3_128.png";

    /**
     * @return New instance if not already instantiated; previously created
     * instance otherwise
     */
    public static SystemTrayItem getInstance() {
        if (instance == null)
            instance = new SystemTrayItem();

        return instance;
    }

    /***
     * MAIN METHOD - Responsible for starting the global hotkey listener adding the Looking Glass
     * icon to the system tray.
     * @param args N/A
     */
    public static void main(String[] args) {
        // Use an appropriate Windows-esque theme
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        // Create system tray element and run on its own thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndAddSystemTrayElement();
            }
        });
    }

        //Check the SystemTray support
    private static void createAndAddSystemTrayElement() {
        // Ensure system tray is supported
        if (!SystemTray.isSupported()) {
            System.err.println("ERROR: SYSTEM TRAY NOT SUPPORTED.");
            return;
        }

        final Image trayIconImage = createImage(TRAY_ICON, "Looking Glass");
        final Image aboutIconImage = createImage(ABOUT_ICON, "Looking Glass");

        final PopupMenu popup = new PopupMenu();
        final SystemTray tray = SystemTray.getSystemTray();
        final TrayIcon trayIcon = new TrayIcon(trayIconImage);

        trayIcon.setPopupMenu(popup);
        trayIcon.setImageAutoSize(true);

        // Create a popup menu components
        MenuItem preferencesItem = new MenuItem("Preferences");
        MenuItem aboutItem = new MenuItem("About Looking Glass");
        Menu displayMenu = new Menu("Display");
        MenuItem errorItem = new MenuItem("Error");
        MenuItem warningItem = new MenuItem("Warning");
        MenuItem infoItem = new MenuItem("Info");
        MenuItem noneItem = new MenuItem("None");
        MenuItem exitItem = new MenuItem("Exit Looking Glass");
        CheckboxMenuItem toggle_listen = new CheckboxMenuItem("Disable");

        //Add components to popup menu
        popup.add(preferencesItem); // TODO - Make preferences and customized keybinds
        popup.add(aboutItem);
        popup.addSeparator();
        // popup.add(displayMenu);
        // displayMenu.add(errorItem);
        // displayMenu.add(warningItem);
        // displayMenu.add(infoItem);
        // displayMenu.add(noneItem);
        popup.add(toggle_listen);
        popup.addSeparator();
        popup.add(exitItem);


        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }

        trayIcon.setToolTip("Looking Glass");

        trayIcon.addActionListener((e) -> {
            // TODO - Open Preferences wiundow
        });

        aboutItem.addActionListener((e) -> {
            // TODO - Replace plain message dialog with better About window
            JOptionPane.showMessageDialog(null,
                    "Looking Glass is free, Open Source window management software for Windows.\n" +
                    "For more information, see https://github.com/yungnickyoung/Looking-Glass\n\n" +
                    "Author: Nicholas Young",
                    "About Looking Glass",
                    JOptionPane.INFORMATION_MESSAGE,
                    new ImageIcon(aboutIconImage));
        });


        // Enable the global key listener
        GlobalKeyListener keyListener = new GlobalKeyListener();

        enableGlobalKeyListener(keyListener);
        if (GlobalScreen.isNativeHookRegistered()) {
            GlobalScreen.addNativeKeyListener(keyListener);
        } else {
            trayIcon.displayMessage("Encountered an Error!",
                "Unable to enable global key listener", TrayIcon.MessageType.ERROR
            );
        }

        // Event listener for toggling hotkey listening
        toggle_listen.addItemListener((e) -> {
            int status = e.getStateChange();
            if (status == ItemEvent.DESELECTED) {
                enableGlobalKeyListener(keyListener);
                if (GlobalScreen.isNativeHookRegistered()) {
                    GlobalScreen.addNativeKeyListener(keyListener);
                } else {
                    trayIcon.displayMessage("Encountered an Error!",
                        "Unable to enable global key listener", TrayIcon.MessageType.ERROR
                    );
                }
            } else if (status == ItemEvent.SELECTED) {
                // Attempt to disable hot key listener
                try {
                    GlobalScreen.unregisterNativeHook();
                }
                catch (NativeHookException ex) {
                    System.err.println("ERROR: PROBLEM UNREGISTERING NATIVE HOOK.");
                    System.err.println(ex.getMessage());
                    System.exit(1);
                }
                GlobalScreen.removeNativeKeyListener(keyListener);
            }
        });

        // ActionListener listener = ((e) -> {
        //     MenuItem item = (MenuItem)e.getSource();
        //     //TrayIcon.MessageType type = null;
        //     System.out.println(item.getLabel());
        //     if ("Error".equals(item.getLabel())) {
        //         //type = TrayIcon.MessageType.ERROR;
        //         trayIcon.displayMessage("Sun TrayIcon Demo",
        //                 "This is an error message", TrayIcon.MessageType.ERROR);

        //     } else if ("Warning".equals(item.getLabel())) {
        //         //type = TrayIcon.MessageType.WARNING;
        //         trayIcon.displayMessage("Sun TrayIcon Demo",
        //                 "This is a warning message", TrayIcon.MessageType.WARNING);

        //     } else if ("Info".equals(item.getLabel())) {
        //         //type = TrayIcon.MessageType.INFO;
        //         trayIcon.displayMessage("Sun TrayIcon Demo",
        //                 "This is an info message", TrayIcon.MessageType.INFO);

        //     } else if ("None".equals(item.getLabel())) {
        //         //type = TrayIcon.MessageType.NONE;
        //         trayIcon.displayMessage("Sun TrayIcon Demo",
        //                 "This is an ordinary message", TrayIcon.MessageType.NONE);
        //     }
        // });

        // errorItem.addActionListener(listener);
        // warningItem.addActionListener(listener);
        // infoItem.addActionListener(listener);
        // noneItem.addActionListener(listener);

        exitItem.addActionListener((e) -> {
            tray.remove(trayIcon);
            System.exit(0);
        });
    }

    //Obtain the image URL
    private static Image createImage(String path, String description) {
        URL imageURL = SystemTrayItem.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }

    private static void enableGlobalKeyListener(GlobalKeyListener keyListener) {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("ERROR: PROBLEM REGISTERING NATIVE HOOK.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }
}
