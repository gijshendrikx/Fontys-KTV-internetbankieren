package database;

import common.Transactie;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DatabaseConnectieMock implements IDatabaseConnectie {

    private final Set<String> geautoriseerdeBanken;
    private final Set<Transactie> transacties;

    public DatabaseConnectieMock() {
        geautoriseerdeBanken = new HashSet<>();
        geautoriseerdeBanken.add("INGB");
        geautoriseerdeBanken.add("RABO");
        geautoriseerdeBanken.add("ABNA");
        transacties = new HashSet<>();
    }

    @Override
    public boolean bankHeeftAutorisatie(String bankCode) {
        return geautoriseerdeBanken.contains(bankCode);
    }

    @Override
    public void insertTransactie(Transactie t) {
        transacties.add(t);
    }

    @Override
    public void deleteTransactie(Transactie t) {
        transacties.remove(t);
    }

    @Override
    public Collection<Transactie> getTransacties() {
        return transacties;
    }

}
