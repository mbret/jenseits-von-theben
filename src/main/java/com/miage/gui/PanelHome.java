package com.miage.gui;

import com.miage.config.ConfigManager;
import com.miage.game.Board;
import com.miage.game.Player;
import com.miage.game.PlayerToken;
import com.miage.game.Sound;
import com.miage.main.Utils;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import org.apache.log4j.LogManager;

/**
 * Class Home Panel
 *
 * @author Anne-Sophie
 */
public class PanelHome extends javax.swing.JPanel {

    // number of players
    private int nbPlayers;

    private final static org.apache.log4j.Logger LOGGER = LogManager.getLogger(PanelHome.class.getName());
    
    /**
     * Creates new form PanelHome
     */
    public PanelHome() throws IOException {

        initComponents();
        nbPlayers = 2; // base nb players

        // Init our componants without netbeans auto generated code
        MenuPanel.setVisible(true);
        playRedLabel.setVisible(false);
        quitRedLabel.setVisible(false);
        rulesRedLabel.setVisible(false);
        newGameRedLabel.setVisible(false);
        backRedLabel.setVisible(false);
        playPanel.setVisible(false);
        newGamePanel.setVisible(false);
        backRedNewGamePanelLabel.setVisible(false);
        playGameRedLabel.setVisible(false);
        player1TextField.setText("");
        player2TextField.setText("");
        player3TextField.setText("");
        player4TextField.setText("");
        player1TextField.setFocusable(true);
        warningInternalFrame.setVisible(false);
        
        // OPTION PANEL
        String audioEnable = ConfigManager.getInstance().getOptions().get("general", "audio", String.class);
        if(audioEnable != null){
            if(audioEnable.compareTo("1") == 0){
                System.out.println("TRUE");
                this.jCheckBox1.setSelected(Boolean.TRUE);
                Sound.enableSound = Boolean.TRUE;
            }else{
                System.out.println("FALSE");
                this.jCheckBox1.setSelected(Boolean.FALSE);
                Sound.enableSound = Boolean.FALSE;
            }
            Sound.play("audioGame");
        }
        
        // COMBO BOX COLOR
        this.colorPlayer1ComboBox.addItem( new ComboBoxColorElement("blue", "Bleu"));
        this.colorPlayer1ComboBox.addItem( new ComboBoxColorElement("red", "Rouge"));
        this.colorPlayer1ComboBox.addItem( new ComboBoxColorElement("yellow", "Jaune"));
        this.colorPlayer1ComboBox.addItem( new ComboBoxColorElement("green", "Vert"));
        
        this.colorPlayer2ComboBox.addItem( new ComboBoxColorElement("blue", "Bleu"));
        this.colorPlayer2ComboBox.addItem( new ComboBoxColorElement("red", "Rouge"));
        this.colorPlayer2ComboBox.addItem( new ComboBoxColorElement("yellow", "Jaune"));
        this.colorPlayer2ComboBox.addItem( new ComboBoxColorElement("green", "Vert"));
        
        this.colorPlayer3ComboBox.addItem( new ComboBoxColorElement("blue", "Bleu"));
        this.colorPlayer3ComboBox.addItem( new ComboBoxColorElement("red", "Rouge"));
        this.colorPlayer3ComboBox.addItem( new ComboBoxColorElement("yellow", "Jaune"));
        this.colorPlayer3ComboBox.addItem( new ComboBoxColorElement("green", "Vert"));
        
        this.colorPlayer4ComboBox.addItem( new ComboBoxColorElement("blue", "Bleu"));
        this.colorPlayer4ComboBox.addItem( new ComboBoxColorElement("red", "Rouge"));
        this.colorPlayer4ComboBox.addItem( new ComboBoxColorElement("yellow", "Jaune"));
        this.colorPlayer4ComboBox.addItem( new ComboBoxColorElement("green", "Vert"));
        
    }

