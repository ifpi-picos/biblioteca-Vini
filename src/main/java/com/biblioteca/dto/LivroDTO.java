package com.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LivroDTO {
    private Long id;
    
    @NotBlank(message = "Título é obrigatório")
    private String titulo;
    
    @NotBlank(message = "Autor é obrigatório")
    private String autor;
    
    @NotBlank(message = "ISBN é obrigatório")
    private String isbn;
    
    @NotNull
    private Boolean disponivel;
    
    public LivroDTO() {
        this.disponivel = true;
    }
}