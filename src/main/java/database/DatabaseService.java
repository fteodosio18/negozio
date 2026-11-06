package database;

import dao.ClienteDAO;
import dao.OrdineDAO;
import dao.ProdottoDAO;
import model.Cliente;
import model.Ordine;
import model.Prodotto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class DatabaseService {
    DatabaseManager db = new DatabaseManager();
    ClienteDAO cDAO = new  ClienteDAO();
    ProdottoDAO pDAO = new  ProdottoDAO();
    OrdineDAO oDAO = new  OrdineDAO();

    public void acquistaProdotto(int idCliente, int idProdotto, int quantita){
        try(Connection conn = db.getConnection("negozio")){
            conn.setAutoCommit(false);
            try{
                //cerco cliente e prodotto
                Cliente c = cDAO.findById(idCliente, conn);
                Prodotto p = pDAO.findById(idProdotto, conn);

                if(c == null || p == null){ //controllo che ci siano un cliente con quell'id e un prodotto con quell'id
                    throw new SQLException("Errore durante la ricerca di prodotto/cliente");
                }else{
                    if(c.getSaldo() < (p.getPrezzo() * quantita)){ //controllo se il saldo è sufficiente
                        throw new SQLException("Saldo non sufficiente");
                    }
                    if(p.getQuantita() < quantita){ //controllo se la quantita richiesta è disponibile per il prodotto
                        throw new SQLException("Il prodotto " + p.getNome() + " ha quantita " + p.getQuantita() + " disponibile, non "+
                                quantita);
                    }

                    Ordine ordine = new Ordine(c,p,quantita, Date.valueOf(LocalDate.now()));
                    int result = oDAO.insert2(ordine, conn); //inserisco il cliente
                    if(result == 0){
                        throw new SQLException("Errore durante l'inserimento dell'ordine");
                    } else{ //l'ordine va a buon fine quindi posso modificare saldo CLIENTE e quantita PRODOTTO
                        double saldoAggiornato = (c.getSaldo() - p.getPrezzo() * quantita);
                        cDAO.aggiornaSaldoCliente(idCliente, saldoAggiornato, conn); // aggiorno il saldo del cliente
                        pDAO.aggiornaQuantita(idProdotto,(p.getQuantita() - quantita), conn); //aggiorno la quantita del prodotto
                        conn.commit(); //rendo effettive le modifiche fatte alle quantita e al saldo
                    }
                }
            }catch (SQLException e){ //l'ordine non va a buon fine, non viene modificato nulla tramite il rollback
                conn.rollback();
                e.printStackTrace();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
