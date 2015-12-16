package balie;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.DefaultCaret;

public class LogWindow extends javax.swing.JFrame {

    SimpleDateFormat sdf;

    public LogWindow(String title, String code) {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(LogWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents(); 
        setTitle(title + " Balie");
        DefaultCaret caret = (DefaultCaret) ta.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        setIconImage(createIcon(code, 20, 0));
        setLocationRelativeTo(null);
        java.awt.EventQueue.invokeLater(() -> {
            setVisible(true);
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sp = new javax.swing.JScrollPane();
        ta = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ta.setEditable(false);
        ta.setColumns(20);
        ta.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        ta.setRows(5);
        sp.setViewportView(ta);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sp, javax.swing.GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sp, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane sp;
    private javax.swing.JTextArea ta;
    // End of variables declaration//GEN-END:variables

    public void log(String message) {
        message = sdf.format(new Date()) + " " + message + "\n";
        ta.append(message); 
    }

    public void exitOnClose(Balie balie) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                balie.exitOnClose();
            }
        });
    }

    private Image createIcon(String s, int x, int y) {
        java.awt.image.BufferedImage bi = new java.awt.image.BufferedImage(48, 48, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(new Font("Arial", Font.TRUETYPE_FONT, 9 * x / 10));
        g.setColor(Color.BLUE);
        String b = "BALIE";
        int width = g.getFontMetrics(g.getFont()).stringWidth(s);
        g.drawString(b, (48 - width) / 2, 20);
        g.setFont(new Font("Arial", Font.TRUETYPE_FONT, x-2));
        g.translate(0, -y);
        width = g.getFontMetrics(g.getFont()).stringWidth(s);
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
}
