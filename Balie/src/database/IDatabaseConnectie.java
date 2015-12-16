package database;

import balie.User;

public interface IDatabaseConnectie {

    void insertUser(User k);

    User getUser(String login);

}
