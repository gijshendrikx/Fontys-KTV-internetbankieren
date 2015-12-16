package balie;

public class User {

    private final String login;
    private final String wachtwoord;
    private final int klantnr;

    public User(String login, String wachtwoord, int klantnr) {
        this.login = login;
        this.wachtwoord = wachtwoord;
        this.klantnr = klantnr;
    }

    public String getLogin() {
        return login;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public int getKlantnr() {
        return klantnr;
    }
}
