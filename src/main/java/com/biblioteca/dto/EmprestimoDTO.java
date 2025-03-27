package com.biblioteca.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmprestimoDTO {
    private Long id;
    
    @NotNull(message = "ID do livro é obrigatório")
    private Long livroId;
    
    @NotNull(message = "ID do usuário é obrigatório")
    private Long usuarioId;
    
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private Boolean ativo;
}