package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBalieObserver extends Remote {

    /**
     * Deze methode vraagt een update van de gui van de gebruiker aan
     *
     * @param klantnummer het klantnummer zoals deze bij balie en bank bekend is
     * @param transactie de transactie waarop een mutatie heeft plaatsgevonden
     * @throws java.rmi.RemoteException
     */
    void updateView(int klantnummer, Transactie transactie) throws RemoteException;

}