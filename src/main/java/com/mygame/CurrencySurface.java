/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygame;

import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Triangle;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author lynden
 */
public class CurrencySurface {
    
    //7x7 CurrencyVectors;
    public Vector3f[] controlPoints;
   
    public Vector3f[] vertices;
    
    public ATMS atms;
    public String address;
    public Mesh mesh;
    public Vector2f[] texCoords;
    public int[] indexes;
    public Geometry geom;
    
    public CurrencySurface(Vector3f[] controlPoints, ATMS atms, String address){
        
        this.controlPoints = controlPoints;
        this.atms = atms;
        this.address = address;
        
        this.vertices = new Vector3f[6 * 6 * 4];
        
        mesh = new Mesh();
        texCoords = new Vector2f[6 * 6 * 4];
        indexes = new int[(6 * 6 * 6)];
        
        Vector3f start = new Vector3f(0,0,0);
        Vector3f end = new Vector3f(1,1,0);
        
        Vector3f distance = end.subtract(start);
        Vector3f unit = distance.divide(6);
          
        int v = 0;
             
        for (int y = 0; y < 7; y++){
        
            if (y == 0 || y == 6){
            for (int x = 0; x < 7; x++){  
        //for (int v = 0; v < 49; v++){
        
            Vector3f current = new Vector3f(start.x + (x * unit.x), start.y + (y * unit.y), start.z + (1 * unit.z));
        
            if (x == 0 || x == 6){
            //Make Same Numbers At All 
            texCoords[v] = new Vector2f(current.x, current.y);
    
            v++;
            } else {
            
                for (int i = 0; i < 2; i++){
                
                    //Duplicate Every Other Square
                     texCoords[v] = new Vector2f(current.x, current.y);
    
                     v++;
                }
            }
            }
            } else {
            
                for (int i = 0; i < 2; i++){
                 for (int x = 0; x < 7; x++){  
        //for (int v = 0; v < 49; v++){
        
            Vector3f current = new Vector3f(start.x + (x * unit.x), start.y + (y * unit.y), start.z + (1 * unit.z));
        
            if (x == 0 || x == 6){
            //Make Same Numbers At All 
            texCoords[v] = new Vector2f(current.x, current.y);
    
            v++;
            } else {
            
                for (int j = 0; j < 2; j++){
                
                    //Duplicate Every Other Square
                     texCoords[v] = new Vector2f(current.x, current.y);
    
                     v++;
                }
            }
            }
            }
            }
        }
        
        int in = 0;
        for (int index = 0; index < indexes.length; index+=6){
        
            if (in % 12 == 0 && in > 0) {
            
                in += 12;
            }
            indexes[index] = in;
            indexes[index + 1] = in + 1;
            indexes[index + 2] = in + 12;
            indexes[index + 3] = in + 1;
            indexes[index + 4] = in + 13;
            indexes[index + 5] = in + 12;
            in+=2;
        }

//             int in = 0;
//        for (int index = 0; index < indexes.length; index+=6){
//        
//            if (in % 7 == 6){
//            
//                in++;
//            }
//            indexes[index] = in;
//            indexes[index + 1] = in + 1;
//            indexes[index + 2] = in + 7;
//            indexes[index + 3] = in + 1;
//            indexes[index + 4] = in + 8;
//            indexes[index + 5] = in + 7;
//            in++;
//            
//            
//        }
//}
    
    }
    
    public static Vector3f[] genSurfaceVectors(Vector3f start, Vector3f end){
   
        Vector3f[] v = new Vector3f[25];
        
        int i = 0;
        for (int y = 0; y < 5; y++){
        
            for (int x = 0; x < 5; x++){
            
                Vector3f distance = end.subtract(start);
                Vector3f unit = distance.divide(4);
                
                Vector3f current = new Vector3f(start.x + (x * unit.x), start.y + (y * unit.y), start.z + (1 * unit.z));
                v[i] = new Vector3f(current);//, "Surface " + " " + current.x + " " + current.y + " " + start);
                i++;
            }
        }
        
        return v;
    }
    
