/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygame;

import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author lynden
 */

public class GameShopDoc extends CurrencySurface {

    public HashMap<Character, Vector4f> characterTexCoordMap;
    
    public GameShopChar[] charPool;
    
    public ArrayList<GameShopWord> words;
    
    //Word is from 0 to 255 vector range
    
    int cursorIndex = 0;
    
    
    
    public GameShopDoc(Vector3f[] vectors, ATMS atms, String address){
        super(vectors, atms, address);
        
        this.atms = new ATMS(4096, 4096);
        this.atms.clear((byte)0, (byte)0, (byte)255, (byte)255);
        this.characterTexCoordMap = new HashMap<>();
        
        charPool = new GameShopChar[15 * 15];
        words = new ArrayList<>();
        
        int xx = 0;
        int yy = 0;
        
        Vector3f start = new Vector3f(0, 0, 0);
        Vector3f end = new Vector3f(1, 1, 0);

        Vector3f distance = end.subtract(start);
        Vector3f unitX = distance.divide(16);
        Vector3f unitY = distance.divide(16);
        for (char c: FontMaker.alphaNumerical.toCharArray()){
        
            if (xx >= 4096){
                xx = 0;
                yy += 256;
            }
            ATMS atmsChar = new ATMS(256,256);
        // Loads "assets/Data/level1.sav"
            ATMS loadAtms = (ATMS) App.app.getAssetManager().loadAsset("ATMS/Fonts/Default/" + c + ".font.atms");

            atmsChar.data = loadAtms.data;
            atmsChar.update();
            
            this.atms.drawATMS(atmsChar, xx, yy);
            this.atms.update();
            
             Vector3f current = new Vector3f(start.x + (((float)xx/256) * unitX.x), start.y + (((float)yy/256) * unitY.y), start.z + (1 * unitX.z));
        
            characterTexCoordMap.put(c, new Vector4f(current.x, current.y, current.x + unitX.x, current.y + unitY.y));
            
            xx += 256;
        }
        this.atms.update();
        
//        if (buffer == null) {
//        System.out.println("Buffer is null");
//        return;
    

//int iiii = 0;
//    while (iiii < 4096 * 1024){
//        System.out.print(" " + atms.texture.getImage().getData(0).get(iiii) );
//        iiii++;
//    }
    
       // System.out.println(atms.texture.getImage().getData());
        
        mesh = new Mesh();
        texCoords = new Vector2f[15 * 15 * 4];
        indexes = new int[(15 * 15 * 6)];
        vertices = new Vector3f[texCoords.length];
        
//        Vector3f start = new Vector3f(0,0,0);
//        Vector3f end = new Vector3f(1,1,0);
//        
//        Vector3f distance = end.subtract(start);
//        Vector3f unitX = distance.divide(15);
//        Vector3f unitY = distance.divide(15);
       
        int v = 0;
             
        for (int y = 0; y < 16; y++){
        
            if (y == 0 || y == 15){
            for (int x = 0; x < 16; x++){  
        //for (int v = 0; v < 49; v++){
        
            Vector3f current = new Vector3f(start.x + (x * unitX.x), start.y + (y * unitY.y), start.z + (1 * unitX.z));
        
            if (x == 0 || x == 15){
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
                 for (int x = 0; x < 16; x++){  
        //for (int v = 0; v < 49; v++){
        
            Vector3f current = new Vector3f(start.x + (x * unitX.x), start.y + (y * unitY.y), start.z + (1 * unitX.z));
        
            if (x == 0 || x == 15){
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
        
        int charPoolIndex = 0;
        
       // System.out.println("Index Length " + indexes.length);
        
        int in = 0;
        for (int index = 0; index < indexes.length; index+=6){
        
            if (in % 30 == 0 && in > 0) {
            
                in += 30;
            }
            indexes[index] = in;
            indexes[index + 1] = in + 1;
            indexes[index + 2] = in + 30;
            indexes[index + 3] = in + 1;
            indexes[index + 4] = in + 31;
            indexes[index + 5] = in + 30;
            
            charPool[charPoolIndex] = new GameShopChar('A', new int[]{in, in + 1, in + 30, in + 31});
            charPoolIndex++;  
            
            in+=2;
            
              
        }
        
        //System.out.println("CharPoolIndex " + charPoolIndex);
        
        //calculateCharPositions();
    }
    
    public void registerWord(String word){
    
//        words.add(new GameShopWord(word, cursorIndex, position));
        cursorIndex += word.length();
        
    }
    
    public void drawAllWords(){
    
        int i = 0;
        for (GameShopWord word: words){
        
            for (char c: word.word.toCharArray()){
            
                charPool[i].character = c;
                i++;
            }
        }
    }
    
    public Vector2f[] renderAllWords(){
    
        Vector2f[] temp = new Vector2f[texCoords.length];
        int tempT = 0;
        for (Vector2f t: temp){
        
            temp[tempT] = new Vector2f();
            tempT++;
        }
        int i = 0;
        for (GameShopChar c: charPool){
        
//            if (i < charPool.length - 31){
//            System.out.println("TexCoords " + texCoords[i] + " " + texCoords[c.indexes[0]]);
//            temp[i] = texCoords[c.indexes[0]];
//            temp[i + 1] = texCoords[c.indexes[1]];
//            //if (i < charPool.length - 30){
//            temp[i + 30] = texCoords[c.indexes[2]];
//            //}
//            temp[i + 31] = texCoords[c.indexes[3]];
//            //}
//            i+=2;
//            }
if (i % 30 == 0 && i > 0) {
            
                i += 30;
            }
if (i < charPool.length - 31){
// System.out.println(i);
            Vector4f store = this.characterTexCoordMap.get(c.character);
            
          //  System.out.println(i);
            temp[i] = new Vector2f(store.x, store.y);
            temp[i + 1] =  new Vector2f(store.z, store.y);
            //if (i < charPool.length - 30){
            temp[i + 30] =  new Vector2f(store.x, store.w);
            //}
            //if (i < charPool.length - 31){
            temp[i + 31] =  new Vector2f(store.z, store.w);
           // }
            i+=2;
           }
        }
        
        return temp;
    }
    
    public void calculateCharPositions(){
    
        //int i = 0;
        //System.out.println(charPool[1].indexes[2]);
        for (int i = 0; i < charPool.length; i++){
    
//            System.out.println("Length " + charPool.length);
//            System.out.println("i " + i);
//            System.out.println("Index 2: " + charPool[i].indexes[2]);
            
            charPool[i].startPosition = new Vector3f(vertices[charPool[i].indexes[0]].add(vertices[charPool[i].indexes[1]].add(vertices[charPool[i].indexes[2]].add(vertices[charPool[i].indexes[3]])))).divide(4);
         //   i++;
    
        }
    }
    
    public void moveCharOffsetToWordPosition(){
    
         int i = 0;
         int j = 0;
        for (GameShopWord word: words){
        
            words.get(j).uv = new Vector4f(texCoords[charPool[words.get(j).startIndex].indexes[0]].x, texCoords[charPool[words.get(j).startIndex].indexes[0]].y,texCoords[charPool[words.get(j).endIndex].indexes[3]].x, texCoords[charPool[words.get(j).endIndex].indexes[3]].y);
            Vector3f position = word.position;
            int firstChar = i;
            Vector3f home = charPool[0].startPosition;//new Vector3f().subtract(charPool[firstChar].startPosition).add(position);
            Vector3f homeStart = home.add(position);
            for (char c: word.word.toCharArray()){
               // System.out.println(charPool[i].indexes[1]);
                float charSpace = vertices[charPool[i].indexes[1]].x - vertices[charPool[i].indexes[0]].x;
          
                charPool[i].uv = new Vector4f(texCoords[charPool[i].indexes[0]].x, texCoords[charPool[i].indexes[0]].y, texCoords[charPool[i].indexes[3]].x, texCoords[charPool[i].indexes[3]].y);
                
                charPool[i].offset = home.subtract(charPool[i].startPosition).add(position).add(charSpace * (i-firstChar), 0, 0);
                        
                i++;
            }
            j++;
        }
        
    }
    
    public Vector3f[] moveAllWords(){
    
        Vector3f[] temp = new Vector3f[vertices.length];
        int tempT = 0;
        for (Vector3f t: temp){
        
            temp[tempT] = new Vector3f();
            tempT++;
        }
        
         int i = 0;
        for (GameShopChar c: charPool){
         
if (i % 30 == 0 && i > 0) {
            
                i += 30;
            }
if (i < charPool.length - 31){
// System.out.println(i);
            //Vector4f store = this.characterTexCoordMap.get(c.character);
            
           // System.out.println(i);
            temp[i] = vertices[i].add(c.offset);//new Vector2f(store.x, store.y);
            temp[i + 1] =  vertices[i + 1].add(c.offset);
            //if (i < charPool.length - 30){
            temp[i + 30] =  vertices[i + 30].add(c.offset);
            //}
            //if (i < charPool.length - 31){
            temp[i + 31] =  vertices[i + 31].add(c.offset);
           // }
            i+=2;
           }
        }
        
        return temp;
        
        
    }
    
    @Override
    public void onClick(Vector2f uv){
    
        int i = 0;
        boolean isBreak = false;
        for (GameShopWord word: words){
            
            for (char c: word.word.toCharArray()){
            
                if (uv.x >= charPool[i].uv.x && uv.x <= charPool[i].uv.z && uv.y >= charPool[i].uv.y && uv.y <= charPool[i].uv.w){
                     
                   charPool[i].onClick(uv);
                   word.onClick(uv);
                   isBreak = true;
                   break;
                    
                }  
                i++;
            }
            if (isBreak){
            
                break;
            }
//            System.out.println(word.word);
//        System.out.println("uv " + uv.x + " " + uv.y);
//        System.out.println("word" + word.uv.x + " " + word.uv.y + " " + word.uv.z + " " + word.uv.w);
            //if (uv.x >= word.uv.x && uv.x <= word.uv.z && uv.y >= word.uv.y && uv.y <= word.uv.w){
            
               // System.out.println(word.word);
            //}  
        }
    }
    
      @Override
    public void onDrag(Vector2f uv){
       // super.onDrag(uv);
    }
    
    @Override
    public void bake(){
        
        //vertices = new Vector3f[texCoords.length];
        
        int w = 0;
      
//        System.out.println("Total TexCoords" + texCoords.length);

  
        Vector3f start = new Vector3f(0,0,0);
        Vector3f end = new Vector3f(1,1,0);
        
        Vector3f distance = end.subtract(start);
        Vector3f unitX = distance.divide(15);
        Vector3f unitY = distance.divide(15);
          
        int v = 0;
             
        for (int y = 0; y < 16; y++){
        
            if (y == 0 || y == 15){
            for (int x = 0; x < 16; x++){  
        //for (int v = 0; v < 49; v++){
        
            Vector3f current = new Vector3f(start.x + (x * unitX.x), start.y + (y * unitY.y), start.z + (1 * unitX.z));
        
            if (x == 0 || x == 15){
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
                 for (int x = 0; x < 16; x++){  
        //for (int v = 0; v < 49; v++){
        
            Vector3f current = new Vector3f(start.x + (x * unitX.x), start.y + (y * unitY.y), start.z + (1 * unitX.z));
        
            if (x == 0 || x == 15){
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
        
        this.calculateCharPositions();
        this.moveCharOffsetToWordPosition();
        
        mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(moveAllWords()));
        mesh.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(renderAllWords()));
        mesh.setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createIntBuffer(indexes));
        mesh.setBuffer(VertexBuffer.Type.TexCoord2, 2, BufferUtils.createFloatBuffer(texCoords));
      
        mesh.updateBound();
        
//        System.out.println(vertices.length);
//        System.out.println(texCoords.length);
//        
//        System.out.println(Arrays.asList(vertices));
//        System.out.println(Arrays.asList(texCoords));
//        System.out.println(Arrays.toString(indexes));
//        
        geom = new Geometry("Mesh", mesh);

        Material mat = new Material(App.app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        //Material mat = new Material(App.app.getAssetManager(), "/MatDefs/UI.j3md");
      
        mat.setColor("Color", ColorRGBA.White);
        mat.setTexture("ColorMap", this.atms.getTexture());
        
        // 1. Render both front and back faces (disables back-face culling)
        mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);

// 2. Enable alpha transparency blending
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);

        geom.setMaterial(mat);

// 3. Put semi-transparent objects in the Transparent bucket so depth sorting works properly
        geom.setQueueBucket(RenderQueue.Bucket.Transparent);
        //geom.setMaterial(mat);

        
        App.app.getRootNode().attachChild(geom);
        
        App.surfaceCollisions.put(geom, this);
        
        //System.out.println("Done");
        atms.update();
       // System.out.println(geom.getLocalTranslation());
       //  App.app.getCamera().lookAt(new Vector3f(), Vector3f.UNIT_Y);
    
    }
}
//public class GameShopDoc {
//    
//    public ATMS doc;
//    
//    //16 x 16 points [256x256 ATMS] 256 squares total
//    
//    //x4 to make individual particles 1024 squares total 
//    //only evaluate 256 squares
//    
//    public CurrencyVector[] vectors;
//    //public ATMS atms;
//    public String address;
//    public Mesh mesh;
//    public Vector2f[] texCoords;
//    public int[] indexes;
//    public Geometry geom;
//    
//    public enum Multiple{
//    
//        One,
//        Two,
//        Three,
//        Four
//    }
//    
//    public GameShopDoc(String address){
//    
//        doc = new ATMS(4096, 4096);
//        
//        doc.clear((byte)0, (byte)0, (byte)255, (byte)255);
//        doc.update();
//        
//        //vectors = new CurrencyVector[256];
//        vectors = new CurrencyVector[1024];
//        
//        int i = 0;
//        int j = 0;
//        float dividend = 15;
//        for (int k = 0; k < vectors.length; k+=4){
//        
//            
//           if (vectors[k] == null){
//            vectors[k] = new CurrencyVector(new Vector3f((float)i/dividend,(float)j/dividend,0), "Doc" + k);
//            }
//            if (vectors[k + 1] == null){
//            vectors[k + 1] = new CurrencyVector(new Vector3f((float)(i + 1)/dividend,(float)j/dividend,0), "Doc" + k + 1);
//            }
//            if (k < vectors.length - 32){//16
//            vectors[k + 32] = new CurrencyVector(new Vector3f((float)i/dividend,(float)(j+ 1)/dividend,0), "Doc" + k + 16);
//            }
//            if (k < vectors.length - 33){//17
//            vectors[k + 33] = new CurrencyVector(new Vector3f((float)(i + 1)/dividend,(float)(j + 1)/dividend,0), "Doc" + k + 17);
//            }
//            
//            
//            if (k < vectors.length - 67){
//            vectors[k + 2] = new CurrencyVector(new Vector3f(), "Dark" + k + 3);
//            vectors[k + 3] = new CurrencyVector(new Vector3f(), "Dark" + k + 4);
//            vectors[k + 34] = new CurrencyVector(new Vector3f(), "Dark" + k + 18);
//            vectors[k + 35] = new CurrencyVector(new Vector3f(), "Dark" + k + 19);
//          
//            vectors[k + 64] = new CurrencyVector(new Vector3f(), "Dark" + k + 32);
//            vectors[k + 65] = new CurrencyVector(new Vector3f(), "Dark" + k + 33);
//            vectors[k + 66] = new CurrencyVector(new Vector3f(), "Dark" + k + 34);
//            vectors[k + 67] = new CurrencyVector(new Vector3f(), "Dark" + k + 35);
//            }
//            //i+=4;
//            
//            i+=2;
//            
//            if (i % dividend == 1 && i != 0 && i > dividend){
//            
//                i = 0;
//                j++;
//            } 
//        }
//        
//        this.address = address;
//        
//         mesh = new Mesh();
//        texCoords = new Vector2f[vectors.length];
//        indexes = new int[(15 * 15 * 6)];
//        
//        Vector3f start = new Vector3f(0,0,0);
//        Vector3f end = new Vector3f(1,1,0);
//        
//        Vector3f distance = end.subtract(start);
//        Vector3f unit = distance.divide(15);
//          
//        int v = 0;
//        
//        Multiple yMultiple = Multiple.One;
//             
//        for (int y = 0; y < 32; y++){
//        
//            Multiple xMultiple = Multiple.One;
//            
//            if (yMultiple == Multiple.One || yMultiple == Multiple.Two){
//            
//            for (int x = 0; x < 32; x++){  
//        //for (int v = 0; v < 49; v++){
//        
//            Vector3f current = new Vector3f(start.x + (x * unit.x), start.y + (y * unit.y), start.z + (1 * unit.z));
//        
//            //Make Same Numbers At All 
//            //texCoords[v] = new Vector2f(current.x, current.y);
//    
//            //for (int ii = 0; ii < 2; ii++){
//            if (xMultiple == Multiple.One){
//            texCoords[v] = new Vector2f(current.x, current.y);
//            xMultiple = xMultiple.Two;
//            } else if (xMultiple == Multiple.Two){
//            
//              texCoords[v] = new Vector2f(current.x, current.y);
//                xMultiple = xMultiple.Three;
//            } else if (xMultiple == Multiple.Three){
//            
//              texCoords[v] = new Vector2f();
//                xMultiple = xMultiple.Four;
//            } else if (xMultiple == Multiple.Four){
//            
//              texCoords[v] = new Vector2f();
//                xMultiple = xMultiple.One;
//            }
//            
//            
//            
//            v++;
//            //}
////            v+=2;
////            
////             for (int ii = 0; ii < 2; ii++){
////            texCoords[v + ii] = new Vector2f();
////            
////            }
////             v+=2;
//            }
//            
//            if (yMultiple == Multiple.One){
//            
//                yMultiple = Multiple.Two;
//            } else if (yMultiple == Multiple.Two){
//            
//                yMultiple = Multiple.Three;
//            }
//
//            } else {
//            
//                for (int x = 0; x < 32; x++){ 
//                
//                    texCoords[v] = new Vector2f();
//                
//                    v++;
//                }
//                
//                if (yMultiple == Multiple.Three){
//                
//                    yMultiple = Multiple.Four;
//                } else if (yMultiple == Multiple.Four){
//                
//                    yMultiple = Multiple.One;
//                }
//                
//            }
//            
//             
//        }
//
//         
//        System.out.println("txCoords Length: " + texCoords.length);
//             int in = 0;
//        for (int index = 0; index < indexes.length - 6; index+=12){
//        
//            if (in % 15 == 1 && in != 1){
//            
//                in++;
//            }
//            indexes[index] = in;
//            indexes[index + 1] = in + 1;
//            indexes[index + 2] = in + 16;//in + 7;
//            indexes[index + 3] = in + 1;
//            indexes[index + 4] = in + 17;//in + 8;
//            indexes[index + 5] = in + 16;//in + 7;
//            
//            indexes[index + 6] = 0;
//            indexes[index + 7] = 0;
//            indexes[index + 8] = 0;//in + 7;
//            indexes[index + 9] = 0;
//            indexes[index + 10] = 0;//in + 8;
//            indexes[index + 11] = 0;//in + 7;
//            
//            in++;
//            
//            
//        }
//         
//    }
//    
//    public void bake(){
//        
//        Vector3f[] vertices = new Vector3f[vectors.length];
//        
////        vectors[0].v = vectors[0].v.add(-0.1f, -0.1f, 0);
////        vectors[1].v = vectors[0].v.add(-0.1f, -0.1f, 0);
////        vectors[16].v = vectors[0].v.add(-0.1f, -0.1f, 0);
////        vectors[17].v = vectors[0].v.add(-0.1f, -0.1f, 0);
//        
//        int i = 0;
//        for (CurrencyVector v: vectors){
//        
//            System.out.println(i);
//            vertices[i] = v.v;
//            i++;
//        }
//        
//        mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
//        mesh.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoords));
//        mesh.setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createIntBuffer(indexes));
//        mesh.updateBound();
//        
//        System.out.println(Arrays.asList(vertices));
//        System.out.println(Arrays.asList(texCoords));
//        System.out.println(Arrays.toString(indexes));
//        
//        geom = new Geometry("Mesh", mesh);
//
//        Material mat = new Material(App.app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
//        mat.setColor("Color", ColorRGBA.White);
//        mat.setTexture("ColorMap", doc.getTexture());
//        
//        // 1. Render both front and back faces (disables back-face culling)
//        mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
//
//// 2. Enable alpha transparency blending
//        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
//
//        geom.setMaterial(mat);
//
//// 3. Put semi-transparent objects in the Transparent bucket so depth sorting works properly
//        geom.setQueueBucket(RenderQueue.Bucket.Transparent);
//        geom.setMaterial(mat);
//
//        
//        App.app.getRootNode().attachChild(geom);
//        
//        App.docs.put(geom, this);
//        
//    
//    }
//    
//}
