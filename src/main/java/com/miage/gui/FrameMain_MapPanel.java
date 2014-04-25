
package com.miage.gui;

import com.miage.game.Board;
import com.miage.game.Player;
import com.miage.game.PlayerToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.LogManager;

/**
 * Main frame, empty.
 * Used to contain different panels next
 * Class Main Frame 
 * @author Anne-Sophie Duhaut
 */
public class FrameMain_MapPanel extends javax.swing.JFrame {

    private final static org.apache.log4j.Logger LOGGER = LogManager.getLogger(FrameMain_MapPanel.class.getName());
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]){
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrameMain_MapPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameMain_MapPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameMain_MapPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameMain_MapPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the main frame
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            //run application
            @Override
            public void run(){
                FrameMain_MapPanel frameMain;
                try {
                    frameMain = new FrameMain_MapPanel();
                    frameMain.setVisible(true);
                } catch ( Exception ex) {
                    LOGGER.fatal(ex);
                    ex.printStackTrace();
                    System.exit(0);
                }
            }
        });
    }
    
    /**
     * Creates new form FrameMain
     */
    public FrameMain_MapPanel() throws IOException, InterruptedException, Exception{
        initComponents();
        
        this.setSize(1370, 795);
        this.setResizable( false);

        /**
         * Create the game
         */
        // add home panel to main frame
        List<Player> players = new ArrayList();
        players.add( new Player("richard", new PlayerToken("blue")));
        players.add( new Player("maxime", new PlayerToken("red")));
        players.add( new Player("anneso", new PlayerToken("green")));
        players.add( new Player("gael", new PlayerToken("yellow")));
        Board board = new Board(4, players);
        PanelContainer.add( new MapPanel(board, PanelContainer), new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
        PanelContainer.updateUI();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelContainer = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PanelContainer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PanelContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(350, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PanelContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(251, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelContainer;
    // End of variables declaration//GEN-END:variables

}
