package database;

import bank.Klant;
import bank.Rekening;
import common.Transactie;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseConnectieMock implements IDatabaseConnectie {

    private final String bankCode;
    private final Map<Integer, Klant> klanten;
    private final Map<String, Rekening> rekeningen;
    private final Map<String, Transactie> transacties;
    private int maxRekNr;
    private int maxKlantNr;
    private int maxTransactieNr;

    public DatabaseConnectieMock(String bankCode) {
        this.bankCode = bankCode;
        klanten = Collections.synchronizedMap(new HashMap<>());
        rekeningen = Collections.synchronizedMap(new HashMap<>());
        transacties = Collections.synchronizedMap(new HashMap<>());
        init();
    }

    private void init() {
        Klant k1 = insertKlant("bram", "tilburg");
        insertRekening(k1);
        Klant k2 = insertKlant("gijs", "venlo");
        insertRekening(k2);
        insertRekening(k2);
    }

    @Override
    public Klant getKlant(String naam, String woonplaats) {
        for (int klantNr : klanten.keySet()) {
            Klant k = klanten.get(klantNr);
            if (k.getNaam().equals(naam) && k.getWoonplaats().equals(woonplaats)) {
                return k;
            }
        }
        return null;
    }

    @Override
    public Klant getKlant(int klantnummer) {
        return klanten.get(klantnummer);
    }

    @Override
    public Klant insertKlant(String naam, String woonplaats) {
        maxKlantNr++;
        Klant k = new Klant(maxKlantNr, naam, woonplaats);
        klanten.put(maxKlantNr, k);
        return k;
    }

    @Override
    public Rekening getRekening(String reknr) {
        return rekeningen.get(reknr);
    }

    @Override
    public Rekening insertRekening(Klant k) {
        maxRekNr++;
        String reknr = String.format("%s%09d", bankCode, maxRekNr);
        Rekening r = new Rekening(10000, reknr, k.getKlantNummer());
        rekeningen.put(reknr, r);
        return r;
    }

    @Override
    public void updateRekening(Rekening r) {
        rekeningen.put(r.getRekeningNr(), r);//overbodig voor mock
    }

    @Override
    public Transactie getTransactie(String rekeningVan, String rekeningNaar, int bedrag, String omschr, Transactie.Status status) {
        maxTransactieNr++;
        String transactieNr = bankCode + maxTransactieNr;
        Transactie t = new Transactie(transactieNr, new Date(), rekeningVan, rekeningNaar, omschr, bedrag);
        t.setStatus(status);
        return t;
    }

    @Override
    public void insertTransactie(Transactie t) {
        transacties.put(t.getTransactieNr(), t);
    }

    @Override
    public Transactie getTransactie(String transactieNr) {
        return transacties.get(transactieNr);
    }

    @Override
    public Collection<Transactie> getTransacties(String rekNr) {
        List<Transactie> c = new ArrayList<>();
        for (Transactie t : transacties.values()) {
            if (t.getRekeningVan().equals(rekNr) || t.getRekeningNaar().equals(rekNr)) {
                c.add(t);
            }
        }
        Collections.sort(c, (Transactie t, Transactie o) -> {
            return t.getDatum().compareTo(o.getDatum());
        });
        return c;
    }

    @Override
    public Collection<Rekening> getRekeningen(int klantnummer) {
        Collection<Rekening> c = new ArrayList<>();
        for (Rekening r : rekeningen.values()) {
            if (r.getKlantNr() == klantnummer) {
                c.add(r);
            }
        }
        return c;
    }

    @Override
    public Collection<Transactie> getOpenTransacties() {
        Collection<Transactie> c = new ArrayList<>();
        for (Transactie t : transacties.values()) {
            if (t.getStatus() == Transactie.Status.DONE) {
                // reeds afgewerkt
                continue;
            }
            if (t.getStatus() == Transactie.Status.FORWARDED && getRekening(t.getRekeningVan()) != null) {
                // reeds verstuurd, wacht eerst op antwoord van centrale bank
                continue;
            }
            c.add(t);
        }
        return c;
    }

    @Override
    public void updateTransactie(Transactie t) {
        transacties.put(t.getTransactieNr(), t); // overbodig voor mock
    }

}
