/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author HLC
 */
public class PongUI extends javax.swing.JFrame {

    /**
     * Creates new form PongUI
     */
    public PongUI() {
        initComponents();
    }
    
    static boolean player1Left, player1Right, player2Left, player2Right, paused = true;
    double dir = Math.toRadians(127);
    double dirX;
    double dirY;
    int rate = 5;
    
    enum move {
        player1Left, player1Right, player2Left, player2Right;
    }
    
    private void countdown() throws InterruptedException {
        dirX = jPanel1.getWidth()/2 - ball.getWidth()/2;
        dirY = jPanel1.getHeight()/2 - ball.getHeight()/2;
        count.setVisible(true);
        ball.setVisible(false);
        
        for (int i = 3; i > 0; i--) {
            count.setText(Integer.toString(i));
            Thread.sleep(1000);
        }
        
        count.setVisible(false);
        ball.setVisible(true);
        paused = false;
    }
    
    private boolean inBounds(JPanel obj) {
        if (obj.getX() <= 0) {
            obj.setLocation(5, obj.getY());
        } else if (obj.getWidth() + obj.getX() >= jPanel1.getWidth()) {
            obj.setLocation(jPanel1.getWidth() - obj.getWidth() - 5, obj.getY());
        } 

        return obj.getX() > 0 && obj.getX() + obj.getWidth() < jPanel1.getWidth();
    }
    
    private boolean paddleCollision(JPanel obj) {
        return ball.getX() + ball.getWidth() - obj.getX() >= 0
                && ball.getX() - obj.getX() - obj.getWidth() <= 0
                && (ball.getY() - obj.getY() <= obj.getHeight()
                || obj.getY() + obj.getHeight() - ball.getY() - ball.getHeight() <= obj.getHeight());
    }
    
    private boolean inBoundsBall() {
        return ball.getX() >= 0 && ball.getX() + ball.getWidth() <= jPanel1.getWidth() 
                && ball.getY() >= 0 && ball.getY() + ball.getHeight() <= jPanel1.getHeight();
    }
    
    private void moveBall() {
        for (Component c: jPanel1.getComponents()) {
            if (c instanceof JPanel) { // check paddle collision
                if (paddleCollision((JPanel) c)) {
                    System.out.println("we're in");
                }
            }
        } 
        
        if (!inBoundsBall()) { // check wall collision
            if (Math.toDegrees(dir) == 0) {
                dir = 180;
            } else if (Math.toDegrees(dir) == 180) {
                dir = 0;
            } else {
                dir = -dir;
            }
            
            if (ball.getX() <= 0) { // check left-right collision
                dirX = 1;
                rate = -rate;
            } else if (ball.getX() + ball.getWidth() >= jPanel1.getWidth()) {
                dirX = jPanel1.getWidth() - ball.getWidth() - 1;
                rate= -rate;
            }
            
            if (ball.getY() <= 0) { // check top-bottom collision
                dirY = 1;
            } else if (ball.getY() + ball.getHeight() >= jPanel1.getHeight()) {
                dirY = jPanel1.getHeight() - ball.getHeight() - 1;
            }
        }
        
        dirX += Math.cos(dir)/rate;
        dirY += Math.sin(dir)/rate;
        ball.setLocation((int)(.5 + dirX), (int)(.5 + dirY));
        //System.out.println(Math.cos(dir)/rate + "\t" + Math.sin(dir)/rate + "\t" + Math.toDegrees(dir));
    }
    
    private void movePaddle(move c) {
        switch (c) {
            case player1Left: 
                if (inBounds(player1)) {
                    player1.setLocation(player1.getX() - 1, player1.getY());
                } break;
            case player1Right:
                if (inBounds(player1)) {
                    player1.setLocation(player1.getX() + 1, player1.getY());
                } break;
            case player2Left:
                if (inBounds(player2)) {
                    player2.setLocation(player2.getX() - 1, player2.getY());
                } break;
            case player2Right:
                if (inBounds(player2)) {
                    player2.setLocation(player2.getX() + 1, player2.getY());
                } break;
        }
    }
    
    private void changeKeyStatus(KeyEvent evt, boolean s) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_A: player1Left = s; break;
            case KeyEvent.VK_D: player1Right = s; break;
            case KeyEvent.VK_LEFT: player2Left = s; break;
            case KeyEvent.VK_RIGHT: player2Right = s; break;
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        player1 = new javax.swing.JPanel();
        player2 = new javax.swing.JPanel();
        count = new javax.swing.JLabel();
        ball = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setResizable(false);
        setSize(new java.awt.Dimension(900, 700));
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        player1.setBackground(new java.awt.Color(204, 51, 0));

        javax.swing.GroupLayout player1Layout = new javax.swing.GroupLayout(player1);
        player1.setLayout(player1Layout);
        player1Layout.setHorizontalGroup(
            player1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        player1Layout.setVerticalGroup(
            player1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        player2.setBackground(new java.awt.Color(0, 51, 204));

        javax.swing.GroupLayout player2Layout = new javax.swing.GroupLayout(player2);
        player2.setLayout(player2Layout);
        player2Layout.setHorizontalGroup(
            player2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        player2Layout.setVerticalGroup(
            player2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        count.setBackground(new java.awt.Color(255, 255, 255));
        count.setFont(new java.awt.Font("Yu Gothic UI", 1, 100)); // NOI18N
        count.setForeground(new java.awt.Color(255, 255, 255));
        count.setText("3");

        ball.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout ballLayout = new javax.swing.GroupLayout(ball);
        ball.setLayout(ballLayout);
        ballLayout.setHorizontalGroup(
            ballLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        ballLayout.setVerticalGroup(
            ballLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(349, 349, 349)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(player2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(player1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(423, 423, 423)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ball, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(count))))
                .addContainerGap(351, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(player2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                .addComponent(count)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ball, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(295, 295, 295)
                .addComponent(player1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        changeKeyStatus(evt, true);
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        changeKeyStatus(evt, false);
    }//GEN-LAST:event_formKeyReleased

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
            java.util.logging.Logger.getLogger(PongUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PongUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PongUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PongUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        PongUI p = new PongUI();
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                p.setVisible(true);
            }
        });
        
        Runnable runnable = new Runnable() {
            public void run() {                
                if (paused) {
                    try {
                        p.countdown();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PongUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    p.moveBall();
                    
                    if (player1Left) {
                        p.movePaddle(move.player1Left);
                    } if (player1Right) {
                        p.movePaddle(move.player1Right);
                    } if (player2Left) {
                        p.movePaddle(move.player2Left);
                    } if (player2Right) {
                        p.movePaddle(move.player2Right);
                    }
                }
            }
        };
        
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.MILLISECONDS);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ball;
    private javax.swing.JLabel count;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel player1;
    private javax.swing.JPanel player2;
    // End of variables declaration//GEN-END:variables
}
