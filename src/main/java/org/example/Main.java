package org.example;


import dao.ClienteDAO;
import dao.OrdineDAO;
import dao.ProdottoDAO;
import database.DatabaseManager;
import database.DatabaseService;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        DatabaseManager db = new DatabaseManager();
        ClienteDAO cDAO = new  ClienteDAO();
        ProdottoDAO pDAO = new  ProdottoDAO();
        OrdineDAO oDAO = new  OrdineDAO();
        DatabaseService ds = new DatabaseService();

        try(Connection conn = db.getConnection("negozio")) {
            //cDAO.aggiornaSaldoCliente(1, 150, conn);
            //pDAO.aggiornaQuantita(1,20,conn);
            //pDAO.caricaProdotto(1,10,conn);
            //cDAO.aggiungiSaldo(1,100,conn);

            //ds.acquistaProdotto(1,2,10);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
