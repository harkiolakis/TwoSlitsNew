/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twoslits;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Calendar;
import java.util.Random;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author nicho_000
 */
public class Run extends javax.swing.JPanel {
    final int Right = 0, Left = 1;
    final int ShapeSymetric = 0, ShapeRectangle = 1;
    final int maxSlits = 3;
    final int maxRectangles = 10;
    public Rectangle [][] rectangles = new Rectangle[maxSlits][maxRectangles];
    public Rectangle wall;
    Line line;
    Vector v,u;
    public int nSlits=2,nRectangles=10,nParticles=1000,shape=0;
    //Based on the following units the univere is built
    //The average displacement of th slits is in bull's eye
    public int slitRadius=10, slitLength=100, distanceLight=20, distanceSlits=100, distanceWall=10; 
    public int halfSide = 500;
    int i,iX,iY;
    public int xHit,yHit;
    public boolean displayPoints[][] = new boolean[2*halfSide][2*halfSide]; //in screen coordinates

    
    public Run() { 
        for (int i=0; i<halfSide; i++)
        for (int j=0; j<halfSide; j++)
           displayPoints[i][j] = false;
        initialize();
    }
    //-------------------------------- METHODS --------------------------------
    //--------------------------------------------------initialize all variables
    void initialize(){
        for (int iSlit=0; iSlit<nSlits; iSlit++)
            for (int iRectangle=0; iRectangle<nRectangles; iRectangle++) 
                rectangles[iSlit][iRectangle] = new Rectangle();
        wall = new Rectangle();
        line = new Line();
        v = new Vector();
        u = new Vector();
    }
    //--------------------------------------Slits always align along negative ZZ
    void createSlit(int iSlit, Vector origin) {
        double angle,angleSlit = 2*Math.PI/nRectangles;
        Vector A = new Vector(),
               B = new Vector(),
               C = new Vector(),
               D = new Vector();

        //Right angle coordinate systme with positive z from screen to viewer
        angle = -angleSlit/2;
        //Front down
        A.setX(origin.getX()+slitRadius*Math.cos(angle));
        if (shape == ShapeRectangle) A.setY(origin.getY()+10*slitRadius*Math.sin(angle));  
        else A.setY(origin.getY()+slitRadius*Math.sin(angle));  
        A.setZ(origin.getZ());
        //Back down
        B.setX(A.getX());
        B.setY(A.getY());
        B.setZ(origin.getZ()-slitLength);
        for (int iRectangle=0; iRectangle<nRectangles; iRectangle++)  {            
            angle += angleSlit;
            //Back up
            //C = new Vector();
            C.setX(origin.getX()+slitRadius*Math.cos(angle));
            if (shape == ShapeRectangle) C.setY(origin.getY()+10*slitRadius*Math.sin(angle));
            else C.setY(origin.getY()+slitRadius*Math.sin(angle));
            C.setZ(B.getZ());
            //Front up
            //D = new Vector();
            D.setX(C.getX());
            D.setY(C.getY());
            D.setZ(origin.getZ());
            rectangles[iSlit][iRectangle] = new Rectangle(D, C, B, A);
            //A = new Vector();
            A.replaceWith(D);
            //B = new Vector();
            B.replaceWith(C);
        }
    }
    //----------------------------------------------------------------Build wall 
    Rectangle createWall() {
        Vector A,B,C,D;
        
        int wallZ = (int)(-slitLength-distanceWall);
        //Right angle coordinate systme with positive z from screen to viewer
        //Bottom right
        A = new Vector();
        A.setX(halfSide); A.setY(-halfSide); A.setZ(wallZ);
        //Top right
        B = new Vector();
        B.setX(halfSide); B.setY(halfSide); B.setZ(wallZ);
        //Top left
        C = new Vector();
        C.setX(-halfSide); C.setY(halfSide); C.setZ(wallZ);
        //Bottom left
        D = new Vector(); 
        D.setX(-halfSide); D.setY(-halfSide); D.setZ(wallZ);
        
        return new Rectangle(A,B,C,D);
    }
    //----------------------------------------------------------------Hits slit
    boolean hitSlit(double x, double y){
        double totalAngle = 0,angle,angleSlit = 2*Math.PI/nRectangles;
        double a,b,c, side = 2*slitRadius*slitRadius*(1-Math.cos(angleSlit));
        //Everything is in (0,0,0)
        angle = -angleSlit/2;
        a = Math.sqrt((slitRadius*Math.cos(angle)-x)*(slitRadius*Math.cos(angle)-x) -
                      (slitRadius*Math.sin(angle)-y)*(slitRadius*Math.sin(angle)-y));
        for (int i=0; i<nRectangles; i++) {
            angle += angleSlit;
            b = Math.sqrt((slitRadius*Math.cos(angle)-x)*(slitRadius*Math.cos(angle)-x) -
                          (slitRadius*Math.sin(angle)-y)*(slitRadius*Math.sin(angle)-y));
            totalAngle += Math.acos((a*a+b*b-side*side)/(2*a*b));
            a = b; }
        if (totalAngle - 2*Math.PI < 0.0001) return false; //$$$$
        else return true;
    }
    //----------------------------------Main method that executes the experiment
    public void experiment() {
        double angle,radius,x,y;
        boolean execute;
        Vector startLine,endLine,incident,intersect = new Vector(),normal = new Vector();
        Line line;
        Random r = new Random(Calendar.getInstance().getTimeInMillis());

        initialize();
        //Create slits        
        Vector origin = new Vector();
        origin.setX(distanceSlits/2);
        createSlit(Right,origin);
        origin.setX(-distanceSlits/2); 
        createSlit(Left,origin);

        //Create wall
        wall = createWall();
        //-------Main execution loop
        for (int iSlit=0; iSlit<2; iSlit++)
        for (int iParticle=0; iParticle<nParticles; iParticle++) {
            //create a random ray
            if (shape == ShapeRectangle) {
                x = r.nextDouble()*(rectangles[0][0].getV1().getX()-rectangles[0][2].getV1().getX());
                y = r.nextDouble()*(rectangles[0][0].getV1().getY()-rectangles[0][0].getV4().getY())+rectangles[0][0].getV4().getY();
            }
            else 
            do {
                //r = new Random(Calendar.getInstance().getTimeInMillis());
                angle = r.nextDouble()*2*Math.PI;
                radius = r.nextDouble()*slitRadius; 
                x = radius*Math.cos(angle); y = radius*Math.sin(angle); 
            }
            while (!hitSlit(x,y));
            if (iSlit == Right) x += distanceSlits/2;
            else x -= distanceSlits/2;
            endLine = new Vector(x,y,0);
            line = new Line(new Vector(0,0,distanceLight), endLine);
            //System.out.println(line.start.getX()+" "+line.start.getY()+" "+line.start.getZ()+" "+line.end.getX()+" "+line.end.getY()+" "+line.end.getZ());
            //xHit = (int)x; yHit = (int)y;
           
            //start ray tracing
            execute = true;
            do {
                //Find which rectangle is hit
                for (i=0; i <nRectangles; i++) {
                    //check if ray is in front of plane
                    normal.replaceWith(rectangles[iSlit][i].getNormal());
                    incident = new Vector(line.coordinates());
                    angle = incident.angleWith(normal);
                    //System.out.println(i+" "+angle);
                    //System.out.println(iSlit+" normal "+normal.getX()+" "+normal.getY()+" "+normal.getZ());
                    //System.out.println(iSlit+" normal "+incident.getX()+" "+incident.getY()+" "+incident.getZ());
                    if (angle > Math.PI/2 && angle < 3*Math.PI/2) {
                        //find intersection point
                        intersect.replaceWith(rectangles[iSlit][i].intersectionPoint(line));
                        //check if intersect inside rectangle
                        if (rectangles[iSlit][i].pointInRectangle(intersect)) {
                            //perform collision
                            line.setStart(intersect);
                            incident.reflect(normal);
                            incident.add(intersect);//$$$$ transfer doesn't work reflection to the intersect
                            line.setEnd(incident);
                            break;
                        }
                    }
                }
                //check if ray hit the Wall
                if (i == nRectangles) {
                        intersect.replaceWith(wall.intersectionPoint(line));
                        //Convert to screen coordinates
                        iX = (int)(intersect.getX()+halfSide);
                        iY = (int)(halfSide-intersect.getY());
                        if (iX >= 0 && iX < 2*halfSide && iY >= 0 && iY < 2*halfSide)
                          displayPoints[iX][iY] = true;
                        //Exit while loop
                        execute = false;
                }
            }
            while(execute);
        }//for (int iSlit, iParticle
    execute = false;    
    }                   
    //----------------------------------Main method that executes the experiment
    void pilot() {
        Vector v1 = new Vector(100,100,0);
        Vector v2 = new Vector(100,-100,0);
        Vector v3 = new Vector(100,-100,-100);
        Vector v4 = new Vector(100,100,-100);
        Rectangle rect = new Rectangle(v1,v2,v3,v4);
        
        Line line = new Line(new Vector(0,0,100), new Vector(100,0,-10));
                
        double angle = line.coordinates().angleWith(rect.getNormal());
        if (angle > Math.PI/2)
             System.out.println("in  front "+angle+" "+rect.getA()+"  "+rect.getB()+"  "+rect.getC()+"  "+rect.getD());
        else System.out.println("behind "+angle+" "+rect.getA()+"  "+rect.getB()+"  "+rect.getC()+"  "+rect.getD());

        rect = new Rectangle(v4,v3,v2,v1);
        angle = line.coordinates().angleWith(rect.getNormal());
        if (angle > Math.PI/2)
             System.out.println("in  front "+angle+" "+rect.getA()+"  "+rect.getB()+"  "+rect.getC()+"  "+rect.getD());
        else System.out.println("behind "+angle+" "+rect.getA()+"  "+rect.getB()+"  "+rect.getC()+"  "+rect.getD());
        
        //System.exit(0);
        
        
        
    }
    
}
