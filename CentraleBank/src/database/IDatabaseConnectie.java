package database;

import common.Transactie;
import java.util.Collection;

public interface IDatabaseConnectie {

    boolean bankHeeftAutorisatie(String bankCode);

    Collection<Transactie> getTransacties();

    void insertTransactie(Transactie t);

    void deleteTransactie(Transactie t);
}
