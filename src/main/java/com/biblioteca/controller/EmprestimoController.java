package main.java.com.biblioteca.controller;

import main.java.com.biblioteca.dao.EmprestimoDAO;
import main.java.com.biblioteca.model.Emprestimo;
import main.java.com.biblioteca.dao.LivroDAO;
import main.java.com.biblioteca.dao.UsuarioDAO;
import main.java.com.biblioteca.dominio.Usuario;
import main.java.com.biblioteca.dominio.notificacao.NotificacaoEmail;
import main.java.com.biblioteca.dominio.notificacao.NotificacaoSMS;
import main.java.com.biblioteca.dominio.notificacao.Notificador;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class EmprestimoController {
    private final EmprestimoDAO emprestimoDAO;
    private final LivroDAO livroDAO;
    private final UsuarioDAO usuarioDAO;
    private final Notificador notificador;
    private final Scanner scanner;

    public EmprestimoController() {
        this.emprestimoDAO = new EmprestimoDAO();
        this.livroDAO = new LivroDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.notificador = new Notificador();
        this.scanner = new Scanner(System.in);
    }

    public void realizarEmprestimo() throws SQLException {
        System.out.println("\n--- NOVO EMPRÉSTIMO ---");
        System.out.print("ID do usuário: ");
        int usuarioId = Integer.parseInt(scanner.nextLine());
        System.out.print("ID do livro: ");
        int livroId = Integer.parseInt(scanner.nextLine());

        Usuario usuario = usuarioDAO.buscarPorId(usuarioId);
        if (usuario == null) {
            System.out.println("Usuário não encontrado!");
            return;
        }

        String dataEmprestimo = LocalDate.now().toString();
        String dataDevolucao = LocalDate.now().plusDays(14).toString(); // 2 semanas

        Emprestimo emprestimo = new Emprestimo(0, usuarioId, livroId, dataEmprestimo, dataDevolucao, false);
        emprestimoDAO.inserir(emprestimo);

        String mensagem = String.format(
            "Empréstimo realizado! Livro ID: %d. Data de devolução: %s",
            livroId, dataDevolucao
        );

        if ("sms".equals(usuario.getTipoNotificacao())) {
            notificador.setEstrategia(new NotificacaoSMS());
            notificador.enviarNotificacao(usuario.getTelefone(), mensagem);
        } else {
            notificador.setEstrategia(new NotificacaoEmail());
            notificador.enviarNotificacao(usuario.getEmail(), mensagem);
        }

        System.out.println("Empréstimo registrado com sucesso!");
    }

    public void listarEmprestimos() throws SQLException {
        System.out.println("\n--- TODOS OS EMPRÉSTIMOS ---");
        emprestimoDAO.listarTodos().forEach(System.out::println);
    }

    public void registrarDevolucao() throws SQLException {
        System.out.print("\nID do empréstimo: ");
        int id = Integer.parseInt(scanner.nextLine());
        emprestimoDAO.registrarDevolucao(id);
        System.out.println("Devolução registrada com sucesso!");
    }
}