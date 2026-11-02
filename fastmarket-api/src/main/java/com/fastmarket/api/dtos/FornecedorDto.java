package com.fastmarket.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FornecedorDto(
        @NotNull
        String cnpj,
        @NotBlank
        String razaoSocial,
        @NotBlank
        String nomeFantasia,
        String telefone,
        String email,
         @NotNull
        boolean ativo
) {
}
