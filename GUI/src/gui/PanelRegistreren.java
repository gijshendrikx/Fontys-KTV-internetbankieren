package gui;

import java.rmi.RemoteException;

public class PanelRegistreren extends javax.swing.JPanel {

    private final GUIWindow gui;

    public PanelRegistreren(GUIWindow g) {
        gui = g;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pfWachtwoord = new javax.swing.JPasswordField();
        tfInlognaam = new javax.swing.JTextField();
        lbInlognaam = new javax.swing.JLabel();
        lbWachtwoord = new javax.swing.JLabel();
        btRegistreren = new javax.swing.JButton();
        btAnnuleren = new javax.swing.JButton();
        tfWoonplaats = new javax.swing.JTextField();
        lbWoonplaats = new javax.swing.JLabel();

        setMaximumSize(null);
        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(473, 162));

        pfWachtwoord.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        tfInlognaam.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lbInlognaam.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lbInlognaam.setText("Inlognaam");

        lbWachtwoord.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lbWachtwoord.setText("Wachtwoord");

        btRegistreren.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btRegistreren.setText("Registreren");
        btRegistreren.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRegistrerenActionPerformed(evt);
            }
        });

        btAnnuleren.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btAnnuleren.setText("Annuleren");
        btAnnuleren.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAnnulerenActionPerformed(evt);
            }
        });

        tfWoonplaats.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lbWoonplaats.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lbWoonplaats.setText("Woonplaats");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbWachtwoord, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btAnnuleren)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                                .addComponent(btRegistreren))
                            .addComponent(pfWachtwoord)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbInlognaam, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbWoonplaats, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfWoonplaats, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tfInlognaam))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfInlognaam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbInlognaam))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfWoonplaats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbWoonplaats))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pfWachtwoord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbWachtwoord))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btRegistreren)
                    .addComponent(btAnnuleren))
                .addContainerGap(20, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btRegistrerenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRegistrerenActionPerformed
        String naam = tfInlognaam.getText();
        String woonplaats = tfWoonplaats.getText();
        String wachtwoord = new String(pfWachtwoord.getPassword());
        pfWachtwoord.setText("");
        try {
            String login = gui.balie.registreren(naam, woonplaats, wachtwoord);
            gui.log("Gefeliciteerd! U bent succesvol registreerd.");
            gui.log("Uw loginnaam is: " + login + ". Uw wachtwoord is: " + wachtwoord);
            gui.log("Bewaar deze goed, deze wordt slechts eenmalig verstrekt.");
            tfInlognaam.setText("");
            tfWoonplaats.setText("");
            gui.setPanel("Inloggen");
        } catch (RemoteException ex) {
            gui.log(ex);
        }
    }//GEN-LAST:event_btRegistrerenActionPerformed

    private void btAnnulerenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAnnulerenActionPerformed
        tfInlognaam.setText("");
        tfWoonplaats.setText("");
        pfWachtwoord.setText("");
        gui.setPanel("Inloggen");
    }//GEN-LAST:event_btAnnulerenActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAnnuleren;
    private javax.swing.JButton btRegistreren;
    private javax.swing.JLabel lbInlognaam;
    private javax.swing.JLabel lbWachtwoord;
    private javax.swing.JLabel lbWoonplaats;
    private javax.swing.JPasswordField pfWachtwoord;
    private javax.swing.JTextField tfInlognaam;
    private javax.swing.JTextField tfWoonplaats;
    // End of variables declaration//GEN-END:variables
}
