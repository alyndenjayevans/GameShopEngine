/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygame;

/**
 *
 * @author lynden
 */
public class FontMaker {
    
    public static String alphaNumerical = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    public int selected = 35;
    
    public char selectedChar = '0';
    
    public String home = "/home/lynden";
    
    public FontMaker(){
    
        
    }
    
    public void clickForward(){
    
        ATMS.saveToFile(App.surfaceCollisions.get(App.fontGeom).atms, home + "/" + selectedChar + ".font.atms");
        
        App.surfaceCollisions.get(App.fontGeom).atms.clear((byte)0, (byte)0, (byte)255, (byte)255);
        App.surfaceCollisions.get(App.fontGeom).atms.update();
         
        System.out.println("Saved " + selectedChar);
        selected++;
        selectedChar = alphaNumerical.charAt(selected);
    }
    
    public void clickBackward(){
    
         selected--;
         selectedChar = alphaNumerical.charAt(selected);
         App.surfaceCollisions.get(App.fontGeom).atms = ATMS.loadFromFile(home + "/" + selectedChar + ".font.atms", App.app.getAssetManager());
         App.surfaceCollisions.get(App.fontGeom).atms.update();
         System.out.println("Loaded " + selectedChar);
    }
}
