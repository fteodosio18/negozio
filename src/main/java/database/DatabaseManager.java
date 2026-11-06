package database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    private final Properties prop;
//    private String baseUrl = "jdbc:mysql://localhost:3306/";
//    private String username = "root";
//    private String password = "root";
    public DatabaseManager() {
        prop = new Properties();
        loadProperties();
    }

    public Connection getConnection(String nomeDatabase){
        //String url = baseUrl + nomeDatabase;
        try{
            Connection conn = DriverManager.getConnection(getUrl() + nomeDatabase, getUsername(), getPassword());
            System.out.println("Connessione al db " + nomeDatabase + " riuscita");
            return conn;
        } catch (SQLException e) {
            System.out.println("Qualcosa è andato storto");
            e.printStackTrace();
            return null;
        }
    }

    private void loadProperties() {

        try (InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream("app.properties")) {

            if (input == null) {
                throw new RuntimeException(
                        "File app.properties non trovato!"
                );
            }

            prop.load(input);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Errore nel caricamento delle configurazioni", e
            );
        }
    }
    public String getUrl(){
        return prop.getProperty("db.baseUrl");
    }

    public String getUsername(){
        return prop.getProperty("db.user");
    }

    public String getPassword(){
        return prop.getProperty("db.pass");
    }
}
