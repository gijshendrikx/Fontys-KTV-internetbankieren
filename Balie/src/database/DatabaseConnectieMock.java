package database;

import balie.User;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DatabaseConnectieMock implements IDatabaseConnectie {

    private final Map<String, User> klanten;

    public DatabaseConnectieMock() {
        klanten = Collections.synchronizedMap(new HashMap<>());
        init();
    }

    private void init() {
        User k1 = new User("bram", "bram", 1);
        User k2 = new User("gijs", "gijs", 1);
        insertUser(k1);
        insertUser(k2);
    }

    @Override
    public void insertUser(User k) {
        klanten.put(k.getLogin(), k);
    }

    @Override
    public User getUser(String login) {
        return klanten.get(login);
    }

}
