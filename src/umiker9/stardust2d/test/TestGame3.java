package umiker9.stardust2d.test;

import org.lwjgl.opengl.Display;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;
import umiker9.stardust2d.BasicGame;
import umiker9.stardust2d.GameObject;
import umiker9.stardust2d.Scene;
import umiker9.stardust2d.graphics.Color;
import umiker9.stardust2d.graphics.lwjgl2.Texture2D;
import umiker9.stardust2d.graphics.lwjgl2.TextureLoader;
import umiker9.stardust2d.math.Vec2;
import umiker9.stardust2d.math.geometry.PolygonShape;
import umiker9.stardust2d.physics.Ball;
import umiker9.stardust2d.physics.PhysicalBody;
import umiker9.stardust2d.physics.Polygon;
import umiker9.stardust2d.systems.io.FileIO;
import umiker9.stardust2d.systems.io.Resource;
import umiker9.stardust2d.systems.log.AdvSoundSystemLogger;
import umiker9.stardust2d.systems.log.LogLevel;
import umiker9.stardust2d.systems.log.Logger;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Notezway on 11.12.2015.
 */
public class TestGame3 extends BasicGame {

    private static TestGame3 instance;
    private Texture2D tex;
    SoundSystem soundSystem;

    public static void main(String args[]) {
        instance = new TestGame3();
        Display.setLocation(350, 20);
        instance.start();
    }

    protected void init() {
        super.init();
        String path = "Assets/jackal.png";
        tex = TextureLoader.loadTexture(new Resource(path, FileIO.getFileAsBytes(path)));
        Logger.getInstance().setMinLevel(LogLevel.DEBUG);

        Ball ball1 = new Ball(new Vec2(-200, 0), 0, 10, 200);
        Ball ball2 = new Ball(new Vec2(200, 0), 0, 10, 200);
        Ball ball3 = new Ball(new Vec2(0, 0), 0, 30, 9000);
        Ball ball4 = new Ball(new Vec2(-325, 0), 0, 2, 5);
        Ball ball5 = new Ball(new Vec2(325, 0), 0, 2, 5);
        Ball ball6 = new Ball(new Vec2(0, 100), 0, 5, 1);
        ball1.setVelocity(new Vec2(120, 0));
        ball2.setVelocity(new Vec2(-120, -10));
        ball3.setVelocity(new Vec2(0, 0));
        ball4.setVelocity(new Vec2(0, 120 + 63));
        ball5.setVelocity(new Vec2(0, -120 - 63));
        ball6.setVelocity(new Vec2(-180, 0));
        ball1.setColor(Color.RED);
        ball2.setColor(Color.BLUE);
        ball3.setColor(Color.RED.mix(Color.GREEN));
        ball4.setColor(Color.WHITE);
        ball5.setColor(Color.WHITE);
        ball6.setColor(Color.BLUE.mix(Color.RED));

        PolygonShape quad = new PolygonShape(
                new Vec2(-10, -10),
                new Vec2(-10, 10),
                new Vec2(10, 10),
                new Vec2(10, -10)
        );

        Polygon p1 = new Polygon(new Vec2(60, 100), 100, 100, 20, quad);
        Polygon p2 = new Polygon(new Vec2(400, 100), 0, 100, 20, quad);
        p2.setVelocity(new Vec2(-100, 0));


        Scene scene = new Scene() {
            @Override
            public void update(long delta) {
                super.update(delta);
                GameObject actor1, actor2;

                for(int i = 0; i < actors.size(); i++) {
                    actor1 = actors.get(i);
                    for(int j = i + 1; j < actors.size(); j++) {
                        actor2 = actors.get(j);
                        if(actor1 instanceof PhysicalBody && actor2 instanceof PhysicalBody) {
                            PhysicalBody body1 = (PhysicalBody) actor1;
                            PhysicalBody body2 = (PhysicalBody) actor2;
                            //System.out.println(body2.getShapes()[0].getClass().getName());
                            boolean collide = body1.doesCollide(body2);
                            if (collide) {
                                System.out.println("collide!");
                                body1.onCollision(body2);
                            }
                            if(!collide) {
                                double K = body1.getMass() * body2.getMass() * 500;
                                double Rsq = body2.getPosition().subtract(body1.getPosition()).getLengthSq();
                                //body1.applyForce(body2.getPosition().subtract(body1.getPosition()).normalize().scale(K / Rsq));
                                //body2.applyForce(body1.getPosition().subtract(body2.getPosition()).normalize().scale(K / Rsq));
                            }
                        }
                    }
                }
            }
        };

        scene.add(ball1);
        scene.add(ball2);
        //scene.add(ball3);
        //scene.add(ball4);
        //scene.add(ball5);
        //scene.add(ball6);
        scene.add(p1);
        scene.add(p2);
        setCurrentScene(scene);

        SoundSystemConfig.setLogger(new AdvSoundSystemLogger(Logger.getInstance()));
        try {
            soundSystem = new SoundSystem(LibraryLWJGLOpenAL.class);
            SoundSystemConfig.setCodec("wav", CodecWav.class);
        } catch (SoundSystemException e) {
            e.printStackTrace();
        }
        /*try {
            soundSystem.backgroundMusic("background", new URL("file:///D:/Dev/Projects/Standalone/Stardust/Assets/Tobu_Colors.wav"), "Tobu_Colors.wav", true);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        soundSystem.setVolume("background", 0.01F);*/

    }

    @Override
    protected void exit() {
        soundSystem.cleanup();
        super.exit();
    }

    public TestGame3() {
        super(1000, 680, "Test game 3");
    }
}