    public void bake(){
        
        //Vector3f[] vertices = new Vector3f[texCoords.length];
        
        int w = 0;
      
//        System.out.println("Total TexCoords" + texCoords.length);

  
        Vector3f start = new Vector3f(0,0,0);
        Vector3f end = new Vector3f(1,1,0);
        
        Vector3f distance = end.subtract(start);
        Vector3f unit = distance.divide(6);
          
        int v = 0;
             
        for (int y = 0; y < 7; y++){
        
            if (y == 0 || y == 6){
            for (int x = 0; x < 7; x++){  
        //for (int v = 0; v < 49; v++){
        
            Vector3f current = new Vector3f(start.x + (x * unit.x), start.y + (y * unit.y), start.z + (1 * unit.z));
        
            if (x == 0 || x == 6){
            //Make Same Numbers At All 
            vertices[v] = surface(current.x, current.y);
    
            v++;
            } else {
            
                for (int i = 0; i < 2; i++){
                
                    //Duplicate Every Other Square
                     vertices[v] = surface(current.x, current.y);
    
                     v++;
                }
            }
            }
            } else {
            
                for (int i = 0; i < 2; i++){
                 for (int x = 0; x < 7; x++){  
        //for (int v = 0; v < 49; v++){
        
            Vector3f current = new Vector3f(start.x + (x * unit.x), start.y + (y * unit.y), start.z + (1 * unit.z));
        
            if (x == 0 || x == 6){
            //Make Same Numbers At All 
            vertices[v] = surface(current.x, current.y);
    
            v++;
            } else {
            
                for (int j = 0; j < 2; j++){
                
                    //Duplicate Every Other Square
                     vertices[v] = surface(current.x, current.y);
    
                     v++;
                }
            }
            }
            }
            }
        }
        

//        int i = 0;
//        float u = 0;
//        float ve = 0;
//        for (Vector3f v: vertices){
//            //System.out.println("u " + u + " ve " + ve + " i " + i);
////            if (u == 1.0f && ve == 1.0f){
////            
////                System.out.println("Last Control Point: " + i);
////                break;
////            }
//           
//            if (ve == 0){
//              if (u >= 1){
//            vertices[i] = surface(u,ve);
//                if (u == 1.0f && ve == 1.0f){
//            
//                System.out.println("Last Control Point: " + i);
//                break;
//            }
//            i++;
//            u = 0;
//            ve += (float)1/6;
//         
//            //continue;
//            }
//        
//            if (u == 0){
//            vertices[i] = surface(u,ve);
//                if (u == 1.0f && ve == 1.0f){
//            
//                System.out.println("Last Control Point: " + i);
//                break;
//            }
//            i++;
//            u+= (float)1/6;
//           
//            } else {
//            
//                for (int j = 0; j < 2; j++){
//                 vertices[i] = surface(u,ve);
//                     if (u == 1.0f && ve == 1.0f){
//            
//                System.out.println("Last Control Point: " + i);
//                break;
//            }
//            i++;
//            
//                }
//            u+= (float)1/6;
//            }
//            } else {
//            
//                for (int k = 0; k < 2; k++){
//                   if (u >= 1){
//            vertices[i] = surface(u,ve);
//                if (u == 1.0f && ve == 1.0f){
//            
//                System.out.println("Last Control Point: " + i);
//                break;
//            }
//            i++;
//             u = 0;
//                ve += (float)1/6;
//                //continue;
//            }
//        
//            if (u == 0){
//            vertices[i] = surface(u,ve);
//                if (u == 1.0f && ve == 1.0f){
//            
//                System.out.println("Last Control Point: " + i);
//                break;
//            }
//            i++;
//            u+= (float)1/6;
//             
//            } else {
//            
//                for (int j = 0; j < 2; j++){
//                 vertices[i] = surface(u,ve);
//                     if (u == 1.0f && ve == 1.0f){
//            
//                System.out.println("Last Control Point: " + i);
//                break;
//            }
//            i++;
//                }
//            u+= (float)1/6;
//            }
//            }
//                //ve += (float)1/6;
//        }
//            
//            
//        }
//        
//          for (Vector3f v: vertices){
//        
//            if (v == null){
//            
//                System.out.println("Total NonNull " + w);
//            }
//                            w++;
//
//        }
        
        mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        mesh.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoords));
        mesh.setBuffer(Type.Index, 3, BufferUtils.createIntBuffer(indexes));
        mesh.updateBound();
        
        System.out.println(vertices.length);
        System.out.println(texCoords.length);
        
        System.out.println(Arrays.asList(vertices));
        System.out.println(Arrays.asList(texCoords));
        System.out.println(Arrays.toString(indexes));
        
        geom = new Geometry("Mesh", mesh);

        Material mat = new Material(App.app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        //Material mat = new Material(App.app.getAssetManager(), "/MatDefs/UI.j3md");
      
        mat.setColor("Color", ColorRGBA.White);
        mat.setTexture("ColorMap", atms.getTexture());
        
        // 1. Render both front and back faces (disables back-face culling)
        mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);

