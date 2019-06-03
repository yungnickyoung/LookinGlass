![alt text](https://raw.githubusercontent.com/yungnickyoung/Looking-Glass/master/src/main/resources/com/github/yungnickyoung/LookingGlass/logo.png "LookinGlass")

LookinGlass is software designed to make it easy to manage your windows on the Windows OS.
LookinGlass provides the user several hotkeys that allow for easy window resizing. It is inspired by and similar to the [Spectacle](https://github.com/eczarny/spectacle) app for Mac OS X.

LookinGlass is still an early work in progress.

## Planned Features
* Flexible window resizing
* Customizable hotkey support
* Multi-Monitor support
* Easy installation for Windows users
* Remote window management
  * e.g. being able to manage Window A while Window B is in focus
* Save window layouts and re-use them later (with option to open any programs not already open in saved layout)

## Known bugs
* When the windows are arranged in any arrangement where the left window has width 2/3 of display and right window has width 1/3 of display, if the display's width % 3 == 2, there is a one-pixel gap between the windows.
* When user attempts hotkey when software is first run (and there is no active window), a white Java window opens.
* There seems to be some weird bugs when handling windows with transparency setting. For example, the window goes to the right and then locks to the left when trying to use hotkeys to move it to the right.
  * e.g. cmder
* ~~If task bar is anchored to the left of the display, it is correctly reflected in Insets and thus the resulting window size, but the window is not given the proper x-coordinate offset.~~
* Window heights are not correct for eliminating pixel margins in any ratio display.

## Developer's TODO
* ~~Consider disabling (Windows Key + Arrow Key) window movement built into Windows, and using Windows key as functional key instead~~
* ~~Fix comment errors in SystemTrayItem~~
* ~~Change patterns of window arrangement (e.g. alt+shift+left should yield left half -> left third -> two thirds, etc.~~
* Add support for ~~quarter~~, sixth, and ninth window arrangements.
* Disable keyboard from typing while command for resizing is being used
* Handle all resolutions properly, including odd ones like 1368x768 (~16.008:9), 16:10, etc
* Switch from AWT to more contemporary design (maybe Swing?)
* Adjust math for current operations for different resolutions
* Incorporate thorough unit testing
* Continuous Integration
