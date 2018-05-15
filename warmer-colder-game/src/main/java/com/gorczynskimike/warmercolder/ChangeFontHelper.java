package com.gorczynskimike.warmercolder;

import java.awt.*;

public class ChangeFontHelper {

    public static void changeFont (Component component, Font font )
    {
        component.setFont ( font );
        if ( component instanceof Container )
        {
            for ( Component child : ( ( Container ) component ).getComponents () )
            {
                changeFont ( child, font );
            }
        }
    }

}
