package com.biblioteca.service;

import com.biblioteca.dto.LivroDTO;
import com.biblioteca.exception.LivroNotFoundException;
import com.biblioteca.model.Livro;
import com.biblioteca.repository.LivroRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroService {
    
    private final LivroRepository livroRepository;
    private final ModelMapper modelMapper;
    
    @Autowired
    public LivroService(LivroRepository livroRepository, ModelMapper modelMapper) {
        this.livroRepository = livroRepository;
        this.modelMapper = modelMapper;
    }
    
    public List<LivroDTO> listarTodos() {
        return livroRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public LivroDTO buscarPorId(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new LivroNotFoundException(id));
        return convertToDto(livro);
    }
    
    public LivroDTO cadastrar(LivroDTO livroDTO) {
        Livro livro = convertToEntity(livroDTO);
        Livro livroSalvo = livroRepository.save(livro);
        return convertToDto(livroSalvo);
    }
    
    public LivroDTO atualizar(Long id, LivroDTO livroDTO) {
        Livro livroExistente = livroRepository.findById(id)
                .orElseThrow(() -> new LivroNotFoundException(id));
        
        modelMapper.map(livroDTO, livroExistente);
        Livro livroAtualizado = livroRepository.save(livroExistente);
        return convertToDto(livroAtualizado);
    }
    
    public void deletar(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new LivroNotFoundException(id);
        }
        livroRepository.deleteById(id);
    }
    
    public List<LivroDTO> buscarPorTitulo(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<LivroDTO> buscarPorAutor(String autor) {
        return livroRepository.findByAutorContainingIgnoreCase(autor).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<LivroDTO> buscarDisponiveis() {
        return livroRepository.findByDisponivel(true).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    private LivroDTO convertToDto(Livro livro) {
        return modelMapper.map(livro, LivroDTO.class);
    }
    
    private Livro convertToEntity(LivroDTO livroDTO) {
        return modelMapper.map(livroDTO, Livro.class);
    }
}