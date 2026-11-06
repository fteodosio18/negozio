import dao.ProdottoDAO;
import database.DatabaseManager;
import model.Cliente;
import model.Prodotto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProdottoDAOTest {

    DatabaseManager db;
    ProdottoDAO pDAO;
    Connection conn;

    @BeforeEach
    public void setup() throws SQLException {
        db = new DatabaseManager();
        pDAO = new ProdottoDAO();
        conn = db.getConnection("negozio");
        conn.setAutoCommit(false);
    }

    @Test
    public void findByIdNoConnTrovato(){
        Prodotto cTrovato = pDAO.findById(1);
        Assertions.assertNotNull(cTrovato);
    }

    @Test
    public void findByIdNoConnNonTrovato(){
        Prodotto cTrovato = pDAO.findById(1000);
        Assertions.assertNull(cTrovato);
    }

    @Test
    public void findByIdTrovato(){
        Prodotto cTrovato = pDAO.findById(1,conn);
        Assertions.assertNotNull(cTrovato);
    }

    @Test
    public void findByIdNonTrovato(){
        Prodotto cTrovato = pDAO.findById(1000,conn);
        Assertions.assertNull(cTrovato);
    }

    @Test
    public void findAllTest(){
        List<Prodotto> pTrovato =  pDAO.findAll();
        Assertions.assertEquals(9,pTrovato.size(),"Mi aspetto ci siano 9 prodotti");
    }

    @Test
    public void insertRiuscito(){
        Prodotto prodotto = new Prodotto("Prova","prova",543,3);
        Assertions.assertDoesNotThrow(()->pDAO.insert(prodotto,conn));
    }

    @Test
    public void insertNonRiuscito(){
        Prodotto prodotto = new Prodotto("Prova","prova",-20,0);
        Assertions.assertThrows(IllegalArgumentException.class, ()->pDAO.insert(prodotto,conn));
    }

    @Test
    public void insertNonRiuscitoQuantitaNeg(){
        Prodotto prodotto = new Prodotto("Prova","prova",20,-10);
        Assertions.assertThrows(IllegalArgumentException.class, ()->pDAO.insert(prodotto,conn));
    }

    @Test
    public void aggiornaQuantitaRiuscito(){
        Assertions.assertDoesNotThrow(() -> pDAO.aggiornaQuantita(1,20,conn));
    }

    @Test
    public void aggiornaQuantitaNonRiuscito(){
        Assertions.assertThrows(IllegalArgumentException.class,()->pDAO.aggiornaQuantita(1000,-50,conn));
    }

    @Test
    public void caricaProdottoRiuscito(){
        Assertions.assertDoesNotThrow(() -> pDAO.caricaProdotto(1,20,conn));
    }

    @Test
    public void caricaProdottoNonRiuscito(){
        Assertions.assertThrows(IllegalArgumentException.class,()->pDAO.caricaProdotto(1000,-50,conn));
    }

    @AfterEach
    public void tearDown() throws SQLException {
        conn.rollback();
        conn.close();
    }
}
