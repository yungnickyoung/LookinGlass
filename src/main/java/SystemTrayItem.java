import jdk.nashorn.internal.objects.Global;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

/*
 * SystemTrayItem.java
 */

public class SystemTrayItem {
    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        //Schedule a job for the event-dispatching thread:
        //adding TrayIcon.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

        //Check the SystemTray support
    private static void createAndShowGUI() {
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon =
                new TrayIcon(createImage("bulb.gif", "tray icon"));
        final SystemTray tray = SystemTray.getSystemTray();

        // Create a popup menu components
        MenuItem aboutItem = new MenuItem("About");
        CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
        CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
        Menu displayMenu = new Menu("Display");
        MenuItem errorItem = new MenuItem("Error");
        MenuItem warningItem = new MenuItem("Warning");
        MenuItem infoItem = new MenuItem("Info");
        MenuItem noneItem = new MenuItem("None");
        MenuItem exitItem = new MenuItem("Exit");
        CheckboxMenuItem toggle_listen = new CheckboxMenuItem("Listen for hotkey combinations");

        //Add components to popup menu
        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(cb1);
        popup.add(cb2);
        popup.addSeparator();
        popup.add(displayMenu);
        displayMenu.add(errorItem);
        displayMenu.add(warningItem);
        displayMenu.add(infoItem);
        displayMenu.add(noneItem);
        popup.add(exitItem);
        popup.addSeparator();
        popup.add(toggle_listen);

        trayIcon.setPopupMenu(popup);

        GlobalKeyListener keyListener = new GlobalKeyListener();

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }

        trayIcon.addActionListener((e) -> {
            JOptionPane.showMessageDialog(null,
                    "This dialog box is run from System Tray");
        });

        aboutItem.addActionListener((e) -> {
            JOptionPane.showMessageDialog(null,
                    "This dialog box is run from the About menu item");
        });

        cb1.addItemListener((e) -> {
            int cb1Id = e.getStateChange();
            if (cb1Id == ItemEvent.SELECTED){
                trayIcon.setImageAutoSize(true);
            } else {
                trayIcon.setImageAutoSize(false);
            }
        });

        cb2.addItemListener((e) -> {
            int cb2Id = e.getStateChange();
            if (cb2Id == ItemEvent.SELECTED){
                trayIcon.setToolTip("Sun TrayIcon");
            } else {
                trayIcon.setToolTip(null);
            }
        });

        toggle_listen.addItemListener((e) -> {
            int status = e.getStateChange();
            if (status == ItemEvent.SELECTED) {
                // listen for hot keys and show display box when they are pressed

                // Attempt to enable hot key listener
                try {
                    GlobalScreen.registerNativeHook();
                }
                catch (NativeHookException ex) {
                    System.err.println("There was a problem registering the native hook.");
                    System.err.println(ex.getMessage());
                    System.exit(1);
                }

                if (GlobalScreen.isNativeHookRegistered()) {
                    GlobalScreen.addNativeKeyListener(keyListener);
                } else {
                    trayIcon.displayMessage("Encountered an Error!",
                            "Unable to enable global hot key listener", TrayIcon.MessageType.ERROR);
                }
            } else {
                // Attempt to enable hot key listener
                try {
                    GlobalScreen.unregisterNativeHook();
                }
                catch (NativeHookException ex) {
                    System.err.println("There was a problem registering the native hook.");
                    System.err.println(ex.getMessage());
                    System.exit(1);
                }
                GlobalScreen.removeNativeKeyListener(keyListener);
            }
        });

        ActionListener listener = ((e) -> {
            MenuItem item = (MenuItem)e.getSource();
            //TrayIcon.MessageType type = null;
            System.out.println(item.getLabel());
            if ("Error".equals(item.getLabel())) {
                //type = TrayIcon.MessageType.ERROR;
                trayIcon.displayMessage("Sun TrayIcon Demo",
                        "This is an error message", TrayIcon.MessageType.ERROR);

            } else if ("Warning".equals(item.getLabel())) {
                //type = TrayIcon.MessageType.WARNING;
                trayIcon.displayMessage("Sun TrayIcon Demo",
                        "This is a warning message", TrayIcon.MessageType.WARNING);

            } else if ("Info".equals(item.getLabel())) {
                //type = TrayIcon.MessageType.INFO;
                trayIcon.displayMessage("Sun TrayIcon Demo",
                        "This is an info message", TrayIcon.MessageType.INFO);

            } else if ("None".equals(item.getLabel())) {
                //type = TrayIcon.MessageType.NONE;
                trayIcon.displayMessage("Sun TrayIcon Demo",
                        "This is an ordinary message", TrayIcon.MessageType.NONE);
            }
        });

        errorItem.addActionListener(listener);
        warningItem.addActionListener(listener);
        infoItem.addActionListener(listener);
        noneItem.addActionListener(listener);

        exitItem.addActionListener((e) -> {
            tray.remove(trayIcon);
            System.exit(0);
        });
    }

    //Obtain the image URL
    protected static Image createImage(String path, String description) {
        URL imageURL = SystemTrayItem.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
}
