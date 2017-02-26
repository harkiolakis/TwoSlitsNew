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
public class Line {
    public Vector start = new Vector(), end = new Vector();
    //initialize to point 0,0,0 - constuctor mainly for cleanup purposes
    public Line() {
        start.setX(0); start.setY(0); start.setZ(0);
        end.setX(0); end.setY(0); end.setZ(0);
    }
    public Line(Vector s, Vector e) {
        start.replaceWith(s);
        end.replaceWith(e);
    }
    public void setStart(Vector v) { start.replaceWith(v); }
    public void setEnd(Vector v) { end.replaceWith(v); }
    public Vector getStart() { return start; }
    public Vector getEnd() { return end; }
    public Vector coordinates() {//this is like transfering start to 0,0,0
        Vector v = new Vector();
        v.setX(end.getX()-start.getX());
        v.setY(end.getY()-start.getY());
        v.setZ(end.getZ()-start.getZ());
        return v;
    }
    public void transfer(Vector displacement) {
        start.setX(displacement.getX()+start.getX());
        start.setY(displacement.getY()+start.getY());
        start.setZ(displacement.getZ()+start.getZ());
        end.setX(displacement.getX()+end.getX());
        end.setY(displacement.getY()+end.getY());
        end.setZ(displacement.getZ()+end.getZ());
    }

}
