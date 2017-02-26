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
public class Rectangle {
    Vector v1, v2, v3, v4;
    double A, B, C, D;
    //Front side view
    //                 v1,D (front up)
    //        v2,C                         counterclockwise is facing up
    //        v3,B
    //                 v4.A (front down)
    
    //--------------------------------------------------------------------------
    public Rectangle() {
    }    
    public Rectangle(Vector v1, Vector v2, Vector v3, Vector v4) {
        this.v1 = new Vector(); this.v1.replaceWith(v1);
        this.v2 = new Vector(); this.v2.replaceWith(v2);
        this.v3 = new Vector(); this.v3.replaceWith(v3);
        this.v4 = new Vector(); this.v4.replaceWith(v4);
        //For form: Ax + By + Cz = D
        A = (v2.getY()-v1.getY())*(v3.getZ()-v1.getZ())-(v2.getZ()-v1.getZ())*(v3.getY()-v1.getY());
        B = (v3.getX()-v1.getX())*(v2.getZ()-v1.getZ())-(v2.getX()-v1.getX())*(v3.getZ()-v1.getZ());
        C = (v2.getX()-v1.getX())*(v3.getY()-v1.getY())-(v3.getX()-v1.getX())*(v2.getY()-v1.getY());
        D = A*v1.getX()+B*v1.getY()+C*v1.getZ();
    }
    //--------------------------------------------------------------------------
    public double getA() { return A; }
    public double getB() { return B; }
    public double getC() { return C; }
    public double getD() { return D; }
    public Vector getV1() { return v1; }
    public Vector getV2() { return v2; }
    public Vector getV3() { return v3; }
    public Vector getV4() { return v4; }
    public Vector getNormal() { 
        Vector point = new Vector(A,B,C);
        point.normalized(); 
        return point;
    }
    //--------------------------------------------------------------------------
    public Vector intersectionPoint(Line line) {
        Vector point = new Vector();
        double t = (D-A*line.start.getX()-B*line.start.getY()-C*line.start.getZ()) / 
                   (A*(line.end.getX()-line.start.getX())+B*(line.end.getY()-line.start.getY())+C*(line.end.getZ()-line.start.getZ()));
        point.setX(line.start.getX()+t*(line.end.getX()-line.start.getX()));
        point.setY(line.start.getY()+t*(line.end.getY()-line.start.getY()));
        point.setZ(line.start.getZ()+t*(line.end.getZ()-line.start.getZ()));        
        return point;
    }
    //--------------------------------------------------------------------------
    boolean pointInRectangle(Vector point){
        double totalAngle;
        double a,b;
        
        a = lengthPointV1(point); b = lengthPointV2(point);
        totalAngle = Math.acos((a*a+b*b-lengthV12()*lengthV12())/(2*a*b));
        
        a = lengthPointV3(point); b = lengthPointV2(point);
        totalAngle += Math.acos((a*a+b*b-lengthV23()*lengthV23())/(2*a*b));
        
        a = lengthPointV3(point); b = lengthPointV4(point);
        totalAngle += Math.acos((a*a+b*b-lengthV34()*lengthV34())/(2*a*b));
        
        a = lengthPointV1(point); b = lengthPointV4(point);
        totalAngle += Math.acos((a*a+b*b-lengthV41()*lengthV41())/(2*a*b));

        if (Math.abs(totalAngle - 2*Math.PI) < 0.0001) return true;
        else return false;
    }
    //--------------------------------------------------------------------------
    public double dihydral(Rectangle rect){
        Vector normalRect = new Vector(), thisRect = new Vector();
        normalRect.replaceWith(rect.getNormal());
        thisRect.replaceWith(this.getNormal());
        
        return Math.acos(normalRect.dotProduct(thisRect)/(normalRect.magnitude()*thisRect.magnitude()));     
    }
    //--------------------------------------------------------------------------
    public double lengthV12() {
        return Math.sqrt((v1.getX()-v2.getX())*(v1.getX()-v2.getX())+
                         (v1.getY()-v2.getY())*(v1.getY()-v2.getY())+
                         (v1.getZ()-v2.getZ())*(v1.getZ()-v2.getZ()));
    }
    public double lengthV23() {
        return Math.sqrt((v3.getX()-v2.getX())*(v3.getX()-v2.getX())+
                         (v3.getY()-v2.getY())*(v3.getY()-v2.getY())+
                         (v3.getZ()-v2.getZ())*(v3.getZ()-v2.getZ()));
    }
    public double lengthV34() {
        return Math.sqrt((v3.getX()-v4.getX())*(v3.getX()-v4.getX())+
                         (v3.getY()-v4.getY())*(v3.getY()-v4.getY())+
                         (v3.getZ()-v4.getZ())*(v3.getZ()-v4.getZ()));
    }
    public double lengthV41() {
        return Math.sqrt((v1.getX()-v4.getX())*(v1.getX()-v4.getX())+
                         (v1.getY()-v4.getY())*(v1.getY()-v4.getY())+
                         (v1.getZ()-v4.getZ())*(v1.getZ()-v4.getZ()));
    }
    public double lengthPointV1(Vector point) {
        return Math.sqrt((v1.getX()-point.getX())*(v1.getX()-point.getX())+
                         (v1.getY()-point.getY())*(v1.getY()-point.getY())+
                         (v1.getZ()-point.getZ())*(v1.getZ()-point.getZ()));
    }
    public double lengthPointV2(Vector point) {
        return Math.sqrt((v2.getX()-point.getX())*(v2.getX()-point.getX())+
                         (v2.getY()-point.getY())*(v2.getY()-point.getY())+
                         (v2.getZ()-point.getZ())*(v2.getZ()-point.getZ()));
    }
    public double lengthPointV3(Vector point) {
        return Math.sqrt((v3.getX()-point.getX())*(v3.getX()-point.getX())+
                         (v3.getY()-point.getY())*(v3.getY()-point.getY())+
                         (v3.getZ()-point.getZ())*(v3.getZ()-point.getZ()));
    }
    public double lengthPointV4(Vector point) {
        return Math.sqrt((v4.getX()-point.getX())*(v4.getX()-point.getX())+
                         (v4.getY()-point.getY())*(v4.getY()-point.getY())+
                         (v4.getZ()-point.getZ())*(v4.getZ()-point.getZ()));
    }
    //--------------------------------------------------------------------------
    //transfer rectangle with A point to origin. Make sure
    public void transfer(Vector origin) {
        Vector v1New = v1.copy();
        Vector v2New = v2.copy();
        Vector v3New = v3.copy();
        Vector v4New = v4.copy();

        Line line = new Line(v1New,v2New);
        line.transfer(origin);
        v2.replaceWith(v2New);
        v1.replaceWith(v1New);      
        
        line = new Line(v1New,v3New);
        line.transfer(origin);
        v3.replaceWith(v3New);
        v1.replaceWith(v1New);
      
        line = new Line(v1New,v4New);
        line.transfer(origin);
        v4.replaceWith(v4New);
        v1.replaceWith(v1New);        
    }
    //--------------------------------------------------------------------------
    //rotate rectangle so AB falls on Z
    public void transformOnXY() {
        //trabsfer to A
        transfer(v1);
        //Bring AB on xz plane
        double R = Math.sqrt(v2.getX()*v2.getX()+v2.getY()*v2.getY());;
        double cos = v2.getX()/R, sin = -v2.getY()/R;
        v2.rotateZ(cos, sin);
        v3.rotateZ(cos, sin);
        v4.rotateZ(cos, sin);
        //Bring AB on positive xx axis
        R = Math.sqrt(v2.getX()*v2.getX()+v2.getZ()*v2.getZ());;
        cos = v2.getX()/R; sin = -v2.getZ()/R;
        v2.rotateY(cos, sin);
        v3.rotateY(cos, sin);
        v4.rotateY(cos, sin);
        //Bring AD on positive yy axis
        R = Math.sqrt(v2.getY()*v2.getY()+v2.getZ()*v2.getZ());;
        cos = v2.getY()/R; sin = -v2.getZ()/R;
        v2.rotateX(cos, sin);
        v3.rotateX(cos, sin);
        v4.rotateX(cos, sin);
    }

 
}
