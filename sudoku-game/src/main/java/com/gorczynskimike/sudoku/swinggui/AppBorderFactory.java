package com.gorczynskimike.sudoku.swinggui;

import javax.swing.*;
import javax.swing.border.Border;


public class AppBorderFactory {

    private AppBorderFactory() {
        //do nothing
    }

    public static Border getStandardBorder(String title) {
        Border inside = BorderFactory.createTitledBorder(title);
        Border outside = BorderFactory.createEmptyBorder(15,15,15,15);
        return BorderFactory.createCompoundBorder(outside, inside);
    }

    public static Border getStandardBorderExtraInnerIndent(String title) {
        Border inside = BorderFactory.createEmptyBorder(15,15,15,15);
        Border outside = getStandardBorder(title);
        return BorderFactory.createCompoundBorder(outside, inside);
    }

}