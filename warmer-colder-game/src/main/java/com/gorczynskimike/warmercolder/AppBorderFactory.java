package com.gorczynskimike.warmercolder;

import javax.swing.*;
import javax.swing.border.Border;

public class AppBorderFactory {

    public static Border getStandardBorder(String title) {
        Border inside = BorderFactory.createTitledBorder(title);
        Border outside = BorderFactory.createEmptyBorder(15,15,15,15);
        return BorderFactory.createCompoundBorder(outside, inside);
    }

}
