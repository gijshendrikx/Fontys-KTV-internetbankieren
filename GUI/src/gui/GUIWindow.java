package gui;

import common.IBalieNaarGUI;
import common.IGUIObserver;
import common.Transactie;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.DefaultCaret;

public class GUIWindow extends javax.swing.JFrame {

    private final Map<String, JPanel> panels;
    private final SimpleDateFormat sdf;
    private final Color background;

    // biedt aan
    private IGUIObserver guiObserver;

    // heeft nodig
    IBalieNaarGUI balie;
    String sessieId;

    public GUIWindow(String naam, String bankCode) {

        panels = new HashMap<>();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        background = new Color(240, 240, 240);
        getContentPane().setBackground(background);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GUIWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        initComponents();
        initPanels();
        DefaultCaret caret = (DefaultCaret) taMessages.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        setTitle(naam + " - Internet bankieren");
        setIconImage(createIcon(bankCode, 20, 0));
        setLocationRelativeTo(null);

        java.awt.EventQueue.invokeLater(() -> {
            setVisible(true);
        });

    }

    private void initPanels() {
        panels.put("Offline", new PanelOffline(this));
        panels.put("Inloggen", new PanelInloggen(this));
        panels.put("Registreren", new PanelRegistreren(this));
        panels.put("Bankieren", new PanelBankieren(this));
        setPanel("Offline");
    }

    public void setPanel(String name) {
        panel1.removeAll();
        JPanel panel = panels.get(name);
        panel.setBackground(background);
        panel1.add(panel);
        if (name.equals("Bankieren")) {
            ((PanelBankieren) panel).toonSaldi();
        }
        lbTitel.setText(name);
        validate();
        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        splitpane = new javax.swing.JSplitPane();
        panel1 = new javax.swing.JPanel();
        panel2 = new javax.swing.JPanel();
        spMessages = new javax.swing.JScrollPane();
        taMessages = new javax.swing.JTextArea();
        pnTitel = new javax.swing.JPanel();
        lbTitel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(250, 250, 250));
        setMinimumSize(new java.awt.Dimension(1000, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        splitpane.setDividerLocation(350);
        splitpane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        panel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panel1.setLayout(new java.awt.GridBagLayout());
        splitpane.setLeftComponent(panel1);

        panel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        taMessages.setEditable(false);
        taMessages.setColumns(20);
        taMessages.setRows(5);
        spMessages.setViewportView(taMessages);

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spMessages, javax.swing.GroupLayout.DEFAULT_SIZE, 925, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spMessages, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                .addContainerGap())
        );

        splitpane.setRightComponent(panel2);

        pnTitel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lbTitel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbTitel.setText("UC");

        javax.swing.GroupLayout pnTitelLayout = new javax.swing.GroupLayout(pnTitel);
        pnTitel.setLayout(pnTitelLayout);
        pnTitelLayout.setHorizontalGroup(
            pnTitelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTitelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTitel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnTitelLayout.setVerticalGroup(
            pnTitelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTitelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTitel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(splitpane)
                    .addComponent(pnTitel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnTitel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(splitpane, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (balie != null) {
            try {
                balie.uitloggen(sessieId);
            } catch (RemoteException ex) {
                log(ex);
            }
        }
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbTitel;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel pnTitel;
    private javax.swing.JScrollPane spMessages;
    private javax.swing.JSplitPane splitpane;
    private javax.swing.JTextArea taMessages;
    // End of variables declaration//GEN-END:variables

    public void log(String message) {
        message += "\n";
        taMessages.append(message);
    }

    private Image createIcon(String s, int x, int y) {
        java.awt.image.BufferedImage bi = new java.awt.image.BufferedImage(48, 48, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(new Font("Arial", Font.TRUETYPE_FONT, 9 * x / 10));
        g.setColor(Color.BLUE);
        String b = "APP";
        int width = g.getFontMetrics().stringWidth(s);
        g.drawString(b, 4 + (48 - width) / 2, 20);
        g.setFont(new Font("Arial", Font.TRUETYPE_FONT, x - 2));
        g.translate(0, -y);
        width = g.getFontMetrics().stringWidth(s);
        g.setColor(Color.RED);
        int p = 1;
        g.drawString(s, ((48 - width) / 2) - p, 42 - p);
        g.drawString(s, ((48 - width) / 2) + p, 42 - p);
        g.drawString(s, ((48 - width) / 2) - p, 42 + p);
        g.drawString(s, ((48 - width) / 2) + p, 42 + p);
        g.setColor(Color.BLACK);
        g.drawString(s, ((48 - width) / 2), 42);
        return bi;
    }

    public void setOnline(IBalieNaarGUI balieNaarGUI) {
        balie = balieNaarGUI;
        setPanel("Inloggen");
    }

    void setSessie(String sessieId) {
        this.sessieId = sessieId;
        try {
            guiObserver = new GUIObserver();
            balie.registreerGUI(sessieId, guiObserver);
        } catch (RemoteException ex) {
            log("Kan GUI niet registreren bij balie.");
            log(ex);
        }

    }

    String getMessage(RemoteException ex) {
        Throwable t = ex.getCause();
        while (t.getCause() != null) {
            t = t.getCause();
        }
        return t.getMessage();
    }

    void log(RemoteException ex) {
        log(getMessage(ex));
    }

    private class GUIObserver extends UnicastRemoteObject implements IGUIObserver {

        public GUIObserver() throws RemoteException {
        }

        @Override
        public void updateView(Transactie t) throws RemoteException {
            log("Er heeft een mutatie op (een van uw) rekening(en) plaatgevonden. De transactie gegevens zijn:"
                    + "\nTransactienummer: " + t.getTransactieNr()
                    + "\nDatum:            " + sdf.format(t.getDatum())
                    + "\nVan rekening:     " + t.getRekeningVan()
                    + "\nTegenrekening:    " + t.getRekeningNaar()
                    + "\nOmschrijving:     " + t.getOmschrijving()
                    + "\nBedrag:           " + String.format("€%.2f", ((float) t.getBedrag()) / 100)
                    + "\nStatus:           " + t.getStatus());
        }

    }

}
