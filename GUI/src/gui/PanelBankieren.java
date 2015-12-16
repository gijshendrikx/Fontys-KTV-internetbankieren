package gui;

import common.Transactie;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;

public class PanelBankieren extends javax.swing.JPanel {

    private final GUIWindow gui;
    private final SimpleDateFormat sdf;

    public PanelBankieren(GUIWindow g) {
        gui = g;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btRekeningOpenen = new javax.swing.JButton();
        btSaldiInzien = new javax.swing.JButton();
        cbRekening = new javax.swing.JComboBox();
        btTransactiesInzien = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lbInlognaam1 = new javax.swing.JLabel();
        lbWachtwoord1 = new javax.swing.JLabel();
        tfBegunstigdeNaam = new javax.swing.JTextField();
        btOverboeken = new javax.swing.JButton();
        lbInlognaam2 = new javax.swing.JLabel();
        lbWachtwoord2 = new javax.swing.JLabel();
        tfBegunstigdeIBAN = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        taOmschrijving = new javax.swing.JTextArea();
        tfBedrag = new javax.swing.JFormattedTextField();

        setMaximumSize(null);
        setMinimumSize(new java.awt.Dimension(0, 0));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mijn rekeningen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        btRekeningOpenen.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btRekeningOpenen.setText("Rekening openen");
        btRekeningOpenen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRekeningOpenenActionPerformed(evt);
            }
        });

        btSaldiInzien.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btSaldiInzien.setText("Saldi inzien");
        btSaldiInzien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaldiInzienActionPerformed(evt);
            }
        });

        cbRekening.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        btTransactiesInzien.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btTransactiesInzien.setText("Transacties inzien");
        btTransactiesInzien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTransactiesInzienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbRekening, 0, 266, Short.MAX_VALUE)
                            .addComponent(btSaldiInzien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btRekeningOpenen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btTransactiesInzien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(btRekeningOpenen)
                .addGap(18, 18, 18)
                .addComponent(btSaldiInzien)
                .addGap(18, 18, 18)
                .addComponent(cbRekening, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btTransactiesInzien)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Overboeken", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        lbInlognaam1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lbInlognaam1.setText("Naam begunstigde");

        lbWachtwoord1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lbWachtwoord1.setText("Bedrag (€)");

        tfBegunstigdeNaam.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        btOverboeken.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btOverboeken.setText("Overboeken");
        btOverboeken.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOverboekenActionPerformed(evt);
            }
        });

        lbInlognaam2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lbInlognaam2.setText("IBAN begunstigde");

        lbWachtwoord2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lbWachtwoord2.setText("Omschrijving");

        tfBegunstigdeIBAN.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        taOmschrijving.setColumns(20);
        taOmschrijving.setRows(5);
        jScrollPane1.setViewportView(taOmschrijving);

        tfBedrag.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        tfBedrag.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lbInlognaam1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                        .addComponent(tfBegunstigdeNaam, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lbInlognaam2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tfBegunstigdeIBAN, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbWachtwoord2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbWachtwoord1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfBedrag)
                            .addComponent(btOverboeken, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfBegunstigdeNaam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbInlognaam1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfBegunstigdeIBAN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbInlognaam2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbWachtwoord1)
                    .addComponent(tfBedrag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbWachtwoord2))
                .addGap(18, 18, 18)
                .addComponent(btOverboeken)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btRekeningOpenenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRekeningOpenenActionPerformed
        try {
            String rekNr = gui.balie.rekeningOpenen(gui.sessieId);
            gui.log("Gefeliciteerd! U heeft een nieuwe rekening geopend: " + rekNr);
            toonSaldi();
        } catch (RemoteException ex) {
            gui.log(ex);
        }
    }//GEN-LAST:event_btRekeningOpenenActionPerformed

    private void btOverboekenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOverboekenActionPerformed
        String rekVan = (String) cbRekening.getSelectedItem();
        if (rekVan == null) {
            gui.log("U heeft nog geen rekeningen. Open eerst een rekening.");
            return;
        }

        String rekNaar = tfBegunstigdeIBAN.getText().replaceAll(" ", "").toUpperCase();
        boolean valid = rekNaar.length() >= 13
                && rekNaar.length() <= 15
                && rekNaar.substring(0, 4).matches("[a-zA-Z]+")
                && rekNaar.substring(4).matches("[0-9]+");
        if (!valid) {
            gui.log("Voer een geldige IBAN in.");
            gui.log("Een IBAN bestaat uit een 4-letterige bankcode, "
                    + "gevolgd door een rekeningnummer van 9, 10 of 11 cijfers.");
            return;
        }

        if (tfBedrag.getText().length() == 0) {
            gui.log("Vul een bedrag in.");
            return;
        }
        int bedrag = (int) (100 * Double.valueOf(tfBedrag.getValue().toString()));

        String omschr = taOmschrijving.getText().trim();

        try {
            gui.balie.overboekenNaar(gui.sessieId, rekVan, rekNaar, bedrag, omschr);
            tfBegunstigdeNaam.setText("");
            tfBegunstigdeIBAN.setText("");
            tfBedrag.setText("");
            taOmschrijving.setText("");
            gui.log("Overboeking is geaccepteerd");
        } catch (RemoteException ex) {
            gui.log(ex);
        }
    }//GEN-LAST:event_btOverboekenActionPerformed

    private void btSaldiInzienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaldiInzienActionPerformed
        toonSaldi();
    }//GEN-LAST:event_btSaldiInzienActionPerformed

    private void btTransactiesInzienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTransactiesInzienActionPerformed

        String rekNr = (String) cbRekening.getSelectedItem();
        if (rekNr == null) {
            gui.log("U heeft nog geen rekeningen. Open eerst een rekening.");
            return;
        }

        Collection<Transactie> transacties = null;
        try {
            transacties = gui.balie.transactiesInzien(gui.sessieId, rekNr);
        } catch (RemoteException ex) {
            gui.log(ex);
        }

        if (transacties == null || transacties.isEmpty()) {
            gui.log("Er zijn geen transacties gevonden voor rekening " + rekNr);
            return;
        }

        gui.log("Overzicht van transacties van rekening " + rekNr);
        String layout = "%-22s%-15s%-18s%-9s%10s%-2s%-27s%-5s";
        gui.log(String.format(layout, "Datum", "Transactienr", "Tegenrekening", "Bij/Af", "Bedrag", "", "Omschrijving", "Status"));

        for (Transactie t : transacties) {

            String rek = t.getRekeningVan();
            String bijaf = "Bij";
            String bedrag = String.format("€%.2f", ((float) t.getBedrag()) / 100);
            if (rekNr.equals(rek)) {
                rek = t.getRekeningNaar();
                bijaf = "Af";
                bedrag = "- " + bedrag;
            }
            String omschr = t.getOmschrijving();
            if (omschr.length() > 25) {
                omschr = omschr.substring(0, 22) + "...";
            }
            gui.log(String.format(layout, sdf.format(t.getDatum()), t.getTransactieNr(), rek, bijaf, bedrag, "", omschr, t.getStatus()));
        }


    }//GEN-LAST:event_btTransactiesInzienActionPerformed

    void toonSaldi() {
        try {
            Map<String, Integer> saldi = gui.balie.saldiInzien(gui.sessieId);

            if (saldi.isEmpty()) {
                gui.log("U heeft nog geen rekeningen. Open eerst een rekening.");
                return;
            }

            gui.log(String.format("%-15s%15s", "Rekening", "Saldo"));

            for (String reknr : saldi.keySet()) {
                String saldo = String.format("€%.2f", ((float) saldi.get(reknr)) / 100);
                gui.log(String.format("%-15s%15s", reknr, saldo));
            }

            cbRekening.setModel(new DefaultComboBoxModel(
                    saldi.keySet().toArray(new String[saldi.size()])
            ));

        } catch (RemoteException ex) {
            gui.log(ex);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btOverboeken;
    private javax.swing.JButton btRekeningOpenen;
    private javax.swing.JButton btSaldiInzien;
    private javax.swing.JButton btTransactiesInzien;
    private javax.swing.JComboBox cbRekening;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbInlognaam1;
    private javax.swing.JLabel lbInlognaam2;
    private javax.swing.JLabel lbWachtwoord1;
    private javax.swing.JLabel lbWachtwoord2;
    private javax.swing.JTextArea taOmschrijving;
    private javax.swing.JFormattedTextField tfBedrag;
    private javax.swing.JTextField tfBegunstigdeIBAN;
    private javax.swing.JTextField tfBegunstigdeNaam;
    // End of variables declaration//GEN-END:variables

}
