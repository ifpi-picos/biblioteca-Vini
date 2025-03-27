package com.biblioteca.service;

import com.biblioteca.dto.EmprestimoDTO;
import com.biblioteca.exception.*;
import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Livro;
import com.biblioteca.model.Usuario;
import com.biblioteca.observer.EmprestimoObserver;
import com.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.repository.LivroRepository;
import com.biblioteca.repository.UsuarioRepository;
import com.biblioteca.strategy.EmprestimoStrategy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmprestimoService {
    
    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;
    private EmprestimoStrategy emprestimoStrategy;
    private List<EmprestimoObserver> observers;
    
    @Autowired
    public EmprestimoService(EmprestimoRepository emprestimoRepository,
                           LivroRepository livroRepository,
                           UsuarioRepository usuarioRepository,
                           ModelMapper modelMapper,
                           List<EmprestimoObserver> observers) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
        this.observers = observers;
    }
    
    public void setEmprestimoStrategy(EmprestimoStrategy strategy) {
        this.emprestimoStrategy = strategy;
    }
    
    public EmprestimoDTO realizarEmprestimo(Long livroId, Long usuarioId) {
        if (emprestimoStrategy == null) {
            throw new EmprestimoStrategyNotSetException();
        }
        
        Emprestimo emprestimo = emprestimoStrategy.realizarEmprestimo(livroId, usuarioId);
        Emprestimo emprestimoSalvo = emprestimoRepository.save(emprestimo);
        
        // Notificar observadores
        observers.forEach(observer -> observer.onEmprestimoRealizado(emprestimoSalvo));
        
        return convertToDto(emprestimoSalvo);
    }
    
    public EmprestimoDTO devolverLivro(Long emprestimoId) {
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new EmprestimoNotFoundException(emprestimoId));
        
        if (!emprestimo.isAtivo()) {
            throw new EmprestimoJaDevolvidoException(emprestimoId);
        }
        
        emprestimo.setAtivo(false);
        emprestimo.setDataDevolucao(LocalDate.now());
        
        Livro livro = emprestimo.getLivro();
        livro.setDisponivel(true);
        livroRepository.save(livro);
        
        Emprestimo emprestimoAtualizado = emprestimoRepository.save(emprestimo);
        
        // Notificar observadores
        observers.forEach(observer -> observer.onLivroDevolvido(emprestimoAtualizado));
        
        return convertToDto(emprestimoAtualizado);
    }
    
    public List<EmprestimoDTO> listarTodos() {
        return emprestimoRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<EmprestimoDTO> listarAtivos() {
        return emprestimoRepository.findByAtivo(true).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<EmprestimoDTO> listarPorUsuario(Long usuarioId) {
        return emprestimoRepository.findByUsuarioId(usuarioId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    private EmprestimoDTO convertToDto(Emprestimo emprestimo) {
        return modelMapper.map(emprestimo, EmprestimoDTO.class);
    }
}