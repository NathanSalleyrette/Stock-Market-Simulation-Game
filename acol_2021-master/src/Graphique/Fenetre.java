package Graphique;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import Systeme.Systeme;


public class Fenetre extends JFrame {

    private JTabbedPane topTabPanel = new JTabbedPane();
    private JPanel actions, portefeuille;
    private JPanel bottomPanel = new JPanel();
    private JButton butAcheter, butVendre, butTourSuiv, butQuit;
    private JTextArea txtPortefeuille, txtHist;
    private final Font police = new Font("Comics sans MS", Font.BOLD, 16);
    private JButton[] actionButtons = new JButton[39];
    private JLabel moneyLabel, portfolioLabel, tourLabel;
    private int lastIDHist = 0;

    public Fenetre(String name){
        this.setTitle("ACOL: jeu de simulation : " + name);
        this.setSize(1200,800);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        // Definition du haut
        topTabPanel.setBackground(Color.LIGHT_GRAY);
        portefeuille = new JPanel();
        portefeuille.setLayout(new GridLayout(1,2));
        portefeuille.setBackground(Color.DARK_GRAY);
        txtPortefeuille = new JTextArea(20, 10);
        txtPortefeuille.setBackground(Color.LIGHT_GRAY);
        txtPortefeuille.setFont(new Font("Comics sans MS", Font.BOLD, 20));

        txtPortefeuille.setEditable(false);

        JScrollPane txtScrollPort = new JScrollPane(txtPortefeuille);

        //txtScrollPort.add(txtPortefeuille);
        txtScrollPort.setPreferredSize(new Dimension(500, 100));
        JPanel FondGris1 = new JPanel();
        FondGris1.setBackground(Color.DARK_GRAY);

        JPanel FondGris2 = new JPanel();
        FondGris2.setBackground(Color.DARK_GRAY);
        JSplitPane splitPort1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, txtScrollPort, FondGris2);
        splitPort1.setDividerLocation(750);
        splitPort1.setEnabled(false);
        JSplitPane splitPort2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, FondGris1, splitPort1);
        splitPort2.setDividerLocation(200);
        splitPort2.setEnabled(false);

        portefeuille.add(splitPort2);



        topTabPanel.addTab("Portefeuille", portefeuille);

        // Les actions
        actions = new JPanel();
        actions.setLayout(new GridLayout(1, 2));
        actions.setPreferredSize(new Dimension(10, 10));
        actions.setBackground(Color.BLUE);
        JPanel actionsButtonPanel = new JPanel();
        actionsButtonPanel.setLayout(new GridLayout(39, 1));

        for (int i = 0; i<39; i++) {
            actionButtons[i] = new JButton("" + i);
            actionButtons[i].setFont(new Font("Comics sans MS", Font.BOLD, 24));
            actionButtons[i].setBackground(Color.BLACK);
            actionButtons[i].setForeground(Color.WHITE);
            actionButtons[i].setHorizontalAlignment(SwingConstants.LEFT);
            actionButtons[i].setPreferredSize(new Dimension(70, 130));
            actionsButtonPanel.add(actionButtons[i]);

            int finalI = i;
            actionButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Systeme.updateHistBut(finalI);
                }
            });
        }

        JScrollPane actionsScroll = new JScrollPane(actionsButtonPanel);


        JPanel hist = new JPanel();
        hist.setPreferredSize(new Dimension(100, 100));
        //hist.setAlignmentX(Component.RIGHT_ALIGNMENT);
        txtHist = new JTextArea(20, 20);
        txtHist.setBackground(Color.LIGHT_GRAY);
        txtHist.setFont(new Font("Comics sans MS", Font.BOLD, 20));
        txtHist.setEditable(false);
        //txtHist.setAlignmentX(StyleConstants.ALIGN_CENTER);
        hist.setLayout(new GridLayout(1,1));
        JScrollPane histScroll = new JScrollPane(txtHist);
        hist.add(histScroll);


        actions.add(actionsScroll);
        actions.add(hist);
        actions.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        topTabPanel.addTab("Actions", actions);
       // topTabPanel.s
        // Actions du bas
        bottomPanel.setBackground(Color.DARK_GRAY);
        bottomPanel.setLayout(new GridLayout(1, 1));

        JPanel informPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        //Inform Panel
        Font pol = new Font("Comics sans MS", Font.BOLD, 25);
        moneyLabel = new JLabel("Test 1");
        moneyLabel.setFont(pol);
        moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);

        portfolioLabel = new JLabel("Test 2");
        portfolioLabel.setFont(pol);
        portfolioLabel.setHorizontalAlignment(SwingConstants.CENTER);

        tourLabel = new JLabel("Tour 0");
        tourLabel.setFont(pol);
        tourLabel.setHorizontalAlignment(SwingConstants.CENTER);

        informPanel.setLayout(new GridLayout(3, 1));
        informPanel.add(moneyLabel);
        informPanel.add(portfolioLabel);
        informPanel.add(tourLabel);

        butAcheter = new JButton("Acheter");


        butVendre = new JButton("Vendre");

        butAcheter.setPreferredSize(new Dimension(400, 60));
        butVendre.setPreferredSize(new Dimension(400, 60));
        //bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        JPanel buyAndSell = new JPanel();
        //buyAndSell.setLayout(new BoxLayout(buyAndSell, BoxLayout.PAGE_AXIS));
        buyAndSell.add(butAcheter);
        buyAndSell.add(butVendre);

        JPanel tourSuiv = new JPanel();
        butTourSuiv = new JButton("Tour suivant");
        butTourSuiv.setPreferredSize(new Dimension(400, 60));
        tourSuiv.add(butTourSuiv);

        butQuit = new JButton("Quitter");
        butQuit.setPreferredSize(new Dimension(400, 60));
        tourSuiv.add(butQuit);

        buttonPanel.add(buyAndSell);
        buttonPanel.add(tourSuiv);

        JSplitPane splitBottom = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, informPanel, buttonPanel);
        splitBottom.setDividerLocation(375);
        splitBottom.setEnabled(false);
        bottomPanel.add(splitBottom);


        // Actions des boutons

        butAcheter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                DialogWindow dw = new DialogWindow(null, "Achat d'une action", true, true);
                dw.dispose();

            }
        }
        );

        butVendre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DialogWindow dw = new DialogWindow(null, "Vente d'une action", true, false);
                dw.dispose();
            }
        }
        );

        butQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // A mettre en place, fenetre voulez vous vraiment quitter, puis fenetre de récapitulation
                Systeme.endWindow();
            }
        });

        butTourSuiv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                Systeme.updateLap();

            }
        });


        // On sépare les différentes fenêtres
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topTabPanel, bottomPanel);
        split.setDividerLocation(600);
        split.setEnabled(false);

        this.setContentPane(split);
        this.setVisible(true);
    }

    public void setTextButtonsHtml(int iDAction, String strHtml){
        actionButtons[iDAction-1].setText(strHtml);
    }

    public void setTextPortefeuille(String str){txtPortefeuille.setText(str);}

    public void setTextHist(String str){txtHist.setText(str);}

    public void setLastIDHist(int i){
        lastIDHist = i;
    }

    public int getLastIDHist(){
        return lastIDHist;
    }

    public void setTextInformLabel(String money, String portfolio){
        moneyLabel.setText(money);
        portfolioLabel.setText(portfolio);
    }

    public void setTextNextLap(int lap){
        String str = "Tour : " + lap;
        tourLabel.setText(str);
    }


}
