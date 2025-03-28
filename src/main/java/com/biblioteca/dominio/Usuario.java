package main.java.com.biblioteca.dominio;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String tipoNotificacao; // "email" ou "sms"

    public Usuario(int id, String nome, String email, String telefone, String tipoNotificacao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.tipoNotificacao = tipoNotificacao;
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public String getTipoNotificacao() { return tipoNotificacao; }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format(
            "Usuário [ID: %d, Nome: %s, Email: %s, Telefone: %s, Notificação: %s]",
            id, nome, email, telefone, tipoNotificacao
        );
    }
}