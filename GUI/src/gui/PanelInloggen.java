package gui;

import java.rmi.RemoteException;

public class PanelInloggen extends javax.swing.JPanel {

    private final GUIWindow gui;

    public PanelInloggen(GUIWindow g) {
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
        btInloggen = new javax.swing.JButton();
        btRegistreren = new javax.swing.JButton();

        setMaximumSize(null);
        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(473, 162));

        pfWachtwoord.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        tfInlognaam.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lbInlognaam.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lbInlognaam.setText("Inlognaam");

        lbWachtwoord.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lbWachtwoord.setText("Wachtwoord");

        btInloggen.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btInloggen.setText("Inloggen");
        btInloggen.setMaximumSize(new java.awt.Dimension(113, 29));
        btInloggen.setMinimumSize(new java.awt.Dimension(113, 29));
        btInloggen.setPreferredSize(new java.awt.Dimension(113, 29));
        btInloggen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btInloggenActionPerformed(evt);
            }
        });

        btRegistreren.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btRegistreren.setText("Registreren");
        btRegistreren.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRegistrerenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbInlognaam, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfInlognaam))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbWachtwoord, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btRegistreren)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                                .addComponent(btInloggen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(pfWachtwoord))))
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
                    .addComponent(pfWachtwoord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbWachtwoord))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btInloggen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btRegistreren))
                .addContainerGap(41, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btInloggenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btInloggenActionPerformed
        String naam = tfInlognaam.getText();
        String wachtwoord = new String(pfWachtwoord.getPassword());
        pfWachtwoord.setText("");
        try {
            String sessieId = gui.balie.inloggen(naam, wachtwoord);
            gui.setSessie(sessieId);
            gui.log("Welkom. U bent ingelogd.");
            gui.setPanel("Bankieren");
        } catch (RemoteException ex) {
            gui.log(ex.getCause().getMessage());
        }
    }//GEN-LAST:event_btInloggenActionPerformed

    private void btRegistrerenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRegistrerenActionPerformed
        tfInlognaam.setText("");
        pfWachtwoord.setText("");
        gui.setPanel("Registreren");
    }//GEN-LAST:event_btRegistrerenActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btInloggen;
    private javax.swing.JButton btRegistreren;
    private javax.swing.JLabel lbInlognaam;
    private javax.swing.JLabel lbWachtwoord;
    private javax.swing.JPasswordField pfWachtwoord;
    private javax.swing.JTextField tfInlognaam;
    // End of variables declaration//GEN-END:variables
}
