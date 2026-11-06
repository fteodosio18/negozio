import dao.ClienteDAO;
import database.DatabaseManager;
import model.Cliente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class ClienteDAOTest {
    DatabaseManager db;
    ClienteDAO cDAO;
    Connection conn;

    @BeforeEach
    public void setup() throws SQLException {
        db = new DatabaseManager();
        cDAO = new ClienteDAO();
        conn = db.getConnection("negozio");
        conn.setAutoCommit(false);
    }

    @Test
    public void findByIdNoConnTrovato(){
        Cliente cTrovato = cDAO.findById(1);
        Assertions.assertNotNull(cTrovato);
    }

    @Test
    public void findByIdNoConnNonTrovato(){
        Cliente cTrovato = cDAO.findById(1000);
        Assertions.assertNull(cTrovato);
    }

    @Test
    public void findByIdTrovato(){
        Cliente cTrovato = cDAO.findById(1, conn);
        Assertions.assertNotNull(cTrovato);
    }

    @Test
    public void findByIdNonTrovato(){
        Cliente cTrovato = cDAO.findById(1000, conn);
        Assertions.assertNull(cTrovato);
    }

    @Test
    public void findAllTest(){
        List<Cliente> cTrovato =  cDAO.findAll();
        Assertions.assertEquals(8,cTrovato.size(),"Mi aspetto ci siano 8 clienti");
    }

    @Test
    public void insertRiuscito() {
        Cliente cliente = new Cliente("Francesco", "Posa", "fraposa@gmail.com", "Bologna", 50);
        Assertions.assertDoesNotThrow(() -> cDAO.insert(cliente,conn));
    }

        @Test
    public void insertNonRiuscito(){
        Cliente cliente = new Cliente("Francesco","Posa","fraposa@gmail.com","Bologna",-50);
        Assertions.assertThrows(IllegalArgumentException.class, ()->cDAO.insert(cliente,conn));
    }

    @Test
    public void aggiornaSaldoClienteRiuscito(){
        Assertions.assertDoesNotThrow(()->cDAO.aggiornaSaldoCliente(1,100,conn));
    }

    @Test
    public void aggiornaSaldoClienteNonRiuscito(){
        Assertions.assertThrows(SQLException.class,()->cDAO.aggiornaSaldoCliente(1000,100,conn));
    }

    @Test
    public void aggiungiSaldoRiuscito(){
        Assertions.assertDoesNotThrow(()->cDAO.aggiungiSaldo(1,100,conn));
    }

    @Test
    public void aggiungiSaldoNonRiuscito(){
        Assertions.assertThrows(SQLException.class,()->cDAO.aggiungiSaldo(1000,100,conn));
    }



    @AfterEach
    public void tearDown() throws SQLException {
        conn.rollback();
        conn.close();
    }
}
