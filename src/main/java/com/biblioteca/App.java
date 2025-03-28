package main.java.com.biblioteca;
import main.java.com.biblioteca.controller.EmprestimoController;
import main.java.com.biblioteca.controller.LivroController;
import main.java.com.biblioteca.controller.UsuarioController;
import org.postgresql.Driver;
import java.sql.SQLException;
import java.util.Scanner;

public class App {
    private final LivroController livroController;
    private final UsuarioController usuarioController;
    private final EmprestimoController emprestimoController;
    private final Scanner scanner;

    public App() {
        this.livroController = new LivroController();
        this.usuarioController = new UsuarioController();
        this.emprestimoController = new EmprestimoController();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("=== SISTEMA DE BIBLIOTECA ===");

        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = lerOpcao();

            try {
                switch (opcao) {
                    case 1 -> gerenciarLivros();
                    case 2 -> gerenciarUsuarios();
                    case 3 -> gerenciarEmprestimos();
                    case 0 -> System.out.println("Encerrando sistema...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.err.println("Erro no banco de dados: " + e.getMessage());
            }
        } while (opcao != 0);

        scanner.close();
    }

    private void exibirMenuPrincipal() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Gerenciar Livros");
        System.out.println("2. Gerenciar Usuários");
        System.out.println("3. Gerenciar Empréstimos");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void gerenciarLivros() throws SQLException {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAR LIVROS ===");
            System.out.println("1. Cadastrar Livro");
            System.out.println("2. Listar Todos");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Buscar por Título");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            opcao = lerOpcao();

            switch (opcao) {
                case 1 -> livroController.cadastrarLivro();
                case 2 -> livroController.listarLivros();
                case 3 -> livroController.buscarPorId();
                case 4 -> livroController.buscarPorTitulo();
                case 0 -> {}
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void gerenciarUsuarios() throws SQLException {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAR USUÁRIOS ===");
            System.out.println("1. Cadastrar Usuário");
            System.out.println("2. Listar Todos");
            System.out.println("3. Buscar por ID");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            opcao = lerOpcao();

            switch (opcao) {
                case 1 -> usuarioController.cadastrarUsuario();
                case 2 -> usuarioController.listarUsuarios();
                case 3 -> usuarioController.buscarPorId();
                case 0 -> {}
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void gerenciarEmprestimos() throws SQLException {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAR EMPRÉSTIMOS ===");
            System.out.println("1. Realizar Empréstimo");
            System.out.println("2. Listar Todos");
            System.out.println("3. Registrar Devolução");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            opcao = lerOpcao();

            switch (opcao) {
                case 1 -> emprestimoController.realizarEmprestimo();
                case 2 -> emprestimoController.listarEmprestimos();
                case 3 -> emprestimoController.registrarDevolucao();
                case 0 -> {}
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    public static void main(String[] args) {
        new App().iniciar();
    }
}