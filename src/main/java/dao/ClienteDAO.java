package dao;

import database.DatabaseManager;
import model.Cliente;

import java.sql.*;
import java.util.ArrayList;

public class ClienteDAO implements BaseDAO<Cliente, Integer>{
    DatabaseManager db = new DatabaseManager();
    @Override
    public Cliente findById(Integer id) {
        Connection conn = db.getConnection("negozio");

        String query = "SELECT * FROM clienti WHERE id = ?";
        try(PreparedStatement ps = conn.prepareStatement(query)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Cliente c = null;

            while (rs.next()) {
                System.out.println(rs.getString("id") + ". " +
                        rs.getString("nome") + " " +
                        rs.getString("cognome"));
                c = new Cliente(id, rs.getString("nome"), rs.getString("cognome"), rs.getString("email"),
                rs.getString("citta_residenza"),rs.getDouble("saldo"));
            }
            return c;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Cliente findById(Integer id, Connection conn) {
        String query = "SELECT * FROM clienti WHERE id = ?";
        try(PreparedStatement ps = conn.prepareStatement(query)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Cliente c = null;

            while (rs.next()) {
                System.out.println(rs.getString("id") + ". " +
                        rs.getString("nome") + " " +
                        rs.getString("cognome"));
                c = new Cliente(id, rs.getString("nome"), rs.getString("cognome"), rs.getString("email"),
                        rs.getString("citta_residenza"),rs.getDouble("saldo"));
            }
            return c;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Cliente> findAll() {
        String query = "SELECT * FROM clienti";
        ArrayList<Cliente> clienti = new ArrayList<>();
        try (Connection conn = db.getConnection("negozio");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)){

            while (rs.next()) {
                System.out.println(rs.getString("nome") + " " +
                        rs.getString("cognome"));
                clienti.add(new Cliente(rs.getString("nome"), rs.getString("cognome"), rs.getString("email"),
                        rs.getString("citta_residenza"),rs.getDouble("saldo")));
            }
            return clienti;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void insert(Cliente cliente, Connection conn) {
        String query = "insert into clienti (nome, cognome, email, citta_residenza, saldo) values (?,?,?,?,?)";
        try(PreparedStatement ps = conn.prepareStatement(query)){

            if(cliente.getSaldo() < 0)
                throw new IllegalArgumentException("Saldo non può essere negativo");

            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCognome());
            ps.setString(3, cliente.getEmail());
            ps.setString(4, cliente.getCitta_residenza());
            ps.setDouble(5, cliente.getSaldo());

            int result = ps.executeUpdate();
            if(result > 0){
                System.out.println("Cliente inserito correttamente.");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void aggiornaSaldoCliente(int idCliente, double nuovoSaldo, Connection conn) throws SQLException{
        String query = "update clienti set saldo = ? where id = ?";
        try(PreparedStatement ps = conn.prepareStatement(query)){
            ps.setDouble(1, nuovoSaldo);
            ps.setInt(2, idCliente);

            int result = ps.executeUpdate();

            if(result > 0){
                System.out.println("Saldo aggiornato correttamente.");
            }else{
                throw new SQLException();
            }
        }catch(SQLException e){
            throw new SQLException("Errore durante l'aggiornamento ",e);
        }
    }

    public void aggiungiSaldo(int idCliente,  double saldo, Connection conn) throws SQLException{
        String query = "update clienti set saldo = saldo + ? where id = ?";
        try(PreparedStatement ps = conn.prepareStatement(query)){
            ps.setDouble(1, saldo);
            ps.setInt(2, idCliente);

            int result = ps.executeUpdate();

            if(result > 0){
                System.out.println("Saldo aggiornato correttamente.");
            }else{
                throw new SQLException();
            }
        }catch(SQLException e){
            throw new SQLException("Errore durante l'aggiornamento ",e);
        }
    }
}
