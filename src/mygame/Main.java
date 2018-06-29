package mygame;

import AI.AIControl;
import AI.SoundEmitterControl;
import animations.AIAnimationControl;
import animations.AdvAnimationManagerControl;
import animations.CharacterInputAnimationAppState;
import appstate.InputAppState;
import characters.AICharacterControl;
import characters.ChaseCamCharacter;
import characters.MyGameCharacterControl;
import com.jme3.app.Application;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingSphere;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.ChaseCamera;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import java.util.List;


public class Main extends SimpleApplication {
    //Class variables
    protected BulletAppState bulletAppState;//Pyhsics object
    private Vector3f normalGravity = new Vector3f(0, -9.81f, 9);//Gravity vector
    private Application app;
    private InputAppState jaimeAppState;
    private MyGameCharacterControl jaimeControl;
    private CameraNode camNode;
    public static Material lineMat;
    private Vector3f camLocation = new Vector3f(0,-20f,0);
    private Vector3f lookAtDirection = new Vector3f(0,-0.8f,-0.2f);
    private float camDistance = 30f;
    private AdvAnimationManagerControl animControl;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        //Add the scene
        Spatial scene = assetManager.loadModel("Scenes/newScene.j3o");
        rootNode.attachChild(scene);
        
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        
        //cam.lookAtDirection(camLocation, Vector3f.UNIT_Y);
        //camLocation.set(cam.getDirection().mult(-camDistance));
        //cam.setLocation(camLocation);
        
        lineMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        
         //createPlayerCharacter();
        createAICharacter();
        

        
//        characterNode.attachChild(model);

        // Add character node to the rootNode
//        rootNode.attachChild(characterNode);
        
        //Create the Physics World based on the Helper class
        PhysicsTestHelper.createPhysicsTestWorld(rootNode, assetManager, bulletAppState.getPhysicsSpace());
        
        //Create 5 different moveable boxes 
        PhysicsTestHelper.createMoveableBox(rootNode, assetManager,  bulletAppState.getPhysicsSpace(), 1f, "Interface/faceX.jpg", 0, -5, -3);
        PhysicsTestHelper.createMoveableBox(rootNode, assetManager,  bulletAppState.getPhysicsSpace(), 2f, "Interface/faceHearts.jpg", 3, -3, -3);
        PhysicsTestHelper.createMoveableBox(rootNode, assetManager,  bulletAppState.getPhysicsSpace(), 1f, "Interface/faceTongue.jpg", -3, 1, -3);
        PhysicsTestHelper.createMoveableBox(rootNode, assetManager,  bulletAppState.getPhysicsSpace(), 1.5f, "Interface/sillyFace.jpg", 5, 5, -3);
        PhysicsTestHelper.createMoveableBox(rootNode, assetManager,  bulletAppState.getPhysicsSpace(), 2.5f, "Interface/monsterFace.jpg", 4, 5, -3);
        PhysicsTestHelper.createMoveableBox(rootNode, assetManager,  bulletAppState.getPhysicsSpace(), 2f, "Interface/mustFace.jpg", -5, 10, -3);
        
        //Create 5 different imoveable spheres
        PhysicsTestHelper.createNonMoveableSphere(rootNode, assetManager, bulletAppState.getPhysicsSpace(),10 ,1, "Interface/rain1.jpg", -10, -5,-3);
        PhysicsTestHelper.createNonMoveableSphere(rootNode, assetManager, bulletAppState.getPhysicsSpace(),20 ,1, "Interface/rain2.jpg", -5, -2,-3);
        PhysicsTestHelper.createNonMoveableSphere(rootNode, assetManager, bulletAppState.getPhysicsSpace(),15 ,2, "Interface/rain3.jpg", 5, -3,-10);
        PhysicsTestHelper.createNonMoveableSphere(rootNode, assetManager, bulletAppState.getPhysicsSpace(),25 ,1, "Interface/rain4.jpg", 10, 5,10);
        PhysicsTestHelper.createNonMoveableSphere(rootNode, assetManager, bulletAppState.getPhysicsSpace(),22 ,2, "Interface/rain5.jpg", -15, -2,-5);
        PhysicsTestHelper.createNonMoveableSphere(rootNode, assetManager, bulletAppState.getPhysicsSpace(),13 ,1, "Interface/rain6.jpg", 15, -3,-3);
        
