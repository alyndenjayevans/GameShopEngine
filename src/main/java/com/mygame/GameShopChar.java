/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygame;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

/**
 *
 * @author lynden
 */
public class GameShopChar {
    
    public char character;
    
    public int[] indexes; //4 indexes make a square
    
    public Vector3f startPosition;
    
    public Vector3f offset;
    
    public GameShopChar(char character, int[] indexes){
    
        this.character = character;
        this.indexes = indexes;
        //offset = new Vector3f[4];
    }
    
    public boolean onClick(Vector2f uv){
    
        return false;
    }
    
}
