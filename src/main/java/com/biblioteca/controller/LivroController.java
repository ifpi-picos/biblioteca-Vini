package main.java.com.biblioteca.controller;

import main.java.com.biblioteca.dao.LivroDAO;
import main.java.com.biblioteca.dominio.Livro;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class LivroController {
    private final LivroDAO livroDAO;
    private final Scanner scanner;

    public LivroController() {
        this.livroDAO = new LivroDAO();
        this.scanner = new Scanner(System.in);
    }

    public void cadastrarLivro() throws SQLException {
        System.out.println("\n--- CADASTRAR LIVRO ---");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        System.out.print("Ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine();

        Livro livro = new Livro(0, titulo, autor, ano, true);
        livroDAO.inserir(livro);
        System.out.println("Livro cadastrado!");
    }

    public void buscarLivroPorId() throws SQLException {
        System.out.print("\nID do Livro: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        Livro livro = livroDAO.buscarPorId(id);
        System.out.println(livro != null ? livro : "Livro não encontrado!");
    }

    public void listarLivros() throws SQLException {
        System.out.println("\n--- TODOS OS LIVROS ---");
        livroDAO.listarTodos().forEach(System.out::println);
    }

    public void buscarPorTitulo() throws SQLException {
        System.out.print("\nTítulo (ou parte): ");
        String titulo = scanner.nextLine();
        
        List<Livro> livros = livroDAO.buscarPorTitulo(titulo);
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado!");
        } else {
            livros.forEach(System.out::println);
        }
    }

    public void buscarPorId() throws SQLException {
        System.out.print("\nDigite o ID do livro: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Livro livro = livroDAO.buscarPorId(id);
        if (livro != null) {
            System.out.println("Livro encontrado:");
            System.out.println(livro);
        } else {
            System.out.println("Livro não encontrado!");
        }
    }
}