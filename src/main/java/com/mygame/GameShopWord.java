/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygame;

import com.jme3.math.Vector2f;

/**
 *
 * @author lynden
 */
public class GameShopWord {
    
    public int startIndex;
    public int endIndex;
    public String word;
    
    public GameShopWord(String word, int startIndex){
    
        this.word = word;
        this.startIndex = startIndex;
        this.endIndex = startIndex + this.word.length() - 1;
        
    }
    
    public void onClick(Vector2f uv){
    
        
    }
}