// 2. Enable alpha transparency blending
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);

        geom.setMaterial(mat);

// 3. Put semi-transparent objects in the Transparent bucket so depth sorting works properly
        geom.setQueueBucket(RenderQueue.Bucket.Transparent);
        geom.setMaterial(mat);

        
        App.app.getRootNode().attachChild(geom);
        
        App.surfaceCollisions.put(geom, this);
        
        System.out.println("Done");
    
    }
    
    
    public void onClick(Vector2f uv){
    
        if (address.contains("CurrencySurface")){
        atms.drawCircle((int)((float)256 * uv.x), (int)((float)256 * uv.y), 16, (byte)255, (byte)255, (byte)255, (byte)255);
        atms.update();
        }
        
        if (address.contains("Forward")){
        
            System.out.println("Forward");
           // App.fontMaker.clickForward();
        }
        
        if (address.contains("Backward")){
        
            System.out.println("Backward");
            //App.fontMaker.clickBackward();
        }
    }
    
    public void onDrag(Vector2f uv) {

        if (address.contains("CurrencySurface")) {
            atms.drawCircle((int) ((float) 256 * uv.x), (int) ((float) 256 * uv.y), 16, (byte) 255, (byte) 255, (byte) 255, (byte) 255);
            atms.update();
            System.out.println("Dragged at " + uv);
        }

        
    }
    
