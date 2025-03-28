package main.java.com.biblioteca.dao;

import main.java.com.biblioteca.model.Emprestimo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {
    public void inserir(Emprestimo emprestimo) throws SQLException {
        String sql = "INSERT INTO emprestimos (usuario_id, livro_id, data_emprestimo, data_devolucao) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, emprestimo.getUsuarioId());
            stmt.setInt(2, emprestimo.getLivroId());
            stmt.setString(3, emprestimo.getDataEmprestimo());
            stmt.setString(4, emprestimo.getDataDevolucao());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    emprestimo.setId(rs.getInt(1));
                }
            }
        }
    }

    public void registrarDevolucao(int id) throws SQLException {
        String sql = "UPDATE emprestimos SET devolvido = true WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Emprestimo> listarTodos() throws SQLException {
        String sql = "SELECT * FROM emprestimos";
        List<Emprestimo> emprestimos = new ArrayList<>();
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                emprestimos.add(new Emprestimo(
                    rs.getInt("id"),
                    rs.getInt("usuario_id"),
                    rs.getInt("livro_id"),
                    rs.getString("data_emprestimo"),
                    rs.getString("data_devolucao"),
                    rs.getBoolean("devolvido")
                ));
            }
        }
        return emprestimos;
    }
}