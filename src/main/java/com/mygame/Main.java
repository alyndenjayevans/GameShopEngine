package com.mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.export.binary.BinaryLoader;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Triangle;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.control.CameraControl;
import com.jme3.scene.mesh.IndexBuffer;
import com.jme3.scene.shape.Box;
import com.jme3.util.BufferUtils;
import java.nio.FloatBuffer;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    // Define the offset relative to the camera (X = right, Y = up, Z = forward in jME3)
//private final Vector3f localOffset = new Vector3f(0.5f, -0.3f, 5.5f);
//private final Vector3f worldPos = new Vector3f(1,0,-1);
//public CameraNode camNode;
//public CurrencySurface surface;
    
    // Offset in camera-local space: right +0.4, down -0.3, forward +1.5
    private final Vector3f localOffset = new Vector3f(0.4f, -0.3f, 1.5f);
 
    public CurrencySurface surface;
    
    public GameShopDoc doc;
//    public GameShopDoc selector;
//    public GameShopDoc buttons;
    
    public GameShopDoc create;
    public GameShopDoc mesh;
    public GameShopDoc atmsMode;
    public GameShopDoc animate;
    public GameShopDoc save;
    
    public GameShopDoc createUI;
    public GameShopDoc meshUI;
    public GameShopDoc atmsUI;
    public GameShopDoc animateUI;
    public GameShopDoc saveUI;
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }
    
    private final ActionListener clickActionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("ClickMesh") && isPressed) {
                pickMeshAtCursor(true);
            }
        }
    };
    
    private final AnalogListener clickAnalogListener = new AnalogListener() {
//        @Override
//        public void onAction(String name, boolean isPressed, float tpf) {
//            if (name.equals("ClickMesh") && isPressed) {
//                pickMeshAtCursor();
//            }
//        }

        @Override
        public void onAnalog(String name, float f, float f1) {
        //    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        if (name.equals("ClickMesh")) {
                pickMeshAtCursor(false);
            }
        }
    };

    @Override
    public void simpleInitApp() {
        
        App app = new App(this);
        
        assetManager.registerLoader(BinaryLoader.class, "atms");
        
         
        
        flyCam.setEnabled(false);
        inputManager.setCursorVisible(true);
        
        // Bind left mouse click
        inputManager.addMapping("ClickMesh", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(clickActionListener, "ClickMesh");
        
        inputManager.addListener(clickAnalogListener, "ClickMesh");
        
        
        //System.out.println("DeCasteljaus " + CurrencySurface.deCasteljaus(new Vector3f[]{new Vector3f(0,0,0), new Vector3f(1,0,0), new Vector3f(2,2,0), new Vector3f(3,1,0)}, 0.5f));
        
        ATMS atms = new ATMS(256,256);
        // Loads "assets/Data/level1.sav"
        ATMS loadAtms = (ATMS) assetManager.loadAsset("ATMS/Fonts/Default/L.font.atms");

        atms.data = loadAtms.data;
        //atms.clear((byte)0, (byte)0, (byte)255, (byte)255);
        //atms.drawCircle(128, 128, 64, (byte)255, (byte)255, (byte)255, (byte)255);
       atms.update();
 
//        Box b = new Box(1, 1, 1);
//        Geometry geom = new Geometry("Box", b);
//
//        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//        mat.setColor("Color", ColorRGBA.White);
//        mat.setTexture("ColorMap", atms.getTexture());
//        geom.setMaterial(mat);
        
/*
        surface = new CurrencySurface(CurrencySurface.genSurfaceVectors(new Vector3f(0,0,0), new Vector3f(1,1,0)), atms, "CurrencySurface");
 
//        surface.controlPoints[6] = surface.controlPoints[6].add(new Vector3f(0,0,1));
//        surface.controlPoints[7] = surface.controlPoints[7].add(new Vector3f(0,0,1));
//        surface.controlPoints[8] = surface.controlPoints[8].add(new Vector3f(0,0,1));
        
        surface.bake();
        */

 doc = new GameShopDoc(CurrencySurface.genSurfaceVectors(new Vector3f(0,0,0), new Vector3f(4,4,0)), atms, "Doc");
 
//        surface.controlPoints[6] = surface.controlPoints[6].add(new Vector3f(0,0,1));
//        surface.controlPoints[7] = surface.controlPoints[7].add(new Vector3f(0,0,1));
//        surface.controlPoints[8] = surface.controlPoints[8].add(new Vector3f(0,0,1));
        //doc.words.add(new GameShopWord(word, cursorIndex, position));
      
        doc.words.add(new GameShopWord("CREATE", doc.cursorIndex, new Vector3f(-7f, 3.75f, 0f)){
        
            @Override
            public void onClick(Vector2f uv){
            
                App.selectMode(Mode.CREATE);
                //System.out.println("GameShop Rules");
            }
        });
        doc.registerWord("CREATE");
        
        doc.words.add(new GameShopWord("MESH", doc.cursorIndex, new Vector3f(-4f, 3.75f, 0f)){
        
            @Override
            public void onClick(Vector2f uv){
            
                App.selectMode(Mode.MESH);
                //System.out.println("GameShop Rules");
            }
        });
        doc.registerWord("MESH");
        
        doc.words.add(new GameShopWord("ATMS", doc.cursorIndex, new Vector3f(-1f, 3.75f, 0f)){
        
            @Override
            public void onClick(Vector2f uv){
            
                App.selectMode(Mode.ATMS);
                //System.out.println("ATMS Rules");
            }
        });
        doc.registerWord("ATMS");
        
        doc.words.add(new GameShopWord("ANIMATE", doc.cursorIndex, new Vector3f(2f, 3.75f, 0f)){
        
            @Override
            public void onClick(Vector2f uv){
            
                //App.selectMode(Mode.ANIMATE);
                System.out.println("Animate Rules");
            }
        });
        doc.registerWord("ANIMATE");
        
        doc.words.add(new GameShopWord("SAVE", doc.cursorIndex, new Vector3f(5f, 3.75f, 0f)){
        
            @Override
            public void onClick(Vector2f uv){
            
                //App.selectMode(Mode.SAVE);
                System.out.println("Save");
            }
        });
        doc.registerWord("SAVE");
//        doc.registerWord("WAS", new Vector3f(-3f, 3f, 0f));
//        doc.registerWord("HERE", new Vector3f(-2f, 2f, 0f));
//        doc.registerWord("COSIGNED", new Vector3f(-1f, 1f, 0f));
//        doc.registerWord("RUNE", new Vector3f(0f, 0, 0f));
//        doc.registerWord("AND", new Vector3f(1f, -1f, 0f));
//        doc.registerWord("LYNDENJAYEVANS", new Vector3f(2f, -2f, 0f));
       
        doc.drawAllWords();
      //  doc.moveCharOffsetToWordPosition();
//        for (GameShopChar c: doc.charPool){
//        System.out.println(c.character);
//        }
//        for (Vector4f v: doc.characterTexCoordMap.values()){
//        System.out.println(v);
//        }
 
        doc.bake();
        
        create = new GameShopDoc(CurrencySurface.genSurfaceVectors(new Vector3f(0,0,0), new Vector3f(4,4,0)), atms, "Create");
 
        create.words.add(new GameShopWord("CREATESTUFFHERE", create.cursorIndex, new Vector3f(-7f, 3.0f, 0f)){
        
            @Override
            public void onClick(Vector2f uv){
            
                System.out.println("Create Things");
            }
        });
        create.registerWord("CREATESTUFFHERE");
        
        
         create.drawAllWords();
      //  doc.moveCharOffsetToWordPosition();
//        for (GameShopChar c: doc.charPool){
//        System.out.println(c.character);
//        }
//        for (Vector4f v: doc.characterTexCoordMap.values()){
//        System.out.println(v);
//        }
 
        create.bake();
        
          atmsMode = new GameShopDoc(CurrencySurface.genSurfaceVectors(new Vector3f(0,0,0), new Vector3f(4,4,0)), atms, "ATMS");
 
        atmsMode.words.add(new GameShopWord("ATMSSTUFFHERE", atmsMode.cursorIndex, new Vector3f(-7f, 3.0f, 0f)){
        
            @Override
            public void onClick(Vector2f uv){
            
                //App.selectMode(Mode.ATMS);
                System.out.println("ATMS Stuff");
            }
        });
        atmsMode.registerWord("ATMSSTUFFHERE");
        
        
         atmsMode.drawAllWords();
      //  doc.moveCharOffsetToWordPosition();
//        for (GameShopChar c: doc.charPool){
//        System.out.println(c.character);
//        }
//        for (Vector4f v: doc.characterTexCoordMap.values()){
//        System.out.println(v);
//        }
 
        atmsMode.bake();
        
        
//          create = new GameShopDoc(CurrencySurface.genSurfaceVectors(new Vector3f(0,0,0), new Vector3f(4,4,0)), atms, "Create");
// 
//        create.words.add(new GameShopWord("CREATESTUFFHERE", doc.cursorIndex, new Vector3f(-7f, 3.0f, 0f)){
//        
//            @Override
//            public void onClick(Vector2f uv){
//            
//                System.out.println("Create Things");
//            }
//        });
//        create.registerWord("CREATESTUFFHERE");
//        
//        
//         create.drawAllWords();
//      //  doc.moveCharOffsetToWordPosition();
////        for (GameShopChar c: doc.charPool){
////        System.out.println(c.character);
////        }
////        for (Vector4f v: doc.characterTexCoordMap.values()){
////        System.out.println(v);
////        }
// 
//        create.bake();
//        
//          create = new GameShopDoc(CurrencySurface.genSurfaceVectors(new Vector3f(0,0,0), new Vector3f(4,4,0)), atms, "Create");
// 
//        create.words.add(new GameShopWord("CREATESTUFFHERE", doc.cursorIndex, new Vector3f(-7f, 3.0f, 0f)){
//        
//            @Override
//            public void onClick(Vector2f uv){
//            
//                System.out.println("Create Things");
//            }
//        });
//        create.registerWord("CREATESTUFFHERE");
//        
//        
//         create.drawAllWords();
//      //  doc.moveCharOffsetToWordPosition();
////        for (GameShopChar c: doc.charPool){
////        System.out.println(c.character);
////        }
////        for (Vector4f v: doc.characterTexCoordMap.values()){
////        System.out.println(v);
////        }
// 
//        create.bake();
//        
//          create = new GameShopDoc(CurrencySurface.genSurfaceVectors(new Vector3f(0,0,0), new Vector3f(4,4,0)), atms, "Create");
// 
//        create.words.add(new GameShopWord("CREATESTUFFHERE", doc.cursorIndex, new Vector3f(-7f, 3.0f, 0f)){
//        
//            @Override
//            public void onClick(Vector2f uv){
//            
//                System.out.println("Create Things");
//            }
//        });
//        create.registerWord("CREATESTUFFHERE");
//        
//        
//         create.drawAllWords();
//      //  doc.moveCharOffsetToWordPosition();
////        for (GameShopChar c: doc.charPool){
////        System.out.println(c.character);
////        }
////        for (Vector4f v: doc.characterTexCoordMap.values()){
////        System.out.println(v);
////        }
// 
       //create.bake();
        
        App.selectors.put(Mode.CREATE, create);
        App.selectors.put(Mode.ATMS, atmsMode);
        
        App.selectMode(Mode.CREATE);
        //App.selectors.put("Save", save);
        //doc.geom.scale(1.1f);
        //doc.geom.scale(10);
        
        
//        CurrencySurface surface1 = new CurrencySurface(CurrencySurface.genSurfaceVectors(new Vector3f(3, 0, 0), new Vector3f(4, 1, 0)), atms, "CurrencySurface1");

       // surface1.bake();
        //rootNode.attachChild(surface.geom);
        
       // App.fontGeom = doc.geom;
        
        //surface.geom.setLocalScale(100);
        
        //surface.geom.setLocalTranslation(100, 100, 0);
        
       // guiNode.attachChild(surface.geom);
        
        
//        ATMS atmsForward = new ATMS(256,256);
//        
//        atmsForward.clear((byte)0, (byte)255, (byte)0, (byte)255);
//        //atms.drawCircle(128, 128, 64, (byte)255, (byte)255, (byte)255, (byte)255);
//        atmsForward.update();
//        
//        ATMS atmsBackward = new ATMS(256,256);
//        atmsBackward.clear((byte)255, (byte)0, (byte)0, (byte)255);
//        //atms.drawCircle(128, 128, 64, (byte)255, (byte)255, (byte)255, (byte)255);
//        atmsBackward.update();
        
        
//        CurrencySurface forward = new CurrencySurface(CurrencySurface.genSurfaceVectors(new Vector3f(0,0,0), new Vector3f(1,1,0)), atmsForward, "Forward");
// 
//        forward.bake();
//        
//        CurrencySurface backward = new CurrencySurface(CurrencySurface.genSurfaceVectors(new Vector3f(0, 0, 0), new Vector3f(1, 1, 0)), atmsBackward, "Backward");
//
//        backward.bake();
        
//        rootNode.attachChild(forward.geom);
//        rootNode.attachChild(backward.geom);
//        
//        forward.geom.move(1, -1, 0);
//        backward.geom.move(-1,-1, 0);
        
        
//        GameShopDoc doc = new GameShopDoc("Document");
//        doc.bake();
//
//        CameraNode camNode = new CameraNode("CamNode", cam);
//        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
//        rootNode.attachChild(camNode);
//        camNode.attachChild(doc.geom);
////        camNode.attachChild(surface.geom);
//        doc.geom.setLocalTranslation(0, 0, 0);
//        doc.geom.move(0, 0, 5);
//        doc.geom.rotate(0, FastMath.DEG_TO_RAD * 180, 0);
//        doc.geom.move(0, 0, 0);
//        //camNode.setLocalTranslation(0, 0, -10);
//       //camNode.lookAt(new Vector3f(0, 0, -10), Vector3f.UNIT_Y);
//        camNode.lookAt(new Vector3f(0,0,5), Vector3f.UNIT_Y);

        
        
//        camNode.attachChild(surface1.geom);
//        surface1.geom.setLocalTranslation(0, 0, 0);
//        surface1.geom.move(0, 0, 5);


        // rootNode.attachChild(geom);
       //System.out.println(geom.getLocalTranslation());
       //System.out.println(surface.geom.getLocalTranslation());
    }
    
    private void pickMeshAtCursor(boolean click) {
        // 1. Get current 2D mouse position in screen pixels
        Vector2f click2d = inputManager.getCursorPosition();

        // 2. Convert screen coordinates (with z=0 near plane, z=1 far plane) into 3D world space
        Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
        Vector3f farPoint = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f);
        Vector3f direction = farPoint.subtractLocal(click3d).normalizeLocal();

        // 3. Build the ray and check collisions against rootNode (or a specific Node/Geometry)
        Ray ray = new Ray(click3d, direction);
        CollisionResults results = new CollisionResults();
        rootNode.collideWith(ray, results);

        if (results.size() > 0) {
        CollisionResult closest = results.getClosestCollision();
        Geometry hitGeom = closest.getGeometry();
        Mesh hitMesh = hitGeom.getMesh();
        int triIndex = closest.getTriangleIndex();

        // 3. Get the 3 vertex indices of the hit triangle
        IndexBuffer ib = hitMesh.getIndexBuffer();
        int i0 = ib.get(triIndex * 3);
        int i1 = ib.get(triIndex * 3 + 1);
        int i2 = ib.get(triIndex * 3 + 2);

        // 4. Get vertex positions in Local Space
        FloatBuffer posBuf = hitMesh.getFloatBuffer(VertexBuffer.Type.Position);
        Vector3f v0 = new Vector3f();
        Vector3f v1 = new Vector3f();
        Vector3f v2 = new Vector3f();
        BufferUtils.populateFromBuffer(v0, posBuf, i0);
        BufferUtils.populateFromBuffer(v1, posBuf, i1);
        BufferUtils.populateFromBuffer(v2, posBuf, i2);

        // 5. Convert world contact point to Local Space
        Vector3f localContact = hitGeom.worldToLocal(closest.getContactPoint(), null);

        // 6. Compute barycentric weights (stored in x, y, z of baryCoords)
        Vector3f baryCoords = getBarycentric(localContact, v0, v1, v2);

        // 7. Fetch the 3 UV coordinates from TexCoord buffer
        FloatBuffer uvBuf = hitMesh.getFloatBuffer(VertexBuffer.Type.TexCoord2);
        Vector2f uv0 = new Vector2f();
        Vector2f uv1 = new Vector2f();
        Vector2f uv2 = new Vector2f();
        BufferUtils.populateFromBuffer(uv0, uvBuf, i0);
        BufferUtils.populateFromBuffer(uv1, uvBuf, i1);
        BufferUtils.populateFromBuffer(uv2, uvBuf, i2);

       

        // 8. Interpolate final UV: (uv0 * bary.x) + (uv1 * bary.y) + (uv2 * bary.z)
        float u = uv0.x * baryCoords.x + uv1.x * baryCoords.y + uv2.x * baryCoords.z;
        float v = uv0.y * baryCoords.x + uv1.y * baryCoords.y + uv2.y * baryCoords.z;

       // System.out.println("UV " + new Vector2f(u, v));
        
        if (click){
        
            App.surfaceCollisions.get(hitGeom).onClick(new Vector2f(u, v));
        
        } else {
        
            App.surfaceCollisions.get(hitGeom).onDrag(new Vector2f(u, v));
      
            
        }
    }

        // 4. Inspect the closest hit
//        if (results.size() > 0) {
//            CollisionResult closest = results.getClosestCollision();
//            Geometry hitGeometry = closest.getGeometry();
//            Vector3f worldHitPoint = closest.getContactPoint();
//            Vector3f hitNormal = closest.getContactNormal();
//            float distance = closest.getDistance();
//            
//            System.out.println("UV Surface Collision: " + App.surfaceCollisions.get(hitGeometry).getSurfacePoint(worldHitPoint, closest.getTriangle(new Triangle())));
//
//            System.out.println("Hit mesh: " + hitGeometry.getName());
//            System.out.println("World hit point: " + worldHitPoint);
////            System.out.println("Surface normal: " + hitNormal);
////            System.out.println("Distance from ray origin: " + distance);
//        }
    }

    

    @Override
    public void simpleUpdate(float tpf) {
        
//        doc.geom.setLocalTranslation(cam.getLocation().add(cam.getDirection().mult(4)));
//        doc.geom.setLocalRotation(cam.getRotation());
//        doc.geom.rotate(0, FastMath.DEG_TO_RAD * 180, 0);
//.add(new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD * 180, Vector3f.UNIT_Y)));
        
//        Vector3f camPos = cam.getLocation();
//        Quaternion camRot = cam.getRotation();
//
//        // Rotate the local offset by the camera's orientation, then add camera position
//        Vector3f worldOffset = camRot.mult(localOffset);
//        surface.geom.setLocalTranslation(camPos.add(worldOffset));
       // surface.geom.setLocalRotation(camRot);
        
        //TODO: add update code
//         // 1. Project the local offset from cameraNode into rootNode (world) space
//    camNode.localToWorld(localOffset, worldPos);
//    surface.geom.setLocalTranslation(worldPos);
//
//    // 2. Match the cameraNode's orientation
    //surface.geom.setLocalRotation(cam.get.getWorldRotation());
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    public static Vector3f getBarycentric(Vector3f p, Vector3f a, Vector3f b, Vector3f c) {
    Vector3f v0 = b.subtract(a);
    Vector3f v1 = c.subtract(a);
    Vector3f v2 = p.subtract(a);

    float d00 = v0.dot(v0);
    float d01 = v0.dot(v1);
    float d11 = v1.dot(v1);
    float d20 = v2.dot(v0);
    float d21 = v2.dot(v1);

    float denom = d00 * d11 - d01 * d01;
    if (denom == 0f) {
        return new Vector3f(1, 0, 0); // Fallback for degenerate triangle
    }

    float v = (d11 * d20 - d01 * d21) / denom;
    float w = (d00 * d21 - d01 * d20) / denom;
    float u = 1.0f - v - w;

    return new Vector3f(u, v, w);
}

}