//    public void onDrag(Vector2f uv){
//    
//        atms.drawCircle((int)((float)256 * uv.x), (int)((float)256 * uv.y), 16, (byte)255, (byte)255, (byte)255, (byte)255);
//        atms.update();
//    }
    
    public static Vector3f deCasteljaus(Vector3f[] vectors, float t){
    
//        if (vectors.length < 3){
//        
//            return null;
//        } 

        int max = vectors.length;
        
        ArrayList<ArrayList<Vector3f>> temp = new ArrayList<ArrayList<Vector3f>>();
        
        for (Vector3f v: vectors){
        
            temp.add(new ArrayList<Vector3f>());
        }
        
        int count = 0;
        
        for (ArrayList<Vector3f> i: temp){
        
            for (int j = 0; j < max - 1; j++){
            
                 if (count == 0){
                 
                     i.add(FastMath.interpolateLinear(t, vectors[j], vectors[j + 1]));
                    // System.out.println("j " + j + " :" + i.get(j));
                 } else {
                 
                      i.add(FastMath.interpolateLinear(t, temp.get(count - 1).get(j), temp.get(count - 1).get(j + 1)));
                     //   System.out.println("j " + j + " :" + i.get(j));
             
                 }
                 
            }
            count++;
            max--;
            
        }
        
//        temp.add(FastMath.interpolateLinear(t, vectors[0], vectors[1]));
//        temp.add(FastMath.interpolateLinear(t, vectors[1], vectors[2]));
//        temp.add(FastMath.interpolateLinear(t, vectors[2], vectors[3]));
//        temp.add(FastMath.interpolateLinear(t, vectors[3], vectors[4]));
        
        //System.out.println("Count " + count + " Max " + max);
        return temp.get(count - 2).get(0);
    }
    
    public Vector3f surface(float u, float v){
//        
//        //Add Cells
//        
//        int startControlPoint = (int)((float)u * 4) + ((int)(((float)v * 4)) * 5); 
//        
//        System.out.println("u " + u + "v " + v);
//        System.out.println("start: " + startControlPoint);
//        
//        
//        ArrayList<Vector3f> rowU = new ArrayList<>();
//        ArrayList<Vector3f> columnV = new ArrayList<>();
//        
//        rowU.add(deCasteljaus(new Vector3f[]{controlPoints[0],controlPoints[1],controlPoints[2],controlPoints[3],controlPoints[4]}, u));
//        
//        columnV.add(deCasteljaus(new Vector3f[]{controlPoints[0],controlPoints[5],controlPoints[10],controlPoints[15],controlPoints[20]}, v));
//        
//        return new Vector3f(u,v,0f);


        int rows = 5;
        Vector3f[] intermediaries = new Vector3f[rows];
        
        for (int i = 0; i < rows; i++) {
            intermediaries[i] = deCasteljaus(getColumn(i), v);
        }
//
        return deCasteljaus(intermediaries, u);
        
         
    
    }
    
    public Vector3f[] getColumn(int index){
    
        Vector3f a = controlPoints[index];
        Vector3f b = controlPoints[index + 5];
        Vector3f c = controlPoints[index + 10];
        Vector3f d = controlPoints[index + 15];
        Vector3f e = controlPoints[index + 20];
        
        return new Vector3f[]{a, b, c, d, e};
         
    }
}



