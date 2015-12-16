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

//
//    /**
//     * Controleert of bedrag afgeschreven kan worden van saldo. Indien waar,
//     * wordt transactiebedrag afgeschreven van saldo en transactie toegevoegd
//     * aan lijst van transacties.
//     * <br><strong>Post: </strong> indien saldo toereikend: bedrag afgeschreven
//     * van saldo.
//     * <br><strong>Post: </strong> indien saldo niet toereikend: status van
//     * transactie bijgewerkt.
//     *
//     * @param t af te schrijven transactie
//     * @return of transactie wel/niet afgeschreven is
//     */
//    public boolean doeTransactie(Transactie t) {
//        if (t.getBedrag() > this.saldo + this.kredietlimiet) {
//            t.setStatus(2);
//            transacties.put(t.getTransactieNr(), t);
//            return false;
//        } else {
//            this.saldo -= t.getBedrag();
//            transacties.put(t.getTransactieNr(), t);
//            return true;
//        }
//    }
//
//    /**
//     * Terugontvangst van een verzonden transactie. Controleert of transactie
//     * goedgekeurd of afgekeurd is. Boekt eventueel bedrag bij saldo als
//     * transactie afgekeurd is. Werkt de transactie bij in de lijst met
//     * transacties.
//     * <br><strong>Pre: </strong> transactie moet bekend zijn in map met
//     * transacties.
//     * <br><strong>Post: </strong> transactie is bijgewerkt in map met
//     * transacties (status).
//     * <br><strong>Post: </strong> Indien transactie afgekeurd is
//     * transactiebedrag weer opgeteld bij saldo.
//     *
//     * @param t terugontvangen transactie
//     */
//    public void rollBack(Transactie t) {
//        if (t.getStatus() == 2) {
//            this.saldo += t.getBedrag();
//        }
//        transacties.put(t.getTransactieNr(), t);
//    } 
//    public Collection<Transactie> getTransacties() {
//        return transacties.values();
//    }
}
