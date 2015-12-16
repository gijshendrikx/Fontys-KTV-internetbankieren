package common;

import java.io.Serializable;
import java.util.Date;

public class Transactie implements Serializable {

    private final String transactieNr;
    private final Date datum;
    private final String rekeningVan;
    private final String rekeningNaar;
    private final String omschrijving;
    private final int bedrag;
    private Status status;

    public enum Status {

        PENDING(1),
        FORWARDED(2),
        CANCELLED(3),
        ACCEPTED(4),
        DONE(5);

        private final int status;

        Status(int status) {
            this.status = status;
        }

        int value() {
            return status;
        }

    }

    public Transactie(String transactieNr, Date datum, String rekeningVan,
            String rekeningNaar, String omschrijving, int bedrag) {
        this.transactieNr = transactieNr;
        this.datum = datum;
        this.rekeningVan = rekeningVan;
        this.rekeningNaar = rekeningNaar;
        this.omschrijving = omschrijving;
        this.bedrag = bedrag;
    }

    public String getTransactieNr() {
        return transactieNr;
    }

    public Date getDatum() {
        return datum;
    }

    public String getRekeningVan() {
        return rekeningVan;
    }

    public String getRekeningNaar() {
        return rekeningNaar;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public int getBedrag() {
        return bedrag;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Transactie{" + "transactieNr=" + transactieNr + ", datum=" + datum + ", rekeningVan=" + rekeningVan + ", rekeningNaar=" + rekeningNaar + ", omschrijving=" + omschrijving + ", bedrag=" + bedrag + ", status=" + status + '}';
    }

}
