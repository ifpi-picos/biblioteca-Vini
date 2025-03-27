package com.biblioteca.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Livro livro;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario usuario;
    
    @Column(nullable = false)
    private LocalDate dataEmprestimo;
    
    private LocalDate dataDevolucao;
    
    @Column(nullable = false)
    private boolean ativo = true;
    
    public Emprestimo(Livro livro, Usuario usuario, LocalDate dataEmprestimo) {
        this.livro = livro;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
    }
}