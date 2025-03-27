package com.biblioteca.observer;

import com.biblioteca.model.Emprestimo;
import org.springframework.stereotype.Component;

@Component
public class HistoricoObserver implements EmprestimoObserver {
    
    @Override
    public void onEmprestimoRealizado(Emprestimo emprestimo) {
        System.out.println("Histórico: Registrando empréstimo ID " + emprestimo.getId());
    }
    
    @Override
    public void onLivroDevolvido(Emprestimo emprestimo) {
        System.out.println("Histórico: Registrando devolução do empréstimo ID " + emprestimo.getId());
    }
}