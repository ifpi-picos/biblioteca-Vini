package com.biblioteca.observer;

import com.biblioteca.model.Emprestimo;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoObserver implements EmprestimoObserver {
    
    @Override
    public void onEmprestimoRealizado(Emprestimo emprestimo) {
        System.out.println("Notificação: Empréstimo realizado - Livro: " + 
                emprestimo.getLivro().getTitulo() + 
                ", Usuário: " + emprestimo.getUsuario().getNome());
    }
    
    @Override
    public void onLivroDevolvido(Emprestimo emprestimo) {
        System.out.println("Notificação: Livro devolvido - Livro: " + 
                emprestimo.getLivro().getTitulo() + 
                ", Usuário: " + emprestimo.getUsuario().getNome());
    }
}