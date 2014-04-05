/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.gi;

import com.miage.cards.*;
import com.miage.game.*;
import com.miage.tokens.Token;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Richard
 */
public class MapPanel extends javax.swing.JPanel {

    private Board b;
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;
    private Player currentP;
    private Deck d;
    private ArrayList<Card> ar;
    private ArrayList<Token> art;

    /**
     * Creates new form MapPanel
     */
    public MapPanel() throws IOException {
        initComponents();
        menuCardsPlayer.setVisible(false);
        displayedCardTokenPanel.setVisible(false);
        menuCardsPlayer.remove(3);
        menuCardsPlayer.remove(2);
        b = new Board(2);
        p1 = new Player("Rouch");
        d = b.getDeck();
        ar = new ArrayList<Card>();
        ar.add(new AssistantCard(16, "assistant", "paris", 2));
        ar.add(new AssistantCard(21, "assistant", "roma", 2));
        ar.add(new AssistantCard(19, "assistant", "vienna", 2));
        ar.add(new AssistantCard(17, "assistant", "paris", 2));
        ar.add(new AssistantCard(18, "assistant", "roma", 2));
        ar.add(new AssistantCard(20, "assistant", "vienna", 2));
        ar.add(new CarCard(5, "car", "moscow", 1));
        ar.add(new CarCard(6, "car", "roma", 1));
        ar.add(new ZeppelinCard(3, "zeppelin", "roma", 1));
        ar.add(new ZeppelinCard(4, "zeppelin", "vienna", 1));
        ar.add(new CongressCard(7, "congress", "london", 2));
        ar.add(new CongressCard(8, "congress", "paris", 2));
        ar.add(new CongressCard(9, "congress", "berlin", 2));
        ar.add(new CongressCard(10, "congress", "vienna", 2));
        ar.add(new CongressCard(11, "congress", "moscow", 2));
        ar.add(new CongressCard(12, "congress", "paris", 2));
        ar.add(new CongressCard(13, "congress", "berlin", 2));
        ar.add(new CongressCard(14, "congress", "vienna", 2));
        ar.add(new CongressCard(15, "congress", "moscow", 2));
        ar.add(new EthnologicalKnowledgeCard(81, "ethno", "moscow", 1, 2, "greece"));
        ar.add(new EthnologicalKnowledgeCard(82, "ethno", "paris", 1, 2, "crete"));
        ar.add(new EthnologicalKnowledgeCard(83, "ethno", "rom", 1, 2, "egypt"));
        ar.add(new EthnologicalKnowledgeCard(84, "ethno", "vienna", 1, 2, "palestine"));
        ar.add(new EthnologicalKnowledgeCard(85, "ethno", "berlin", 1, 2, "mesopotamia"));
        ar.add(new ExcavationAuthorizationCard(1, "excavation", "london", 3));
        ar.add(new ExcavationAuthorizationCard(2, "excavation", "moscow", 3));
        ar.add(new ExpoCard(86, "expo", "london", 4, true, 5));
        ar.add(new ExpoCard(91, "expo", "london", 3, false, 4));
        ar.add(new ExpoCard(87, "expo", "paris", 4, true, 5));
        ar.add(new ExpoCard(92, "expo", "paris", 3, false, 4));
        ar.add(new ExpoCard(88, "expo", "berlin", 4, true, 5));
        ar.add(new ExpoCard(93, "expo", "berlin", 3, false, 4));
        ar.add(new ExpoCard(89, "expo", "vienna", 4, true, 5));
        ar.add(new ExpoCard(94, "expo", "vienna", 3, false, 4));
        ar.add(new ExpoCard(90, "expo", "moscow", 4, true, 5));
        ar.add(new ExpoCard(95, "expo", "moscow", 3, false, 4));
        ar.add(new ShovelCard(22, "shovel", "london", 3));
        ar.add(new ShovelCard(23, "shovel", "rom", 3));
        ar.add(new ShovelCard(24, "shovel", "moscow", 3));
        ar.add(new ShovelCard(25, "shovel", "london", 3));
        ar.add(new ShovelCard(26, "shovel", "roma", 3));
        ar.add(new ShovelCard(27, "shovel", "moscow", 3));
        ar.add(new GeneralKnowledgeCard(28, "genKnow", "paris", 3, 1));
        ar.add(new GeneralKnowledgeCard(29, "genKnow", "paris", 3, 1));
        ar.add(new GeneralKnowledgeCard(30, "genKnow", "roma", 3, 1));
        ar.add(new GeneralKnowledgeCard(31, "genKnow", "berlin", 3, 1));
        ar.add(new GeneralKnowledgeCard(32, "genKnow", "berlin", 6, 2));
        ar.add(new GeneralKnowledgeCard(33, "genKnow", "vienna", 6, 2));
        ar.add(new GeneralKnowledgeCard(34, "genKnow", "roma", 6, 2));
        ar.add(new GeneralKnowledgeCard(35, "genKnow", "moscow", 6, 2));
        ar.add(new SpecificKnowledgeCard(36, "specKnow", "roma", 1, 1, "greece"));
        ar.add(new SpecificKnowledgeCard(37, "specKnow", "roma", 2, 2, "greece"));
//        ar.add(new SpecificKnowledgeCard(38, "roma", 2, 2, "greece"));
//        ar.add(new SpecificKnowledgeCard(39, "berlin", 1, 1, "greece"));
//        ar.add(new SpecificKnowledgeCard(40, "berlin", 4, 3, "greece"));
//        ar.add(new SpecificKnowledgeCard(41, "vienna", 1, 1, "greece"));
//        ar.add(new SpecificKnowledgeCard(42, "moscow", 1, 1, "greece"));
        ar.add(new SpecificKnowledgeCard(43, "specKnow", "london", 2, 2, "greece"));
//        ar.add(new SpecificKnowledgeCard(44, "specKnow", "london", 4, 3, "greece"));
        ar.add(new SpecificKnowledgeCard(45, "specKnow", "berlin", 1, 1, "crete"));
//        ar.add(new SpecificKnowledgeCard(46, "berlin", 1, 1, "crete"));
//        ar.add(new SpecificKnowledgeCard(47, "paris", 1, 1, "crete"));
//        ar.add(new SpecificKnowledgeCard(48, "vienna", 1, 1, "crete"));
//        ar.add(new SpecificKnowledgeCard(49, "paris", 2, 2, "crete"));
        ar.add(new SpecificKnowledgeCard(50, "specKnow", "vienna", 2, 2, "crete"));
//        ar.add(new SpecificKnowledgeCard(51, "specKnow", "roma", 2, 2, "crete"));
        ar.add(new SpecificKnowledgeCard(52, "specKnow", "moscow", 4, 3, "crete"));
        ar.add(new SpecificKnowledgeCard(53, "specKnow", "moscow", 4, 3, "crete"));
        ar.add(new SpecificKnowledgeCard(54, "specKnow", "roma", 1, 1, "egypt"));
        ar.add(new SpecificKnowledgeCard(55, "specKnow", "paris", 1, 1, "egypt"));
//        ar.add(new SpecificKnowledgeCard(56, "paris", 1, 1, "egypt"));
//        ar.add(new SpecificKnowledgeCard(57, "moscow", 1, 1, "egypt"));
//        ar.add(new SpecificKnowledgeCard(58, "berlin", 2, 2, "egypt"));
//        ar.add(new SpecificKnowledgeCard(59, "berlin", 2, 2, "egypt"));
        ar.add(new SpecificKnowledgeCard(60, "specKnow", "london", 2, 2, "egypt"));
//        ar.add(new SpecificKnowledgeCard(61, "specKnow", "london", 4, 3, "egypt"));
        ar.add(new SpecificKnowledgeCard(62, "specKnow", "moscow", 4, 3, "egypt"));
        ar.add(new SpecificKnowledgeCard(63, "specKnow", "vienna", 1, 1, "palestine"));
//        ar.add(new SpecificKnowledgeCard(64, "vienna", 1, 1, "palestine"));
//        ar.add(new SpecificKnowledgeCard(65, "vienna", 1, 1, "palestine"));
//        ar.add(new SpecificKnowledgeCard(66, "roma", 1, 1, "palestine"));
        ar.add(new SpecificKnowledgeCard(67, "specKnow", "paris", 2, 2, "palestine"));
        ar.add(new SpecificKnowledgeCard(68, "specKnow", "berlin", 2, 2, "palestine"));
//        ar.add(new SpecificKnowledgeCard(69, "specKnow", "london", 2, 2, "palestine"));
        ar.add(new SpecificKnowledgeCard(70, "specKnow", "london", 4, 3, "palestine"));
        ar.add(new SpecificKnowledgeCard(71, "specKnow", "paris", 4, 3, "palestine"));
        ar.add(new SpecificKnowledgeCard(72, "specKnow", "paris", 1, 1, "mesopotamia"));
        ar.add(new SpecificKnowledgeCard(73, "specKnow", "roma", 1, 1, "mesopotamia"));
//        ar.add(new SpecificKnowledgeCard(74, "moscow", 1, 1, "mesopotamia"));
//        ar.add(new SpecificKnowledgeCard(75, "moscow", 1, 1, "mesopotamia"));
//        ar.add(new SpecificKnowledgeCard(76, "london", 2, 2, "mesopotamia"));
//        ar.add(new SpecificKnowledgeCard(77, "london", 2, 2, "mesopotamia"));
        ar.add(new SpecificKnowledgeCard(78, "specKnow", "vienna", 2, 2, "mesopotamia"));
        ar.add(new SpecificKnowledgeCard(79, "specKnow", "moscow", 4, 3, "mesopotamia"));
//        ar.add(new SpecificKnowledgeCard(80, "specKnow", "london", 4, 3, "mesopotamia"));
        art = new ArrayList<Token>();
        art.add(new Token("1A", "crete", "purple") {});
        art.add(new Token("1B", "crete", "purple") {});
        art.add(new Token("1C", "crete", "purple") {});
        art.add(new Token("2A", "crete", "purple") {});
        art.add(new Token("2B", "crete", "purple") {});
        art.add(new Token("3A", "crete", "purple") {});
        art.add(new Token("3B", "crete", "purple") {});
        art.add(new Token("3C", "crete", "purple") {});
        art.add(new Token("3D", "crete", "purple") {});
        art.add(new Token("4A", "crete", "purple") {});
        art.add(new Token("4B", "crete", "purple") {});
        art.add(new Token("4C", "crete", "purple") {});
        art.add(new Token("5A", "crete", "purple") {});
        art.add(new Token("genKnow", "crete", "purple") {});
        art.add(new Token("scienKnow", "crete", "purple") {});
        art.add(new Token("2B", "egypt", "yellow") {});
        art.add(new Token("genKnow", "egypt", "yellow") {});
        art.add(new Token("3A", "greece", "orange") {});
        art.add(new Token("scienKnow", "greece", "orange") {});
        art.add(new Token("4C", "mesopotamia", "blue") {});
        art.add(new Token("1E", "mesopotamia", "blue") {});
        art.add(new Token("6A", "palestine", "green") {});
        art.add(new Token("7A", "palestine", "green") {});
        p1.setCards(ar);
        p1.setTokens(art);
        menuCardsPlayer.setSelectedIndex(0);
        menuCardsPlayer.setTitleAt(0, p1.getName());
        playerPanel.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        arrowMenuLabel = new javax.swing.JLabel();
        playerPanel = new javax.swing.JPanel();
        berlinAssistantLabel = new javax.swing.JLabel();
        moscowCarLabel = new javax.swing.JLabel();
        berlinEtnoLabel = new javax.swing.JLabel();
        romaZeppelinLabel = new javax.swing.JLabel();
        londonExcavationLabel = new javax.swing.JLabel();
        berlinCongressPLabel = new javax.swing.JLabel();
        berlinGenKnowledgeLabel = new javax.swing.JLabel();
        moscowScienKnowledgeLabel = new javax.swing.JLabel();
        londonShovelLabel = new javax.swing.JLabel();
        berlinSmallExpoLabel = new javax.swing.JLabel();
        mesopotamiaExcavationLabel = new javax.swing.JLabel();
        palestineExcavationLabel = new javax.swing.JLabel();
        egyptExcavationLabel = new javax.swing.JLabel();
        creteExcavationLabel = new javax.swing.JLabel();
        greeceExcavationLabel = new javax.swing.JLabel();
        mesopotamiaNullTokenLabel = new javax.swing.JLabel();
        palestineNullTokenLabel = new javax.swing.JLabel();
        greeceNullTokenLabel = new javax.swing.JLabel();
        creteNullTokenLabel = new javax.swing.JLabel();
        egyptNullTokenLabel = new javax.swing.JLabel();
        playerBackgroundLabel = new javax.swing.JLabel();
        menuCardsPlayer = new javax.swing.JTabbedPane();
        player1Panel = new javax.swing.JPanel();
        player2Panel = new javax.swing.JPanel();
        player3Panel = new javax.swing.JPanel();
        player4Panel = new javax.swing.JPanel();
        logMenu = new javax.swing.JPanel();
        logMenuScrollBar = new javax.swing.JScrollBar();
        usableCardsMenu = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        usedCardsMenu = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        displayedCardTokenPanel = new javax.swing.JPanel();
        backgroundLabel = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        arrowMenuLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background/menuArrow.png"))); // NOI18N
        arrowMenuLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                arrowMenuLabelMouseEntered(evt);
            }
        });
        add(arrowMenuLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 384, -1, -1));

        playerPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                playerPanelMouseExited(evt);
            }
        });
        playerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        berlinAssistantLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cards/18.jpg"))); // NOI18N
        berlinAssistantLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                berlinAssistantLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                berlinAssistantLabelMouseExited(evt);
            }
        });
        playerPanel.add(berlinAssistantLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 190, -1, -1));

        moscowCarLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cards/5.jpg"))); // NOI18N
        moscowCarLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                moscowCarLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                moscowCarLabelMouseExited(evt);
            }
        });
        playerPanel.add(moscowCarLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, -1, -1));

        berlinEtnoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cards/85.jpg"))); // NOI18N
        berlinEtnoLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                berlinEtnoLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                berlinEtnoLabelMouseExited(evt);
            }
        });
        playerPanel.add(berlinEtnoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, -1, -1));

        romaZeppelinLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cards/3.jpg"))); // NOI18N
        romaZeppelinLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                romaZeppelinLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                romaZeppelinLabelMouseExited(evt);
            }
        });
        playerPanel.add(romaZeppelinLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, -1, -1));

        londonExcavationLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cards/1.jpg"))); // NOI18N
        londonExcavationLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                londonExcavationLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                londonExcavationLabelMouseExited(evt);
            }
        });
        playerPanel.add(londonExcavationLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, -1, -1));

        berlinCongressPLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cards/9.jpg"))); // NOI18N
        berlinCongressPLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                berlinCongressPLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                berlinCongressPLabelMouseExited(evt);
            }
        });
        playerPanel.add(berlinCongressPLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        berlinGenKnowledgeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cards/31.jpg"))); // NOI18N
        berlinGenKnowledgeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                berlinGenKnowledgeLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                berlinGenKnowledgeLabelMouseExited(evt);
            }
        });
        playerPanel.add(berlinGenKnowledgeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        moscowScienKnowledgeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cards/74.jpg"))); // NOI18N
        moscowScienKnowledgeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                moscowScienKnowledgeLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                moscowScienKnowledgeLabelMouseExited(evt);
            }
        });
        playerPanel.add(moscowScienKnowledgeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 360, -1, -1));

        londonShovelLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cards/22.jpg"))); // NOI18N
        londonShovelLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                londonShovelLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                londonShovelLabelMouseExited(evt);
            }
        });
        playerPanel.add(londonShovelLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, -1, -1));

        berlinSmallExpoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cards/88.jpg"))); // NOI18N
        berlinSmallExpoLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                berlinSmallExpoLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                berlinSmallExpoLabelMouseExited(evt);
            }
        });
        playerPanel.add(berlinSmallExpoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 390, -1, -1));

        mesopotamiaExcavationLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tokens/excavations/recto/mesopotamiaExcavation.jpg"))); // NOI18N
        playerPanel.add(mesopotamiaExcavationLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 550, -1, -1));

        palestineExcavationLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tokens/excavations/recto/palestineExcavation.jpg"))); // NOI18N
        playerPanel.add(palestineExcavationLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 550, -1, -1));

        egyptExcavationLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tokens/excavations/recto/egyptExcavation.jpg"))); // NOI18N
        playerPanel.add(egyptExcavationLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 550, -1, -1));

        creteExcavationLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tokens/excavations/recto/creteExcavation.jpg"))); // NOI18N
        playerPanel.add(creteExcavationLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, -1, -1));

        greeceExcavationLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tokens/excavations/recto/greeceExcavation.jpg"))); // NOI18N
        playerPanel.add(greeceExcavationLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 550, -1, -1));

        mesopotamiaNullTokenLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tokens/mesopotamia/blueNull.png"))); // NOI18N
        mesopotamiaNullTokenLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mesopotamiaNullTokenLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mesopotamiaNullTokenLabelMouseExited(evt);
            }
        });
        playerPanel.add(mesopotamiaNullTokenLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 650, -1, -1));

        palestineNullTokenLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tokens/palestine/greenNull.png"))); // NOI18N
        palestineNullTokenLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                palestineNullTokenLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                palestineNullTokenLabelMouseExited(evt);
            }
        });
        playerPanel.add(palestineNullTokenLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 650, -1, -1));

        greeceNullTokenLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tokens/greece/orangeNull.png"))); // NOI18N
        greeceNullTokenLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                greeceNullTokenLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                greeceNullTokenLabelMouseExited(evt);
            }
        });
        playerPanel.add(greeceNullTokenLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 650, -1, -1));

        creteNullTokenLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tokens/crete/purpleNull.png"))); // NOI18N
        creteNullTokenLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                creteNullTokenLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                creteNullTokenLabelMouseExited(evt);
            }
        });
        playerPanel.add(creteNullTokenLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 650, -1, -1));

        egyptNullTokenLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tokens/egypt/yellowNull.png"))); // NOI18N
        egyptNullTokenLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                egyptNullTokenLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                egyptNullTokenLabelMouseExited(evt);
            }
        });
        playerPanel.add(egyptNullTokenLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 650, -1, -1));

        playerBackgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background/playerBackground.jpg"))); // NOI18N
        playerPanel.add(playerBackgroundLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        add(playerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 25, -1, -1));

        player1Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        menuCardsPlayer.addTab("Joueur 1", player1Panel);

        player2Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        menuCardsPlayer.addTab("Joueur 2", player2Panel);

        player3Panel.setOpaque(false);
        player3Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        menuCardsPlayer.addTab("Joueur 3", player3Panel);

        player4Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        menuCardsPlayer.addTab("Joueur 4", player4Panel);

        add(menuCardsPlayer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 770));

        logMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        logMenu.add(logMenuScrollBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, -1, 180));

        add(logMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 320, 180));

        usableCardsMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        usableCardsMenu.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, 10));

        add(usableCardsMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 180, 320, 370));

        usedCardsMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        usedCardsMenu.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, -1));

        add(usedCardsMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 550, 320, 220));

        displayedCardTokenPanel.setOpaque(false);
        add(displayedCardTokenPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(474, 20, 550, 730));

        backgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background/map.jpg"))); // NOI18N
        add(backgroundLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void arrowMenuLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_arrowMenuLabelMouseEntered
        menuCardsPlayer.setVisible(true);
        playerPanel.setVisible(true);
        arrowMenuLabel.setVisible(false);
        backgroundLabel.setEnabled(false);
    }//GEN-LAST:event_arrowMenuLabelMouseEntered

    private void displayPlayerCard(Class cl) {
        displayedCardTokenPanel.setVisible(true);
        for (Card c : getPlayerTab(menuCardsPlayer).getCards()) {
            if (c.getClass().getName().equals(cl.getName())) {
                javax.swing.JLabel imageCard = new javax.swing.JLabel();
                imageCard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cards/" + c.getId() + ".jpg")));
                displayedCardTokenPanel.add(imageCard);
            }
        }
        displayedCardTokenPanel.updateUI();
    }

    private void displayPlayerAreaToken(String color) {
        displayedCardTokenPanel.setVisible(true);
        for (Token t : getPlayerTab(menuCardsPlayer).getTokens()) {
            if (t.getColor().equals(color)) {
                javax.swing.JLabel imageToken = new javax.swing.JLabel();
                imageToken.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tokens/" + t.getAreaName()+ "/"+t.getId() +".png")));
                displayedCardTokenPanel.add(imageToken);
            }
        }
        displayedCardTokenPanel.updateUI();
    }

    private Player getPlayerTab(javax.swing.JTabbedPane tp) {
        switch (tp.getSelectedIndex()) {
            case 0:
                currentP = p1;
                break;
            case 1:
                currentP = p2;
                break;
            case 2:
                currentP = p3;
                break;
            case 3:
                currentP = p4;
                break;
        }
        return currentP;
    }

    private void clearDiplayedCardPlayer() {
        displayedCardTokenPanel.setVisible(false);
        displayedCardTokenPanel.removeAll();
    }

    private void moscowCarLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_moscowCarLabelMouseEntered
        this.displayPlayerCard(CarCard.class);
    }//GEN-LAST:event_moscowCarLabelMouseEntered

    private void moscowCarLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_moscowCarLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_moscowCarLabelMouseExited

    private void romaZeppelinLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_romaZeppelinLabelMouseEntered
        this.displayPlayerCard(ZeppelinCard.class);
    }//GEN-LAST:event_romaZeppelinLabelMouseEntered

    private void romaZeppelinLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_romaZeppelinLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_romaZeppelinLabelMouseExited

    private void londonExcavationLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_londonExcavationLabelMouseEntered
        this.displayPlayerCard(ExcavationAuthorizationCard.class);
    }//GEN-LAST:event_londonExcavationLabelMouseEntered

    private void londonExcavationLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_londonExcavationLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_londonExcavationLabelMouseExited

    private void berlinCongressPLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_berlinCongressPLabelMouseEntered
        this.displayPlayerCard(CongressCard.class);
    }//GEN-LAST:event_berlinCongressPLabelMouseEntered

    private void berlinCongressPLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_berlinCongressPLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_berlinCongressPLabelMouseExited

    private void berlinGenKnowledgeLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_berlinGenKnowledgeLabelMouseEntered
        this.displayPlayerCard(GeneralKnowledgeCard.class);
    }//GEN-LAST:event_berlinGenKnowledgeLabelMouseEntered

    private void berlinGenKnowledgeLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_berlinGenKnowledgeLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_berlinGenKnowledgeLabelMouseExited

    private void berlinAssistantLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_berlinAssistantLabelMouseEntered
        this.displayPlayerCard(AssistantCard.class);
    }//GEN-LAST:event_berlinAssistantLabelMouseEntered

    private void berlinAssistantLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_berlinAssistantLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_berlinAssistantLabelMouseExited

    private void berlinEtnoLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_berlinEtnoLabelMouseEntered
        this.displayPlayerCard(EthnologicalKnowledgeCard.class);
    }//GEN-LAST:event_berlinEtnoLabelMouseEntered

    private void berlinEtnoLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_berlinEtnoLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_berlinEtnoLabelMouseExited

    private void moscowScienKnowledgeLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_moscowScienKnowledgeLabelMouseEntered
        this.displayPlayerCard(SpecificKnowledgeCard.class);
    }//GEN-LAST:event_moscowScienKnowledgeLabelMouseEntered

    private void moscowScienKnowledgeLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_moscowScienKnowledgeLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_moscowScienKnowledgeLabelMouseExited

    private void londonShovelLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_londonShovelLabelMouseEntered
        this.displayPlayerCard(ShovelCard.class);
    }//GEN-LAST:event_londonShovelLabelMouseEntered

    private void londonShovelLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_londonShovelLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_londonShovelLabelMouseExited

    private void berlinSmallExpoLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_berlinSmallExpoLabelMouseEntered
        this.displayPlayerCard(ExpoCard.class);
    }//GEN-LAST:event_berlinSmallExpoLabelMouseEntered

    private void berlinSmallExpoLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_berlinSmallExpoLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_berlinSmallExpoLabelMouseExited

    private void playerPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playerPanelMouseExited
        if ((evt.getXOnScreen() > playerPanel.getWidth()) || (evt.getYOnScreen() > playerPanel.getHeight())) {
            menuCardsPlayer.setVisible(false);
            playerPanel.setVisible(false);
            arrowMenuLabel.setVisible(true);
            backgroundLabel.setEnabled(true);
        }
    }//GEN-LAST:event_playerPanelMouseExited

    private void creteNullTokenLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_creteNullTokenLabelMouseEntered
        displayPlayerAreaToken("purple");
    }//GEN-LAST:event_creteNullTokenLabelMouseEntered

    private void creteNullTokenLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_creteNullTokenLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_creteNullTokenLabelMouseExited

    private void palestineNullTokenLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_palestineNullTokenLabelMouseEntered
        displayPlayerAreaToken("green");
    }//GEN-LAST:event_palestineNullTokenLabelMouseEntered

    private void palestineNullTokenLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_palestineNullTokenLabelMouseExited
         this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_palestineNullTokenLabelMouseExited

    private void mesopotamiaNullTokenLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mesopotamiaNullTokenLabelMouseEntered
        displayPlayerAreaToken("blue");
    }//GEN-LAST:event_mesopotamiaNullTokenLabelMouseEntered

    private void mesopotamiaNullTokenLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mesopotamiaNullTokenLabelMouseExited
         this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_mesopotamiaNullTokenLabelMouseExited

    private void greeceNullTokenLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_greeceNullTokenLabelMouseEntered
        displayPlayerAreaToken("orange");
    }//GEN-LAST:event_greeceNullTokenLabelMouseEntered

    private void greeceNullTokenLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_greeceNullTokenLabelMouseExited
         this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_greeceNullTokenLabelMouseExited

    private void egyptNullTokenLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_egyptNullTokenLabelMouseEntered
        displayPlayerAreaToken("yellow");
    }//GEN-LAST:event_egyptNullTokenLabelMouseEntered

    private void egyptNullTokenLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_egyptNullTokenLabelMouseExited
         this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_egyptNullTokenLabelMouseExited

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel arrowMenuLabel;
    private javax.swing.JLabel backgroundLabel;
    private javax.swing.JLabel berlinAssistantLabel;
    private javax.swing.JLabel berlinCongressPLabel;
    private javax.swing.JLabel berlinEtnoLabel;
    private javax.swing.JLabel berlinGenKnowledgeLabel;
    private javax.swing.JLabel berlinSmallExpoLabel;
    private javax.swing.JLabel creteExcavationLabel;
    private javax.swing.JLabel creteNullTokenLabel;
    private javax.swing.JPanel displayedCardTokenPanel;
    private javax.swing.JLabel egyptExcavationLabel;
    private javax.swing.JLabel egyptNullTokenLabel;
    private javax.swing.JLabel greeceExcavationLabel;
    private javax.swing.JLabel greeceNullTokenLabel;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPanel logMenu;
    private javax.swing.JScrollBar logMenuScrollBar;
    private javax.swing.JLabel londonExcavationLabel;
    private javax.swing.JLabel londonShovelLabel;
    private javax.swing.JTabbedPane menuCardsPlayer;
    private javax.swing.JLabel mesopotamiaExcavationLabel;
    private javax.swing.JLabel mesopotamiaNullTokenLabel;
    private javax.swing.JLabel moscowCarLabel;
    private javax.swing.JLabel moscowScienKnowledgeLabel;
    private javax.swing.JLabel palestineExcavationLabel;
    private javax.swing.JLabel palestineNullTokenLabel;
    private javax.swing.JPanel player1Panel;
    private javax.swing.JPanel player2Panel;
    private javax.swing.JPanel player3Panel;
    private javax.swing.JPanel player4Panel;
    private javax.swing.JLabel playerBackgroundLabel;
    private javax.swing.JPanel playerPanel;
    private javax.swing.JLabel romaZeppelinLabel;
    private javax.swing.JPanel usableCardsMenu;
    private javax.swing.JPanel usedCardsMenu;
    // End of variables declaration//GEN-END:variables
}
