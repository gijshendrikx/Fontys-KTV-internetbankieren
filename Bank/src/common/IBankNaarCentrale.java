package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBankNaarCentrale extends Remote {

    /**
     * Deze methode verzoekt om verwerking van transactie t van bank A naar bank
     * B
     *
     * @param t de transactie
     * @throws java.rmi.RemoteException
     */
    void verwerk(Transactie t) throws RemoteException;

}
