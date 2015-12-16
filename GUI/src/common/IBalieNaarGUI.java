package common;

import java.rmi.Remote;
import java.rmi.RemoteException; 
import java.util.Collection;
import java.util.Map;

public interface IBalieNaarGUI extends Remote {

    /**
     *
     * @param naam achternaam van de gebruiker
     * @param woonplaats Nederlandse woonplaats van de gebruiker
     * @param wachtwoord een door de gebruiker gekozen wachtwoord, die minimaal
     * 8 karakters bevast, minimaal 1 kleine letter bevat, minimaal 1
     * hoofdletter bevat, en minimaal 1 cijfer bevat
     * @return String gebruikersnaam, waarmee gebruiker bij de balie bekend is
     * @throws RemoteException foutmelding indien woonplaats niet in Nederland
     * is of wachtwoord ongeldig is
     */
    String registreren(String naam, String woonplaats, String wachtwoord) throws RemoteException;

    /**
     *
     * @param username bij regeistratie verkregen gebruikersnaam
     * @param wachtwoord bij registratie gekozen wachtwoord
     * @return sessieid van de gebruiker
     * @throws RemoteException indien geen gebruiker gevonden kan worden met
     * opgegeven credentials
     */
    String inloggen(String username, String wachtwoord) throws RemoteException;

    /**
     *
     * @param sessieid bij inloggen verkregen sessieid
     * @return rekeningnummer van de geopende rekening
     * @throws RemoteException indien sessieid ongeldig is of bank kan niet
     * gevonden worden
     */
    String rekeningOpenen(String sessieid) throws RemoteException;

    /**
     *
     * @param sessieid bij inloggen verkregen sessieid
     * @return een map van rekeningen en het daarbij behorende saldo van de
     * gebruiker
     * @throws RemoteException indien geberuiker geen rekeningen heeft, sessie
     * ongeldig is of bank niet gevonden kan worden
     */
    Map<String, Integer> saldiInzien(String sessieid) throws RemoteException;

    /**
     *
     * @param sessieid bij inloggen verkregen sessieid
     * @param rekeningnummer het rekeningnummer waarvan transacties getoond
     * worden
     * @return lijst met bij rekening behorende Transacties
     * @throws RemoteException indien sessie ongeldig is, bank niet gevonden kan
     * worden of rekening niet gevonden kan worden
     */
    Collection<Transactie> transactiesInzien(String sessieid, String rekeningnummer) throws RemoteException;

    /**
     *
     * @param sessieid bij inloggen verkregen sessieid
     * @param rekeningVan een rekeningnummer van de gebruiker
     * @param rekeningNaar de door de gebruiker opgegeven tegenrekening
     * @param bedrag een bedrag in eurocenten, groter dan 0 en kleiner dan
     * 10000000 (honderd duizend euro)
     * @param omschrijving een door de gebruiker opgegeven omschrijving
     * @return true bij geldige invoer, anders false
     * @throws RemoteException indien sessie ongeldig is, bedrag niet geldig is,
     * bank niet gevonden kan worden of rekeningVan niet gevonden kan worden
     */
    boolean overboekenNaar(String sessieid, String rekeningVan, String rekeningNaar, int bedrag, String omschrijving) throws RemoteException;

    /**
     *
     * @param sessieid bij inloggen verkregen sessieid
     * @return true als uitloggen gelukt id, anders false
     * @throws java.rmi.RemoteException
     */
    boolean uitloggen(String sessieid) throws RemoteException;

    /**
     * Deze methode geeft een IGUIObserver terug aan de balie zodra de balie is
     * gevonden door de client. Dit maakt tweezijdige communicatie tussen balie
     * en client mogelijk.
     *
     * @param sessieid bij inloggen verkregen sessieid
     * @param gui IGUIObserver interface
     * @throws java.rmi.RemoteException
     */
    public void registreerGUI(String sessieid, IGUIObserver gui) throws RemoteException;
}
