package bank;

public class Rekening {

    private int saldo;
    private int kredietlimiet;
    private final String rekeningNr;
    private final int klantNr;

    /**
     *
     * Constructor van Rekening
     *
     * @param kredietlimiet maximaal toegestane negatieve saldo van de rekening
     * @param rekeningNr eerste 4 karakters zijn gelijk aan de bankcode, gevolgd
     * door 9 of meer cijfers gelijk zijn aan de bank code
     * @param klantNr nummer van de rekeninghouder
     */
    public Rekening(int kredietlimiet, String rekeningNr, int klantNr) {
        this.saldo = 0;
        this.kredietlimiet = kredietlimiet;
        this.rekeningNr = rekeningNr;
        this.klantNr = klantNr;
    }

    public int getKlantNr() {
        return klantNr;
    }

    public int getSaldo() {
        return saldo;
    }

    public String getRekeningNr() {
        return rekeningNr;
    }

    public int getKredietlimiet() {
        return kredietlimiet;
    }

    public void setKredietlimiet(int limiet) {
        kredietlimiet = limiet;
    }

    /**
     * geeft aan of een bedrag van de rekening afgeschreven kan worden zonder 
     * het kredietlimiet te overschrijven.
     * 
     * @param bedrag bedrag wat voor afschrijving gecontroleerd wordt
     * @return 
     */
    public boolean kanAfboeken(int bedrag) {
        return saldo + kredietlimiet >= bedrag;
    }

    public void boekAf(int bedrag) {
        saldo -= bedrag;
    }

    public void boekBij(int bedrag) {
        saldo += bedrag;
    }

    @Override
    public String toString() {
        return "Rekening{" + "saldo=" + saldo + ", kredietlimiet=" + kredietlimiet + ", rekeningNr=" + rekeningNr + ", klantNr=" + klantNr + '}';
    }
}
