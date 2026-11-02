package com.fastmarket.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClienteDto(
        @NotNull
        String cpf,
        @NotBlank
        String primeiroNome,
        @NotBlank
        String ultimoNome,
        String telefone,
        String email,
        @NotNull
        boolean ativo
) {
}
