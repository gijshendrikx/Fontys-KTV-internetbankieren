package balie;

import common.IGUIObserver;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Timer;
import java.util.TimerTask;

public class Sessie {

    private final String sessieId;

    private final int klantnr;
    private final Balie balie;
    private Timer timer;
    private IGUIObserver guiobserver;

    public Sessie(int klantnr, Balie balie) {
        SecureRandom random = new SecureRandom();
        this.sessieId = new BigInteger(130, random).toString(32);
        this.klantnr = klantnr;
        this.balie = balie;
        setTimeout();
    }

    String getSessieId() {
        return sessieId;
    }

    int getKlantNummer() {
        // Aanroep van deze methode zorgt tevens voor update van de sessie timeout
        setTimeout();
        return klantnr;
    }

    IGUIObserver getGUI() {
        return guiobserver;
    }

    void setGUI(IGUIObserver gui) {
        guiobserver = gui;
    }

    private void setTimeout() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                balie.removeVerlopenSessie(getSessieId());
            }
        };
        timer.schedule(task, 30 * 60 * 1000);
    }
}
