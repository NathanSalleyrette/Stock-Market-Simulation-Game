package Graphique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Systeme.Systeme;

public class DialogWindow extends JDialog {

    private int actionID;
    private int quantity;

    public DialogWindow(JFrame parentFrame, String titre, boolean modality, boolean buy) {
        super(parentFrame, titre, modality);
        this.setSize(500, 300);
        this.setBackground(Color.LIGHT_GRAY);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        JPanel generalPanel = new JPanel();

        // Achat ou Vente
            // ID de l'action désiré


        JPanel iDPanel = new JPanel();
        JTextField iDText = new JTextField();
        iDPanel.setPreferredSize(new Dimension(400, 100));
        iDText.setPreferredSize(new Dimension(100, 20));
        iDPanel.setBorder(BorderFactory.createTitledBorder((buy)?"ID de l'action à acheter":"ID de l'action à vendre"));
        iDPanel.add(new JLabel("Ecrire l'ID ici : "));
        iDPanel.add(iDText);

            // Quantité d'actions désirés
        JPanel quantityPanel = new JPanel();
        JTextField quantityText = new JTextField();
        quantityPanel.setPreferredSize(new Dimension(400, 100));
        quantityText.setPreferredSize(new Dimension(100, 20));
        quantityPanel.setBorder(BorderFactory.createTitledBorder("Quantité désiré"));
        quantityPanel.add(new JLabel("Ecrire la quantité ici :"));
        quantityPanel.add(quantityText);




        //Button
        JPanel controlPanel = new JPanel();
        JButton butValider = new JButton("Valider");
        JButton butAnnuler = new JButton("Annuler");

        butValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                boolean iDorQuant = true;
                try {
                    //On a besoin de ce -1 car le premier indice des tableaux est 0 et non 1
                    actionID = Integer.parseInt(iDText.getText()) - 1;
                    if (actionID > 38 || actionID < 0) throw new NumberFormatException();
                    iDorQuant = false;
                    quantity = Integer.parseInt(quantityText.getText());
                    if (quantity < 0) throw new NumberFormatException();
                    if (buy) Systeme.buy(actionID, quantity);
                    else Systeme.sell(actionID, quantity);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,
                            (iDorQuant)?"Veuillez entrer un nombre entier compris entre 1 et 39":"La quantité demandée doit être positive",
                            (iDorQuant)?"Erreur ID":"Erreur Quantité",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Quantité d'actions disponibles insuffisantes",
                            "Erreur nombre action demandé",
                            JOptionPane.ERROR_MESSAGE);
                }
                setVisible(false);
            }
        });

        butAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
            }
        });

        controlPanel.add(butValider);
        controlPanel.add(butAnnuler);

        generalPanel.add(iDPanel);
        generalPanel.add(quantityPanel);
        generalPanel.add(controlPanel);

        this.add(generalPanel);


        setVisible(true);


    }

    public int getQuantity() {
        return quantity;
    }

    public int getActionID() {
        return actionID;
    }

}
