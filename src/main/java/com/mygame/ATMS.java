/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygame;

import com.jme3.asset.AssetManager;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import com.jme3.export.binary.BinaryExporter;
import com.jme3.export.binary.BinaryImporter;
import com.jme3.math.Vector4f;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import com.jme3.util.BufferUtils;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 *
 * @author lynden
 */
public class ATMS implements Savable {
    
    public int width;
    public int height;
    public byte[] data;
    public ByteBuffer buffer;
    public Image image;
    public Texture2D texture;
    
    public ATMS(){
    
    }
    
    public ATMS(int width, int height){
    
        this.width = width;
        this.height = height;
        
        // RGBA8 format = 4 bytes per pixel (Red, Green, Blue, Alpha)
        this.data = new byte[width * height * 4];
        this.buffer = BufferUtils.createByteBuffer(data.length);

        // Create jME3 Image and Texture2D
        this.image = new Image(Image.Format.RGBA8, width, height, buffer, com.jme3.texture.image.ColorSpace.sRGB);
        this.texture = new Texture2D(image);

        // Set texture filters (Nearest is great for pixel art or procedural grids)
        this.texture.setMagFilter(Texture.MagFilter.Nearest);
        this.texture.setMinFilter(Texture.MinFilter.NearestNoMipMaps);
    }

    /**
     * Sets the RGBA color of a specific pixel at (x, y).
     */
    public void setPixel(int x, int y, byte r, byte g, byte b, byte a) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return;
        }
        int index = (y * width + x) * 4;
        data[index] = r;
        data[index + 1] = g;
        data[index + 2] = b;
        data[index + 3] = a;
    }
    
    public byte[] getPixel(int x, int y){
    
        int index = (y * width + x) * 4;

        return new byte[]{data[index], data[index + 1], data[index + 2], data[index + 3]};
    }
    
    /**
     * Draws a filled square starting from top-left (startX, startY) with a
     * given size.
     */
    public void drawSquare(int startX, int startY, int size, byte r, byte g, byte b, byte a) {
        for (int y = startY; y < startY + size; y++) {
            for (int x = startX; x < startX + size; x++) {
                setPixel(x, y, r, g, b, a);
            }
        }
    }

    /**
     * Draws a filled circle centered at (centerX, centerY) with a given radius.
     */
    public void drawCircle(int centerX, int centerY, int radius, byte r, byte g, byte b, byte a) {
        int rSquared = radius * radius;
        for (int y = centerY - radius; y <= centerY + radius; y++) {
            for (int x = centerX - radius; x <= centerX + radius; x++) {
                int dx = x - centerX;
                int dy = y - centerY;
                if (dx * dx + dy * dy <= rSquared) {
                    setPixel(x, y, r, g, b, a);
                }
            }
        }
         
    }

    /**
     * Clears the entire texture canvas with a specific RGBA color.
     */
    public void clear(byte r, byte g, byte b, byte a) {
        for (int i = 0; i < data.length; i += 4) {
            data[i] = r;
            data[i + 1] = g;
            data[i + 2] = b;
            data[i + 3] = a;
        }
    }
    
    public void drawATMS(ATMS atms, int x, int y){
    
        for (int h = (int) y; h < y + atms.height; h++){
        
            for (int w = (int)x; w < x + atms.width; w++){
            
                byte[] pixel = atms.getPixel((int)(w - x),(int) (h - y));
                //System.out.println("x " + w + " y " + h);
                
                setPixel(w, h, (byte) ((byte)pixel[0]), (byte)((byte)pixel[1]), (byte)((byte)pixel[2]), (byte)((byte)pixel[3]));//, pixel[0]);
            }
            
        }
        
        
            System.out.println("Done With ATMS " + x + " " + y);
       //update();
        
    }

    /**
     * Bulk updates the internal byte array. The input array length must match
     * width * height * 4.
     */
    public void setByteArray(byte[] newData) {
        if (newData.length == data.length) {
            System.arraycopy(newData, 0, data, 0, newData.length);
        } else {
            throw new IllegalArgumentException("Data array length does not match texture dimensions.");
        }
    }

    /**
     * Returns the raw byte array so you can draw or manipulate it directly.
     */
    public byte[] getByteArray() {
        return data;
    }

    /**
     * Pushes the latest byte array modifications to the GPU texture buffer.
     * Must be called whenever you want your visual changes to show up on
     * screen.
     */
    public void update() {
        buffer.clear();
        buffer.put(data);
        buffer.flip();
        image.setData(buffer);
        image.setUpdateNeeded();
    }

    /**
     * Retrieves the jME3 Texture2D object to attach to materials.
     */
    public Texture2D getTexture() {
        return texture;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void write(JmeExporter je) throws IOException {
        
        OutputCapsule out = je.getCapsule(this);
        
        out.write(width, "width", 0);
        out.write(height, "height", 0);
        out.write(data, "data", null);
        
        
        
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void read(JmeImporter ji) throws IOException {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
   
        InputCapsule in = ji.getCapsule(this);
        this.width = in.readInt("width", 0);
        this.height = in.readInt("height", 0);
        this.data = in.readByteArray("data", null);
    }
    
    public static void saveToFile(ATMS data, String customFilePath) {
    File file = new File(customFilePath);
    
    // Ensure parent directories exist
    if (file.getParentFile() != null) {
        file.getParentFile().mkdirs();
    }

    BinaryExporter exporter = BinaryExporter.getInstance();
    try {
        exporter.save(data, file);
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}
    
    public static ATMS loadFromFile(String customFilePath, AssetManager assetManager) {
    File file = new File(customFilePath);
    if (!file.exists()) {
        return null;
    }

    BinaryImporter importer = BinaryImporter.getInstance();
    // Required if your Savable holds Spatials, Textures, or Materials
    importer.setAssetManager(assetManager); 

    try {
        return (ATMS) importer.load(file);
    } catch (IOException ex) {
        ex.printStackTrace();
        return null;
    }
}
}
