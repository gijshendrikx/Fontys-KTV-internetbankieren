package database;

import common.Transactie;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection; 

public class DatabaseConnectie implements IDatabaseConnectie {

    static final String jdbcDriver = "com.mysql.jdbc.Driver";
    static final String dbURL = "jdbc:mysql://localhost:3306/centralebank";
    static final String user = "root";
    static final String password = "";

    private Connection con;

    public DatabaseConnectie() {
        connectDB();
    }

    private void connectDB() {
        try {
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(dbURL, user, password);
        } catch (SQLException | ClassNotFoundException ex) {
            // todo
        }
    }

    @Override
    public boolean bankHeeftAutorisatie(String bankCode) {
        try {
            String query = "SELECT * FROM banken WHERE bankCode = '" + bankCode + "'";
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(query);
            if (!rs.first()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public Collection<Transactie> getTransacties() {
        Collection c = new ArrayList<>();
        try {
            String query = "SELECT * FROM transactie";
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                Transactie t = new Transactie(
                        rs.getString("transactienummer"),
                        rs.getDate("datum"),
                        rs.getString("rekeningVan"),
                        rs.getString("rekeningNaar"),
                        rs.getString("beschrijving"),
                        rs.getInt("bedrag"));
                int status = rs.getByte("status");
                switch (status) {
                    case 1:
                        t.setStatus(Transactie.Status.PENDING);
                        break;
                    case 2:
                        t.setStatus(Transactie.Status.FORWARDED);
                        break;
                    case 3:
                        t.setStatus(Transactie.Status.CANCELLED);
                        break;
                    case 4:
                        t.setStatus(Transactie.Status.ACCEPTED);
                        break;
                    case 5:
                        t.setStatus(Transactie.Status.DONE);
                        break;
                }
                c.add(t);
            }
            return c;
        } catch (SQLException ex) {
            return c;
        }
    }

    @Override
    public void insertTransactie(Transactie t) {
        try {
            String status = t.getStatus().toString();
            int s = 0;
            switch (status) {
                case "PENDING":
                    s = 1;
                    break;
                case "FORWARDED":
                    s = 2;
                    break;
                case "CANCELLED":
                    s = 3;
                    break;
                case "ACCEPTED":
                    s = 4;
                    break;
                case "DONE":
                    s = 5;
                    break;
            }

            String query = String.format("INSERT INTO transactie ("
                    + "`transactienummer`, "
                    + "`datum`, "
                    + "`rekeningVan`,"
                    + "`rekeningNaar`,"
                    + "`beschrijving`,"
                    + "`bedrag`,"
                    + "`status`"
                    + ") VALUES ('%s', '%s', '%s', '%s', '%s', '%d', '%d')",
                    t.getTransactieNr(), t.getDatum(), t.getRekeningVan(), t.getRekeningNaar(), t.getOmschrijving(), t.getBedrag(), s);
            Statement stat = con.createStatement();
            stat.executeUpdate(query);
        } catch (SQLException ex) {
            //todo
        }
    }

    @Override
    public void deleteTransactie(Transactie t) {
        try {
            String query = "DELETE FROM transactie WHERE transactienummer = '" + t.getTransactieNr() + "'";
            Statement stat = con.createStatement();
            stat.executeUpdate(query);
        } catch (SQLException ex) {
            //todo
        }
    }

}