    public void launchGame( Board board ){
        // remove actual GUI content and add de MapPanel
        this.removeAll();
        MapPanel map = MapPanel.create(board);
        add(map, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
        this.updateUI();
    }
    
    /**
     * check color players
     *
     * @return ok
     */
    public boolean checkColors() {
        nbPlayers = Integer.parseInt((String) jComboBoxChooseNbPlayers.getSelectedItem()) ;
        boolean ok = true;
        
        String colorPlayer1 = ((ComboBoxColorElement)colorPlayer1ComboBox.getSelectedItem()).id ;
        String colorPlayer2 = ((ComboBoxColorElement)colorPlayer2ComboBox.getSelectedItem()).id ;
        String colorPlayer3 = ((ComboBoxColorElement)colorPlayer3ComboBox.getSelectedItem()).id ;
        String colorPlayer4 = ((ComboBoxColorElement)colorPlayer4ComboBox.getSelectedItem()).id ;

        switch( nbPlayers ){
            case 2:
                if (colorPlayer1.equals(colorPlayer2)) {
                    ok = false;
                }
                break;
            case 3:
                if (colorPlayer1.equals(colorPlayer2) || colorPlayer1.equals(colorPlayer3) || colorPlayer2.equals(colorPlayer3) ) {
                    ok = false;
                }
                break;
            case 4:
                if (colorPlayer1.compareTo(colorPlayer2) == 0 || colorPlayer1.compareTo(colorPlayer3) == 0 || colorPlayer2.compareTo(colorPlayer3) == 0
                        || colorPlayer1.compareTo(colorPlayer4) == 0 || colorPlayer2.compareTo(colorPlayer4) == 0 || colorPlayer3.compareTo(colorPlayer4) == 0) {
                    ok = false;
                }
                break;
        }

        return ok;
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        warningInternalFrame = new javax.swing.JInternalFrame();
        jPanel1 = new javax.swing.JPanel();
        writingLabel = new javax.swing.JLabel();
        warningLabel = new javax.swing.JLabel();
        backgroundInternalFrameLabel = new javax.swing.JLabel();
        newGamePanel = new javax.swing.JPanel();
        playGameRedLabel = new javax.swing.JLabel();
        newPlayLabel = new javax.swing.JLabel();
        backRedNewGamePanelLabel = new javax.swing.JLabel();
        backNewGamePanelLabel = new javax.swing.JLabel();
        jComboBoxChooseNbPlayers = new javax.swing.JComboBox();
        numberPlayersLabel = new javax.swing.JLabel();
        player4TextField = new javax.swing.JTextField();
        player3TextField = new javax.swing.JTextField();
        player2TextField = new javax.swing.JTextField();
        player1TextField = new javax.swing.JTextField();
        player4Label = new javax.swing.JLabel();
        player3Label = new javax.swing.JLabel();
        player2Label = new javax.swing.JLabel();
        couleurLabel = new javax.swing.JLabel();
        player1Label = new javax.swing.JLabel();
        namePlayLabel = new javax.swing.JLabel();
        colorPlayer4ComboBox = new javax.swing.JComboBox();
        colorPlayer1ComboBox = new javax.swing.JComboBox();
        colorPlayer2ComboBox = new javax.swing.JComboBox();
        colorPlayer3ComboBox = new javax.swing.JComboBox();
        papyrusLabel = new javax.swing.JLabel();
        MenuPanel = new javax.swing.JPanel();
        playRedLabel = new javax.swing.JLabel();
        playLabel = new javax.swing.JLabel();
        parameterLabel = new javax.swing.JLabel();
        quitRedLabel = new javax.swing.JLabel();
        quitLabel = new javax.swing.JLabel();
        rulesRedLabel = new javax.swing.JLabel();
        rulesLabel = new javax.swing.JLabel();
        menuLabel = new javax.swing.JLabel();
        playPanel = new javax.swing.JPanel();
        backRedLabel = new javax.swing.JLabel();
        backLabel = new javax.swing.JLabel();
        loadGameJLabel = new javax.swing.JLabel();
        newGameRedLabel = new javax.swing.JLabel();
        newGameLabel = new javax.swing.JLabel();
        menuLabel1 = new javax.swing.JLabel();
        optionPanel = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        returnLabel = new javax.swing.JLabel();
        optionPanelBackgroundLabel = new javax.swing.JLabel();
        crossLabel = new javax.swing.JLabel();
        backgroundLabel = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(1366, 768));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        warningInternalFrame.setClosable(true);
        warningInternalFrame.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        warningInternalFrame.setVisible(true);
        warningInternalFrame.addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                warningInternalFrameInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        warningInternalFrame.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        writingLabel.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        writingLabel.setText("<html>Veuillez renseigner les pseudonymes des joueurs !<br>Les couleurs ne doivent être sélectionnées qu'une seule fois !");
        jPanel1.add(writingLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 290, 120));

        warningLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo-warning.png"))); // NOI18N
        jPanel1.add(warningLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 80, 80));

        backgroundInternalFrameLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/backgroundWarning.jpg"))); // NOI18N
        jPanel1.add(backgroundInternalFrameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 290));

        warningInternalFrame.getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        add(warningInternalFrame, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 200, 580, 320));

        newGamePanel.setOpaque(false);
        newGamePanel.setPreferredSize(new java.awt.Dimension(450, 480));
        newGamePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        playGameRedLabel.setFont(new java.awt.Font("Tempus Sans ITC", 0, 18)); // NOI18N
        playGameRedLabel.setForeground(new java.awt.Color(153, 0, 0));
        playGameRedLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        playGameRedLabel.setText("<html><b>JOUER !</b>");
        playGameRedLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                playGameRedLabelMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                playGameRedLabelMouseExited(evt);
            }
        });
        newGamePanel.add(playGameRedLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 450, -1, -1));

        newPlayLabel.setFont(new java.awt.Font("Tempus Sans ITC", 0, 18)); // NOI18N
        newPlayLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        newPlayLabel.setText("<html><b>JOUER !</b>");
        newPlayLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                newPlayLabelMouseEntered(evt);
            }
        });
        newGamePanel.add(newPlayLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 450, -1, -1));

        backRedNewGamePanelLabel.setFont(new java.awt.Font("Tempus Sans ITC", 0, 18)); // NOI18N
        backRedNewGamePanelLabel.setForeground(new java.awt.Color(153, 0, 0));
        backRedNewGamePanelLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        backRedNewGamePanelLabel.setText("<html><b>RETOUR</b>");
        backRedNewGamePanelLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backRedNewGamePanelLabelMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backRedNewGamePanelLabelMouseExited(evt);
            }
        });
        newGamePanel.add(backRedNewGamePanelLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 450, -1, -1));

        backNewGamePanelLabel.setFont(new java.awt.Font("Tempus Sans ITC", 0, 18)); // NOI18N
        backNewGamePanelLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        backNewGamePanelLabel.setText("<html><b>RETOUR</b>");
        backNewGamePanelLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backNewGamePanelLabelMouseEntered(evt);
            }
        });
        newGamePanel.add(backNewGamePanelLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 450, -1, -1));

        jComboBoxChooseNbPlayers.setBackground(new java.awt.Color(255, 153, 153));
        jComboBoxChooseNbPlayers.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jComboBoxChooseNbPlayers.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2", "3", "4" }));
        jComboBoxChooseNbPlayers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxChooseNbPlayersActionPerformed(evt);
            }
        });
        newGamePanel.add(jComboBoxChooseNbPlayers, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 120, -1, -1));

        numberPlayersLabel.setFont(new java.awt.Font("Tempus Sans ITC", 0, 18)); // NOI18N
        numberPlayersLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numberPlayersLabel.setText("<html><b>Nombre de joueurs</b>");
        newGamePanel.add(numberPlayersLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, -1, -1));

        player4TextField.setFont(new java.awt.Font("Tempus Sans ITC", 0, 18)); // NOI18N
        player4TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                player4TextFieldActionPerformed(evt);
            }
        });
        newGamePanel.add(player4TextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 390, 100, 30));

        player3TextField.setFont(new java.awt.Font("Tempus Sans ITC", 0, 18)); // NOI18N
        player3TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                player3TextFieldActionPerformed(evt);
            }
        });
        newGamePanel.add(player3TextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 330, 100, 30));

        player2TextField.setFont(new java.awt.Font("Tempus Sans ITC", 0, 18)); // NOI18N
        player2TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                player2TextFieldActionPerformed(evt);
            }
        });
        newGamePanel.add(player2TextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 270, 100, 30));

        player1TextField.setFont(new java.awt.Font("Tempus Sans ITC", 0, 18)); // NOI18N
        player1TextField.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        player1TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                player1TextFieldActionPerformed(evt);
            }
        });
        newGamePanel.add(player1TextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 210, 100, 30));

        player4Label.setFont(new java.awt.Font("Tempus Sans ITC", 0, 18)); // NOI18N
        player4Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        player4Label.setText("<html><b>Joueur 4</b>");
        newGamePanel.add(player4Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 390, -1, -1));

        player3Label.setFont(new java.awt.Font("Tempus Sans ITC", 0, 18)); // NOI18N
        player3Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        player3Label.setText("<html><b>Joueur 3</b>");
        newGamePanel.add(player3Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 330, -1, -1));

        player2Label.setFont(new java.awt.Font("Tempus Sans ITC", 0, 18)); // NOI18N
        player2Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        player2Label.setText("<html><b>Joueur 2</b>");
        newGamePanel.add(player2Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, -1, -1));

        couleurLabel.setFont(new java.awt.Font("Tempus Sans ITC", 0, 18)); // NOI18N
        couleurLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        couleurLabel.setText("<html><b>Couleur</b>");
        newGamePanel.add(couleurLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 170, -1, -1));

        player1Label.setFont(new java.awt.Font("Tempus Sans ITC", 0, 18)); // NOI18N
        player1Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        player1Label.setText("<html><b>Joueur 1</b>");
        newGamePanel.add(player1Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, -1, -1));

        namePlayLabel.setFont(new java.awt.Font("Tempus Sans ITC", 0, 24)); // NOI18N
        namePlayLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        namePlayLabel.setText("<html><b>NOUVELLE PARTIE</b>");
        newGamePanel.add(namePlayLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, -1, -1));

        colorPlayer4ComboBox.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        newGamePanel.add(colorPlayer4ComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 390, -1, -1));

        colorPlayer1ComboBox.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        colorPlayer1ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorPlayer1ComboBoxActionPerformed(evt);
            }
        });
        newGamePanel.add(colorPlayer1ComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 210, -1, -1));

        colorPlayer2ComboBox.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        newGamePanel.add(colorPlayer2ComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 270, -1, -1));

        colorPlayer3ComboBox.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        newGamePanel.add(colorPlayer3ComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 330, -1, -1));

        papyrusLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pap.png"))); // NOI18N
        newGamePanel.add(papyrusLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        add(newGamePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 230, 490, 510));

        MenuPanel.setOpaque(false);
        MenuPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        playRedLabel.setFont(new java.awt.Font("Tempus Sans ITC", 0, 36)); // NOI18N
        playRedLabel.setForeground(new java.awt.Color(153, 0, 0));
        playRedLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        playRedLabel.setText("<html><b>JOUER</b>");
        playRedLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                playRedLabelMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                playRedLabelMouseExited(evt);
            }
        });
        MenuPanel.add(playRedLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, 140, 30));

        playLabel.setFont(new java.awt.Font("Tempus Sans ITC", 0, 36)); // NOI18N
        playLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        playLabel.setText("<html><b>JOUER</b>");
        playLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                playLabelMouseEntered(evt);
            }
        });
        MenuPanel.add(playLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, 140, 30));

        parameterLabel.setFont(new java.awt.Font("Tempus Sans ITC", 0, 36)); // NOI18N
        parameterLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        parameterLabel.setText("<html><b>OPTIONS</b>");
        parameterLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                parameterLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                parameterLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                parameterLabelMouseExited(evt);
            }
        });
        MenuPanel.add(parameterLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, 170, 30));

        quitRedLabel.setFont(new java.awt.Font("Tempus Sans ITC", 0, 36)); // NOI18N
        quitRedLabel.setForeground(new java.awt.Color(153, 0, 0));
        quitRedLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        quitRedLabel.setText("<html><b>QUITTER</b>");
        quitRedLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                quitRedLabelMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                quitRedLabelMouseExited(evt);
            }
        });
        MenuPanel.add(quitRedLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 370, 170, 30));

        quitLabel.setFont(new java.awt.Font("Tempus Sans ITC", 0, 36)); // NOI18N
        quitLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        quitLabel.setText("<html><b>QUITTER</b>");
        quitLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                quitLabelMouseEntered(evt);
            }
        });
        MenuPanel.add(quitLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 370, 170, 30));

        rulesRedLabel.setFont(new java.awt.Font("Tempus Sans ITC", 0, 36)); // NOI18N
        rulesRedLabel.setForeground(new java.awt.Color(153, 0, 0));
        rulesRedLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rulesRedLabel.setText("<html><b>REGLES DU JEU</b>");
        rulesRedLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rulesRedLabelMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                rulesRedLabelMouseExited(evt);
            }
        });
        MenuPanel.add(rulesRedLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 270, 290, 60));

        rulesLabel.setFont(new java.awt.Font("Tempus Sans ITC", 0, 36)); // NOI18N
        rulesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rulesLabel.setText("<html><b>REGLES DU JEU</b>");
        rulesLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                rulesLabelMouseEntered(evt);
            }
        });
        MenuPanel.add(rulesLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 270, 290, 60));

        menuLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pap.png"))); // NOI18N
        MenuPanel.add(menuLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 420, 460));

        add(MenuPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 260, 490, 490));

        playPanel.setOpaque(false);
        playPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        backRedLabel.setFont(new java.awt.Font("Tempus Sans ITC", 0, 24)); // NOI18N
        backRedLabel.setForeground(new java.awt.Color(153, 0, 0));
        backRedLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        backRedLabel.setText("<html><b>RETOUR</b>");
        backRedLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backRedLabelMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backRedLabelMouseExited(evt);
            }
        });
        playPanel.add(backRedLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 390, 130, 20));

        backLabel.setFont(new java.awt.Font("Tempus Sans ITC", 0, 24)); // NOI18N
        backLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        backLabel.setText("<html><b>RETOUR</b>");
        backLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backLabelMouseEntered(evt);
            }
        });
        playPanel.add(backLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 390, 130, 20));

        loadGameJLabel.setFont(new java.awt.Font("Tempus Sans ITC", 0, 36)); // NOI18N
        loadGameJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        loadGameJLabel.setText("<html><b>CHARGER PARTIE</b>");
        loadGameJLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loadGameJLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loadGameJLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loadGameJLabelMouseExited(evt);
            }
        });
        playPanel.add(loadGameJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, 350, 50));

        newGameRedLabel.setFont(new java.awt.Font("Tempus Sans ITC", 0, 36)); // NOI18N
        newGameRedLabel.setForeground(new java.awt.Color(153, 0, 0));
        newGameRedLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        newGameRedLabel.setText("<html><b>NOUVELLE PARTIE</b>");
        newGameRedLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newGameRedLabelMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                newGameRedLabelMouseExited(evt);
            }
        });
        playPanel.add(newGameRedLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, 350, 50));

        newGameLabel.setFont(new java.awt.Font("Tempus Sans ITC", 0, 36)); // NOI18N
        newGameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        newGameLabel.setText("<html><b>NOUVELLE PARTIE</b>");
        newGameLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                newGameLabelMouseEntered(evt);
            }
        });
        playPanel.add(newGameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, 350, 50));

        menuLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pap.png"))); // NOI18N
        playPanel.add(menuLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 420, 460));

        add(playPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 260, 490, 490));

        optionPanel.setOpaque(false);
        optionPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jCheckBox1.setFont(new java.awt.Font("Tempus Sans ITC", 0, 36)); // NOI18N
        jCheckBox1.setText("Son");
        jCheckBox1.setOpaque(false);
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        optionPanel.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 110, 60));

        returnLabel.setFont(new java.awt.Font("Tempus Sans ITC", 0, 36)); // NOI18N
        returnLabel.setText("Retour");
        returnLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                returnLabelMouseClicked(evt);
            }
        });
        optionPanel.add(returnLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 370, -1, -1));

        optionPanelBackgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pap.png"))); // NOI18N
        optionPanel.add(optionPanelBackgroundLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 460));

        add(optionPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 270, 430, 460));

        crossLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/croix_fermer.png"))); // NOI18N
        crossLabel.setText("jLabel2");
        crossLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                crossLabelMouseClicked(evt);
            }
        });
        add(crossLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1310, 10, 40, 30));

        backgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ThebesHome.jpg"))); // NOI18N
        add(backgroundLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 1370, 770));
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Sitch two label, pass one to visible and the other to hidden
     * @param labelToShow
     * @param labelToHide 
     */
    private void switchVisible(JLabel labelToShow, JLabel labelToHide ){
        labelToHide.setVisible(false);
        labelToShow.setVisible(true);
    }
    
    /**
     * Quit application
     *
     * @param evt
     */
    private void crossLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_crossLabelMouseClicked
        System.exit(0);
    }//GEN-LAST:event_crossLabelMouseClicked

    /**
     * When mouse enter into play label
     *
     * @param evt
     */
    private void playLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playLabelMouseEntered
        this.switchVisible( playRedLabel, playLabel);
    }//GEN-LAST:event_playLabelMouseEntered

    /**
     * When mouse exit from quit label
     *
     * @param evt
     */
    private void quitRedLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_quitRedLabelMouseExited
        this.switchVisible( quitLabel, quitRedLabel);
    }//GEN-LAST:event_quitRedLabelMouseExited

    /**
     * When mouse enter into quit label
     *
     * @param evt
     */
    private void quitLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_quitLabelMouseEntered
        this.switchVisible( quitRedLabel, quitLabel);
    }//GEN-LAST:event_quitLabelMouseEntered

    /**
     * When mouse exit from play label
     *
     * @param evt
     */
    private void playRedLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playRedLabelMouseExited
        this.switchVisible( playLabel, playRedLabel);
    }//GEN-LAST:event_playRedLabelMouseExited

    /**
     * When mouse enter into parameter label
     *
     * @param evt
     */
    private void parameterLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_parameterLabelMouseEntered
        ((JLabel)evt.getSource()).setForeground( new java.awt.Color(153, 0, 0) );
    }//GEN-LAST:event_parameterLabelMouseEntered

    /**
     * When mouse enter into rules label
     *
     * @param evt
     */
    private void rulesLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rulesLabelMouseEntered
        this.switchVisible( rulesRedLabel, rulesLabel);
    }//GEN-LAST:event_rulesLabelMouseEntered

    /**
     * When mouse exit from rules label
     *
     * @param evt
     */
    private void rulesRedLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rulesRedLabelMouseExited
        this.switchVisible( rulesLabel, rulesRedLabel);
    }//GEN-LAST:event_rulesRedLabelMouseExited

    /**
     * When mouse click on play label
     *
     * @param evt
     */
    private void playRedLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playRedLabelMouseClicked
        MenuPanel.setVisible(false);
        playPanel.setVisible(true);
    }//GEN-LAST:event_playRedLabelMouseClicked

    /**
     * When mouse enter into new game label
     *
     * @param evt
     */
    private void newGameLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newGameLabelMouseEntered
        this.switchVisible( newGameRedLabel, newGameLabel);
    }//GEN-LAST:event_newGameLabelMouseEntered

    /**
     * When mouse exit from new game label
     *
     * @param evt
     */
    private void newGameRedLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newGameRedLabelMouseExited
        this.switchVisible( newGameLabel, newGameRedLabel);
    }//GEN-LAST:event_newGameRedLabelMouseExited

    /**
     * When mouse enter into load game label
     *
     * @param evt
     */
    private void loadGameJLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loadGameJLabelMouseEntered
