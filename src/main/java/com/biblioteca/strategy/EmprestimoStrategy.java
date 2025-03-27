package com.biblioteca.strategy;

import com.biblioteca.model.Emprestimo;
import com.biblioteca.exception.*;

public interface EmprestimoStrategy {
    Emprestimo realizarEmprestimo(Long livroId, Long usuarioId) 
        throws LivroNotFoundException, UsuarioNotFoundException, LivroIndisponivelException;
}