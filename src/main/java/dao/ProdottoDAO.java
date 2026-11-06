package dao;

import database.DatabaseManager;
import model.Cliente;
import model.Prodotto;

import java.sql.*;
import java.util.ArrayList;

public class ProdottoDAO implements BaseDAO<Prodotto,  Integer> {
    DatabaseManager db = new DatabaseManager();

    @Override
    public Prodotto findById(Integer id) {
        Connection conn = db.getConnection("negozio");

        String query = "SELECT * FROM prodotti WHERE id = ?";
        try(PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            Prodotto p = null;

            while (rs.next()) {
                System.out.println(rs.getString("id") + ". " +
                        rs.getString("nome") + " " +
                        rs.getString("categoria") + " " +
                        rs.getDouble("prezzo") + " " +
                        rs.getInt("quantita"));
                p = new Prodotto(id, rs.getString("nome"), rs.getString("categoria"),
                        rs.getDouble("prezzo"), rs.getInt("quantita"));
            }
            return p;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Prodotto findById(Integer id, Connection conn) {
        String query = "SELECT * FROM prodotti WHERE id = ?";
        try(PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            Prodotto p = null;

            while (rs.next()) {
                System.out.println(rs.getString("id") + ". " +
                        rs.getString("nome") + " " +
                        rs.getString("categoria") + " " +
                        rs.getDouble("prezzo") + " " +
                        rs.getInt("quantita"));
                p = new Prodotto(id, rs.getString("nome"), rs.getString("categoria"),
                        rs.getDouble("prezzo"), rs.getInt("quantita"));
            }
            return p;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Prodotto> findAll() {
        String query = "SELECT * FROM prodotti";
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        try (Connection conn = db.getConnection("negozio");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)){

            while (rs.next()) {
                System.out.println(rs.getInt("id") + ". " +
                        rs.getString("nome") + " " +
                        rs.getString("categoria") + " " +
                        rs.getDouble("prezzo") + " " +
                        rs.getInt("quantita"));
                prodotti.add(new Prodotto(rs.getInt("id"), rs.getString("nome"),
                        rs.getString("categoria"), rs.getDouble("prezzo"), rs.getInt("quantita")));
            }
            return prodotti;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void insert(Prodotto prodotto, Connection conn) {
        String query = "insert into prodotti (nome, categoria, prezzo, quantita) values (?,?,?,?)";
        try(PreparedStatement ps = conn.prepareStatement(query)){

            if(prodotto.getQuantita() < 0){
                throw new IllegalArgumentException("Quantita non può essere negativo");
            }
            if(prodotto.getPrezzo() < 0){
                throw new IllegalArgumentException("Il prezzo non può essere negativo");
            }

            ps.setString(1, prodotto.getNome());
            ps.setString(2, prodotto.getCategoria());
            ps.setDouble(3, prodotto.getPrezzo());
            ps.setInt(4, prodotto.getQuantita());

            int result = ps.executeUpdate();
            if(result > 0){
                System.out.println("Cliente inserito correttamente.");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void aggiornaQuantita(int idProdotto, int quantitaAggiornata, Connection conn) throws SQLException{
        String query = "update prodotti set quantita = ?  where id = ?";
        try(PreparedStatement ps = conn.prepareStatement(query)){

            if(quantitaAggiornata < 0){
                throw new IllegalArgumentException("Quantità non può essere negativo");
            }

            ps.setInt(1, quantitaAggiornata);
            ps.setInt(2, idProdotto);

            int result = ps.executeUpdate();
            if(result > 0){
                System.out.println("Quantita aggiornata correttamente.");
            }
            else{
                throw new SQLException("Errore durante l'aggiornamento quantita prodotto");
            }
        }catch (SQLException e){
            throw new SQLException("Errore durante l'aggiornamento quantita prodotto ",e);
        }
    }

    public void caricaProdotto(int idProdotto, int quantita, Connection conn) throws SQLException{
        String query = "update prodotti set quantita = quantita + ? where id = ?";
        try(PreparedStatement ps = conn.prepareStatement(query)){

            if(quantita < 0){
                throw new IllegalArgumentException("Quantità non può essere negativo");
            }
            ps.setInt(1, quantita);
            ps.setInt(2, idProdotto);

            int result = ps.executeUpdate();
            if(result > 0){
                System.out.println("Quantita aggiornata correttamente.");
            }
            else{
                throw new SQLException("Errore durante l'aggiornamento quantita prodotto");
            }
        }catch (SQLException e){
            throw new SQLException("Errore durante l'aggiornamento quantita prodotto ",e);
        }
    }
}