//        this.switchVisible( loadGameRedLabel, loadGameLabel);
        ((JLabel)evt.getSource()).setForeground( new java.awt.Color(153, 0, 0) );
    }//GEN-LAST:event_loadGameJLabelMouseEntered

    /**
     * When mouse enter into back label
     *
     * @param evt
     */
    private void backLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backLabelMouseEntered
        this.switchVisible( backRedLabel, backLabel);
    }//GEN-LAST:event_backLabelMouseEntered

    /**
     * When mouse exit from back label
     *
     * @param evt
     */
    private void backRedLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backRedLabelMouseExited
        this.switchVisible( backLabel, backRedLabel);

    }//GEN-LAST:event_backRedLabelMouseExited

    /**
     * When mouse click on back label
     *
     * @param evt
     */
    private void backRedLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backRedLabelMouseClicked
        MenuPanel.setVisible(true);
        playLabel.setVisible(true);
        quitLabel.setVisible(true);
        parameterLabel.setVisible(true);
        rulesLabel.setVisible(true);
        playPanel.setVisible(false);
    }//GEN-LAST:event_backRedLabelMouseClicked

    /**
     * When mouse click on new game label
     *
     * @param evt
     */
    private void newGameRedLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newGameRedLabelMouseClicked
        newGamePanel.setVisible(true);
        player3Label.setVisible(false);
        player4Label.setVisible(false);
        player3TextField.setVisible(false);
        player4TextField.setVisible(false);
        colorPlayer3ComboBox.setVisible(false);
        colorPlayer4ComboBox.setVisible(false);
        player1TextField.requestFocus();
    }//GEN-LAST:event_newGameRedLabelMouseClicked

    private void player1TextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_player1TextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_player1TextFieldActionPerformed

    private void player2TextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_player2TextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_player2TextFieldActionPerformed

    private void player3TextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_player3TextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_player3TextFieldActionPerformed

    private void player4TextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_player4TextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_player4TextFieldActionPerformed

    /**
     * comboBox : Select how many player to play
     * @param evt 
     */
    private void jComboBoxChooseNbPlayersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxChooseNbPlayersActionPerformed
        nbPlayers = Integer.parseInt((String)jComboBoxChooseNbPlayers.getSelectedItem());
        /**
         * Depend of how many players, player 3 and 4 we will displayed or not
         * - players 1 & 2 are always displayed
         */
        switch( nbPlayers ){
            case 2:
                player3Label.setVisible(false);
                player3TextField.setVisible(false);
                colorPlayer3ComboBox.setVisible(false);
                player4Label.setVisible(false);
                player4TextField.setVisible(false);
                colorPlayer4ComboBox.setVisible(false);
                break;
            case 3:
                player3Label.setVisible(true);
                player3TextField.setVisible(true);
                colorPlayer3ComboBox.setVisible(true);
                player4Label.setVisible(false);
                player4TextField.setVisible(false);
                colorPlayer4ComboBox.setVisible(false);
                break;
            case 4:
                player3Label.setVisible(true);
                player3TextField.setVisible(true);
                colorPlayer3ComboBox.setVisible(true);
                player4Label.setVisible(true);
                player4TextField.setVisible(true);
                colorPlayer4ComboBox.setVisible(true);
                break;
            
        }
    }//GEN-LAST:event_jComboBoxChooseNbPlayersActionPerformed

    /**
     * When mouse exit from back label
     *
     * @param evt
     */
    private void backRedNewGamePanelLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backRedNewGamePanelLabelMouseExited
        this.switchVisible( backNewGamePanelLabel, backRedNewGamePanelLabel);
    }//GEN-LAST:event_backRedNewGamePanelLabelMouseExited

    /**
     * When mouse enter into back label
     *
     * @param evt
     */
    private void backNewGamePanelLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backNewGamePanelLabelMouseEntered
        this.switchVisible( backRedNewGamePanelLabel, backNewGamePanelLabel);
    }//GEN-LAST:event_backNewGamePanelLabelMouseEntered

    /**
     * When mouse click on back label
     *
     * @param evt
     */
    private void backRedNewGamePanelLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backRedNewGamePanelLabelMouseClicked
        newGamePanel.setVisible(false);
        playPanel.setVisible(true);
    }//GEN-LAST:event_backRedNewGamePanelLabelMouseClicked

    /**
     * When mouse enter into play label(run map)
     *
     * @param evt
     */
    private void newPlayLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newPlayLabelMouseEntered
        this.switchVisible( playGameRedLabel, newPlayLabel);
    }//GEN-LAST:event_newPlayLabelMouseEntered

    /**
     * When mouse exit from play label
     *
     * @param evt
     */
    private void playGameRedLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playGameRedLabelMouseExited
        this.switchVisible( newPlayLabel, playGameRedLabel);
    }//GEN-LAST:event_playGameRedLabelMouseExited

    /**
     * When mouse click on play label(run map and create board)
     *
     * @param evt
     */
    //définir area position et localdate pour chaque playerToken
    private void playGameRedLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playGameRedLabelMouseClicked
        LOGGER.debug("playGameRedLabelMouseClicked");
        String colorPlayer1 = ((ComboBoxColorElement)colorPlayer1ComboBox.getSelectedItem()).id ;
        String colorPlayer2 = ((ComboBoxColorElement)colorPlayer2ComboBox.getSelectedItem()).id ;
        String colorPlayer3 = ((ComboBoxColorElement)colorPlayer3ComboBox.getSelectedItem()).id ;
        String colorPlayer4 = ((ComboBoxColorElement)colorPlayer4ComboBox.getSelectedItem()).id ;
        String loginPlayer1 = player1TextField.getText();
        String loginPlayer2 = player2TextField.getText();
        String loginPlayer3 = player3TextField.getText();
        String loginPlayer4 = player4TextField.getText();
        ArrayList<Player> players = new ArrayList();
        
        try {
            if (checkColors() && checkLogin()) {
                switch( nbPlayers ){
                    case 2:
                        players.add(new Player(loginPlayer1, new PlayerToken(colorPlayer1)));
                        players.add(new Player(loginPlayer2, new PlayerToken(colorPlayer2)));
                        break;
                    case 3:
                        players.add(new Player(loginPlayer1, new PlayerToken(colorPlayer1)));
                        players.add(new Player(loginPlayer2, new PlayerToken(colorPlayer2)));
                        players.add(new Player(loginPlayer3, new PlayerToken(colorPlayer3)));
                        break;
                    case 4:
                        players.add(new Player(loginPlayer1, new PlayerToken(colorPlayer1)));
                        players.add(new Player(loginPlayer2, new PlayerToken(colorPlayer2)));
                        players.add(new Player(loginPlayer3, new PlayerToken(colorPlayer3)));
                        players.add(new Player(loginPlayer4, new PlayerToken(colorPlayer4)));
                        break;
                }
                
                /**
                * We create the new game internally
                */
               Board board = new Board(nbPlayers, players);
               this.launchGame(board);
                
            } else {
                //fenêtre interne pour avertir qu'il faut donner des noms aux joueurs et une seule couleur par joueur
                warningInternalFrame.setVisible(true);
                newGamePanel.setEnabled(false);
//                newGamePanel.setVisible( false );
                playPanel.setVisible( false );
                optionPanel.setVisible( false  );
            }

            

        } catch (Exception ex) {
            LOGGER.fatal( ex );
            ex.printStackTrace();
            System.exit( 0 );
        }


    }//GEN-LAST:event_playGameRedLabelMouseClicked

    private void warningInternalFrameInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_warningInternalFrameInternalFrameClosed
        newGamePanel.setEnabled(true);
    }//GEN-LAST:event_warningInternalFrameInternalFrameClosed

    private void rulesRedLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rulesRedLabelMouseClicked
        removeAll();
        RulesPanel rules = new RulesPanel();
        add(rules, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
        updateUI();
    }//GEN-LAST:event_rulesRedLabelMouseClicked

    private void quitRedLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_quitRedLabelMouseClicked
        System.exit(0);
    }//GEN-LAST:event_quitRedLabelMouseClicked

    private void loadGameJLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loadGameJLabelMouseExited
        ((JLabel)evt.getSource()).setForeground( new java.awt.Color(0, 0, 0) );
    }//GEN-LAST:event_loadGameJLabelMouseExited

    /**
     * Load game
     * @param evt 
     */
    private void loadGameJLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loadGameJLabelMouseClicked
        Board boardLoaded = Utils.loadGame();
        if( boardLoaded == null ){
            JOptionPane.showMessageDialog( this, "Vous n'avez aucun jeu à charger !");
        }
        else{
            this.launchGame( boardLoaded );
        }
    }//GEN-LAST:event_loadGameJLabelMouseClicked

    private void parameterLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_parameterLabelMouseExited
        ((JLabel)evt.getSource()).setForeground( new java.awt.Color(0, 0, 0) );
    }//GEN-LAST:event_parameterLabelMouseExited

    /**
     * When we enter in Option
     * @param evt 
     */
    private void parameterLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_parameterLabelMouseClicked
        this.optionPanel.setVisible( true );
        this.MenuPanel.setVisible( false );
    }//GEN-LAST:event_parameterLabelMouseClicked

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        JCheckBox cb = (JCheckBox)evt.getSource();
        try {
            if( cb.isSelected()){
                ConfigManager.getInstance().setOption("general", "audio", "1");
                Sound.startAudioGame();
                JOptionPane.showMessageDialog( this, "Vous activez le son");
            }else{
                ConfigManager.getInstance().setOption("general", "audio", "0");
                Sound.stopAudioGame();
                JOptionPane.showMessageDialog( this, "Vous coupez le son");
            }
        } catch (IOException ex) {}
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void returnLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_returnLabelMouseClicked
        this.optionPanel.setVisible( false );
        this.MenuPanel.setVisible( true );
    }//GEN-LAST:event_returnLabelMouseClicked

    private void colorPlayer1ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorPlayer1ComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_colorPlayer1ComboBoxActionPerformed

    
    

    /**
     * check login players
     *
     * @return ok
     */
    public boolean checkLogin() {
        boolean ok = false;
        String logPlayer1 = player1TextField.getText();
        String logPlayer2 = player2TextField.getText();
        String logPlayer3 = player3TextField.getText();
        String logPlayer4 = player4TextField.getText();
        
        if (nbPlayers == 2) {
            if (logPlayer1.compareTo("") != 0 && logPlayer2.compareTo("") != 0 && logPlayer1.compareTo(logPlayer2) != 0) {
                ok = true;
            }
        } else if (nbPlayers == 3) {
            if (logPlayer1.compareTo("") != 0 && logPlayer2.compareTo("") != 0 && logPlayer3.compareTo("") != 0
                    && logPlayer1.compareTo(logPlayer2) != 0 && logPlayer1.compareTo(logPlayer3) != 0 && logPlayer2.compareTo(logPlayer3) != 0) {
                ok = true;
            }
        } else if (nbPlayers == 4) {
            if (logPlayer1.compareTo("") != 0 && logPlayer2.compareTo("") != 0 && logPlayer3.compareTo("") != 0
                    && logPlayer4.compareTo("") != 0 && logPlayer1.compareTo(logPlayer2) != 0
                    && logPlayer1.compareTo(logPlayer3) != 0 && logPlayer2.compareTo(logPlayer3) != 0
                    && logPlayer1.compareTo(logPlayer4) != 0 && logPlayer2.compareTo(logPlayer4) != 0
                    && logPlayer3.compareTo(logPlayer4) != 0) {
                ok = true;
            }
        }
        LOGGER.debug("checkLogin: ok="+ok);
        return ok;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MenuPanel;
    private javax.swing.JLabel backLabel;
    private javax.swing.JLabel backNewGamePanelLabel;
    private javax.swing.JLabel backRedLabel;
    private javax.swing.JLabel backRedNewGamePanelLabel;
    private javax.swing.JLabel backgroundInternalFrameLabel;
    private javax.swing.JLabel backgroundLabel;
    private javax.swing.JComboBox colorPlayer1ComboBox;
    private javax.swing.JComboBox colorPlayer2ComboBox;
    private javax.swing.JComboBox colorPlayer3ComboBox;
    private javax.swing.JComboBox colorPlayer4ComboBox;
    private javax.swing.JLabel couleurLabel;
    private javax.swing.JLabel crossLabel;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBoxChooseNbPlayers;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel loadGameJLabel;
    private javax.swing.JLabel menuLabel;
    private javax.swing.JLabel menuLabel1;
    private javax.swing.JLabel namePlayLabel;
    private javax.swing.JLabel newGameLabel;
    private javax.swing.JPanel newGamePanel;
    private javax.swing.JLabel newGameRedLabel;
    private javax.swing.JLabel newPlayLabel;
    private javax.swing.JLabel numberPlayersLabel;
    private javax.swing.JPanel optionPanel;
    private javax.swing.JLabel optionPanelBackgroundLabel;
    private javax.swing.JLabel papyrusLabel;
    private javax.swing.JLabel parameterLabel;
    private javax.swing.JLabel playGameRedLabel;
    private javax.swing.JLabel playLabel;
    private javax.swing.JPanel playPanel;
    private javax.swing.JLabel playRedLabel;
    private javax.swing.JLabel player1Label;
    private javax.swing.JTextField player1TextField;
    private javax.swing.JLabel player2Label;
    private javax.swing.JTextField player2TextField;
    private javax.swing.JLabel player3Label;
    private javax.swing.JTextField player3TextField;
    private javax.swing.JLabel player4Label;
    private javax.swing.JTextField player4TextField;
    private javax.swing.JLabel quitLabel;
    private javax.swing.JLabel quitRedLabel;
    private javax.swing.JLabel returnLabel;
    private javax.swing.JLabel rulesLabel;
    private javax.swing.JLabel rulesRedLabel;
    private javax.swing.JInternalFrame warningInternalFrame;
    private javax.swing.JLabel warningLabel;
    private javax.swing.JLabel writingLabel;
    // End of variables declaration//GEN-END:variables


    class ComboBoxColorElement {
        String id = "";
        String value = "";

        public ComboBoxColorElement(String id, String value) {
            this.id = id;
            this.value = value;
        }
        
        public String toString() {
            return "<html><font color='"+id+"'>"+value+"</font>";
        }
    }


}
