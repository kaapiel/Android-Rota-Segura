package br.com.fatec.tcc.rotasegura.model;

/**
 * Created by Inmetrics on 23/08/2016.
 */
public class Usuario {

    private String nome;
    private String email;
    private String senha;
    private String numero;

    public Usuario() {
    }

    public Usuario(String nome, String email, String senha, String numero) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
