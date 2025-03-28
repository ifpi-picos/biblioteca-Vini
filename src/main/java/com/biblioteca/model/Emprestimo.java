package main.java.com.biblioteca.model;


public class Emprestimo {
    private int id;
    private int usuarioId;
    private int livroId;
    private String dataEmprestimo;
    private String dataDevolucao;
    private boolean devolvido;

    public Emprestimo(int id, int usuarioId, int livroId, String dataEmprestimo, String dataDevolucao, boolean devolvido) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.livroId = livroId;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.devolvido = devolvido;
    }

    // Getters e Setters
    public int getId() { return id; }
    public int getUsuarioId() { return usuarioId; }
    public int getLivroId() { return livroId; }
    public String getDataEmprestimo() { return dataEmprestimo; }
    public String getDataDevolucao() { return dataDevolucao; }
    public boolean isDevolvido() { return devolvido; }
    public void setDevolvido(boolean devolvido) { this.devolvido = devolvido; }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format(
            "Empréstimo [ID: %d, Usuário: %d, Livro: %d, Empréstimo: %s, Devolução: %s, %s]",
            id, usuarioId, livroId, dataEmprestimo, dataDevolucao, devolvido ? "Devolvido" : "Pendente"
        );
    }
}