///*
//
//import com.jme3.math.Vector2f;
//import com.jme3.math.Vector3f;
//import com.jme3.scene.Mesh;
//import com.jme3.scene.VertexBuffer.Type;
//import com.jme3.util.BufferUtils;
//
//public class CurrencySurface extends Mesh {
//
//    // 2D grid of control points (e.g., 4x4 for a bicubic patch, or any NxM size)
//    private Vector3f[][] controlPoints;
//    
//    // 6x6 squares requires 7x7 evaluated grid points
//    private static final int QUADS_X = 6;
//    private static final int QUADS_Y = 6;
//
//    public CurrencySurface(Vector3f[][] controlPoints) {
//        this.controlPoints = controlPoints;
//        updateMesh();
//    }
//
//    public void updateMesh() {
//        int pointsX = QUADS_X + 1; // 7
//        int pointsY = QUADS_Y + 1; // 7
//
//        // Step 1: Evaluate the 7x7 grid of 3D positions on the Bézier surface
//        Vector3f[][] evaluatedGrid = new Vector3f[pointsX][pointsY];
//        for (int x = 0; x < pointsX; x++) {
//            float u = (float) x / QUADS_X;
//            for (int y = 0; y < pointsY; y++) {
//                float v = (float) y / QUADS_Y;
//                evaluatedGrid[x][y] = evaluateSurface(u, v);
//            }
//        }
//
//        // Step 2: Build 6x6 unique squares (4 unique vertices per square = 144 vertices)
//        int totalQuads = QUADS_X * QUADS_Y; // 36
//        Vector3f[] vertices = new Vector3f[totalQuads * 4];
//        Vector3f[] normals = new Vector3f[totalQuads * 4];
//        Vector2f[] texCoords = new Vector2f[totalQuads * 4];
//        int[] indices = new int[totalQuads * 6];
//
//        int vIndex = 0;
//        int iIndex = 0;
//
//        for (int qx = 0; qx < QUADS_X; qx++) {
//            for (int qy = 0; qy < QUADS_Y; qy++) {
//                // Grab the 4 corners of this specific square
//                Vector3f p00 = evaluatedGrid[qx][qy];         // Bottom-Left
//                Vector3f p10 = evaluatedGrid[qx + 1][qy];     // Bottom-Right
//                Vector3f p01 = evaluatedGrid[qx][qy + 1];     // Top-Left
//                Vector3f p11 = evaluatedGrid[qx + 1][qy + 1]; // Top-Right
//
//                // Clone into unique vertex slots so nothing is shared
//                vertices[vIndex]     = p00.clone();
//                vertices[vIndex + 1] = p10.clone();
//                vertices[vIndex + 2] = p01.clone();
//                vertices[vIndex + 3] = p11.clone();
//
//                // Calculate flat face normal for this unique square
//                Vector3f edge1 = p10.subtract(p00);
//                Vector3f edge2 = p01.subtract(p00);
//                Vector3f flatNormal = edge1.cross(edge2).normalizeLocal();
//
//                normals[vIndex]     = flatNormal.clone();
//                normals[vIndex + 1] = flatNormal.clone();
//                normals[vIndex + 2] = flatNormal.clone();
//                normals[vIndex + 3] = flatNormal.clone();
//
//                // Per-quad UVs (0..1 per square, or map across the whole surface)
//                texCoords[vIndex]     = new Vector2f(0f, 0f);
//                texCoords[vIndex + 1] = new Vector2f(1f, 0f);
//                texCoords[vIndex + 2] = new Vector2f(0f, 1f);
//                texCoords[vIndex + 3] = new Vector2f(1f, 1f);
//
//                // Two triangles per square (Counter-Clockwise winding)
//                // Triangle 1: Bottom-Left -> Bottom-Right -> Top-Left
//                indices[iIndex]     = vIndex;
//                indices[iIndex + 1] = vIndex + 1;
//                indices[iIndex + 2] = vIndex + 2;
//
//                // Triangle 2: Bottom-Right -> Top-Right -> Top-Left
//                indices[iIndex + 3] = vIndex + 1;
//                indices[iIndex + 4] = vIndex + 3;
//                indices[iIndex + 5] = vIndex + 2;
//
//                vIndex += 4;
//                iIndex += 6;
//            }
//        }
//
//        // Step 3: Upload buffers to jME3 Mesh
//        setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
//        setBuffer(Type.Normal,   3, BufferUtils.createFloatBuffer(normals));
//        setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoords));
//        setBuffer(Type.Index,    3, BufferUtils.createIntBuffer(indices));
//        updateBound();
//    }
//
//    /**
//     * Evaluates a 2D Bézier surface at (u, v) using two passes of De Casteljau's algorithm.
//     */
//    private Vector3f evaluateSurface(float u, float v) {
//        int rows = controlPoints.length;
//        Vector3f[] intermediatePoints = new Vector3f[rows];
//
//        // Pass 1: Evaluate 1D Bézier along 'v' for each row of control points
//        for (int i = 0; i < rows; i++) {
//            intermediatePoints[i] = evaluate1DBezier(controlPoints[i], v);
//        }
//
//        // Pass 2: Evaluate 1D Bézier along 'u' across the intermediate points
//        return evaluate1DBezier(intermediatePoints, u);
//    }
//
//    /**
//     * Standard 1D De Casteljau algorithm for any number of control points.
//     */
//    private Vector3f evaluate1DBezier(Vector3f[] points, float t) {
//        int n = points.length;
//        Vector3f[] temp = new Vector3f[n];
//        for (int i = 0; i < n; i++) {
//            temp[i] = points[i].clone();
//        }
//
//        for (int r = 1; r < n; r++) {
//            for (int i = 0; i < n - r; i++) {
//                // temp[i] = (1 - t) * temp[i] + t * temp[i + 1]
//                temp[i].interpolateLocal(temp[i + 1], t);
//            }
//        }
//        return temp[0];
//    }
//}
//*/
