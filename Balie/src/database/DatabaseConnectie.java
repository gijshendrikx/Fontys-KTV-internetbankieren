package database;

import balie.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectie implements IDatabaseConnectie {

    static final String jdbcDriver = "com.mysql.jdbc.Driver";
    static final String dbURL = "jdbc:mysql://localhost:3306/balie";
    static final String user = "root";
    static final String password = "";
    
    private Connection con;
    
    public DatabaseConnectie() {
        connectDB();               
    }

    private void connectDB() {
        try{
            Class.forName(jdbcDriver);   
            con = DriverManager.getConnection(dbURL,user,password);
        }catch(SQLException | ClassNotFoundException ex){
            //TODO
        }
    }
    
    @Override
    public void insertUser(User k) {
        try{
            String query = String.format("INSERT INTO users (`login`, `wachtwoord`, `klantnummer`) VALUES ('%s', '%s', '%d')", 
                    k.getLogin(),k.getWachtwoord(), k.getKlantnr());
            Statement stat = con.createStatement();
            stat.executeUpdate(query);
        } catch( SQLException ex){
            //TODO
        }
    }

    @Override
    public User getUser(String login) {
        try{
            String query = "SELECT * FROM users WHERE login = '" + login + "'";
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(query);
            if(!rs.first()){
                return null;
            } else {
                return new User(rs.getString("login"),rs.getString("wachtwoord"), rs.getInt("klantnummer"));
            }
        } catch( SQLException ex){
            return null;
            //TODO
        }
    }

}
