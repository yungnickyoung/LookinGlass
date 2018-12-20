package com.github.yungnickyoung.lookingglass;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ColorModel;
import java.lang.reflect.Constructor;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DisplayScreenTests {
    // GraphicsConfiguration gc = new GraphicsConfiguration(){

    //     @Override
    //     public AffineTransform getNormalizingTransform() {
    //         return null;
    //     }
    
    //     @Override
    //     public GraphicsDevice getDevice() {
    //         return null;
    //     }
    
    //     @Override
    //     public AffineTransform getDefaultTransform() {
    //         return null;
    //     }
    
    //     @Override
    //     public ColorModel getColorModel(int transparency) {
    //         return null;
    //     }
    
    //     @Override
    //     public ColorModel getColorModel() {
    //         return null;
    //     }
    
    //     @Override
    //     public Rectangle getBounds() {
    //         return new Rectangle(new Point(15,20), new Dimension(100, 200));
    //     }
    // };

    // Rectangle rect = new Rectangle(new Point(15,20), new Dimension(100, 200));
    // Insets insets = new Insets(10, 20, 30, 40);
    // GraphicsDevice gd = null;

    // @BeforeEach
    // void initAll() {
    //     try {
    //         Constructor<DisplayScreen> cds = DisplayScreen.class.getDeclaredConstructor(Rectangle.class, Insets.class, GraphicsDevice.class);
            
    //         cds.setAccessible(true);

    //         DisplayScreen display = new DisplayScreen(rect, insets, gd);
    //     } catch (NoSuchMethodException e) {
    //         System.out.println(e.toString());
    //     }
    // }

}