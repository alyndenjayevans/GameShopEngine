/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.scene.Geometry;
import java.util.HashMap;

/**
 *
 * @author lynden
 */
public class App {
    
    public static SimpleApplication app;
    public static HashMap<Geometry, CurrencySurface> surfaceCollisions;
    public static Geometry fontGeom;
    public static HashMap<Mode, GameShopDoc> selectors;
    public static HashMap<Mode, GameShopDoc> buttons;
    
    //public static HashMap<Geometry, GameShopDoc> docs;
    
    //public static FontMaker fontMaker;
    
    public App(SimpleApplication app){
        
        App.app = app;
        App.surfaceCollisions = new HashMap<>();
        App.fontGeom = new Geometry();
        App.selectors = new HashMap<>();
        App.buttons = new HashMap<>();
//        App.selectMode(Mode.CREATE);
       // App.docs = new HashMap<>();
      //  App.fontMaker = new FontMaker();
    }
    
    public static void selectMode(Mode mode){
        
        for (GameShopDoc s: selectors.values()){
            
           // System.out.println(s.geom.getParent().getName());
        
            App.app.getRootNode().detachChild(s.geom);
        }
        
         for (GameShopDoc b: buttons.values()){
        
            App.app.getRootNode().detachChild(b.geom);
        }
    
         App.app.getRootNode().attachChild(selectors.get(mode).geom);
//         App.app.getRootNode().attachChild(buttons.get(mode).geom);
              
//        if(null != mode)switch (mode) {
//            
//            case CREATE -> {
//                
//               // App.app.getRootNode().attachChild(buttons.get(mode).geom);
//               System.out.println("CREATE");
//            }
//            case MESH -> {
//                System.out.println("MESH");
//            }
//            case ATMS -> {
//            }
//            case ANIMATE -> {
//            }
//            case SAVE -> {
//            }
//            default -> {
//            }
//        }  
        
    }
}
