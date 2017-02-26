/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twoslits;

import java.util.Calendar;
import java.util.Random;
import java.util.*;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author nicho_000
 */
public class twoSlitsJFrame extends JFrame {
    Graphics2D g2d;
    Graphics g;
    Run run = new Run();
    Container cp;
    boolean expRun = false;
/**
     * Creates new form twoSlitsJFrame
     */
    public twoSlitsJFrame() {
        initComponents();
        setLocationRelativeTo(null); //this is where the frame will appear
        //frame is resizable by default
        setSize(2*run.halfSide, 2*run.halfSide); 
        cp = getContentPane(); //this is where drawing takes place
        cp.setBackground(Color.BLACK);
        repaint();
        setVisible(true);
    }
    public void paint(Graphics g) {
        super.paint(g);
        
        if (expRun) {
            g.setColor(Color.GRAY);
            g.fillRect(0, 0, 2*run.halfSide, 2*run.halfSide);
            g.setColor(Color.yellow);
        
            for (int i=0; i<2*run.halfSide; i++)
            for (int j=0; j<2*run.halfSide; j++)
                if (run.displayPoints[i][j]) g.drawLine(i, j, i, j);       
            //Draw slits
            for (int iSlit=0; iSlit<run.nSlits; iSlit++)
            for (int iRectangle=0; iRectangle<run.nRectangles; iRectangle++) {
                g.setColor(Color.red);
                g.drawLine((int)(run.rectangles[iSlit][iRectangle].v1.getX()+run.halfSide), run.halfSide-(int)(run.rectangles[iSlit][iRectangle].v1.getY()), 
                           (int)(run.rectangles[iSlit][iRectangle].v4.getX()+run.halfSide), run.halfSide-(int)(run.rectangles[iSlit][iRectangle].v4.getY()));               
            }
            //Draw hits
            //g.setColor(Color.green);
            //g.drawLine(run.xHit+run.halfSide,run.yHit+run.halfSide,run.xHit+run.halfSide,run.yHit+run.halfSide);
        }
    }
    //--------------------------------------------------------display components
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        javax.swing.JMenu Menu = new javax.swing.JMenu();
        menuRun = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        Menu.setText("Menu");

        menuRun.setText("Run");
        menuRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRunActionPerformed(evt);
            }
        });
        Menu.add(menuRun);

        jMenuBar1.add(Menu);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRunActionPerformed
        expRun = true;
        run.experiment();
        this.repaint();

    }//GEN-LAST:event_menuRunActionPerformed
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(twoSlitsJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(twoSlitsJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(twoSlitsJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(twoSlitsJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new twoSlitsJFrame();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem menuRun;
    // End of variables declaration//GEN-END:variables
}
