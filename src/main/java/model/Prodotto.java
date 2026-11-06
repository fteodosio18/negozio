package model;

public class Prodotto {
    private int id;
    private String nome;
    private String categoria;
    private double prezzo;
    private int quantita;

    public Prodotto(){}
    public Prodotto(String nome, String categoria, double prezzo, int quantita) {
        this.nome = nome;
        this.categoria = categoria;
        this.prezzo = prezzo;
        this.quantita = quantita;
    }

    public Prodotto(int id, String nome, String categoria, double prezzo, int quantita) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.prezzo = prezzo;
        this.quantita = quantita;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
}
