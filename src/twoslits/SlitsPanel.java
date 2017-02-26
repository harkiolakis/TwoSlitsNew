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
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author nicho_000
 */
public class SlitsPanel extends javax.swing.JPanel{
    final int Right = 0, Left = 1;
    final int maxSlits = 3;
    final int maxRectangles = 10;
    Rectangle [][] rectangles = new Rectangle[maxSlits][maxRectangles];
    Rectangle wall;
    Line line;
    Vector v,u;
    int nSlits=2,nRectangles=6,nParticles=1000;
    //Based on the following units the univere is built
    //The average displacement of th slits is in bull's eye
    double slitRadius=1, slitLength=4, distanceLight=100, distanceSlits=50, distanceWall=20; 
    double halfSide = distanceSlits*5;
    int i,iX,iY;
    Graphics2D g;
    /**
     * Creates new form twoSLitsPanel
     */
    public SlitsPanel(Graphics2D g) {
        this.setBackground(Color.RED);
        this.g = g;
        g.drawRect(10, 10, 100, 100);
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
        Vector A,B,C,D;

        angle = -angleSlit/2;
        for (int iRectangle=0; iRectangle<nRectangles; iRectangle++)  {
            A = new Vector();
            A.setX(origin.getX()+slitRadius*Math.cos(angle));
            A.setY(origin.getY()+slitRadius*Math.sin(angle));
            
            D = new Vector();
            D.setX(A.getX());
            D.setY(A.getY());
            D.setZ(-slitLength);
                      
            B = new Vector();
            angle += angleSlit;
            B.setX(origin.getX()+slitRadius*Math.cos(angle));
            B.setY(origin.getY()+slitRadius*Math.sin(angle));
            
            
            C = new Vector();
            C.setX(B.getX());
            C.setY(B.getY());
            C.setZ(-slitLength);
            
            rectangles[iSlit][iRectangle] = new Rectangle(A,B,C,D);
        }
    }
    //----------------------------------------------------------------Build wall 
    Rectangle createWall() {
        Vector A,B,C,D;
        
        A = new Vector();
        A.setX(halfSide); A.setY(-halfSide); A.setZ(slitLength+distanceWall);
            
        B = new Vector(); 
        B.setX(halfSide); B.setY(halfSide); B.setZ(slitLength+distanceWall);
         
        C = new Vector();
        C.setX(-halfSide); C.setY(halfSide); C.setZ(slitLength+distanceWall);

        D = new Vector();
        D.setX(-halfSide); D.setY(-halfSide); D.setZ(slitLength+distanceWall);
        
        return new Rectangle(A,B,C,D);
    }
    //----------------------------------------------------------------Hits slit
    boolean hitSlit(double x, double y){
        double totalAngle = 0,angle,angleSlit = 2*Math.PI/nRectangles;
        double a,b,c, side = 2*slitRadius*slitRadius*Math.cos(angleSlit);
        
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
    void runExperiment() {
        double angle,radius,x,y;
        boolean execute;
        Vector ray,intersect;
        Line line;
        Random r = new Random(Calendar.getInstance().getTimeInMillis());

        initialize();
        g.drawRect(10, 10, 100, 100);
        //Create slits        
        Vector origin = new Vector();
        origin.setX(distanceSlits/2); createSlit(0,origin); //right slit
        origin.setX(-distanceSlits/2); createSlit(1,origin); //left slit
        //Create wall
        wall = createWall();
        //-------Main execution loop
        for (int iSlit=0; iSlit<nSlits; iSlit++)
        for (int iParticle=0; iParticle<nParticles; iParticle++) {
            //create a random ray
            do {
                angle = r.nextDouble()*2*Math.PI;
                radius = r.nextDouble()*slitRadius; 
                x = radius*Math.cos(angle); y = radius*Math.sin(angle); 
            }
            while (!hitSlit(x,y));
            if (iSlit == Right) x += distanceSlits;
            else x -= distanceSlits;
            ray = new Vector(x,y,0);
            line = new Line(new Vector(0,0,distanceLight), ray);

            //start ray tracing
            execute = true;
            do {
                //Find which rectangle is hit
                for (i=0; i <nRectangles; i++)
                    //check if ray is in front of plane
                    if (ray.angleWith(new Vector(rectangles[iSlit][i].getA(),rectangles[iSlit][i].getB(),rectangles[iSlit][i].getC())) < Math.PI/2) {
                        //find intersection point
                        intersect = new Vector();
                        intersect = rectangles[iSlit][i].intersectionPoint(line);
                        //check if intersect inside rectangle
                        if (rectangles[iSlit][i].pointInRectangle(intersect)) {
                            //perform collision
                            break;
                        }
                    }
                //check if ray hit the Wall
                if (i == nRectangles) {
                        intersect = new Vector();
                        intersect = rectangles[iSlit][i].intersectionPoint(line);
                        //Display point on canvas
                        iX = (int)(intersect.getX()+halfSide);
                        iY = (int)(intersect.getY()+halfSide);
                        g.drawLine(iX, iY, iX, iY);
                        //Exit while loop
                        execute = false;
                }
            }
            while(execute);
        }//for (int iSlit, iParticle
    }                   

    // Variables declaration - do not modify                     
    // End of variables declaration                   
    
}
