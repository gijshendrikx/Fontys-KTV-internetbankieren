package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICentraleNaarBank extends Remote {

    /**
     * Deze methode verzoekt om verwerking van transactie t van bank A naar bank
     * B
     *
     * @param t de transactie
     * @throws java.rmi.RemoteException
     */
    void verwerk(Transactie t) throws RemoteException;

    /**
     * Deze methode geeft een IBankNaarCentrale terug aan de centrale bank zodra
     * de centrale bank is gevonden. Dit maakt tweezijdige communicatie tussen
     * bank en centrale bank mogelijk.
     *
     * @param bankCode de code van de bank
     * @param bank IBankNaarCentrale interface
     * @throws java.rmi.RemoteException
     */
    public void registreerBank(String bankCode, IBankNaarCentrale bank) throws RemoteException;

    /**
     * Deze methode maak registratie van een bank bij de centrale bank ongedaan.
     *
     * @param bankCode de code van de bank
     * @param bank IBankNaarCentrale interface
     * @throws java.rmi.RemoteException
     */
    public void deregistreerBank(String bankCode, IBankNaarCentrale bank) throws RemoteException;

}
