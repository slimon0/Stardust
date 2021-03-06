package umiker9.stardust2d.math.geometry;

import umiker9.stardust2d.math.Vec2;
import umiker9.stardust2d.math.collision.Collision2D;

/**
 * Created by Notezway on 07.12.2015.
 */
public class CircleShape extends Shape {

    private Vec2 center;
    private double radius;

    public CircleShape(Vec2 center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public double getSize() {
        return 2 * radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public Vec2 getCenterPoint() {
        return center;
    }

    @Override
    public void center() {
        center.setX(0);
        center.setY(0);
    }

    @Override
    public void scale(double factor) {
        radius *= factor;
    }

    @Override
    public void rotate(double rad) {
        //nothing
    }

    @Override
    public boolean doesCollide(Shape another, Vec2 pos1, Vec2 pos2) {
        if(another instanceof CircleShape) {
            return Collision2D.isCirclesIntersect(pos1.add(center), radius, pos2.add(((CircleShape)another).center),
                    ((CircleShape)another).radius);
        } else if(another instanceof PolygonShape) {
            PolygonShape polygon = (PolygonShape) another;
            Vec2[] points = polygon.getPoints();
            Vec2[] shifted = new Vec2[points.length];
            for(int i = 0; i < points.length; i++) {
                shifted[i] = points[i].add(pos2);
            }
            return Collision2D.getShapeWithCircleCollision(shifted, pos1.add(center), radius).length > 0;
        }
        return false;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
