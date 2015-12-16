package gui;

import common.IBalieNaarGUI;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUI {

    private final GUIWindow window;

    // heeft nodig
    private IBalieNaarGUI balieNaarGUI;

    public GUI(String naam, String bankCode, int port) throws RemoteException {
        window = new GUIWindow(naam, bankCode);
        connectBalie(port, 10000);
    }

    private void connectBalie(int port, int reconnectMillis) {
        new Thread(() -> {
            while (true) {
                if (balieNaarGUI == null) {
                    try {
                        Registry registry = LocateRegistry.getRegistry("127.0.0.1", port);
                        balieNaarGUI = (IBalieNaarGUI) registry.lookup("BalieNaarGUI");
                        window.setOnline(balieNaarGUI);
                    } catch (RemoteException | NotBoundException ex) {
                        balieNaarGUI = null;
                    }
                }
                try {
                    Thread.sleep(reconnectMillis);
                } catch (InterruptedException ex) {
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        String naam = args.length > 0 ? args[0] : "ING Bank";
        String code = args.length > 1 ? args[1] : "INGB";
        int port = args.length > 2 ? Integer.valueOf(args[2]) : 1098;
        try {
            new GUI(naam, code, port);
        } catch (RemoteException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
