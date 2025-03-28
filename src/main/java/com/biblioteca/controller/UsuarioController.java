package main.java.com.biblioteca.controller;

import main.java.com.biblioteca.dao.UsuarioDAO;
import main.java.com.biblioteca.dominio.Usuario;
import main.java.com.biblioteca.dominio.notificacao.NotificacaoEmail;
import main.java.com.biblioteca.dominio.notificacao.NotificacaoSMS;
import main.java.com.biblioteca.dominio.notificacao.Notificador;

import java.sql.SQLException;
import java.util.Scanner;

public class UsuarioController {
    private final UsuarioDAO usuarioDAO;
    private final Notificador notificador;
    private final Scanner scanner;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
        this.notificador = new Notificador();
        this.scanner = new Scanner(System.in);
    }

    public void cadastrarUsuario() throws SQLException {
        System.out.println("\n--- CADASTRAR USUÁRIO ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Tipo de notificação (email/sms): ");
        String tipoNotificacao = scanner.nextLine();

        Usuario usuario = new Usuario(0, nome, email, telefone, tipoNotificacao);
        usuarioDAO.inserir(usuario);

        String mensagem = "Cadastro realizado na biblioteca!";
        if ("sms".equals(tipoNotificacao)) {
            notificador.setEstrategia(new NotificacaoSMS());
            notificador.enviarNotificacao(telefone, mensagem);
        } else {
            notificador.setEstrategia(new NotificacaoEmail());
            notificador.enviarNotificacao(email, mensagem);
        }

        System.out.println("Usuário cadastrado com sucesso!");
    }

    public void listarUsuarios() throws SQLException {
        System.out.println("\n--- LISTA DE USUÁRIOS ---");
        usuarioDAO.listarTodos().forEach(System.out::println);
    }

    public void buscarPorId() throws SQLException {
        System.out.print("\nID do usuário: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Usuario usuario = usuarioDAO.buscarPorId(id);
        System.out.println(usuario != null ? usuario : "Usuário não encontrado!");
    }
}