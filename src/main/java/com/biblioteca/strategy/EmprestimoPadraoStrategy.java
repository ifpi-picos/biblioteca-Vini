package com.biblioteca.strategy;

import com.biblioteca.exception.*;
import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Livro;
import com.biblioteca.model.Usuario;
import com.biblioteca.repository.LivroRepository;
import com.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EmprestimoPadraoStrategy implements EmprestimoStrategy {
    
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;
    
    @Autowired
    public EmprestimoPadraoStrategy(LivroRepository livroRepository, 
                                   UsuarioRepository usuarioRepository) {
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
    }
    
    @Override
    public Emprestimo realizarEmprestimo(Long livroId, Long usuarioId) 
        throws LivroNotFoundException, UsuarioNotFoundException, LivroIndisponivelException {
        
        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new LivroNotFoundException(livroId));
        
        if (!livro.isDisponivel()) {
            throw new LivroIndisponivelException(livroId);
        }
        
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNotFoundException(usuarioId));
        
        livro.setDisponivel(false);
        livroRepository.save(livro);
        
        Emprestimo emprestimo = new Emprestimo(livro, usuario, LocalDate.now());
        return emprestimo;
    }
}