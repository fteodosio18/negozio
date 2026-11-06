package model;

public class Cliente {
    private int id;
    private String nome;
    private String cognome;
    private String email;
    private String citta_residenza;
    private double saldo;

    public Cliente(){}
    public Cliente(String nome, String cognome, String email, String citta_residenza, double saldo){
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.citta_residenza = citta_residenza;
        this.saldo = saldo;
    }

    public Cliente(int id, String nome, String cognome, String email, String citta_residenza, double saldo){
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.citta_residenza = citta_residenza;
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCitta_residenza() {
        return citta_residenza;
    }

    public void setCitta_residenza(String citta_residenza) {
        this.citta_residenza = citta_residenza;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
