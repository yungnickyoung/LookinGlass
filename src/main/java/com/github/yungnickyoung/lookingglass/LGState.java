package com.github.yungnickyoung.lookingglass;

/**
 * The relative position of an LGWindow object on a display device.
 */
enum LGState{
    VERTICAL_HALF_LEFT,
    VERTICAL_HALF_RIGHT,
    VERTICAL_THIRD_LEFT,
    VERTICAL_THIRD_MIDDLE,
    VERTICAL_THIRD_RIGHT,
    HORIZONTAL_HALF_TOP,
    HORIZONTAL_HALF_BOTTOM,
    HORIZONTAL_THIRD_TOP,
    HORIZONTAL_THIRD_MIDDLE,
    HORIZONTAL_THIRD_BOTTOM,
    SIXTH_TOP_LEFT,
    SIXTH_TOP_MIDDLE,
    SIXTH_TOP_RIGHT,
    SIXTH_BOTTOM_LEFT,
    SIXTH_BOTTOM_MIDDLE,
    SIXTH_BOTTOM_RIGHT,
    NINTH_TOP_LEFT,
    NINTH_TOP_MIDDLE,
    NINTH_TOP_RIGHT,
    NINTH_MIDDLE_LEFT,
    NINTH_MIDDLE_MIDDLE,
    NINTH_MIDDLE_RIGHT,
    NINTH_BOTTOM_LEFT,
    NINTH_BOTTOM_MIDDLE,
    NINTH_BOTTOM_RIGHT,
    CENTER,
    MAXIMIZE,
    MINIMIZE,
    REPOSITIONED /* Use when a window has been repositioned by means
                    outside of Looking Glass, e.g. via mouse */
}