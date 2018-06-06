package com.gorczynskimike.sudoku.swinggui;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * Class that creates borders for the application.
 */
public class AppBorderFactory {

    /**
     * This class shouldn't be instantiated.
     */
    private AppBorderFactory() {
        //do nothing
    }

    /**
     * It creates titled border with extra space on the inside.
     * @param title Title of the border.
     * @param spaceSize Size of empty space inside of the border.
     * @return Created border.
     */
    public static Border getInnerIndentBorder(String title, int spaceSize) {
        if(spaceSize < 0) {
            throw new IllegalArgumentException("Space size cannot be negative");
        }
        Border inside = BorderFactory.createEmptyBorder(spaceSize, spaceSize, spaceSize, spaceSize);
        Border outside = BorderFactory.createTitledBorder(title);
        return BorderFactory.createCompoundBorder(outside, inside);
    }

    /**
     * It creates titled border with extra space on the outside.
     * @param title Title of the border.
     * @return Created border.
     */
    public static Border getStandardBorder(String title) {
        Border inside = BorderFactory.createTitledBorder(title);
        Border outside = BorderFactory.createEmptyBorder(15,15,15,15);
        return BorderFactory.createCompoundBorder(outside, inside);
    }

    /**
     * It creates titled border with extra empty space on the inside and on the outside.
     * @param title Title of the border.
     * @return Created border.
     */
    public static Border getStandardBorderExtraInnerIndent(String title) {
        Border inside = BorderFactory.createEmptyBorder(15,15,15,15);
        Border outside = getStandardBorder(title);
        return BorderFactory.createCompoundBorder(outside, inside);
    }
}