package com.biblioteca.observer;

import com.biblioteca.model.Emprestimo;

public interface EmprestimoObserver {
    void onEmprestimoRealizado(Emprestimo emprestimo);
    void onLivroDevolvido(Emprestimo emprestimo);
}