        //Add the Player to the world and use the customer character and input control classes
        //Node jaimeNode = (Node)assetManager.loadModel("Models/Jaime/Jaime.j3o");
        
        //ChaseCamCharacter charControl = new ChaseCamCharacter(0.5f, 2.5f, 8f);
        //charControl.setGravity(normalGravity);
        //charControl.setCamera(cam);
        
        //ChaseCamera chaseCam = new ChaseCamera(cam, jaimeNode, inputManager);
        //chaseCam.setDragToRotate(false);
        //chaseCam.setSmoothMotion(true);
        //chaseCam.setLookAtOffset(new Vector3f(0, 1f, 0));
        //chaseCam.setDefaultDistance(10f);
        //chaseCam.setMaxDistance(20f);
        //chaseCam.setMinDistance(10f);
        
        //chaseCam.setTrailingSensitivity(50);
        //chaseCam.setChasingSensitivity(1);
        //chaseCam.setRotationSpeed(1);
        //chaseCam.setDragToRotate(true);
       // chaseCam.setToggleRotationTrigger();
        
        //jaimeNode.addControl(charControl);
        //bulletAppState.getPhysicsSpace().add(charControl);
        
        //CharacterInputAnimationAppState appState = new CharacterInputAnimationAppState();
        //appState.addActionListener(charControl);
        //appState.addAnalogListener(charControl);
        //appState.setChaseCamera(chaseCam);
        
        //stateManager.attach(appState);
        //rootNode.attachChild(jaimeNode);
        //inputManager.setCursorVisible(false);
        
        //AdvAnimationManagerControl animControl = new AdvAnimationManagerControl("animations/animations-jaime.properties");
        //jaimeNode.addControl(animControl);
        
        //appState.addActionListener(animControl);
        //appState.addAnalogListener(animControl);
        
        //Add Ball Shooter
        //PhysicsTestHelper.createBallShooter(this,rootNode,bulletAppState.getPhysicsSpace());
        
        //Add a custom font and text to the scene
        //BitmapFont myFont = assetManager.loadFont("Interface/Fonts/Arial.fnt");

        //BitmapText hudText = new BitmapText(myFont, true);
        //hudText.setText("CMSC325 Moore Project 1");
       // hudText.setColor(ColorRGBA.Cyan);
        //hudText.setSize(20);
        //hudText.setSize(guiFont.getCharSet().getRenderedSize());
        
