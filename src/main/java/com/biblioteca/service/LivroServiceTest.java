package com.biblioteca.service;

import com.biblioteca.dto.LivroDTO;
import com.biblioteca.exception.LivroNotFoundException;
import com.biblioteca.model.Livro;
import com.biblioteca.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LivroServiceTest {
    
    @Mock
    private LivroRepository livroRepository;
    
    @Mock
    private ModelMapper modelMapper;
    
    @InjectMocks
    private LivroService livroService;
    
    private Livro livro;
    private LivroDTO livroDTO;
    
    @BeforeEach
    void setUp() {
        livro = new Livro("Dom Casmurro", "Machado de Assis", "9788535931245");
        livro.setId(1L);
        
        livroDTO = new LivroDTO();
        livroDTO.setTitulo("Dom Casmurro");
        livroDTO.setAutor("Machado de Assis");
        livroDTO.setIsbn("9788535931245");
        livroDTO.setDisponivel(true);
    }
    
    @Test
    void whenCadastrarLivro_thenReturnLivroDTO() {
        when(modelMapper.map(livroDTO, Livro.class)).thenReturn(livro);
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);
        when(modelMapper.map(livro, LivroDTO.class)).thenReturn(livroDTO);
        
        LivroDTO result = livroService.cadastrar(livroDTO);
        
        assertNotNull(result);
        assertEquals(livroDTO.getTitulo(), result.getTitulo());
        verify(livroRepository, times(1)).save(any(Livro.class));
    }
    
    @Test
    void whenBuscarPorId_thenReturnLivroDTO() {
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(modelMapper.map(livro, LivroDTO.class)).thenReturn(livroDTO);
        
        LivroDTO result = livroService.buscarPorId(1L);
        
        assertNotNull(result);
        assertEquals(livroDTO.getTitulo(), result.getTitulo());
    }
    
    @Test
    void whenBuscarPorIdInvalid_thenThrowException() {
        when(livroRepository.findById(99L)).thenReturn(Optional.empty());
        
        assertThrows(LivroNotFoundException.class, () -> livroService.buscarPorId(99L));
    }
}