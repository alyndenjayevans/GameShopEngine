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
    //public static HashMap<Geometry, GameShopDoc> docs;
    
    //public static FontMaker fontMaker;
    
    public App(SimpleApplication app){
        
        App.app = app;
        App.surfaceCollisions = new HashMap<>();
        App.fontGeom = new Geometry();
       // App.docs = new HashMap<>();
      //  App.fontMaker = new FontMaker();
    }
}
