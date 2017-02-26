/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twoslits;

/**
 *
 * @author nicho_000
 */
public class Vector {
    double x, y, z;
    public Vector() {
        x = y = z = 0;
    }
    public Vector(double x, double y, double z) {
        this.x = x; this.y = y; this.z = z;
    }
    public Vector(Vector v) {
        x = v.getX(); y = v.getY(); z = v.getZ();
    }
    public void replaceWith(Vector v) {
        x = v.getX(); y = v.getY(); z = v.getZ();
    }
    public Vector copy() { return new Vector(x,y,z); }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setZ(double z) { this.z = z; }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }
    public double magnitude() { return Math.sqrt(x*x+y*y+z*z); }
    public double distanceFrom(Vector v) { return Math.sqrt(x*v.getX()+y*v.getY()-z*v.getZ()); }    
    public double angleWith(Vector v) { 
        Vector vec = new Vector(x,y,z);
        v.normalized();
        vec.normalized();
        return Math.acos(vec.dotProduct(v));//magnitudes become one when normalised /(magnitude()*v.magnitude())); 
    }    
    public double componentAcross(Vector v) { return dotProduct(v)/v.magnitude(); }    
    public void normalized() {
        double m = magnitude();
        x /= m;
        y /= m;
        z /= m;
    }
    public void add(Vector v) {
        x += v.getX();
        y += v.getY();
        z += v.getZ();
    }
    public double dotProduct(Vector v) { return x*v.getX()+y*v.getY()+z*v.getZ(); }
    public Vector crossProduct(Vector v) {
        //The method presume the vectors start from (0,0,0)
        Vector result = new Vector();
        
        result.setX(y*v.getZ()-z*v.getY());
        //The Y component is returned with the minus computed
        result.setY(z*v.getX()-x*v.getZ());
        result.setZ(x*v.getY()-y*v.getX());
 
        return result;
    }
    public void transfer(Vector origin) {
        x -= origin.getX();
        y -= origin.getY();
        z -= origin.getZ();
    }
    public void reflect(Vector normal) {
        double dot = dotProduct(normal);
        x -= 2*dot*normal.getX();
        y -= 2*dot*normal.getY();
        z -= 2*dot*normal.getZ();
    }
    //positive angle is counterclockwise
    public void rotateZ(double cos, double sin) {
        //xx is horizontal, yy is vetical
        double temp = x;        
        x = temp*cos-y*sin;
        y = y*cos+temp*sin;
    }
    public void rotateY(double cos, double sin) {
        //zz is horizontal, xx is vertical 
        double temp = z;        
        z = temp*cos-x*sin;
        x = x*cos+temp*sin;
    }
    public void rotateX(double cos, double sin) {
        //yy horisontal, zz vertical
        double temp = y;        
        y = temp*cos-z*sin;
        z = z*cos+temp*sin;
    }
}
