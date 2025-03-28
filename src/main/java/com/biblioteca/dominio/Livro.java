package main.java.com.biblioteca.dominio;

public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private boolean disponivel;

    // Construtor, getters e setters
    public Livro(int id, String titulo, String autor, int anoPublicacao, boolean disponivel) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.disponivel = disponivel;
    }

    // Getters e Setters
    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getAnoPublicacao() { return anoPublicacao; }
    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format(
            "Livro [ID: %d, Título: %s, Autor: %s, Ano: %d, %s]",
            id, titulo, autor, anoPublicacao, disponivel ? "Disponível" : "Indisponível"
        );
    }
}
