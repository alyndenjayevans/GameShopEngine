/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygame;

import com.jme3.math.Vector3f;

/**
 *
 * @author lynden
 */
public class CurrencyMesh {
    
    public static final int FRONT = 0;
    public static final int BACK = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int TOP = 4;
    public static final int BOTTOM = 5;
    
      
    public static final int BOTTOMRIGHTBACK = 0;
                
    public static final int BOTTOMLEFTBACK = 1;

    public static final int BOTTOMRIGHTFRONT = 2;

    public static final int BOTTOMLEFTFRONT = 3;
  
    public static final int TOPRIGHTBACK = 4;
                
    public static final int TOPLEFTBACK = 5;

    public static final int TOPRIGHTFRONT = 6;

    public static final int TOPLEFTFRONT = 7;
    
    public CurrencySurface[] surfaces;
    public String address;
    
    public CurrencyMesh(CurrencySurface[] surfaces, String address){
   
        this.surfaces = surfaces;
        this.address = address;
    }
    
    //bottom right back to bottom left front to top right back to top left front
    
    public static CurrencySurface[] genSurfaces(Vector3f[] corners){
    
        CurrencySurface[] s = new CurrencySurface[6];
              
        s[FRONT] = new CurrencySurface(CurrencySurface.genSurfaceVectors(corners[BOTTOMLEFTFRONT], corners[TOPRIGHTFRONT]), null, "Front");
        s[BACK] = new CurrencySurface(CurrencySurface.genSurfaceVectors(corners[BOTTOMLEFTBACK], corners[TOPRIGHTBACK]), null, "Back");
        s[LEFT] = new CurrencySurface(CurrencySurface.genSurfaceVectors(corners[BOTTOMLEFTBACK], corners[TOPLEFTFRONT]), null, "Left");
        s[RIGHT] = new CurrencySurface(CurrencySurface.genSurfaceVectors(corners[BOTTOMRIGHTFRONT], corners[TOPRIGHTBACK]), null, "Right");
        s[TOP] = new CurrencySurface(CurrencySurface.genSurfaceVectors(corners[TOPLEFTFRONT], corners[TOPRIGHTBACK]), null, "Top");
        s[BOTTOM] = new CurrencySurface(CurrencySurface.genSurfaceVectors(corners[BOTTOMLEFTBACK], corners[BOTTOMRIGHTFRONT]), null, "Bottom");
                                                                
        
        return s;
    }
    
    public void bake(){
    
        for (CurrencySurface s: surfaces){
        
            s.bake();
        }
    }
}
