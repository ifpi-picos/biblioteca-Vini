package main.java.com.biblioteca.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    // Configurações de conexão com o PostgreSQL
    private static final String URL = "jdbc:postgresql://localhost:5432/biblioteca";
    private static final String USER = "postgres"; // usuário padrão do PostgreSQL
    private static final String PASSWORD = "vinibol";

    public static Connection getConnection() {
        try {
            // Registra o driver JDBC do PostgreSQL
            Class.forName("org.postgresql.Driver");
            
            // Cria e retorna a conexão
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC do PostgreSQL não encontrado", e);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco de dados PostgreSQL", e);
        }
    }

    // Método para testar a conexão (opcional)
    public static void testarConexao() {
        try (Connection conn = getConnection()) {
            System.out.println("Conexão com PostgreSQL estabelecida com sucesso!");
        } catch (SQLException e) {
            System.out.println("Falha ao conectar com PostgreSQL:");
            e.printStackTrace();
        }
    }
}