        //Set the text in the middle of the screen
        //hudText.setLocalTranslation(settings.getWidth() /2 , settings.getHeight() / 2 + hudText.getLineHeight(), 0f); //Positions text to middle of screen
        //guiNode.attachChild(hudText);
        
        
        //
        
        
    }
    
     private PhysicsSpace getPhysicsSpace() {
        return bulletAppState.getPhysicsSpace();
    }
 
    private void createAICharacter() {
        // Load model, attach to character node
        Node jaime = (Node) assetManager.loadModel("Models/Jaime/Jaime.j3o");
        
        jaime.setLocalScale(1.50f);
        
        Node mainPlayer = createPlayerCharacter();
        AICharacterControl AIcharacter = new AICharacterControl(0.3f, 2.5f, 8f);
        
        jaime.addControl(AIcharacter);
        getPhysicsSpace().add(AIcharacter);
        rootNode.attachChild(jaime);
        jaime.addControl(new AIControl());
        jaime.addControl(new AIAnimationControl());
        //Create camera for jaime
        //CameraNode camNode = new CameraNode("CamNode", cam);
        //camNode.setControlDir(CameraControl.ControlDirection.CameraToSpatial);
        
        //Create line of sight
        //Geometry g = new Geometry("", new Box(1,1,1));
        //g.setModelBound(new BoundingSphere(5f, Vector3f.ZERO));
        //g.updateModelBound();
        //g.setMaterial(lineMat);
        //camNode.attachChild(g);
        //camNode.addControl(new SoundEmitterControl());
        //getFlyByCamera().setMoveSpeed(25);
        //rootNode.attachChild(camNode);
        List<Spatial> targets = new ArrayList<Spatial>();
        //targets.add(camNode);
        targets.add(mainPlayer);
        
        jaime.getControl(AIControl.class).setState(AIControl.State.Follow);
        jaime.getControl(AIControl.class).setTargetList(targets);
        //jaime.getControl(AIControl.class).setTarget(camNode);
    }
     
  
     private Node createPlayerCharacter() {
        
        stateManager.attach(bulletAppState);
        //stateManager.detach(stateManager.getState(FlyCamAppState.class));
        
        //Node jaimeNode = (Node) assetManager.loadModel("Models/Jaime/Jaime.j3o");
        //jaimeNode.setLocalTranslation(12f, 0, 8f);
        //ChaseCamCharacter charControl = new ChaseCamCharacter(-4.5f, 2.5f, 0f);
        //MyGameCharacterControl charControl = new MyGameCharacterControl(0,-20f,0);
        
        //charControl.setGravity(normalGravity);
        //charControl.setCamera(cam);
        
        //ChaseCamera chaseCam = new ChaseCamera(cam, jaimeNode, inputManager);
        //chaseCam.setDragToRotate(false);
        //chaseCam.setSmoothMotion(true);
        //chaseCam.setLookAtOffset(new Vector3f(0, 1f, 0));
        //chaseCam.setDefaultDistance(7f);
        //chaseCam.setMaxDistance(8f);
        //chaseCam.setMinDistance(6f);
       
        //chaseCam.setTrailingSensitivity(50);        
        //chaseCam.setChasingSensitivity(10);
        //chaseCam.setRotationSpeed(10);
        //chaseCam.setDragToRotate(true);
        //chaseCam.setToggleRotationTrigger();

        //jaimeNode.addControl(charControl);
        //bulletAppState.getPhysicsSpace().add(charControl);

        //CharacterInputAnimationAppState appState = new CharacterInputAnimationAppState();
        
        //appState.addActionListener(charControl);
        //appState.addAnalogListener(charControl);
        //appState.setChaseCamera(chaseCam);
        //stateManager.attach(appState);
        //rootNode.attachChild(jaimeNode);
        //inputManager.setCursorVisible(false);
        
        //animControl = new AdvAnimationManagerControl("animations/animations-jaime.properties");
        //jaimeNode.addControl(animControl);
        
        //appState.addActionListener(animControl);
        //appState.addAnalogListener(animControl);
        
        //Add the Player to the world and use the customer character and input control classes
        Node jaimeNode = (Node)assetManager.loadModel("Models/Jaime/Jaime.j3o");
        
        ChaseCamCharacter charControl = new ChaseCamCharacter(0.5f, 2.5f, 8f);
        charControl.setGravity(normalGravity);
        charControl.setCamera(cam);
        
        ChaseCamera chaseCam = new ChaseCamera(cam, jaimeNode, inputManager);
        chaseCam.setDragToRotate(false);
        chaseCam.setSmoothMotion(true);
        chaseCam.setLookAtOffset(new Vector3f(0, 1f, 0));
        chaseCam.setDefaultDistance(10f);
       chaseCam.setMaxDistance(20f);
        chaseCam.setMinDistance(10f);
        
        chaseCam.setTrailingSensitivity(50);
        chaseCam.setChasingSensitivity(1);
        chaseCam.setRotationSpeed(1);
        chaseCam.setDragToRotate(true);
        chaseCam.setToggleRotationTrigger();
        
        jaimeNode.addControl(charControl);
        bulletAppState.getPhysicsSpace().add(charControl);
        
        CharacterInputAnimationAppState appState = new CharacterInputAnimationAppState();
        appState.addActionListener(charControl);
       appState.addAnalogListener(charControl);
        appState.setChaseCamera(chaseCam);
        
        stateManager.attach(appState);
        rootNode.attachChild(jaimeNode);
        inputManager.setCursorVisible(false);
        
        AdvAnimationManagerControl animControl = new AdvAnimationManagerControl("animations/animations-jaime.properties");
        jaimeNode.addControl(animControl);
        
        appState.addActionListener(animControl);
        appState.addAnalogListener(animControl);
        
        return jaimeNode;
    }


    @Override
    public void simpleUpdate(float tpf) {
          
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
