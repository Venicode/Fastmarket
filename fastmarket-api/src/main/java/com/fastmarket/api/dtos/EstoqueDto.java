package com.fastmarket.api.dtos;

import com.fastmarket.api.domains.Produto;
import jakarta.validation.constraints.NotNull;

public record EstoqueDto(
        @NotNull
        Produto produto,
        int quantidade
) {
}
