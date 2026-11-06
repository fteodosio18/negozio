package dao;

import database.DatabaseManager;
import model.Cliente;
import model.Ordine;
import model.Prodotto;

import java.sql.*;
import java.util.ArrayList;

public class OrdineDAO implements BaseDAO<Ordine, Integer> {
    DatabaseManager db = new DatabaseManager();
    ClienteDAO cDAO = new  ClienteDAO();
    ProdottoDAO pDAO = new  ProdottoDAO();

    @Override
    public Ordine findById(Integer id) {
        Connection conn = db.getConnection("negozio");

        String query = "SELECT * FROM ordini WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            Ordine o = null;

            while (rs.next()) {
                Cliente c = cDAO.findById(rs.getInt("id_cliente"));
                Prodotto p = pDAO.findById(rs.getInt("id_prodotto"));

                System.out.println(rs.getInt("id") + ". " +
                        rs.getInt("id_cliente") + " " +
                        rs.getInt("id_prodotto") + " " +
                        rs.getInt("quantita") + " " +
                        rs.getDate("data_ordine"));
                o = new Ordine(id, c, p, rs.getInt("quantita"), rs.getDate("data_ordine"));
            }
            return o;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Ordine findById(Integer id, Connection conn) {
        String query = "SELECT * FROM ordini WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            Ordine o = null;

            while (rs.next()) {
                Cliente c = cDAO.findById(rs.getInt("id_cliente"));
                Prodotto p = pDAO.findById(rs.getInt("id_prodotto"));

                System.out.println(rs.getInt("id") + ". " +
                        rs.getInt("id_cliente") + " " +
                        rs.getInt("id_prodotto") + " " +
                        rs.getInt("quantita") + " " +
                        rs.getDate("data_ordine"));
                o = new Ordine(id, c, p, rs.getInt("quantita"), rs.getDate("data_ordine"));
            }
            return o;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Ordine> findAll() {
        String query = "SELECT * FROM ordini";
        ArrayList<Ordine> ordini = new ArrayList<>();
        try (Connection conn = db.getConnection("negozio");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Cliente c = cDAO.findById(rs.getInt("id_cliente"));
                Prodotto p = pDAO.findById(rs.getInt("id_prodotto"));

                System.out.println(rs.getInt("id") + ". " +
                        rs.getInt("id_cliente") + " " +
                        rs.getInt("id_prodotto") + " " +
                        rs.getInt("quantita") + " " +
                        rs.getDate("data_ordine"));

                ordini.add(new Ordine(rs.getInt("id"), c, p, rs.getInt("quantita"),
                        rs.getDate("data_ordine")));
            }
            return ordini;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void insert(Ordine ordine, Connection conn) {
        String query = "insert into ordini (id_cliente, id_prodotto, quantita, data_ordine) values (?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {

            Cliente c = ordine.getCliente();
            Prodotto p =  ordine.getProdotto();
            ps.setInt(1, c.getId());
            ps.setInt(2, p.getId());
            ps.setInt(3, ordine.getQuantita());
            ps.setDate(4, ordine.getData_ordine());

            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Cliente inserito correttamente.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int insert2(Ordine ordine, Connection conn){
        String query = "insert into ordini (id_cliente, id_prodotto, quantita, data_ordine) values (?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            Cliente c = ordine.getCliente();
            Prodotto p =  ordine.getProdotto();
            ps.setInt(1, c.getId());
            ps.setInt(2, p.getId());
            ps.setInt(3, ordine.getQuantita());
            ps.setDate(4, ordine.getData_ordine());

            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Cliente inserito correttamente.");
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
