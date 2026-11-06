package dao;

import model.Azienda;

import java.sql.Connection;
import java.util.ArrayList;

public class AziendaDAO implements BaseDAO <Azienda, Integer>{

    @Override
    public Azienda findById(Integer integer) {
        return null;
    }

    @Override
    public Azienda findById(Integer integer, Connection conn) {
        return null;
    }

    @Override
    public ArrayList<Azienda> findAll() {
        return null;
    }

    @Override
    public void insert(Azienda entity, Connection conn) {

    }
}
