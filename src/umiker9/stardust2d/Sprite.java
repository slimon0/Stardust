package umiker9.stardust2d;

import umiker9.stardust2d.graphics.lwjgl2.Renderer;
import umiker9.stardust2d.graphics.lwjgl2.Texture2D;

/**
 * Created by miker9 on 23/11/2015.
 */
public class Sprite extends GameObject {
    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected double originX;
    protected double originY;
    protected double rotation;
    protected Texture2D texture;

    public Sprite() {

    }

    public Sprite(Texture2D texture) {
        this(texture, 0, 0, texture.getWidth(), texture.getHeight());
    }

    public Sprite(Texture2D texture, double x, double y) {
        this(texture, x, y, texture.getWidth(), texture.getHeight());
    }

    public Sprite(Texture2D texture, double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.originX = width/2;
        this.originY = height/2;
        this.texture = texture;
    }

    @Override
    public void render(Renderer renderer) {
        renderer.pushMatrix();
        renderer.translate(x, y);
        renderer.rotate(rotation);

        renderer.drawTexturedQuad(-originX, -originY, width, height, texture);

        renderer.popMatrix();
    }

    @Override
    public void update(long delta) {

    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getOriginX() {
        return originX;
    }

    public void setOriginX(double originX) {
        this.originX = originX;
    }

    public double getOriginY() {
        return originY;
    }

    public void setOriginY(double originY) {
        this.originY = originY;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public Texture2D getTexture() {
        return texture;
    }

    public void setTexture(Texture2D texture) {
        this.texture = texture;
    }
}
