package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGUIObserver extends Remote {

    /**
     * Deze methode update de view van de gebruiker met nieuwe transactie t
     *
     * @param t transactie waarmee view geupdate wordt
     * @throws java.rmi.RemoteException
     */
    void updateView(Transactie t) throws RemoteException;

}
