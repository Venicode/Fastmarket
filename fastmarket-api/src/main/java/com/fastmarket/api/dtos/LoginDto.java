package com.fastmarket.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastmarket.api.domains.Acesso;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

public record LoginDto(
        @NotNull
        int matricula,
        @NotNull
        String nome,
        @NotNull
        String senha,
        @NotNull
        Acesso acesso,
        @NotNull
        boolean ativo
) {
}
