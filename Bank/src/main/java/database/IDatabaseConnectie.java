package database;

import bank.Klant;
import bank.Rekening;
import common.Transactie;
import java.util.Collection;

public interface IDatabaseConnectie {

    ///
    /// KLANT
    Klant getKlant(String naam, String woonplaats);

    Klant getKlant(int klantnummer);

    Klant insertKlant(String naam, String woonplaats);

    ///
    /// REKENING
    Rekening getRekening(String reknr);

    Collection<Rekening> getRekeningen(int klantnummer);

    Rekening insertRekening(Klant k);

    void updateRekening(Rekening r);

    ///
    /// TRANSACTIE
    Transactie getTransactie(String rekeningVan, String rekeningNaar, int bedrag, String omschr, Transactie.Status status);

    Transactie getTransactie(String transactieNr);

    Collection<Transactie> getTransacties(String rekNr);

    Collection<Transactie> getOpenTransacties();

    void insertTransactie(Transactie t);

    void updateTransactie(Transactie t);
}
