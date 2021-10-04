package Graphique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Systeme.Systeme;

public class EndWindow extends JDialog {

    public EndWindow(JFrame parentFrame, String titre, boolean modality, double money){
        super(parentFrame, titre, modality);
        this.setSize(500, 300);
        this.setBackground(Color.LIGHT_GRAY);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        JLabel txtPanel = new JLabel();
        txtPanel.setHorizontalAlignment(SwingConstants.CENTER);
        String str = "Vous avez terminé avec " + money + "$ \n";
        txtPanel.setText(str);
        txtPanel.setFont(new Font("Comics sans MS", Font.BOLD, 20));


        JButton terminate = new JButton("Terminer le jeu");
        terminate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Systeme.quit();
                dispose();
            }
        });
        terminate.setPreferredSize(new Dimension(50, 50));

        this.add(txtPanel, BorderLayout.CENTER);
        this.add(terminate, BorderLayout.SOUTH);
        setVisible(true);

    }

}
